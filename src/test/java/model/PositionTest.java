package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PositionTest {
    @Test
    public void testEquals() {
        Position p1 = new Position(4, 5);
        Position p2 = new Position(4, 5);
        assertEquals(p1, p2);
        assertEquals(p1, p1);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testNotEqualX() {
        Position p1 = new Position(4, 5);
        Position p2 = new Position(5, 4);
        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testNotEqualY() {
        Position p1 = new Position(5, 5);
        Position p2 = new Position(5, 4);
        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testNotEqualsNull() {
        Position p1 = new Position(4, 5);
        assertNotEquals(p1, null);
    }

    @Test
    public void testNotEqualsObject() {
        assertNotEquals(new Position(0, 0), 8);
    }
}
