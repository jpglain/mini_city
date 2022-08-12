package ui.components.inspector;

import model.buildings.Building;
import ui.UIConstants;

import java.awt.*;

/**
 * A specialized inspector panel for displaying {@link Building} info.
 */
public class BuildingInspector extends Inspector {
    /**
     * Displays the summary of the given building in the inspector
     *
     * @param b The building to display the summary of.
     */
    public void displayBuilding(Building b) {
        reset();
        if (b == null) {
            setDefaultText();
            return;
        }

        displaySummaryHeader(b.getSummary());
        displaySummaryProperties(b.getSummary());
    }

    /**
     * Display info about a building that is about to be bought.
     *
     * @param b The building about to be bought.
     */
    public void setBuyingBuilding(Building b) {
        reset();
        setStyleFont(UIConstants.HEADER_FONT);
        appendString(Building.typeToString(b.getBuildingType()) + "\n");
        resetStyle();
        setStyleFont(UIConstants.ITALIC_FONT);
        appendString(b.getSummary().getDescription() + "\n\n");
        displayCost(b.getValue());
    }

    /**
     * Adds a line that displays the building's cost,
     * does not clear text buffer.
     *
     * @param cost The cost of the building.
     */
    public void displayCost(int cost) {
        resetStyle();
        setStyleFont(UIConstants.HEADER_FONT);
        appendString(String.format("Cost: %s", cost));
        resetStyle();
    }

    /**
     * Notify the user that they cannot buy the building because it costs too much.
     */
    public void showCostError() {
        reset();
        setColor(Color.RED);
        setStyleFont(UIConstants.HEADER_FONT);
        appendString("ERROR: Cannot buy building - insufficient money!");
    }

    /**
     * Notify the user that no building was selected when they clicked remove.
     */
    public void showRemoveError() {
        reset();
        setColor(Color.RED);
        setStyleFont(UIConstants.HEADER_FONT);
        appendString("ERROR: No building selected to remove!");
    }

    @Override
    protected void setDefaultText() {
        super.setDefaultText();
        appendString("Select a building on the map or list to interact with it.");
    }
}
