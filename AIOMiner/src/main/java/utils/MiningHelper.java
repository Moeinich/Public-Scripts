package utils;

import helpers.utils.Tile;

import java.awt.*;
import java.util.Random;

import static helpers.Interfaces.*;

public class MiningHelper {
    private final Random random = new Random();

    public boolean checkPositionsAndPerformActions(LocationInfo locationInfo, VeinColors veinColors) {
        for (int i = 1; i <= 3; i++) {
            Rectangle checkLocation = getCheckLocation(locationInfo, i);
            if (isValidRect(checkLocation)) {
                Logger.log("Checking vein " + i);
                if (Client.isAnyColorInRect(veinColors.getActiveColor(), checkLocation, 5)) {
                    clickPositions(locationInfo, i, veinColors);
                }
            }
        }
        return true;
    }

    private void clickPositions(LocationInfo locationInfo, int position, VeinColors veinColors) {
        Rectangle clickLocation = getClickLocation(locationInfo, position);
        if (isValidRect(clickLocation)) {
            Logger.log("Tapping vein " + position);
            Client.tap(clickLocation);
            Condition.wait(() -> !Client.isAnyColorInRect(veinColors.getActiveColor(), clickLocation, 5), 50, 200);
            Logger.debugLog("Successfully mined vein " + position);
            XpBar.getXP();
        }
    }

    private Rectangle getCheckLocation(LocationInfo locationInfo, int locationNumber) {
        switch (locationNumber) {
            case 1: return locationInfo.getCheckLocation1();
            case 2: return locationInfo.getCheckLocation2();
            case 3: return locationInfo.getCheckLocation3();
            default:
                throw new IllegalArgumentException("Invalid check location number: " + locationNumber);
        }
    }

    private Rectangle getClickLocation(LocationInfo locationInfo, int locationNumber) {
        switch (locationNumber) {
            case 1: return locationInfo.getClickLocation1();
            case 2: return locationInfo.getClickLocation2();
            case 3: return locationInfo.getClickLocation3();
            default:
                throw new IllegalArgumentException("Invalid click location number: " + locationNumber);
        }
    }

    private boolean isValidRect(Rectangle rect) {
        return !(rect.width == 1 && rect.height == 1 && rect.x == 1 && rect.y == 1);
    }

    public Tile[] pickRandomPath(PathsToBanks pathsToBanks) {
        int pick = random.nextInt(3);
        switch (pick) {
            case 0:
                return pathsToBanks.Path1();
            case 1:
                return pathsToBanks.Path2();
            case 2:
                return pathsToBanks.Path3();
            default:
                // In case of an unexpected value, return null or handle appropriately
                return null;
        }
    }

    public Tile[] pickRandomPathReversed(PathsToBanks pathsToBanks) {
        int pick = random.nextInt(3);
        Tile[] path;
        switch (pick) {
            case 0:
                path = pathsToBanks.Path1();
                break;
            case 1:
                path = pathsToBanks.Path2();
                break;
            case 2:
                path = pathsToBanks.Path3();
                break;
            default:
                // In case of an unexpected value, return null or handle appropriately
                return null;
        }
        // Reverse the array
        if (path != null) {
            for (int i = 0; i < path.length / 2; i++) {
                Tile temp = path[i];
                path[i] = path[path.length - 1 - i];
                path[path.length - 1 - i] = temp;
            }
        }
        return path;
    }
}
