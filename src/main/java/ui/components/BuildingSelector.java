package ui.components;

import model.buildings.Building;
import model.buildings.BuildingType;
import ui.components.button.BuildingButton;
import ui.util.ColorMap;

import javax.swing.*;
import java.awt.*;

/**
 * A building selector displays possible buildings that can be bought
 * and added to the city
 */
public class BuildingSelector extends JPanel {
    private final BuildingsEditor buildingsEditor;

    /**
     * Creates a new building selector.
     *
     * @param buildingsEditor The parent {@link BuildingsEditor}.
     */
    public BuildingSelector(BuildingsEditor buildingsEditor) {
        super();
        this.buildingsEditor = buildingsEditor;
        init();
    }

    /**
     * Initialize and add components.
     */
    private void init() {
        GridLayout grid = new GridLayout(3, 3);
        setLayout(grid);
        setBackground(Color.WHITE);

        addBuildings();
    }

    /**
     * Add building buttons for all buildings in the building index.
     */
    private void addBuildings() {
        for (BuildingType type : BuildingType.values()) {
            addBuildingButton(type, Building.typeToString(type), ColorMap.getBuildingColor(type));
        }
    }

    /**
     * Add a new building button.
     *
     * @param type  The building type this button represents.
     * @param text  The text (name) of the button.
     * @param color The color of the button.
     */
    private void addBuildingButton(BuildingType type, String text, Color color) {
        add(new BuildingButton(this, type, text, color));
    }

    /**
     * Handler that's called when a building button is called,
     *
     * @param buildingType The building type the button represents.
     */
    public void buttonClicked(BuildingType buildingType) {
        buildingsEditor.enableAddMode(buildingType);
    }
}
