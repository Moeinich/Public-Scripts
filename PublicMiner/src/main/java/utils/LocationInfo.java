package utils;

import helpers.utils.Tile;

import java.awt.*;

public enum LocationInfo {
    VARROCK_EAST_COPPER(
            new Rectangle(421, 246, 112, 114), //Check
            6, // tolerance
            new Tile(2847, 998) //Step
    ),
    VARROCK_EAST_IRON(
            new Rectangle(369, 129, 147, 334),
            2,
            new Tile(2846, 992) //Step
    ),
    VARROCK_EAST_TIN(
            new Rectangle(372, 230, 88, 65),
            3,
            new Tile(2840, 999) //Step
    ),
    VARROCK_WEST_CLAY(
            new Rectangle(358, 205, 112, 107),
            5,
            new Tile(2704, 988) //Step
    ),
    VARROCK_WEST_IRON(
            new Rectangle(411, 209, 63, 146),
            2,
            new Tile(2697, 994) //Step
    ),
    VARROCK_WEST_SILVER(
            new Rectangle(361, 206, 111, 104),
            3,
            new Tile(2700, 996) //Step
    ),
    ISLE_OF_SOULS(
            new Rectangle(362, 192, 188, 98),
            2,
            new Tile(1510, 1758) //Step
    ),
    AL_KHARID_EAST(
            new Rectangle(420, 253, 107, 97),
            7,
            new Tile(3000, 1258) //Step
    ),
    MINING_GUILD_COAL(
            new Rectangle(385, 235, 100, 89),
            4,
            new Tile(2137,214)
    ),
    MINING_GUILD_IRON_WEST(
            new Rectangle(359, 191, 133, 193),
            2,
            new Tile(2140,206)
    ),
    MINING_GUILD_IRON_EAST(
            new Rectangle(348, 182, 205, 144),
            2,
            new Tile(2150,207) //step
    );

    private final Rectangle checkLocation;
    private final int tolerance;
    private final Tile stepLocation;

    LocationInfo(Rectangle checkLocation, int tolerance, Tile stepLocation) {
        this.checkLocation = checkLocation;
        this.tolerance = tolerance;
        this.stepLocation = stepLocation;
    }

    public Rectangle getCheckLocation() {
        return checkLocation;
    }

    public int getTolerance() {
        return tolerance;
    }

    public Tile getStepLocation() {
        return stepLocation;
    }
}