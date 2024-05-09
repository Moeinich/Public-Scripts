package utils;

import helpers.utils.Area;
import helpers.utils.RegionBox;
import helpers.utils.Tile;

public enum RegionInfo {
    VARROCK_EAST(
            new RegionBox("VARROCK_EAST", 8232, 2601, 8748, 3213), //World region for this spot
            new Area(new Tile(2829, 983), new Tile(2862, 1013)), // the Mine area
            new Area(new Tile(2796, 916), new Tile(2808, 929)) // the Bank area
    ),
    VARROCK_WEST(
            new RegionBox("VARROCK_WEST", 7830, 2538, 8292, 3135),
            new Area(new Tile(2690, 975), new Tile(2712, 1002)),
            new Area(new Tile(2704, 886), new Tile(2718, 906))
    ),
    ISLE_OF_SOULS(
            new RegionBox("SOUL_ISLES", 4440, 4938, 4707, 5355),
            new Area(new Tile(1498, 1745), new Tile(1522, 1769)),
            new Area(new Tile(1518, 1663), new Tile(1544, 1675))
    ),
    AL_KHARID_EAST(
            new RegionBox("AL_KHARID_EAST", 8937, 3699, 9093, 3849),
            new Area(new Tile(2993, 1250), new Tile(3007, 1263)),
            new Area(new Tile(2817, 1251), new Tile(2828, 1270))
    ),
    MINING_GUILD_COAL(
            new RegionBox("MINING_GUILD", 6291, 459, 6654, 777), // Region
            new Area(new Tile(2131, 207), new Tile(2153, 219)), // Mine area
            new Area(new Tile(2124, 205), new Tile(2130, 212)) // Bank
    ),
    MINING_GUILD_IRON_EAST(
            new RegionBox("MINING_GUILD", 6291, 459, 6654, 777), // Region
            new Area(new Tile(2147, 204), new Tile(2155, 209)), //mine area
            new Area(new Tile(2124, 205), new Tile(2130, 212)) // Bank
    ),
    MINING_GUILD_IRON_WEST(
            new RegionBox("MINING_GUILD", 6291, 459, 6654, 777), // Region
            new Area(new Tile(2136, 202), new Tile(2141, 209)), // mining area
            new Area(new Tile(2124, 205), new Tile(2130, 212)) // Bank

    );

    private final RegionBox worldRegion;
    private final Area mineArea;
    private final Area bankArea;

    RegionInfo(RegionBox worldRegion, Area mineRegion, Area bankRegion) {
        this.worldRegion = worldRegion;
        this.mineArea = mineRegion;
        this.bankArea = bankRegion;
    }

    public RegionBox getWorldRegion() {
        return worldRegion;
    }
    public Area getMineArea() {
        return mineArea;
    }
    public Area getBankArea() {return bankArea;}
}
