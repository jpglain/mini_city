package persistence;

import model.City;
import model.buildings.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * A json reader that reads in a City from a json file
 * partially modelled around the JsonReader class in the
 * <a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo">JsonSerializationDemo</a>
 */
public class JsonReader {
    private final String source;

    /**
     * Create a new JSON reader.
     *
     * @param source The source JSON file.
     */
    public JsonReader(String source) {
        this.source = source;
    }

    /**
     * Creates a {@link City} object, reading data from a JSON file.
     * Adapted from the <a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo">JsonSerializationDemo</a>.
     *
     * @throws IOException Failed to read the JSON source file.
     */
    public City read() throws IOException, JSONException {
        String jsonString = readFileToString();
        JSONObject jsonObject = new JSONObject(jsonString);

        return parseCity(jsonObject);
    }

    /**
     * Reads the source file's contents.
     * Adapted from the <a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo">JsonSerializationDemo</a>.
     *
     * @return The contents of the file.
     * @throws IOException The source file's contents cannot be read.
     */
    private String readFileToString() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(stringBuilder::append);
        }

        return stringBuilder.toString();
    }

    /**
     * Parses data from json and creates and returns a new City object from it.
     * Adapted from the <a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo">JsonSerializationDemo</a>.
     *
     * @param jsonObject The {@link JSONObject} to parse.
     * @return The parsed {@link City} from the jsonObject.
     */
    private City parseCity(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        City city = new City(name);
        city.setPopulation(jsonObject.getInt("population"));
        city.setMoney(jsonObject.getInt("money"));
        city.setAttraction(jsonObject.getDouble("attraction"));
        city.setTaxRate(jsonObject.getDouble("taxRate"));
        addBuildings(city, jsonObject);

        return city;
    }

    /**
     * Adds all buildings in a json object to the given city.
     * Adapted from the <a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo">JsonSerializationDemo</a>.
     *
     * @param city       The {@link City} to add the buildings to.
     * @param jsonObject The {@link JSONObject} to parse the buildings from.
     */
    private void addBuildings(City city, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONObject("map").getJSONArray("buildings");
        for (Object json : jsonArray) {
            JSONObject buildingJson = (JSONObject) json;
            addBuilding(city, buildingJson);
        }
    }

    /**
     * Adds a building specified by json data into a given city.
     *
     * @param city       The {@link City} to add the building to.
     * @param jsonObject The {@link JSONObject} to parse the building from.
     */
    private void addBuilding(City city, JSONObject jsonObject) {
        String stringType = jsonObject.getString("type");
        BuildingType type = BuildingType.valueOf(stringType);
        Building b = getBuildingFromType(type, jsonObject);
        city.getMap().addBuilding(b);
    }

    /**
     * Create a new {@link Building} from the given type and json.
     *
     * @param type       The new building's type.
     * @param jsonObject The {@link JSONObject} to parse the building from.
     */
    private Building getBuildingFromType(BuildingType type, JSONObject jsonObject) {
        if (type.equals(BuildingType.BUSINESS)) {
            return Business.fromJson(jsonObject);
        } else if (type.equals(BuildingType.FIRE_HALL)) {
            return FireHall.fromJson(jsonObject);
        } else if (type.equals(BuildingType.HOSPITAL)) {
            return Hospital.fromJson(jsonObject);
        } else if (type.equals(BuildingType.HOUSING)) {
            return Housing.fromJson(jsonObject);
        } else if (type.equals(BuildingType.LANDFILL)) {
            return Landfill.fromJson(jsonObject);
        } else if (type.equals(BuildingType.PARK)) {
            return Park.fromJson(jsonObject);
        } else if (type.equals(BuildingType.POWER_PLANT)) {
            return PowerPlant.fromJson(jsonObject);
        } else {
            return School.fromJson(jsonObject);
        }
    }
}
