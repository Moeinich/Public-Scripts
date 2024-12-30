package mTrawler.Tasks;

import helpers.utils.Area;
import helpers.utils.Tile;
import mTrawler.Task;

import static mTrawler.PublicTrawler.GAME_FLAG;
import static mTrawler.PublicTrawler.currentPos;
import java.awt.*;

import static helpers.Interfaces.*;

public class Port extends Task {
    Area portArea = new Area(
            new Tile(10621, 12351, 0),
            new Tile(10678, 12415, 0)
    );
    Area boatArea = new Area(
            new Tile(10662, 12400, 0),
            new Tile(10708, 12482, 0)
    );
    Rectangle enterBoatRect = new Rectangle(400, 265, 25, 16);
    Tile boatEnterTile = new Tile(10703, 12429, 0);
    Tile boatEndTile = new Tile(10687, 12429, 1);

    @Override
    public boolean activate() {
        // if player at port but not on boat
        return (Player.isTileWithinArea(currentPos, portArea) && GAME_FLAG == 0);
    }

    @Override //the code to execute if criteria met
    public boolean execute() {
        Logger.log("Attempting to walk to boat entrance.");
        Walker.step(boatEnterTile);
        Condition.wait(() -> {
            currentPos = Walker.getPlayerPosition(); // Update location after moving
            return Player.tileEquals(currentPos, boatEndTile);
        }, 200, 10);

        if(Player.tileEquals(currentPos, boatEnterTile)) {
            Logger.log("Entering boat.");
            Client.tap(enterBoatRect);
            Condition.wait(() -> {
                currentPos = Walker.getPlayerPosition(); // Update location after moving
                return Player.isTileWithinArea(currentPos, boatArea);
            }, 200, 10);
            return true;
        }
        return false;
    }
}
