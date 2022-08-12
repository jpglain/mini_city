package model.buildings;

import model.Position;
import model.Summary;
import org.json.JSONObject;

/**
 * A class that represents residential housing with residents.
 */
public class Housing extends Building {
    private final int residentCapacity;
    private int residents;

    /**
     * Creates new housing.
     *
     * @param name             The name of the new building.
     * @param value            The initial value of new the building.
     * @param residentCapacity The initial resident capacity of the new building.
     * @param position         The position of the new building.
     */
    public Housing(String name, int value, int residentCapacity, Position position) {
        super(name, value, position);
        type = BuildingType.HOUSING;
        this.residentCapacity = residentCapacity;
        this.residents = 0;
    }

    /**
     * Create a new housing object from building json data
     *
     * @param json The json object to parse from.
     */
    public static Housing fromJson(JSONObject json) {
        String name = json.getString("name");
        int value = json.getInt("value");
        int capacity = json.getInt("capacity");
        Housing housing = new Housing(name, value, capacity, positionFromJson(json));
        housing.setResidents(json.getInt("residents"));
        return housing;
    }

    public int getResidents() {
        return residents;
    }

    public void setResidents(int residents) {
        this.residents = residents;
    }

    public int getResidentCapacity() {
        return residentCapacity;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("residents", getResidents());
        jsonObject.put("capacity", getResidentCapacity());
        return jsonObject;
    }

    @Override
    public Summary getSummary() {
        Summary summary = super.getSummary();
        summary.setDescription("Housing that is occupied by residents.");
        summary.put("Current residents", String.valueOf(residents));
        summary.put("Resident capacity", String.valueOf(getResidentCapacity()));
        return summary;
    }
}
