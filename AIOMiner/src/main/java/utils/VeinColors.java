package utils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public enum VeinColors {
    COPPER_VEIN(
            Arrays.asList(new Color(0x865d3a),
                    new Color(0x9c6d44),
                    new Color(0x805b39),
                    new Color(0x875e3b),
                    new Color(0x835d3a),
                    new Color(0x62462c),
                    new Color(0x8d623e))
    ),
    TIN_VEIN(
            Arrays.asList(new Color(0x665d5d),
                    new Color(0x817777),
                    new Color(0x8e8282),
                    new Color(0x504a4a),
                    new Color(0x948988),
                    new Color(0x554f4f))
    ),
    IRON_VEIN(
            Arrays.asList(
                    new Color(0x4e2f25),
                    new Color(0x3d261f)
            )
    ),
    CLAY(
            Arrays.asList(new Color(0x6f5932))
    ),
    SILVER(
            Arrays.asList(new Color(0x897e7d))
    );

    // Enum setup
    private final List<Color> activeColor;

    VeinColors(List<Color> activeColor) {
        this.activeColor = activeColor;
    }

    public List<Color> getActiveColor() {
        return activeColor;
    }
}
