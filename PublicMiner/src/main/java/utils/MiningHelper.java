package utils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static helpers.Interfaces.*;
import static main.PublicMiner.*;
import static main.PublicMiner.locationInfo;

public class MiningHelper {
    private final List<Rectangle> checkRects = Arrays.asList(
            new Rectangle(397, 266, 22, 20),
            new Rectangle(440, 221, 27, 23),
            new Rectangle(439, 306, 23, 24),
            new Rectangle(484, 260, 22, 24)
            );

    public boolean performMining(LocationInfo locationInfo, VeinColors veinColors) {
        while (!Inventory.isFull() && !Script.isScriptStopping() && Player.atTile(locationInfo.getStepLocation())) {

            if (!GameTabs.isInventoryTabOpen()) {
                GameTabs.openInventoryTab();
            }

            if (hopEnabled) {
                if (Game.isPlayersUnderUs()) {
                    Game.instantHop(hopProfile);
                    continue;
                } else {
                    Game.hop(hopProfile, useWDH, useWDH);
                }
            }

            // Loop through each rectangle in checkRects
            for (Rectangle checkRect : checkRects) {
                // Get objects from colors within the current rectangle
                List<Rectangle> objects = Client.getObjectsFromColorsInRect(
                        veinColors.getActiveColor(),
                        checkRect,  // Using the current checkRect in the loop
                        locationInfo.getTolerance()
                );

                if (!objects.isEmpty()) {  // Check if the list is not empty
                    clickPositions(checkRect, veinColors);
                    break;
                }
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
