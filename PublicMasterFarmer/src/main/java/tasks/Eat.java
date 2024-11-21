package tasks;

import utils.Task;

import java.util.Arrays;
import java.util.List;

import static helpers.Interfaces.*;
import static helpers.Interfaces.Condition;
import static main.PublicMasterFarmer.*;

public class Eat extends Task {
    private final List<Integer> cakeIds = Arrays.asList(1895, 1893, 1891);

    @Override
    public boolean activate() {
        currentHP = Player.getHP();
        return (currentHP <= hpToEat && currentHP != -1);
    }

    @Override
    public boolean execute() {
        Logger.log("Below HP threshold, eating food.");
        if (selectedFood.equals("Cake")) {
            for (int cakeId : cakeIds) {
                if (Inventory.contains(cakeId, 0.8)) {
                    eat(cakeId);
                    break;
                }
            }
        } else {
            if (Inventory.count(foodID, 0.8) > 0) {
                eat(foodID);
            }
        }
        Logger.log("Done eating food.");
        return true;
    }

    public void eat(int food){
        Logger.debugLog("Eating food now.");
        Inventory.eat(food, 0.8);
        Condition.sleep(getRandomInt(2750, 3500));
        currentHP = Player.getHP();
    }
}
