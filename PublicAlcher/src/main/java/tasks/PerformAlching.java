package tasks;

import utils.Task;

import static tasks.CheckForItems.checkedForItems;

public class PerformAlching extends Task {

    public boolean activate() {
        return checkedForItems;
    }

    @Override
    public boolean execute() {
        return true;
    }
}
