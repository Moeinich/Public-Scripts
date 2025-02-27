package tasks;

import helpers.utils.Area;
import helpers.utils.Tile;
import utils.Task;

import java.awt.*;

import static helpers.Interfaces.*;
import static main.PublicPumper.hopProfile;
import static main.PublicPumper.useWDH;

public class PerformPumping extends Task {
    Rectangle pumpRect = new Rectangle(384, 249, 13, 27);
    Area BFArea = new Area(
            new Tile(7734, 19562, 0),
            new Tile(7838, 19652, 0)
    );
    Tile pumping = new Tile(7799, 19593, 0);
    Tile nextToPump = new Tile(7803, 19593, 0);
    Tile location;

    public boolean activate() {
        location = Walker.getPlayerPosition();
        return Player.isTileWithinArea(location, BFArea);
    }

    @Override
    public boolean execute() {
        if (useWDH) {
            Game.hop(hopProfile, useWDH, true); // Check if we should worldhop
        }

        if (Player.tileEquals(location, pumping)) {
            Logger.log("We already pumpin!");
            XpBar.getXP();
            Condition.sleep(2000);
            return true;
        } else {
            if (!Player.tileEquals(location, pumping)) {
                Logger.log("Stepping next to the pump");
                Walker.step(nextToPump);
                Condition.wait(() -> Player.atTile(nextToPump), 100, 40);

                Logger.log("Starting to pump!");
                Client.tap(pumpRect);
                Condition.wait(() -> Player.atTile(pumping), 100, 40);
                return true;
            }
        }

        return false; // Return false if the delay has not yet passed
    }
}
