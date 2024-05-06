package utils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public enum VeinColors {
    COPPER_VEIN(
            Arrays.asList(
                    Color.decode("#996b43"),
                    Color.decode("#875e3b")
            )
    ),
    TIN_VEIN(
            Arrays.asList(
                    Color.decode("#827878"),
                    Color.decode("#675e5e")
            )
    ),
    IRON_VEIN(
            Arrays.asList(
                    Color.decode("#4c2e24"),
                    Color.decode("#503126")
            )
    ),
    CLAY(
            Arrays.asList(
                    Color.decode("#745d34"),
                    Color.decode("#997b43")
            )
    ),
    SILVER(
            Arrays.asList(
                    Color.decode("#ac9e9e"),
                    Color.decode("#827878")
            )
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
