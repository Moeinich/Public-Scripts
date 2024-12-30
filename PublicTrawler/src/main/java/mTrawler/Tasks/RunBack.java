package mTrawler.Tasks;

import helpers.utils.Area;
import helpers.utils.Tile;
import mTrawler.Task;

import static helpers.Interfaces.*;
import static mTrawler.PublicTrawler.GAME_FLAG;
import static mTrawler.PublicTrawler.currentPos;

public class RunBack extends Task {
    Area failArea = new Area(
            new Tile(10658, 12621, 0),
            new Tile(10728, 12674, 0)
    );
    Tile[] runBackPath = new Tile[] {
            new Tile(10673, 12652, 0),
            new Tile(10648, 12647, 0),
            new Tile(10632, 12641, 0),
            new Tile(10632, 12620, 0),
            new Tile(10631, 12594, 0),
            new Tile(10601, 12582, 0),
            new Tile(10584, 12566, 0),
            new Tile(10566, 12551, 0),
            new Tile(10536, 12532, 0),
            new Tile(10521, 12518, 0),
            new Tile(10501, 12491, 0),
            new Tile(10505, 12464, 0),
            new Tile(10505, 12434, 0),
            new Tile(10521, 12417, 0),
            new Tile(10551, 12409, 0),
            new Tile(10576, 12387, 0),
            new Tile(10606, 12378, 0),
            new Tile(10639, 12378, 0),
            new Tile(10665, 12390, 0)
    };

    @Override
    public boolean activate() {
        return (Player.isTileWithinArea(currentPos, failArea));
    }

    @Override //the code to execute if criteria met
    public boolean execute() {
        Paint.setStatus("Run back to port");
        Logger.debugLog("Running back to port after a failed game.");
        Walker.walkPath(runBackPath);
        GAME_FLAG = 0;
        return true;
    }
}
