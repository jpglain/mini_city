package model;

import java.util.Objects;

/**
 * A position in 2D space with an x and y coordinate.
 */
public class Position {
    public static final Position ORIGIN = new Position(0, 0);

    private final int posX;
    private final int posY;

    /**
     * Create a new position with an x and y coordinate.
     *
     * @param posX The x coordinate of the new position.
     * @param posY The y coordinate of the new position.
     */
    public Position(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return posX == position.posX && posY == position.posY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }
}
