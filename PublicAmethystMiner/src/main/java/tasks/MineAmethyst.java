package tasks;

import helpers.utils.Tile;
import utils.Task;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static helpers.Interfaces.*;
import static main.PublicAmethystMiner.*;

public class MineAmethyst extends Task {
    List<Color> amethystColors =
            Arrays.asList(
                    Color.decode("#b1768b"),
                    Color.decode("#956472"),
                    Color.decode("#8a5d6a")
            );
    Tile location;
    private Tile lastLocation;
    private Instant lastCheckTime = Instant.now();

    @Override
    public boolean activate() {
        location = Walker.getPlayerPosition(); // Cache our position so we only need to check once per loop

        // Check if we should go to mining spot
        if (!Inventory.isFull() && Player.isTileWithinArea(location, bankArea)) {
            Logger.log("Walking to mining spot!");
            Walker.walkPath(toMinePath);
        }

        return Player.isTileWithinArea(location, mineArea);
    }

    @Override
    public boolean execute() {
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

            List<Rectangle> objects = Client.getObjectsFromColor(amethystColors, 4);
            if (!objects.isEmpty()) {
                Rectangle closest = getClosest(objects);

                if (closest != null) {
                    clickPositions(closest);
                }
            }
        }
        return true;
    }

    private void clickPositions(Rectangle initialPosition) {
        Logger.log("Tapping vein");
        Client.tap(initialPosition);

        lastLocation = null; //Reset it
        Condition.wait(() -> !playerIsMoving(), 200, 30);
        Logger.debugLog("We stopped moving, continuing to check vein");

        AtomicReference<String> exitReason = new AtomicReference<>("Timed out while waiting");  // Default exit reason
        List<Rectangle> objects = Client.getObjectsFromColor(amethystColors, 4);
        Rectangle closest = getClosest(objects);

        Condition.wait(() -> {
            if (closest == null) {
                exitReason.set("No more veins detected");
                return true;  // Stop waiting
            }

            if (!Client.isAnyColorInRect(amethystColors, closest, 4)) {
                exitReason.set("Vein appears to be mined");
                return true;  // Stop waiting
            }

            if (Inventory.isFull()) {
                exitReason.set("Inventory full, exiting wait");
                return true; // Stop waiting
            }

            if (shouldHop()) {
                exitReason.set("Need to hop servers");
                return true;  // Stop waiting
            }

            return false;  // Continue waiting
        }, 200, 200);

        Logger.debugLog(exitReason.get());
        XpBar.getXP();
    }


    private Rectangle getClosest(List<Rectangle> objects) {
        int centerX = 447;
        int centerY = 270;

        Rectangle closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Rectangle rect : objects) {
            int rectCenterX = rect.x + rect.width / 2;
            int rectCenterY = rect.y + rect.height / 2;
            double distance = Math.sqrt(Math.pow(rectCenterX - centerX, 2) + Math.pow(rectCenterY - centerY, 2));

            if (distance < minDistance) {
                minDistance = distance;
                closest = rect;
            }
        }
        return closest;
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

    private boolean playerIsMoving() {
        Tile currentLocation = Walker.getPlayerPosition();

        if (lastLocation != null && Player.tileEquals(lastLocation, currentLocation)) {
            long elapsedMillis = Duration.between(lastCheckTime, Instant.now()).toMillis();
            if (elapsedMillis >= 500) { // check if at least 500 ms have passed
                Logger.debugLog("Player has not moved for " + elapsedMillis + " ms");
                return false; // Player is not moving
            }
        } else {
            lastCheckTime = Instant.now();
            lastLocation = currentLocation;
        }
        return true; // Assume movement by default
    }
}
