package tasks;

import helpers.utils.RegionBox;
import helpers.utils.Tile;
import utils.Task;

import java.awt.*;

import static helpers.Interfaces.*;
import static main.PublicPumper.hopProfile;
import static main.PublicPumper.useWDH;

public class PerformPumping extends Task {
    Rectangle pumpRect = new Rectangle(382, 262, 31, 26);
    RegionBox BFRegion = new RegionBox("BF", 10263, 1407, 10557, 1707);
    Tile pumping = new Tile(3479, 507);
    Tile nextToPump = new Tile(3480, 507);
    Tile location;

    public boolean activate() {
        location = Walker.getPlayerPosition(BFRegion);
        return Player.isTileWithinRegionbox(location, BFRegion);
    }

    @Override
    public boolean execute() {
        if (useWDH) {
            Game.hop(hopProfile, useWDH, true); // Check if we should worldhop
            return true;
        }

        if (Player.tileEquals(location, pumping)) {
            Logger.log("We already pumpin!");
        } else {
            if (!Player.tileEquals(location, pumping)) {
                Logger.log("Stepping next to the pump");
                Walker.step(nextToPump);
                Condition.wait(() -> Player.atTile(nextToPump), 100, 40);

                Logger.log("Starting to pump!");
                Client.tap(pumpRect);
                Condition.wait(() -> Player.atTile(pumping), 100, 40);
            }
        }

        return false; // Return false if the delay has not yet passed
    }

}
