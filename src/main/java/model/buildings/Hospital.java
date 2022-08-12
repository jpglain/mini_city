package model.buildings;

import model.Position;
import model.Summary;
import org.json.JSONObject;

/**
 * Represents a hospital with a number of ambulances it can dispatch.
 */
public class Hospital extends Building {
    private int ambulances;

    /**
     * Create a new hospital.
     *
     * @param name     The name of the new building.
     * @param value    The initial value of new the building.
     * @param position The position of the new building.
     */
    public Hospital(String name, int value, Position position) {
        super(name, value, position);
        type = BuildingType.HOSPITAL;
        this.ambulances = 0;
    }

    /**
     * Create a new hospital object from building json data.
     *
     * @param json The json object to parse from.
     */
    public static Hospital fromJson(JSONObject json) {
        String name = json.getString("name");
        int value = json.getInt("value");
        Hospital hospital = new Hospital(name, value, positionFromJson(json));
        hospital.setAmbulances(json.getInt("ambulances"));
        return hospital;
    }

    public int getAmbulances() {
        return ambulances;
    }

    public void setAmbulances(int ambulances) {
        this.ambulances = ambulances;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("ambulances", getAmbulances());
        return jsonObject;
    }

    @Override
    public Summary getSummary() {
        Summary summary = super.getSummary();
        summary.setDescription("A hospital dispatches ambulances to deal with medical emergencies.");
        summary.put("Current ambulances", String.valueOf(getAmbulances()));
        return summary;
    }
}
