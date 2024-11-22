package tasks;

import utils.Task;

import java.awt.*;
import java.util.List;

import static helpers.Interfaces.*;
import static main.PublicMasterFarmer.getRandomInt;

public class DoPickpockets extends Task {
    final int MIN_WIDTH = 20;
    final int MIN_HEIGHT = 10;

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

        // Loop through each NPC rectangle in the list
        for (Rectangle NPCRect : NPCs) {
            // Check if the rectangle meets the minimum size requirements
            if (NPCRect.width < MIN_WIDTH || NPCRect.height < MIN_HEIGHT) {
                Logger.debugLog("Skipping found NPC Rect: Does not meet minimum size requirements.");
                continue;
            }

            // Trim the rectangle
            Rectangle trimmedRect = new Rectangle(
                    NPCRect.x + 5,                       // Add 5 to the left
                    NPCRect.y + 3,                       // Add 3 to the top
                    NPCRect.width - 10,                  // Subtract 5 from each side (total 10)
                    NPCRect.height - 6                   // Subtract 3 from top and bottom (total 6)
            );

            Logger.log("Clicking Master Farmer");
            Client.tap(trimmedRect); // Perform the tap action on the trimmed rectangle
            Condition.sleep(getRandomInt(150, 500));
            XpBar.getXP();
            return true;
        }

        return false;
    }
}
