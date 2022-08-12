package model.buildings;

import model.Position;
import model.Summary;
import org.json.JSONObject;

/**
 * Represents a park that can increase the value of adjacent buildings.
 */
public class Park extends Building {
    private final double valueModifier;

    /**
     * Create a new park.
     *
     * @param name          The name of the new building.
     * @param value         The initial value of new the building.
     * @param valueModifier The amount this park increases surrounding property value.
     * @param position      The position of the new building.
     */
    public Park(String name, int value, double valueModifier, Position position) {
        super(name, value, position);
        type = BuildingType.PARK;
        this.valueModifier = valueModifier;
    }

    /**
     * Create a new park object from building json data
     *
     * @param json The json object to parse from.
     */
    public static Park fromJson(JSONObject json) {
        String name = json.getString("name");
        int value = json.getInt("value");
        double valueModifier = json.getDouble("valueModifier");
        return new Park(name, value, valueModifier, positionFromJson(json));
    }

    public double getValueModifier() {
        return valueModifier;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("valueModifier", getValueModifier());
        return jsonObject;
    }

    @Override
    public Summary getSummary() {
        Summary summary = super.getSummary();
        summary.setDescription("A park increases happiness and can increase property value of surrounding buildings.");
        summary.put("Value modifier", String.valueOf(getValueModifier()));
        return summary;
    }
}
