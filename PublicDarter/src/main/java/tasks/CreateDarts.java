package tasks;

import main.PublicDarter;
import utils.Task;

import java.util.Random;

import static helpers.Interfaces.*;


public class CreateDarts extends Task {
    Random random = new Random();
    @Override
    public boolean activate() {
        return CheckInventory.checkedInventory;
    }

    @Override
    public boolean execute() {
        if (!Inventory.contains(CheckInventory.foundDartID, 0.80)) {
            Logger.log("no more darts in inventory");
            Script.stop();
        }

        Inventory.tapItem(CheckInventory.featherInventorySpot);
        Condition.sleep(generateRandomDelay(PublicDarter.minTapSpeed, PublicDarter.maxTapSpeed));
        Inventory.tapItem(CheckInventory.dartInventorySpot);
        Condition.sleep(generateRandomDelay(PublicDarter.minTapSpeed, PublicDarter.maxTapSpeed));
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
