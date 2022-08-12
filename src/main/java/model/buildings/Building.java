package model.buildings;

import model.Position;
import model.Summarizable;
import model.Summary;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that represents a building with basic properties.
 */
public abstract class Building implements Writable, Summarizable {
    private final String name;
    private int value;
    protected BuildingType type;
    private Position position;

    /**
     * Create a new building.
     *
     * @param name     The name of the new building.
     * @param value    The initial value of the building.
     * @param position The position of the building.
     */
    public Building(String name, int value, Position position) {
        type = null;
        this.name = name;
        this.value = value;
        this.position = position;
    }

    /**
     * Return a new {@link Position} instance from building json data.
     *
     * @param json The {@link JSONObject} to parse from.
     * @return The position of the parsed building.
     */
    protected static Position positionFromJson(JSONObject json) {
        JSONArray coordinates = json.getJSONArray("position");
        return new Position(coordinates.getInt(0), coordinates.getInt(1));
    }

    /**
     * Convert a building type to a display-friendly string.
     *
     * @param type The {@link BuildingType} to convert.
     * @return The display-friendly string of the building type.
     */
    public static String typeToString(BuildingType type) {
        String typeString = type.toString();
        String[] words = typeString.split("_");

        List<String> outputWords = new ArrayList<>();
        for (String word : words) {
            String output = word.charAt(0) + word.substring(1).toLowerCase();
            outputWords.add(output);
        }

        return String.join(" ", outputWords);
    }

    public BuildingType getBuildingType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        jsonObject.put("type", getBuildingType().toString());
        jsonObject.put("value", getValue());
        jsonObject.put("position", new int[]{getPosition().getX(), getPosition().getY()});
        return jsonObject;
    }

    @Override
    public Summary getSummary() {
        Summary summary = new Summary(getName(), "A normal building");
        summary.put("Property value", String.valueOf(getValue()));
        return summary;
    }
}
