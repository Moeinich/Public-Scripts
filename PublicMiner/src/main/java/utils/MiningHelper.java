package utils;

import helpers.utils.Tile;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static helpers.Interfaces.*;
import static main.PublicMiner.*;
import static main.PublicMiner.locationInfo;
import static tasks.CheckPickaxe.useSpecial;

public class MiningHelper {
    public boolean performMining(LocationInfo locationInfo, VeinColors veinColors) {
        while (!Inventory.isFull() && !Script.isScriptStopping() && !Script.isTimeForBreak() && !Script.isPaused() && isAtStepLocation()) {

            if (!GameTabs.isInventoryTabOpen()) {
                Logger.log("Opening inventory");
                GameTabs.openInventoryTab();
            }

            if (shouldSpecialAttack()) {
                Player.useSpec();
                Condition.wait(() -> Player.getSpec() < 100, 400, 10);
            }

            if (hopEnabled) {
                if (Game.isPlayersUnderUs()) {
                    Logger.log("instant hopping, player is under us");
                    Game.instantHop(hopProfile);
                    continue;
                } else {
                    Game.hop(hopProfile, useWDH, useWDH);
                }
            }

            // Loop through each rectangle in locationInfo's associated directions
            for (DirectionCheckRect direction : locationInfo.getDirections()) {
                Rectangle checkRect = direction.getRectangle(); // Get the rectangle for the current direction
                Logger.debugLog("Checking for veins in direction: " + direction);

                // Get objects from colors within the current rectangle
                List<Rectangle> objects = Client.getObjectsFromColorsInRect(
                        veinColors.getActiveColor(),
                        checkRect,
                        locationInfo.getTolerance()
                );

                if (!objects.isEmpty()) {  // Check if the list is not empty
                    Logger.log("Clicking vein!");
                    clickPositions(checkRect, veinColors);
                    break;
                } else {
                    Condition.sleep(generateRandomDelay(50, 120));
                }
            }
        }
        return true;
    }

    private boolean shouldSpecialAttack() {
        if (useSpecial) {
            if (Player.getSpec() >= 100) {
                return true;
            }
        }
        return false;
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

    private static final Tile alKharidOffTile = new Tile(13611, 12421, 0);
    public static boolean isAtStepLocation() {
        if (Location.equals("Al Kharid East")) {
            return Player.atTile(locationInfo.getStepLocation()) || Player.atTile(alKharidOffTile);
        }
        return Player.atTile(locationInfo.getStepLocation());
    }
}
