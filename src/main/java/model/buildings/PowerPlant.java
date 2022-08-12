package model.buildings;

import model.Position;
import model.Summary;
import org.json.JSONObject;

/**
 * A power plant that produces energy units at a certain rate.
 */
public class PowerPlant extends Building {
    private final int productionRate;

    /**
     * Create a new power plant.
     *
     * @param name           The name of the new building.
     * @param value          The initial value of new the building.
     * @param productionRate The rate of energy this power plant produces.
     * @param position       The position of the new building.
     */
    public PowerPlant(String name, int value, int productionRate, Position position) {
        super(name, value, position);
        type = BuildingType.POWER_PLANT;
        this.productionRate = productionRate;
    }

    /**
     * Create a new power plant object from building json data
     *
     * @param json The json object to parse from.
     */
    public static PowerPlant fromJson(JSONObject json) {
        String name = json.getString("name");
        int value = json.getInt("value");
        int productionRate = json.getInt("productionRate");
        return new PowerPlant(name, value, productionRate, positionFromJson(json));
    }

    public int getProductionRate() {
        return productionRate;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("productionRate", getProductionRate());
        return jsonObject;
    }

    @Override
    public Summary getSummary() {
        Summary summary = super.getSummary();
        summary.setDescription("A power plant generates electricity to power buildings in your city.");
        summary.put("Power production rate", String.valueOf(getProductionRate()));
        return summary;
    }
}
