package utils;

import helpers.utils.Tile;

import java.awt.*;

public enum LocationInfo {
    VARROCK_EAST_COPPER(
            new Rectangle(381, 270, 31, 31), //Check 1
            new Rectangle(445, 224, 9, 10), //Check 2
            new Rectangle(1, 1, 1, 1), //Check 3 (only 2 ores)
            new Rectangle(399, 268, 16, 19), //Click 1
            new Rectangle(445, 223, 19, 22), //Click 2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2847, 998) //Step
    ),
    VARROCK_EAST_IRON(
            new Rectangle(389, 266, 38, 26), //Check 1
            new Rectangle(434, 217, 36, 30), //Check 2
            new Rectangle(1, 1, 1, 1), //Check 3 (only 2 ores)
            new Rectangle(394, 261, 26, 25), //Click 1
            new Rectangle(440, 223, 25, 24), //Click 2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2846, 992) //Step
    ),
    VARROCK_EAST_TIN(
            new Rectangle(375, 267, 40, 31), //Check 1
            new Rectangle(425, 228, 37, 29), //Check 2
            new Rectangle(1, 1, 1, 1), //Check 3 (only 2 ores)
            new Rectangle(382, 269, 27, 33), //Click 1
            new Rectangle(431, 232, 25, 24), //Click 2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2840, 999) //Step
    ),
    VARROCK_WEST_CLAY(
            new Rectangle(394, 264, 13, 19), //Check 1
            new Rectangle(430, 226, 20, 23), //Check 2
            new Rectangle(1, 1, 1, 1), //Check 3 (only 2 ores)
            new Rectangle(384, 295, 24, 25), //Click 1
            new Rectangle(429, 255, 26, 27), //Click 2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2704, 988) //Step
    ),
    VARROCK_WEST_IRON(
            new Rectangle(440, 299, 16, 20), //Check 1
            new Rectangle(428, 219, 29, 14), //Check 2
            new Rectangle(1, 1, 1, 1), //Check 3 (only 2 ores)
            new Rectangle(429, 302, 26, 31), //Click 1
            new Rectangle(457, 219, 22, 22), //Click 2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2697, 994) //Step
    ),
    VARROCK_WEST_SILVER(
            new Rectangle(435, 314, 23, 33), //Check 1
            new Rectangle(481, 268, 31, 25), //Check 2
            new Rectangle(1, 1, 1, 1), //Check 3 (only 2 ores)
            new Rectangle(435, 314, 23, 33), //Click 1
            new Rectangle(481, 268, 31, 25), //Click 2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2700, 996) //Step
    ),
    ISLE_OF_SOULS(
            new Rectangle(386, 265, 30, 33), //Check 1
            new Rectangle(432, 217, 32, 17), //Check 2
            new Rectangle(475, 267, 33, 25), //Check 3
            new Rectangle(386, 265, 30, 33), //Click 1
            new Rectangle(431, 227, 30, 25), //Click 2
            new Rectangle(475, 265, 32, 15), //Click 3
            new Tile(1510, 1758) //Step
    ),
    AL_KHARID_EAST(
            new Rectangle(429, 313, 10, 13), //Check 1
            new Rectangle(500, 282, 8, 10), //Check 2
            new Rectangle(1, 1, 1, 1), //Check 3
            new Rectangle(429, 310, 20, 17), //Click 1
            new Rectangle(478, 276, 22, 21), //Click 2
            new Rectangle(1, 1, 1, 1), //Click 3
            new Tile(3000, 1258) //Step
    );
    // ...

    private final Rectangle checkLocation1;
    private final Rectangle checkLocation2;
    private final Rectangle checkLocation3;
    private final Rectangle clickLocation1;
    private final Rectangle clickLocation2;
    private final Rectangle clickLocation3;
    private final Tile stepLocation;

    LocationInfo(Rectangle checkLocation1, Rectangle checkLocation2, Rectangle checkLocation3, Rectangle clickLocation1, Rectangle clickLocation2, Rectangle clickLocation3, Tile stepLocation) {
        this.checkLocation1 = checkLocation1;
        this.checkLocation2 = checkLocation2;
        this.checkLocation3 = checkLocation3;
        this.clickLocation1 = clickLocation1;
        this.clickLocation2 = clickLocation2;
        this.clickLocation3 = clickLocation3;
        this.stepLocation = stepLocation;
    }

    public Rectangle getCheckLocation1() {
        return checkLocation1;
    }
    public Rectangle getCheckLocation2() {
        return checkLocation2;
    }
    public Rectangle getCheckLocation3() {
        return checkLocation3;
    }

    public Rectangle getClickLocation1() {
        return clickLocation1;
    }
    public Rectangle getClickLocation2() {
        return clickLocation2;
    }
    public Rectangle getClickLocation3() {
        return clickLocation3;
    }

    public Tile getStepLocation() {
        return stepLocation;
    }
}