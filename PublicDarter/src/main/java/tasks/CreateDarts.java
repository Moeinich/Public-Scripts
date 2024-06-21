package tasks;

import utils.Task;

import java.util.Random;

import static helpers.Interfaces.Condition;
import static helpers.Interfaces.Inventory;


public class CreateDarts extends Task {
    Random random = new Random();
    @Override
    public boolean activate() {
        return CheckInventory.checkedInventory;
    }

    @Override
    public boolean execute() {
        Inventory.tapItem(CheckInventory.featherInventorySpot);
        Condition.sleep(generateRandomDelay(100, 400));
        Inventory.tapItem(CheckInventory.dartInventorySpot);
        Condition.sleep(generateRandomDelay(100, 400));
        return true;
    }

    private int generateRandomDelay(int lowerBound, int upperBound) {
        // Swap if lowerBound is greater than upperBound
        if (lowerBound > upperBound) {
            int temp = lowerBound;
            lowerBound = upperBound;
            upperBound = temp;
        }
        return lowerBound + random.nextInt(upperBound - lowerBound + 1);
    }
}
