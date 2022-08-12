package ui;

import model.City;
import model.Position;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.exceptions.InsufficientMoneyException;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.components.BuildingsPanel;
import ui.components.CityPanel;
import ui.components.graphics.MapView;
import ui.util.BuildingCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An editor that contains components for viewing and editing {@link City} properties.
 */
public class CityEditor extends JFrame {
    private City city;
    private MapView mapView;
    private BuildingsPanel buildingsPanel;
    private CityPanel cityPanel;
    private Building selectedBuilding;
    private BuildingType addBuildingType;
    private Building potentialBuilding;

    /**
     * Create a new {@link CityEditor} for the given {@link City}
     *
     * @param city The {@link City} this editor is associated with.
     */
    public CityEditor(City city) {
        this.city = city;
    }

    /**
     * Initialize the UI and run the {@link CityEditor}.
     */
    public void run() {
        init();
        // call update once to initialize ui
        update();
    }

    /**
     * Initialize JFrame's properties and add components.
     */
    private void init() {
        setTitle(UIConstants.WINDOW_TITLE);
        setSize(UIConstants.WIDTH, UIConstants.HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponents();
        setVisible(true);
        setupTimer();

        // print the event log when the window is closing
        addWindowListener(new WindowAdapter() {
            // EFFECTS: print the event log when the editor is closed
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                CitySimulator.printLog();
            }
        });
    }

    /**
     * Initialize and add components.
     */
    private void addComponents() {
        mapView = new MapView(this);
        buildingsPanel = new BuildingsPanel(this);
        cityPanel = new CityPanel(this);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(buildingsPanel);
        tabbedPane.add(cityPanel);
        JLabel editLabel = new JLabel("BUILDINGS");
        JLabel viewLabel = new JLabel("CITY");
        editLabel.setFont(UIConstants.HEADER_FONT);
        viewLabel.setFont(UIConstants.HEADER_FONT);
        tabbedPane.setTabComponentAt(0, editLabel);
        tabbedPane.setTabComponentAt(1, viewLabel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane, mapView);
        splitPane.setResizeWeight(0.00005);

        add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Sets up the timer that periodically updates the {@link City} and gui.
     */
    private void setupTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateCity();
                updateCityDisplay();
            }
        }, 0, 5000);
    }

    /**
     * Update the actively selected building in appropriate info panels.
     *
     * @param b The {@link Building} to select.
     */
    public void setSelectedBuilding(Building b) {
        selectedBuilding = b;
        buildingsPanel.setSelectedBuilding(b);
        mapView.setSelectedBuilding(b);
        mapView.update();
    }

    /**
     * Removes the currently selected building.
     */
    public void removeSelectedBuilding() {
        if (selectedBuilding == null) {
            buildingsPanel.showRemoveError();
            return;
        }
        city.getMap().removeBuilding(selectedBuilding);
        clearSelectedBuilding();
    }

    /**
     * Clear the selected building (set to null) and update.
     */
    private void clearSelectedBuilding() {
        selectedBuilding = null;
        update();
    }

    /**
     * Enable "add building mode" in the editor
     *
     * @param buildingType The building type that should be added.
     */
    public void enableAddMode(BuildingType buildingType) {
        addBuildingType = buildingType;
        setSelectedBuilding(null);
        mapView.enableAddMode();
        potentialBuilding = BuildingCreator.createBuildingFromType(addBuildingType);
        buildingsPanel.setBuyingBuilding(potentialBuilding);
    }

    /**
     * Add a new building to the city at the given position.
     *
     * @param position The position that the new building will be placed in.
     */
    public void addBuilding(Position position) {
        if (!city.getMap().checkBuildingCollision(position)) {
            potentialBuilding.setPosition(position);
            try {
                city.buyBuilding(potentialBuilding);
                update();
            } catch (InsufficientMoneyException e) {
                buildingsPanel.showCostError();
            }
        } else {
            setSelectedBuilding(city.getMap().getBuildingAtPosition(position));
        }
    }

    /**
     * Save the city to a file.
     *
     * @throws IOException The JSON file couldn't be saved to disk.
     */
    public void saveCity() throws IOException {
        JsonWriter writer = new JsonWriter(CitySimulator.DATA_PATH);
        writer.writeAndClose(city);
        update();
    }

    /**
     * Load the city from a file.
     *
     * @throws IOException The JSON file couldn't be read.
     */
    public void loadCity() throws IOException {
        JsonReader reader = new JsonReader(CitySimulator.DATA_PATH);
        city = reader.read();
        clearSelectedBuilding();
    }

    /**
     * Update this editor's {@link City}.
     */
    private void updateCity() {
        city.applyRules();
        city.update();
    }

    /**
     * Update gui to display changes made to city excluding building changes,
     * i.e. no updating the map or list view.
     */
    private void updateCityDisplay() {
        buildingsPanel.updateMoneyDisplay(city.getMoney());
        cityPanel.displayCity();
    }

    /**
     * Update the entire gui to properly reflect city state.
     */
    public void update() {
        setSelectedBuilding(selectedBuilding);

        buildingsPanel.update();
        cityPanel.update();
    }

    public BuildingType getAddBuildingType() {
        return addBuildingType;
    }

    public City getCity() {
        return city;
    }
}
