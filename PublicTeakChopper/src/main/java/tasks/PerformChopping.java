package tasks;

import helpers.utils.Area;
import helpers.utils.Tile;
import utils.Task;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static helpers.Interfaces.*;
import static main.PublicTeakChopper.hopProfile;
import static main.PublicTeakChopper.useWDH;
import static tasks.CheckEquipment.hasAxe;

public class PerformChopping extends Task {
    Rectangle searchRect = new Rectangle(408, 159, 69, 174);
    Tile teakTile = new Tile(8739, 11705, 0);
    Area teakArea = new Area(
            new Tile(8699, 11657, 0),
            new Tile(8810, 11764, 0)
    );
    Tile location;

    List<Color> teakColors =
            Arrays.asList(
                    Color.decode("#7e9453"),
                    Color.decode("#647543"),
                    Color.decode("#93ac5f")
            );

    public boolean activate() {
        location = Walker.getPlayerPosition();
        return Player.isTileWithinArea(location, teakArea) && hasAxe;
    }

    @Override
    public boolean execute() {
        if (useWDH) {
            Game.hop(hopProfile, useWDH, false); // Check if we should worldhop
        }

        if (!GameTabs.isInventoryTabOpen()) {
            GameTabs.openInventoryTab();
            Condition.wait(() -> GameTabs.isInventoryTabOpen(), 100, 10);
        }

        if (!Player.tileEquals(location, teakTile)) {
            Logger.log("Walking to teak tile");
            Walker.step(teakTile);
            Condition.wait(() -> Player.atTile(teakTile), 100, 10);
            return true;
        } else if (Player.tileEquals(location, teakTile)) {
            // Get the rectangle objects for trees found
            List<Rectangle> trees = Client.getObjectsFromColorsInRect(teakColors, searchRect, 5);
            // Loop through each tree rectangle in the list
            for (Rectangle treeRect : trees) {
                Logger.log("Clicking teak tree");
                Client.tap(treeRect); // Perform the tap action on each tree rectangle
                Condition.wait(() -> !Client.isAnyColorInRect(teakColors, treeRect, 5) || Inventory.isFull() || shouldHop() || Player.leveledUp(), 300, 200);
                Logger.debugLog("Finished cutting tree");
                XpBar.getXP();
                return true;
            }
        }
        return false; // Return false if the delay has not yet passed
    }

    private boolean shouldHop() {
        if (useWDH) {
            return Game.isPlayersUnderUs() || Game.isPlayersAround();
        }
        return false;
    }
}
