package mTrawler.Tasks;

import mTrawler.Task;

import static helpers.Interfaces.Logger;
import static helpers.Interfaces.Player;

public class Run extends Task {
    @Override
    public boolean activate() {
        // if run energy is >= 30 and you're not running
        return (Player.getRun() >= 30 && !Player.isRunEnabled());
    }

    @Override //the code to execute if criteria met
    public boolean execute() {
        Logger.debugLog("Turning on run.");
        Player.toggleRun();
        return true;
    }
}
