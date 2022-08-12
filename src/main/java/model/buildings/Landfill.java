package model.buildings;

import model.Position;
import model.Summary;
import org.json.JSONObject;

/**
 * Represents a landfill with garbage trucks and garbage capacity
 */
public class Landfill extends Building {
    private final int capacity;
    private int trucks;

    /**
     * Create a new landfill.
     *
     * @param name     The name of the new building.
     * @param value    The initial value of new the building.
     * @param capacity The garbage capacity of the new building.
     * @param position The position of the new building.
     */
    public Landfill(String name, int value, int capacity, Position position) {
        super(name, value, position);
        type = BuildingType.LANDFILL;
        this.trucks = 0;
        this.capacity = capacity;
    }

    /**
     * Create a new landfill object from building json data
     *
     * @param json The json object to parse from.
     */
    public static Landfill fromJson(JSONObject json) {
        String name = json.getString("name");
        int value = json.getInt("value");
        int capacity = json.getInt("capacity");
        Landfill landfill = new Landfill(name, value, capacity, positionFromJson(json));
        landfill.setTrucks(json.getInt("trucks"));
        return landfill;
    }

    public int getTrucks() {
        return trucks;
    }

    public void setTrucks(int trucks) {
        this.trucks = trucks;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("trucks", getTrucks());
        jsonObject.put("capacity", getCapacity());
        return jsonObject;
    }

    @Override
    public Summary getSummary() {
        Summary summary = super.getSummary();
        summary.setDescription("A landfill has trucks that it can dispatch to collect trash in your city.");
        summary.put("Current trucks", String.valueOf(getTrucks()));
        summary.put("Garbage capacity", String.valueOf(getCapacity()));
        return summary;
    }
}
