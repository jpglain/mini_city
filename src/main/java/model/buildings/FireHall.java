package model.buildings;

import model.Position;
import model.Summary;
import org.json.JSONObject;

/**
 * Represent a fire hall that has a certain number of fire trucks.
 */
public class FireHall extends Building {
    private int trucks;

    /**
     * Create a new fire hall.
     *
     * @param name     The name of the new building.
     * @param value    The initial value of new the building.
     * @param position The position of the new building.
     */
    public FireHall(String name, int value, Position position) {
        super(name, value, position);
        type = BuildingType.FIRE_HALL;
        trucks = 0;
    }

    /**
     * Create a new fire hall object from building json data.
     *
     * @param json The json object to parse from.
     */
    public static FireHall fromJson(JSONObject json) {
        String name = json.getString("name");
        int value = json.getInt("value");
        FireHall fireHall = new FireHall(name, value, positionFromJson(json));
        fireHall.setTrucks(json.getInt("trucks"));
        return fireHall;
    }

    public int getTrucks() {
        return trucks;
    }

    public void setTrucks(int trucks) {
        this.trucks = trucks;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("trucks", getTrucks());
        return jsonObject;
    }

    @Override
    public Summary getSummary() {
        Summary summary = super.getSummary();
        summary.setDescription("A fire hall dispatches fire trucks to deal with any fires in your city.");
        summary.put("Current trucks", String.valueOf(getTrucks()));
        return summary;
    }
}
