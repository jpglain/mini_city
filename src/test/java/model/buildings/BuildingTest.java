package model.buildings;

import model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildingTest {
    @Test
    public void testSetPosition() {
        Park park = new Park("P1", 2000, 1.0, new Position(1, 2));
        assertEquals(new Position(1, 2), park.getPosition());
        park.setPosition(new Position(10, 10));
        assertEquals(new Position(10, 10), park.getPosition());
    }

    @Test
    public void testTypeToString() {
        assertEquals("Business", Building.typeToString(BuildingType.BUSINESS));
        assertEquals("Fire Hall", Building.typeToString(BuildingType.FIRE_HALL));
        assertEquals("Hospital", Building.typeToString(BuildingType.HOSPITAL));
        assertEquals("Housing", Building.typeToString(BuildingType.HOUSING));
        assertEquals("Landfill", Building.typeToString(BuildingType.LANDFILL));
        assertEquals("Park", Building.typeToString(BuildingType.PARK));
        assertEquals("Power Plant", Building.typeToString(BuildingType.POWER_PLANT));
        assertEquals("School", Building.typeToString(BuildingType.SCHOOL));
    }
}
