package tasks;

import utils.Task;

import java.util.Random;


public class CreateDarts extends Task {
    Random random = new Random();
    @Override
    public boolean activate() {
        return CheckInventory.checkedInventory;
    }

    @Override
    public boolean execute() {
        return false;
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
