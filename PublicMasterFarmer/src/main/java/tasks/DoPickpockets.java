package tasks;

import utils.Task;

import java.awt.*;
import java.util.List;

import static helpers.Interfaces.*;
import static main.PublicMasterFarmer.getRandomInt;

public class DoPickpockets extends Task {

    private final List<Color> outlineColor = List.of(
            Color.decode("#26ffff")
    );
    private final Rectangle NPCSearchRect = new Rectangle(5, 8, 882, 530);

    public boolean activate() {
        return !Inventory.isFull();
    }

    @Override
    public boolean execute() {
        // Get the rectangle objects for NPCs found
        List<Rectangle> NPCs = Client.getObjectsFromColorsInRect(outlineColor, NPCSearchRect, 5);
        // Loop through each tree rectangle in the list
        for (Rectangle NPCRects : NPCs) {
            Logger.log("Clicking Master Farmer");
            Client.tap(NPCRects); // Perform the tap action on each tree rectangle
            Condition.sleep(getRandomInt(150, 500));
            XpBar.getXP();
            return true;
        }

        return false;
    }
}
