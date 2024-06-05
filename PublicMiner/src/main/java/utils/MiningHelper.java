package utils;

import java.awt.*;
import java.util.List;
import java.util.Random;

import static helpers.Interfaces.*;
import static main.PublicMiner.*;

public class MiningHelper {
    private final Random random = new Random();

    public boolean performMining(LocationInfo locationInfo, VeinColors veinColors) {
        while (!Inventory.isFull() && !Script.isScriptStopping()) {
            if (!GameTabs.isInventoryTabOpen()) {
                GameTabs.openInventoryTab();
            }

            if (hopEnabled) {
                if (Game.isPlayersUnderUs()) {
                    Game.instantHop(hopProfile);
                } else {
                    Game.hop(hopProfile, useWDH, false);
                }
            }

            List<Rectangle> objects = Client.getObjectsFromColorsInRect(veinColors.getActiveColor(), locationInfo.getCheckLocation(), locationInfo.getTolerance());

            if (!objects.isEmpty()) {  // Check if the list is not empty
                int randomIndex = random.nextInt(objects.size()); // Generate a random index
                Rectangle randomObject = objects.get(randomIndex);  // Get a random rectangle from the list

                clickPositions(randomObject, veinColors); // Click the random object
            }
        }
        return true;
    }

    private void clickPositions(Rectangle position, VeinColors veinColors) {
        if (isValidRect(position)) {
            Logger.log("Tapping vein");
            Client.tap(position);
            Condition.wait(() -> !Client.isAnyColorInRect(veinColors.getActiveColor(), position, locationInfo.getTolerance()) || shouldHop() || Inventory.isFull(), 50, 145);
            Logger.debugLog("Successfully mined vein");
            XpBar.getXP();
        }
    }

    private boolean isValidRect(Rectangle rect) {
        return !(rect.width == 1 && rect.height == 1 && rect.x == 1 && rect.y == 1);
    }

    private boolean shouldHop() {
        if (hopEnabled) {
            if (!useWDH) {
                return false;
            } else {
                return Game.isPlayersUnderUs() || Game.isPlayersAround();
            }
        }
        return false;
    }
}
