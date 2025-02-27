package utils;

import java.awt.*;

public enum DirectionCheckRect {
    WEST(new Rectangle(385, 261, 25, 31)),
    NORTH(new Rectangle(433, 225, 20, 14)),
    EAST(new Rectangle(478, 265, 30, 25)),
    SOUTH(new Rectangle(438, 300, 27, 35));

    private final Rectangle rectangle;

    DirectionCheckRect(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
