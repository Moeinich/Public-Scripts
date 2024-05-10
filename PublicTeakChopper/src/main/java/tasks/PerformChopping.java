package tasks;

import helpers.utils.RegionBox;
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
    RegionBox TeakRegion = new RegionBox("ISLE_OF_SOULS_TEAKS", 4410, 4389, 4695, 4638);
    Rectangle searchRect = new Rectangle(408, 159, 69, 174);
    Tile teakTile = new Tile(1496, 1495);
    Tile location;

    List<Color> teakColors =
            Arrays.asList(
                    Color.decode("#7e9453"),
                    Color.decode("#647543"),
                    Color.decode("#93ac5f")
            );

    public boolean activate() {
        location = Walker.getPlayerPosition(TeakRegion);
        return Player.isTileWithinRegionbox(location, TeakRegion) && hasAxe;
    }

    @Override
    public boolean execute() {
        if (useWDH) {
            Game.hop(hopProfile, useWDH, true); // Check if we should worldhop
        }

        if (!GameTabs.isInventoryTabOpen()) {
            GameTabs.openInventoryTab();
            Condition.wait(() -> GameTabs.isInventoryTabOpen(), 100, 10);
        }

        if (!Player.tileEquals(location, teakTile)) {
            Logger.log("Walking to teak tile");
            Walker.step(teakTile);
            Condition.wait(() -> Player.atTile(teakTile), 100, 10);
        } else if (Player.tileEquals(location, teakTile)) {
            // Get the rectangle objects for trees found
            List<Rectangle> trees = Client.getObjectsFromColorsInRect(teakColors, searchRect, 5);
            // Loop through each tree rectangle in the list
            for (Rectangle treeRect : trees) {
                Logger.log("Clicking teak tree");
                Client.tap(treeRect); // Perform the tap action on each tree rectangle
                Condition.wait(() -> !Client.isAnyColorInRect(teakColors, treeRect, 5) || Inventory.isFull() || shouldHop(), 300, 200);
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
