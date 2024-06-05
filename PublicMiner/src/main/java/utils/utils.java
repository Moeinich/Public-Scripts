package utils;

import helpers.utils.Tile;

import java.util.Random;

public class utils {
    private static final Random random = new Random();

    public static Tile[] pickRandomPath(PathsToBanks pathsToBanks) {
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

    public static Tile[] pickRandomPathReversed(PathsToBanks pathsToBanks) {
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
