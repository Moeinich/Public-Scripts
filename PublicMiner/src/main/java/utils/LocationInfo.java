package utils;

import helpers.utils.Tile;

import java.awt.*;

public enum LocationInfo {
    VARROCK_EAST_COPPER(
            new Rectangle(421, 246, 112, 114), //Check
            6, // tolerance
            new Tile(13143, 13209, 0) //Step
    ),
    VARROCK_EAST_IRON(
            new Rectangle(369, 129, 147, 334),
            2,
            new Tile(13143, 13221, 0) //Step
    ),
    VARROCK_EAST_TIN(
            new Rectangle(372, 230, 88, 65),
            3,
            new Tile(13127, 13201, 0) //Step
    ),
    VARROCK_WEST_CLAY(
            new Rectangle(358, 205, 112, 107),
            5,
            new Tile(12719, 13233, 0) //Step
    ),
    VARROCK_WEST_IRON(
            new Rectangle(411, 209, 63, 146),
            2,
            new Tile(12699, 13217, 0) //Step
    ),
    VARROCK_WEST_SILVER(
            new Rectangle(361, 206, 111, 104),
            3,
            new Tile(12707, 13225, 0) //Step
    ),
    ISLE_OF_SOULS(
            new Rectangle(362, 192, 188, 98),
            2,
            new Tile(8783, 10913, 0) //Step
    ),
    AL_KHARID_EAST(
            new Rectangle(368, 220, 102, 80),
            2,
            new Tile( 13611,12421, 0) //Step
    ),
    MINING_GUILD_COAL(
            new Rectangle(385, 235, 100, 89),
            4,
            new Tile(12075, 38609, 0)
    ),
    MINING_GUILD_IRON_WEST(
            new Rectangle(359, 191, 133, 193),
            2,
            new Tile(12083, 38633, 0)
    ),
    MINING_GUILD_IRON_EAST(
            new Rectangle(348, 182, 205, 144),
            2,
            new Tile(12115, 38629, 0) //step
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