package ui.components.list;

import model.buildings.Building;
import ui.UIConstants;

import javax.swing.*;

/**
 * Represents a {@link Building} item in a list view.
 */
public class ListItem extends JLabel {
    private final Building building;

    /**
     * Create a new list item representing a building.
     *
     * @param building The {@link Building} this list item represents.
     */
    public ListItem(Building building) {
        super(building.getName());
        this.building = building;
        init();
    }

    /**
     * Initialize component properties.
     */
    private void init() {
        setFont(UIConstants.REGULAR_FONT);
        setHorizontalAlignment(SwingConstants.LEFT);
    }

    public Building getBuilding() {
        return building;
    }
}
