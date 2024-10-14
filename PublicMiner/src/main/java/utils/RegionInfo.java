package utils;

import helpers.utils.Area;
import helpers.utils.Tile;

public enum RegionInfo {
    VARROCK_EAST(
            new Area(
                    new Tile(13119, 13177, 0),
                    new Tile(13179, 13233, 0)
            ), // the Mine area
            new Area(
                    new Tile(13000, 13414, 0),
                    new Tile(13035, 13442, 0)
            ) // the Bank area
    ),
    VARROCK_WEST(
            new Area(
                    new Tile(12686, 13197, 0),
                    new Tile(12741, 13268, 0)
            ),
            new Area(
                    new Tile(12719, 13475, 0),
                    new Tile(12765, 13536, 0)
            )
    ),
    ISLE_OF_SOULS(
            new Area(
                    new Tile(8768, 10900, 0),
                    new Tile(8803, 10928, 0)
            ),
            new Area(
                    new Tile(8828, 11163, 0),
                    new Tile(8871, 11197, 0)
            )
    ),
    AL_KHARID_EAST(
            new Area(
                    new Tile(13599, 12409, 0),
                    new Tile(13625, 12436, 0)
            ),
            new Area(
                    new Tile(13062, 12385, 0),
                    new Tile(13093, 12438, 0)
            )
    ),
    MINING_GUILD_COAL(
            new Area(new Tile(12063, 38595, 0), new Tile(12086, 38620, 0)), // Mine area
            new Area(
                    new Tile(12042, 38610, 0),
                    new Tile(12058, 38627, 0)
            ) // Bank
    ),
    MINING_GUILD_IRON_EAST(
            new Area(
                    new Tile(12113, 38618, 0),
                    new Tile(12129, 38629, 0)
            ), //mine area
            new Area(
                    new Tile(12042, 38610, 0),
                    new Tile(12058, 38627, 0)
            ) // Bank
    ),
    MINING_GUILD_IRON_WEST(
            new Area(
                    new Tile(12080, 38619, 0),
                    new Tile(12091, 38634, 0)
            ), // mining area
            new Area(
                    new Tile(12042, 38610, 0),
                    new Tile(12058, 38627, 0)
            ) // Bank

    );

    private final Area mineArea;
    private final Area bankArea;

    RegionInfo(Area mineRegion, Area bankRegion) {
        this.mineArea = mineRegion;
        this.bankArea = bankRegion;
    }

    public Area getMineArea() {
        return mineArea;
    }
    public Area getBankArea() {return bankArea;}
}
