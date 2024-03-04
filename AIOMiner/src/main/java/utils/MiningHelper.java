package utils;

import helpers.utils.Tile;

import java.awt.*;
import java.util.Random;

import static helpers.Interfaces.*;

public class MiningHelper {
    private final Random random = new Random();

    public boolean checkPositionsAndPerformActions(LocationInfo locationInfo, VeinColors veinColors) {
        // Check location 1
        if (isValidRect(locationInfo.getCheckLocation1())) {
            Logger.log("Checking first vein");
            if (Client.isAnyColorInRect(veinColors.getActiveColor(), locationInfo.getCheckLocation1(), 5)) {
                clickPositions(locationInfo, 1, veinColors);
                return true;
            }
        }

        // Check location 2
        if (isValidRect(locationInfo.getCheckLocation2())) {
            Logger.log("Checking second vein");
            if (Client.isAnyColorInRect(veinColors.getActiveColor(), locationInfo.getCheckLocation2(), 5)) {
                clickPositions(locationInfo, 2, veinColors);
                return true;
            }
        }

        // Check location 3
        if (isValidRect(locationInfo.getCheckLocation3())) {
            Logger.log("Checking third vein");
            if (Client.isAnyColorInRect(veinColors.getActiveColor(), locationInfo.getCheckLocation3(), 5)) {
                clickPositions(locationInfo, 3, veinColors);
                return true;
            }
        }

        return false;
    }

    private void clickPositions(LocationInfo locationInfo, int position, VeinColors veinColors) {
        // Assuming Client has a tap method
        switch (position) {
            case 1:
                if (isValidRect(locationInfo.getClickLocation1())) {
                    Logger.log("Tapping vein 1");
                    Client.tap(locationInfo.getClickLocation1());
                    Condition.wait(() -> !Client.isAnyColorInRect(veinColors.getActiveColor(), locationInfo.getCheckLocation1(), 5), 200, 30);
                    Logger.debugLog("Succesfully mined vein 1");
                    XpBar.getXP();
                }
                break;
            case 2:
                if (isValidRect(locationInfo.getClickLocation2())) {
                    Logger.log("Tapping vein 2");
                    Client.tap(locationInfo.getClickLocation2());
                    Condition.wait(() -> !Client.isAnyColorInRect(veinColors.getActiveColor(), locationInfo.getCheckLocation2(), 5), 200, 30);
                    Logger.debugLog("Succesfully mined vein 2");
                    XpBar.getXP();
                }
                break;
            case 3:
                if (isValidRect(locationInfo.getClickLocation3())) {
                    Logger.log("Tapping vein 3");
                    Client.tap(locationInfo.getClickLocation3());
                    Condition.wait(() -> !Client.isAnyColorInRect(veinColors.getActiveColor(), locationInfo.getCheckLocation3(), 5), 200, 30);
                    Logger.debugLog("Succesfully mined vein 3");
                    XpBar.getXP();
                }
                break;
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
