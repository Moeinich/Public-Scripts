package tasks;

import helpers.utils.ItemList;
import utils.Task;

import java.util.Random;

import static helpers.Interfaces.*;
import static main.PublicAmethystMiner.craftOptionSelected;
import static main.PublicAmethystMiner.cutAmethysts;

public class Cut extends Task {
    Random random = new Random();
    private int makeOption = 100;
    private boolean checkedForChisel;

    @Override
    public boolean activate() {
        return cutAmethysts && Inventory.isFull();
    }

    @Override
    public boolean execute() {
        if (makeOption == 100) {
            setMakeOption();
        }

        if (!GameTabs.isInventoryTabOpen()) {
            GameTabs.openInventoryTab();
            Condition.wait(() -> GameTabs.isInventoryTabOpen(), 100, 5);
        }

        if (!checkedForChisel) {
            if (Inventory.contains(ItemList.CHISEL_1755, 0.75)) {
                checkedForChisel = true;
            } else {
                Logger.debugLog("You dont have a chisel in inventory to cut amethysts!");
                Script.stop();
            }
        }

        if (checkedForChisel) {
            Inventory.tapItem(ItemList.CHISEL_1755, true, 0.75);
            int sleepTime = 100 + random.nextInt(200);
            Condition.sleep(sleepTime); // Sleep for the random duration
            Inventory.tapItem(ItemList.AMETHYST_21347, 0.75);

            Condition.wait(() -> Chatbox.isMakeMenuVisible(), 100, 20);
            Chatbox.makeOption(makeOption);

            Condition.wait(() -> !Inventory.contains(ItemList.AMETHYST_21347, 0.75), 200, 100);
            return true;
        }

        return false;
    }

    public void setMakeOption() {
        switch (craftOptionSelected) {
            case "Amethyst bolt tips":
                makeOption = 1;
                break;
            case "Amethyst arrowtips":
                makeOption = 2;
                break;
            case "Amethyst javelin heads":
                makeOption = 3;
                break;
            case "Amethyst dart tip":
                makeOption = 4;
                break;
            default:
                Logger.log("Incorrect script setup, please read script guide!");
                break;
        }
    }
}
