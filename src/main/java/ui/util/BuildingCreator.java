package ui.util;

import model.Position;
import model.buildings.*;

import java.util.Random;

/**
 * Class that contains procedures for creating new buildings with default values.
 */
public class BuildingCreator {
    private static final Random rand = new Random();

    /**
     * Return a new building of the given type at the given position.
     *
     * @param type     The type of building to create.
     * @param position The position of the new building.
     */
    public static Building createBuildingFromType(BuildingType type, Position position) {
        if (type.equals(BuildingType.HOUSING)) {
            return createHousing(position);
        } else if (type.equals(BuildingType.BUSINESS)) {
            return createBusiness(position);
        } else if (type.equals(BuildingType.FIRE_HALL)) {
            return createFireHall(position);
        } else if (type.equals(BuildingType.HOSPITAL)) {
            return createHospital(position);
        } else if (type.equals(BuildingType.LANDFILL)) {
            return createLandfill(position);
        } else if (type.equals(BuildingType.PARK)) {
            return createPark(position);
        } else if (type.equals(BuildingType.POWER_PLANT)) {
            return createPowerPlant(position);
        } else if (type.equals(BuildingType.SCHOOL)) {
            return createSchool(position);
        } else {
            return null;
        }
    }

    /**
     * Return a new building of the given type at the default position.
     *
     * @param type The type of building to create.
     */
    public static Building createBuildingFromType(BuildingType type) {
        return createBuildingFromType(type, new Position(0, 0));
    }

    /**
     * Create new housing.
     *
     * @param position The position of the new building.
     */
    public static Housing createHousing(Position position) {
        return new Housing("Housing", 2000, rand.nextInt(100) + 100, position);
    }

    /**
     * Create a new business.
     *
     * @param position The position of the new building.
     */
    public static Business createBusiness(Position position) {
        return new Business("Business", 3000, rand.nextInt(100) + 40, position);
    }

    /**
     * Create a new fire hall.
     *
     * @param position The position of the new building.
     */
    public static FireHall createFireHall(Position position) {
        return new FireHall(String.format("Fire Hall no. %s", rand.nextInt(1000)), 6000, position);
    }

    /**
     * Create a new hospital.
     *
     * @param position The position of the new building.
     */
    public static Hospital createHospital(Position position) {
        return new Hospital("Hospital", 10000, position);
    }

    /**
     * Create a new landfill.
     *
     * @param position The position of the new building.
     */
    public static Landfill createLandfill(Position position) {
        return new Landfill("Landfill", 6000, 10000, position);
    }

    /**
     * Create a new park.
     *
     * @param position The position of the new building.
     */
    public static Park createPark(Position position) {
        return new Park("Park", 10000, 1.5, position);
    }

    /**
     * Create a new power plant.
     *
     * @param position The position of the new building.
     */
    public static PowerPlant createPowerPlant(Position position) {
        return new PowerPlant("Power Plant", 15000, 100, position);
    }

    /**
     * Create a new school.
     *
     * @param position The position of the new building.
     */
    public static School createSchool(Position position) {
        return new School("School", 20000, 1000, position);
    }
}
