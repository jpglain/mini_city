package model;

import model.buildings.*;
import model.exceptions.InsufficientMoneyException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

/**
 * Represents a city. Keeps track of buildings and other city information,
 * like name, funds, tax, and population.
 */
public class City implements Writable, Summarizable {
    public static final int BASE_POPULATION_INCREASE = 10;
    public static final int BUILDING_ENERGY_USAGE = 15;
    private final Map map;
    private final RuleApplier applier;
    private String name;
    private int population;
    private int money;
    private double attraction;
    private double taxRate;
    private int housingCapacity;
    private int workerCapacity;
    private int energyUsage;
    private int energyCapacity;

    /**
     * Creates a new City with some default initial values.
     *
     * @param name The name of the new city.
     */
    public City(String name) {
        this.name = name;
        population = 0;
        money = 0;
        attraction = 1.0;
        taxRate = 0.05;
        map = new Map();
        applier = new RuleApplier(this);
    }

    /**
     * Apply the simulation rules based on the city and map state.
     */
    public void applyRules() {
        applier.applyRules();
    }

    /**
     * Update all city statistics such as housing capacity, population, etc.
     */
    public void update() {
        updateHousingCapacity();
        updateWorkerCapacity();
        updateEnergyCapacity();
        updateEnergyUsage();
        updatePopulation();
        updateMoney();
    }

    /**
     * Calculates and updates the next population of the city,
     * taking into account its attraction
     * (degree to which someone wants to live there)
     */
    public void updatePopulation() {
        population += BASE_POPULATION_INCREASE * getAttraction();
        if (population >= housingCapacity) {
            population = housingCapacity;
        }
        if (population < 0) {
            population = 0;
        }
        EventLog.getInstance().logEvent(new Event(String.format("Population updated to %s", population)));
    }

    /**
     * Update the total amount of people this city can hold with all of its housing.
     *
     * @return The new housing capacity.
     */
    public int updateHousingCapacity() {
        housingCapacity = 0;
        List<Building> housingList = map.getBuildingsFromType(BuildingType.HOUSING);
        for (Building b : housingList) {
            Housing housing = (Housing) b;
            housingCapacity = housingCapacity + housing.getResidentCapacity();
        }
        EventLog.getInstance().logEvent(new Event(String.format("Housing capacity updated to %s", housingCapacity)));
        return housingCapacity;
    }

    /**
     * Update the total amount of people this city can employ.
     *
     * @return The new worker capacity.
     */
    public int updateWorkerCapacity() {
        workerCapacity = 0;
        List<Building> businessList = map.getBuildingsFromType(BuildingType.BUSINESS);
        for (Building b : businessList) {
            Business business = (Business) b;
            workerCapacity = workerCapacity + business.getWorkerCapacity();
        }
        EventLog.getInstance().logEvent(new Event(String.format("Worker capacity updated to %s", workerCapacity)));
        return workerCapacity;
    }

    /**
     * Updates the city's money by applying taxes to all buildings.
     */
    public void updateMoney() {
        for (Building b : map.getBuildings()) {
            setMoney((int) (getMoney() + b.getValue() * taxRate));
        }
        EventLog.getInstance().logEvent(new Event(String.format("Money updated to %s", money)));
    }

    public void updateEnergyUsage() {
        energyUsage = map.getNumOfBuildings() * BUILDING_ENERGY_USAGE;
    }

    public void updateEnergyCapacity() {
        int energy = 0;
        for (Building building : map.getBuildingsFromType(BuildingType.POWER_PLANT)) {
            energy += ((PowerPlant) building).getProductionRate();
        }
        energyCapacity = energy;
    }

    /**
     * Buy a building, using its property value as its cost.
     *
     * @param b The building to buy.
     * @throws InsufficientMoneyException The city doesn't have enough money to buy the building.
     */
    public void buyBuilding(Building b) throws InsufficientMoneyException {
        if (b.getValue() <= getMoney()) {
            EventLog.getInstance().logEvent(new Event(String.format("A building was bought for %s", b.getValue())));
            map.addBuilding(b);
            setMoney(getMoney() - b.getValue());
        } else {
            throw new InsufficientMoneyException();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        jsonObject.put("population", getPopulation());
        jsonObject.put("money", getMoney());
        jsonObject.put("attraction", getAttraction());
        jsonObject.put("taxRate", getTaxRate());
        jsonObject.put("map", map.toJson());

        return jsonObject;
    }

    @Override
    public Summary getSummary() {
        Summary summary = new Summary(getName(), String.format("The city of %s", getName()));
        summary.put("Population", String.valueOf(getPopulation()));
        summary.put("Money", String.valueOf(getMoney()));
        summary.put("Attraction", String.format("%.2f", getAttraction()));
        summary.put("Tax rate", String.format("%.2f%%", getTaxRate() * 100));
        summary.put("Buildings", String.valueOf(getMap().getNumOfBuildings()));
        summary.put("Worker capacity", String.valueOf(getWorkerCapacity()));
        summary.put("Housing capacity", String.valueOf(getHousingCapacity()));
        summary.put("Energy usage", String.valueOf(getEnergyUsage()));
        summary.put("Energy capacity", String.valueOf(getEnergyCapacity()));
        return summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getMap() {
        return map;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public double getAttraction() {
        return attraction;
    }

    public void setAttraction(double attraction) {
        this.attraction = attraction;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public int getHousingCapacity() {
        return housingCapacity;
    }

    public int getWorkerCapacity() {
        return workerCapacity;
    }

    public int getEnergyUsage() {
        return energyUsage;
    }

    public int getEnergyCapacity() {
        return energyCapacity;
    }

}
