package mTrawler.Tasks;

import helpers.utils.Area;
import helpers.utils.Tile;
import helpers.utils.UITabs;
import mTrawler.Task;

import java.awt.*;

import static helpers.Interfaces.*;
import static mTrawler.PublicTrawler.*;

public class Minigame extends Task {
    Area minigameArea = new Area(
            new Tile(7764, 19023, 0),
            new Tile(7866, 19079, 0)
    );
    Tile repairTile = new Tile(7803, 19045, 0);
    Rectangle tapRect = new Rectangle(427, 308, 38, 14);
    Rectangle contributionBarRect = new Rectangle(279, 39, 139, 7);
    Color greenContribution = new Color(69, 188, 94);
    public static boolean inPosition = false;
    private final Color waterColor = Color.decode("#4d5970");
    private final Rectangle waterCheckRect = new Rectangle(423, 214, 54, 61);

    @Override
    public boolean activate() {
        return (Player.isTileWithinArea(currentPos, minigameArea));
    }

    @Override
    public boolean execute() {
        GAME_FLAG = 1;

        ensureInventoryTabOpen();

        if (!inPosition) {
            Paint.setStatus("Move to repair tile");
            moveToRepairTile();
        }

        if (inPosition) {
            handleContributionAndWater();
        }

        return true;
    }

    private void ensureInventoryTabOpen() {
        if (!GameTabs.isTabOpen(UITabs.INVENTORY)) {
            GameTabs.openTab(UITabs.INVENTORY);
            Condition.wait(() -> GameTabs.isTabOpen(UITabs.INVENTORY), 100, 10);
        }
    }

    private void moveToRepairTile() {
        currentPos = Walker.getPlayerPosition();

        if (!Player.tileEquals(currentPos, repairTile)) {
            Logger.log("Stepping to spot");
            Walker.step(repairTile);
            Condition.wait(() -> {
                currentPos = Walker.getPlayerPosition(); // Update location after moving
                return Player.tileEquals(currentPos, repairTile);
            }, 200, 10);
        }

        if (Player.tileEquals(currentPos, repairTile)) {
            Logger.debugLog("We are in spot, setting inPosition to true");
            inPosition = true;
        }
    }

    private void handleContributionAndWater() {
        if (Client.isColorInRect(greenContribution, contributionBarRect, 5)) {
            if (Inventory.contains(583, 0.75)) {
                handleBailingWater();
            } else {
                Paint.setStatus("Sleep");
                Logger.log("We have enough contribution, just sleeping");
                sleepRandom(generateRandomDelay(500, 1000));
            }
        } else {
            Paint.setStatus("Repair hole");
            Logger.log("Getting contribution");
            Client.tap(tapRect);
            sleepRandom(generateRandomDelay(250, 500));
        }
    }

    private void handleBailingWater() {
        if (Client.isColorInRect(waterColor, waterCheckRect, 5)) {
            Paint.setStatus("Bail water");
            Logger.log("Bailing water..");
            Inventory.tapItem(583, true, 0.75);
            sleepRandom(generateRandomDelay(200, 700));

        } else {
            Paint.setStatus("Sleep");
            Logger.log("We have enough contribution and no water to bail, just sleeping");
            sleepRandom(generateRandomDelay(500, 1000));
        }
    }

    private void sleepRandom(int delay) {
        Condition.sleep(delay);
    }
}
