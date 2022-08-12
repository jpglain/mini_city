package model;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.exceptions.BuildingNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A map that keeps track of buildings in a {@link City}.
 */
public class Map implements Writable {
    private final List<Building> buildingList;

    /**
     * Create a new empty map.
     */
    public Map() {
        buildingList = new ArrayList<>();
    }

    /**
     * Adds a building to this map.
     *
     * @param building The building to add.
     */
    public void addBuilding(Building building) {
        buildingList.add(building);
        Event logEvent = new Event(String.format("A building (%s) was added to the map", building.getName()));
        EventLog.getInstance().logEvent(logEvent);
    }

    /**
     * Removes a building from this map.
     *
     * @param building The building to remove.
     */
    public void removeBuilding(Building building) {
        buildingList.remove(building);
        Event logEvent = new Event(String.format("A building (%s) was removed from the map", building.getName()));
        EventLog.getInstance().logEvent(logEvent);
    }

    /**
     * Get the building at the given index.
     * Requires that {@code index < getNumOfBuildings()}.
     *
     * @param index The index of the building to get.
     * @return The building at the given index.
     */
    public Building getBuilding(int index) {
        return buildingList.get(index);
    }

    /**
     * Get the building with the given name.
     * If multiple buildings have the same name, returns the first one that's found.
     *
     * @param name The name of the building to get.
     * @throws BuildingNotFoundException No building with the given name exists.
     */
    public Building getBuilding(String name) throws BuildingNotFoundException {
        for (Building b : buildingList) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        throw new BuildingNotFoundException();
    }

    /**
     * Get the building at the given position.
     *
     * @param position The position of the building to get.
     * @return The building at the given position.
     * @throws BuildingNotFoundException If no building exists at the given position.
     */
    public Building getBuildingAtPosition(Position position) throws BuildingNotFoundException {
        for (Building b : buildingList) {
            if (b.getPosition().equals(position)) {
                return b;
            }
        }

        throw new BuildingNotFoundException();
    }

    /**
     * Get the number of buildings in this map.
     *
     * @return The number of buildings in this map.
     */
    public int getNumOfBuildings() {
        return buildingList.size();
    }

    /**
     * Get all buildings in the map that match the given type.
     *
     * @param type The type of the buildings to get.
     * @return A list of buildings that match the given type.
     */
    public List<Building> getBuildingsFromType(BuildingType type) {
        List<Building> buildings = new ArrayList<>();
        for (Building b : buildingList) {
            if (b.getBuildingType().equals(type)) {
                buildings.add(b);
            }
        }
        return buildings;
    }

    /**
     * Returns whether there is a building at the given position already.
     *
     * @param position The position to check if there is a collision.
     * @return Whether or not there is a building at the given position.
     */
    public boolean checkBuildingCollision(Position position) {
        try {
            getBuildingAtPosition(position);
            return true;
        } catch (BuildingNotFoundException e) {
            return false;
        }
    }

    /**
     * Get all the buildings in this map.
     *
     * @return A list of every building in this map.
     */
    public List<Building> getBuildings() {
        return buildingList;
    }

    /**
     * Get every adjacent building to a given building.
     *
     * @param b The building to get the adjacent buildings of.
     * @return A list of adjacent buildings.
     */
    public List<Building> getAdjacentBuildings(Building b) {
        Position position = b.getPosition();
        int x = position.getX();
        int y = position.getY();
        List<Position> adjacentPositions = Arrays.asList(
                new Position(x, y + 1),
                new Position(x + 1, y),
                new Position(x, y - 1),
                new Position(x - 1, y)
        );
        return adjacentPositions.stream()
                .map(p -> {
                    try {
                        return getBuildingAtPosition(p);
                    } catch (BuildingNotFoundException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Building b : buildingList) {
            jsonArray.put(b.toJson());
        }

        jsonObject.put("buildings", jsonArray);

        return jsonObject;
    }
}
