package model;

import model.buildings.Building;
import model.buildings.BuildingType;

/**
 * Applies a series of simulation rules to a {@link City}.
 */
public class RuleApplier {
    public static final double POWER_PLANT_ATTRACTION_DEPRECIATION = 0.15;
    public static final double POWER_PLANT_BUILDING_VALUE_MULTIPLIER = 0.5;
    public static final double ENERGY_OVER_CONSUMPTION_ATTRACTION_DEPRECIATION = 0.2;
    public static final int ENERGY_OVER_CONSUMPTION_MONEY_PENALTY = 1000;
    public static final double NEUTRAL_TAX_RATE = 0.15;
    public static final double PARK_ATTRACTION_MODIFIER = 0.1;
    private final City city;
    /**
     * Create a new rule applier.
     * @param city The city that the rules should be applied to.
     */
    public RuleApplier(City city) {
        this.city = city;
    }

    /**
     * Apply the simulation rules to this rule applier's
     * associated city. This will mutate the {@link City} state.
     */
    public void applyRules() {
        powerPlantRule();
        energyRule();
        taxRule();
        parkAttractionRule();
    }

    /**
     * Apply the power plant rule. This will depreciate the value
     * of buildings that are surrounded by a power plant, as well as the city's attraction.
     */
    private void powerPlantRule() {
        Map map = city.getMap();
        for (Building powerPlant : map.getBuildingsFromType(BuildingType.POWER_PLANT)) {
            for (Building building : map.getAdjacentBuildings(powerPlant)) {
                building.setValue((int)(building.getValue() * POWER_PLANT_BUILDING_VALUE_MULTIPLIER));
            }
            city.setAttraction(city.getAttraction() - POWER_PLANT_ATTRACTION_DEPRECIATION);
        }
    }

    /**
     * Apply the energy rule. This will compare the city's energy usage to the capacity.
     * If the usage is higher than the capacity, the city's attraction and money will fall.
     * If it's not, then nothing will happen.
     */
    private void energyRule() {
        if (city.getEnergyUsage() > city.getEnergyCapacity()) {
            city.setAttraction(city.getAttraction() - ENERGY_OVER_CONSUMPTION_ATTRACTION_DEPRECIATION);
            city.setMoney(city.getMoney() - ENERGY_OVER_CONSUMPTION_MONEY_PENALTY);
        }
    }

    /**
     * Increase/decrease the attraction depending on the city's tax rate.
     */
    private void taxRule() {
        double taxRate = city.getTaxRate();
        double difference = (NEUTRAL_TAX_RATE - taxRate) / (NEUTRAL_TAX_RATE * 6.0);
        city.setAttraction(city.getAttraction() + difference);
    }

    /**
     * Increase the attraction of the city based on the number of parks.
     */
    private void parkAttractionRule() {
        int numParks = city.getMap().getBuildingsFromType(BuildingType.PARK).size();
        city.setAttraction(city.getAttraction() + numParks * PARK_ATTRACTION_MODIFIER);
    }
}
