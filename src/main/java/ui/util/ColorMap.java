package ui.util;

import model.buildings.BuildingType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Data class that holds information about default {@link model.buildings.Building} colors.
 */
public class ColorMap {
    private static final Map<BuildingType, Color> colors = loadColors();

    /**
     * Return a new populated color map.
     *
     * @return The color map, linking a building type to a color.
     */
    private static Map<BuildingType, Color> loadColors() {
        Map<BuildingType, Color> colorMap = new HashMap<>();
        colorMap.put(BuildingType.HOUSING, Color.ORANGE);
        colorMap.put(BuildingType.BUSINESS, Color.MAGENTA);
        colorMap.put(BuildingType.SCHOOL, Color.CYAN);
        colorMap.put(BuildingType.FIRE_HALL, Color.RED);
        colorMap.put(BuildingType.HOSPITAL, Color.BLUE);
        colorMap.put(BuildingType.PARK, Color.GREEN);
        colorMap.put(BuildingType.POWER_PLANT, Color.YELLOW);
        colorMap.put(BuildingType.LANDFILL, new Color(178, 152, 114));
        return colorMap;
    }

    /**
     * Get the color of the given building type.
     *
     * @param type The type of building.
     * @return The building type's color.
     */
    public static Color getBuildingColor(BuildingType type) {
        return colors.get(type);
    }
}
