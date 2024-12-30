package mTrawler.Tasks;

import mTrawler.Task;

import static helpers.Interfaces.*;

public class Run extends Task {
    @Override
    public boolean activate() {
        // if run energy is >= 30 and you're not running
        return (Player.getRun() >= 30 && !Player.isRunEnabled());
    }

    @Override //the code to execute if criteria met
    public boolean execute() {
        Paint.setStatus("Turn run on");
        Logger.debugLog("Turning on run.");
        Player.toggleRun();
        return true;
    }
}
