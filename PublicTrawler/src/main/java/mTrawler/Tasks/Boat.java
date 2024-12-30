package mTrawler.Tasks;

import helpers.utils.Area;
import helpers.utils.Tile;
import mTrawler.Task;

import static helpers.Interfaces.*;
import static mTrawler.PublicTrawler.*;

public class Boat extends Task {
    Area boatArea = new Area(
            new Tile(10677, 12416, 0),
            new Tile(10694, 12463, 0)
    );

    @Override
    public boolean activate() {
        // if player on boat
        return (Player.isTileWithinArea(currentPos, boatArea) && GAME_FLAG == 0);
    }

    @Override //the code to execute if criteria met
    public boolean execute() {
        Logger.log("Player in boat area - waiting for game to start.");

        // Check for inactivity
        Game.antiAFK();

        Condition.sleep(generateRandomDelay(1000, 6000));
        return true;
    }
}
