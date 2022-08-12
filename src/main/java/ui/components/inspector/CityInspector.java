package ui.components.inspector;

import model.City;
import ui.CitySimulator;
import ui.UIConstants;

import java.awt.*;

/**
 * A specialized inspector for displaying {@link City} info.
 */
public class CityInspector extends Inspector {
    /**
     * Displays information about the given {@link City}.
     *
     * @param city The city to display information about.
     */
    public void displayCity(City city) {
        reset();
        displaySummaryHeader(city.getSummary());
        displaySummaryProperties(city.getSummary());
    }

    /**
     * Show a success message when saving a city succeeds.
     */
    public void showSaveSuccess() {
        prepareSuccess();
        appendString(String.format("Successfully saved city to %s", CitySimulator.DATA_PATH));
    }

    /**
     * Show an error message when saving a city fails.
     */
    public void showSaveError() {
        prepareError();
        appendString(String.format("Failed to save city to %s", CitySimulator.DATA_PATH));
    }

    /**
     * Show a success message when loading a city succeeds.
     */
    public void showLoadSuccess() {
        prepareSuccess();
        appendString(String.format("Successfully loaded city from %s", CitySimulator.DATA_PATH));
    }

    /**
     * Show an error message when loading a city fails.
     */
    public void showLoadError() {
        prepareError();
        appendString(String.format("Failed to load city from %s", CitySimulator.DATA_PATH));
    }

    /**
     * Show an error message when an invalid tax rate is entered in the tax field.
     */
    public void showTaxInputError() {
        prepareError();
        appendString("Invalid tax rate input!");
    }

    /**
     * Prepares text and style of inspector for error message.
     */
    private void prepareError() {
        resetStyle();
        appendString(String.format("%n"));
        setStyleFont(UIConstants.HEADER_FONT);
        setColor(Color.RED);
    }

    /**
     * Prepares text and style of inspector for success message.
     */
    private void prepareSuccess() {
        resetStyle();
        appendString(String.format("%n"));
        setStyleFont(UIConstants.HEADER_FONT);
        setColor(new Color(79, 220, 79));
    }
}