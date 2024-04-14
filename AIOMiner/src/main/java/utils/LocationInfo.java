package utils;

import helpers.utils.Tile;

import java.awt.*;

public enum LocationInfo {
    VARROCK_EAST_COPPER(
            new Rectangle(381, 270, 31, 31), //Check1
            new Rectangle(445, 224, 9, 10), //Check2
            new Rectangle(1, 1, 1, 1), //Check3 (only 2 ores)
            new Rectangle(399, 268, 16, 19), //Click1
            new Rectangle(445, 223, 19, 22), //Click2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2847, 998) //Step
    ),
    VARROCK_EAST_IRON(
            new Rectangle(389, 266, 38, 26), //Check1
            new Rectangle(434, 217, 36, 30), //Check2
            new Rectangle(1, 1, 1, 1), //Check3 (only 2 ores)
            new Rectangle(394, 261, 26, 25), //Click1
            new Rectangle(440, 223, 25, 24), //Click2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2846, 992) //Step
    ),
    VARROCK_EAST_TIN(
            new Rectangle(375, 267, 40, 31), //Check1
            new Rectangle(425, 228, 37, 29), //Check2
            new Rectangle(1, 1, 1, 1), //Check3 (only 2 ores)
            new Rectangle(382, 269, 27, 33), //Click1
            new Rectangle(431, 232, 25, 24), //Click2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2840, 999) //Step
    ),
    VARROCK_WEST_CLAY(
            new Rectangle(394, 264, 13, 19), //Check1
            new Rectangle(430, 226, 20, 23), //Check2
            new Rectangle(1, 1, 1, 1), //Check3 (only 2 ores)
            new Rectangle(384, 295, 24, 25), //Click1
            new Rectangle(429, 255, 26, 27), //Click2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2704, 988) //Step
    ),
    VARROCK_WEST_IRON(
            new Rectangle(440, 299, 16, 20), //Check1
            new Rectangle(428, 219, 29, 14), //Check2
            new Rectangle(1, 1, 1, 1), //Check3 (only 2 ores)
            new Rectangle(429, 302, 26, 31), //Click1
            new Rectangle(457, 219, 22, 22), //Click2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2697, 994) //Step
    ),
    VARROCK_WEST_SILVER(
            new Rectangle(435, 314, 23, 33), //Check1
            new Rectangle(481, 268, 31, 25), //Check2
            new Rectangle(1, 1, 1, 1), //Check3 (only 2 ores)
            new Rectangle(435, 314, 23, 33), //Click1
            new Rectangle(481, 268, 31, 25), //Click2
            new Rectangle(1, 1, 1, 1), //Click 3 (only 2 ores)
            new Tile(2700, 996) //Step
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