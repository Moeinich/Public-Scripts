package tasks;

import helpers.utils.Area;
import helpers.utils.Tile;
import utils.Task;

import java.awt.*;

import static helpers.Interfaces.*;

public class PerformPumping extends Task {
    Rectangle pumpRect = new Rectangle(381, 258, 6, 13);
    Rectangle pumpingPumpRect = new Rectangle(423, 260, 5, 15);

    Area BFArea = new Area(
            new Tile(7734, 19562, 0),
            new Tile(7838, 19652, 0)
    );
    Tile pumpingTile = new Tile(7799, 19593, 0);
    Tile nextToPump = new Tile(7803, 19593, 0);
    Tile location;

    int currentXp;
    long lastXpCheckTime = System.currentTimeMillis();

    public boolean activate() {
        location = Walker.getPlayerPosition();
        return Player.isTileWithinArea(location, BFArea);
    }

    @Override
    public boolean execute() {
        int newXp = XpBar.getXP();
        if (newXp == currentXp && (System.currentTimeMillis() - lastXpCheckTime) >= 5000) {
            Logger.log("XP hasn't changed in 5 seconds, re-clicking pump!");

            Rectangle targetRect = null;

            if (Player.tileEquals(location, pumpingTile)) {
                Logger.log("Clicking the pump while on pump tile");
                targetRect = pumpingPumpRect;
            } else if (Player.tileEquals(location, nextToPump)) {
                Logger.log("Clicking the pump while next to the pump");
                targetRect = pumpRect;
            }

            if (targetRect != null) {
                Client.tap(targetRect);
                lastXpCheckTime = System.currentTimeMillis();
            }
        } else if (newXp != currentXp) {
            currentXp = newXp;
            lastXpCheckTime = System.currentTimeMillis();
        }

        if (Player.tileEquals(location, pumpingTile)) {
            Logger.debugLog("We already pumpin!");
            Game.antiAFK();
            Condition.sleep(2000);
            return true;
        } else {
            Logger.log("Stepping next to the pump");
            Walker.step(nextToPump);
            Condition.wait(() -> Player.atTile(nextToPump), 100, 40);

            Logger.log("Starting to pump!");
            Client.tap(pumpRect);
            Condition.wait(() -> Player.atTile(pumpingTile), 100, 40);
            return true;
        }
    }
}
