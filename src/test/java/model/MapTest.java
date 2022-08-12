package model;

import model.buildings.*;
import model.exceptions.BuildingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    Map map;
    Business business;
    FireHall fireHall;
    Hospital hospital;
    Housing housing;
    Landfill landfill;
    Park park;
    PowerPlant powerPlant;
    School school;

    @BeforeEach
    public void setup() {
        map = new Map();

        business = new Business("1", 1000, 200, Position.ORIGIN);
        fireHall = new FireHall("2", 1000, Position.ORIGIN);
        hospital = new Hospital("3", 2000, Position.ORIGIN);
        housing = new Housing("4", 100, 1, Position.ORIGIN);
        landfill = new Landfill("5", 2000, 1000, Position.ORIGIN);
        park = new Park("6", 200, 1.0, Position.ORIGIN);
        powerPlant = new PowerPlant("7", 2000, 200, Position.ORIGIN);
        school = new School("8", 2000, 2000, Position.ORIGIN);
    }

    @Test
    public void testAddBuilding() {
        map.addBuilding(housing);
        assertEquals(1, map.getNumOfBuildings());
        assertEquals(housing.getName(), map.getBuilding(0).getName());
    }

    @Test
    public void testAddBuildingMultiple() {
        addAll();
        assertEquals(8, map.getNumOfBuildings());
        assertEquals(business.getName(), map.getBuilding(0).getName());
        assertEquals(fireHall.getName(), map.getBuilding(1).getName());
        assertEquals(hospital.getName(), map.getBuilding(2).getName());
        assertEquals(housing.getName(), map.getBuilding(3).getName());
        assertEquals(landfill.getName(), map.getBuilding(4).getName());
        assertEquals(park.getName(), map.getBuilding(5).getName());
        assertEquals(powerPlant.getName(), map.getBuilding(6).getName());
        assertEquals(school.getName(), map.getBuilding(7).getName());
    }

    @Test
    public void testRemoveBuilding() {
        map.addBuilding(housing);
        map.removeBuilding(housing);
        assertEquals(0, map.getNumOfBuildings());
        assertEquals(0, map.getBuildingsFromType(BuildingType.HOUSING).size());
    }

    @Test
    public void testRemoveBuildingMultiple() {
        addAll();
        map.removeBuilding(business);
        map.removeBuilding(school);
        map.removeBuilding(powerPlant);
        assertEquals(5, map.getNumOfBuildings());
        assertEquals(0, map.getBuildingsFromType(BuildingType.BUSINESS).size());
        assertEquals(0, map.getBuildingsFromType(BuildingType.SCHOOL).size());
        assertEquals(0, map.getBuildingsFromType(BuildingType.POWER_PLANT).size());
        assertEquals(fireHall.getName(), map.getBuilding(0).getName());
        assertEquals(hospital.getName(), map.getBuilding(1).getName());
        assertEquals(housing.getName(), map.getBuilding(2).getName());
        assertEquals(landfill.getName(), map.getBuilding(3).getName());
        assertEquals(park.getName(), map.getBuilding(4).getName());
    }

    @Test
    public void testGetBuildingIndex() {
        addAll();
        assertEquals(business.getName(), map.getBuilding(0).getName());
        assertEquals(housing.getName(), map.getBuilding(3).getName());
        assertEquals(school.getName(), map.getBuilding(7).getName());
    }

    @Test
    public void testGetBuildingString() {
        addAll();
        assertEquals(business.getName(), map.getBuilding(business.getName()).getName());
        assertEquals(housing.getName(), map.getBuilding(housing.getName()).getName());
        assertEquals(school.getName(), map.getBuilding(school.getName()).getName());
    }

    @Test
    public void testGetBuildingStringMissing() {
        addAll();
        try {
            map.getBuilding("@#$*(@#-232");
            fail("Map.getBuilding() should have thrown an exception");
        } catch (BuildingNotFoundException e) {
            // pass
        }
    }

    @Test
    public void testGetBusiness() {
        addAll();
        Business other = new Business("b1", 2000, 200, Position.ORIGIN);
        map.addBuilding(other);
        List<Building> buildings = map.getBuildingsFromType(BuildingType.BUSINESS);
        assertEquals(2, buildings.size());
        assertEquals(business.getName(), buildings.get(0).getName());
        assertEquals(other.getName(), buildings.get(1).getName());
    }

    @Test
    public void testGetFireHall() {
        addAll();
        FireHall other = new FireHall("f1", 2000, Position.ORIGIN);
        map.addBuilding(other);
        List<Building> buildings = map.getBuildingsFromType(BuildingType.FIRE_HALL);
        assertEquals(2, buildings.size());
        assertEquals(fireHall.getName(), buildings.get(0).getName());
        assertEquals(other.getName(), buildings.get(1).getName());
    }

    @Test
    public void testGetHospital() {
        addAll();
        Hospital other = new Hospital("h1", 2000, Position.ORIGIN);
        map.addBuilding(other);
        List<Building> buildings = map.getBuildingsFromType(BuildingType.HOSPITAL);
        assertEquals(2, buildings.size());
        assertEquals(hospital.getName(), buildings.get(0).getName());
        assertEquals(other.getName(), buildings.get(1).getName());
    }

    @Test
    public void testGetHousing() {
        addAll();
        Housing other = new Housing("h1", 2000, 2000, Position.ORIGIN);
        map.addBuilding(other);
        List<Building> buildings = map.getBuildingsFromType(BuildingType.HOUSING);
        assertEquals(2, buildings.size());
        assertEquals(housing.getName(), buildings.get(0).getName());
        assertEquals(other.getName(), buildings.get(1).getName());
    }

    @Test
    public void testGetLandfill() {
        addAll();
        Landfill other = new Landfill("l1", 2000, 2000, Position.ORIGIN);
        map.addBuilding(other);
        List<Building> buildings = map.getBuildingsFromType(BuildingType.LANDFILL);
        assertEquals(2, buildings.size());
        assertEquals(landfill.getName(), buildings.get(0).getName());
        assertEquals(other.getName(), buildings.get(1).getName());
    }

    @Test
    public void testGetPark() {
        addAll();
        Park other = new Park("p1", 2000, 1.0, Position.ORIGIN);
        map.addBuilding(other);
        List<Building> buildings = map.getBuildingsFromType(BuildingType.PARK);
        assertEquals(2, buildings.size());
        assertEquals(park.getName(), buildings.get(0).getName());
        assertEquals(other.getName(), buildings.get(1).getName());
    }

    @Test
    public void testGetPowerPlant() {
        addAll();
        PowerPlant other = new PowerPlant("p1", 2000, 100, Position.ORIGIN);
        map.addBuilding(other);
        List<Building> buildings = map.getBuildingsFromType(BuildingType.POWER_PLANT);
        assertEquals(2, buildings.size());
        assertEquals(powerPlant.getName(), buildings.get(0).getName());
        assertEquals(other.getName(), buildings.get(1).getName());
    }

    @Test
    public void testGetSchool() {
        addAll();
        School other = new School("s1", 2000, 1000, Position.ORIGIN);
        map.addBuilding(other);
        List<Building> buildings = map.getBuildingsFromType(BuildingType.SCHOOL);
        assertEquals(2, buildings.size());
        assertEquals(school.getName(), buildings.get(0).getName());
        assertEquals(other.getName(), buildings.get(1).getName());
    }

    @Test
    public void testGetNumOfBuildingsEmpty() {
        assertEquals(0, map.getNumOfBuildings());
    }

    @Test
    public void testGetNumOfBuildingsMultiple() {
        addAll();
        assertEquals(8, map.getNumOfBuildings());
    }

    @Test
    public void testGetBuildingAtPosition() {
        addAll();
        Position position = new Position(4, 5);
        School school = new School("s1", 1000, 1000, position);
        map.addBuilding(school);
        try {
            Building result = map.getBuildingAtPosition(position);
            assertEquals(school.getName(), result.getName());
        } catch (BuildingNotFoundException e) {
            fail("Map.getBuildingAtPosition should have returned a building");
        }
    }

    @Test
    public void testGetBuildingAtPositionNotFound() {
        addAll();
        Position position = new Position(4, 5);
        Park park = new Park("p1", 1000, 1.0, new Position(5, 4));
        map.addBuilding(park);
        try {
            map.getBuildingAtPosition(position);
            fail("Map.getBuildingAtPosition should have thrown a runtime exception");
        } catch (BuildingNotFoundException e) {
            // pass
        }
    }

    @Test
    public void testCheckCollisionFound() {
        map.addBuilding(new Housing("H1", 2000, 200, new Position(1, 1)));
        assertTrue(map.checkBuildingCollision(new Position(1, 1)));
    }

    @Test
    public void testCheckCollisionNotFound() {
        map.addBuilding(new Business("B1", 3000, 20, new Position(10, 3)));
        assertFalse(map.checkBuildingCollision(new Position(2, 2)));
    }

    // MODIFIES: this
    // EFFECTS: add all field buildings to the map
    private void addAll() {
        map.addBuilding(business);
        map.addBuilding(fireHall);
        map.addBuilding(hospital);
        map.addBuilding(housing);
        map.addBuilding(landfill);
        map.addBuilding(park);
        map.addBuilding(powerPlant);
        map.addBuilding(school);
    }
}
