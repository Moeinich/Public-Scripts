package mTrawler.Tasks;

import helpers.utils.Area;
import helpers.utils.Tile;
import mTrawler.Task;

import static helpers.Interfaces.*;
import static mTrawler.PublicTrawler.*;

public class Boat extends Task {
    Area boatArea = new Area(
            new Tile(10664, 12486, 1),
            new Tile(10741, 12395, 1)
    );

    @Override
    public boolean activate() {
        // if player on boat
        return (Player.isTileWithinArea(currentPos, boatArea) && GAME_FLAG == 0);
    }

    @Override //the code to execute if criteria met
    public boolean execute() {
        Logger.log("Player in boat area - waiting for game to start.");

        // Close the already someone on a fishing trip message if visible
        if (Chatbox.isMakeMenuVisible()) {
            Client.sendKeystroke("space");
            Condition.wait(() -> !Chatbox.isMakeMenuVisible(), 100, 30);
        }

        // Check for inactivity
        Game.antiAFK();

        Condition.sleep(generateRandomDelay(1000, 6000));
        return true;
    }
}
