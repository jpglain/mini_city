package ui.components;

import model.buildings.Building;
import model.buildings.BuildingType;
import ui.CityEditor;
import ui.components.inspector.BuildingInspector;
import ui.components.list.ListView;

import javax.swing.*;

/**
 * The buildings panel manages components for viewing information
 * about buildings and adding and removing new buildings.
 */
public class BuildingsPanel extends JSplitPane {
    private final CityEditor editor;
    private BuildingsEditor buildingsEditor;
    private BuildingInspector inspector;
    private ListView listView;

    /**
     * Create a new building panel.
     *
     * @param editor The parent {@link CityEditor}.
     */
    public BuildingsPanel(CityEditor editor) {
        super(JSplitPane.VERTICAL_SPLIT);
        this.editor = editor;

        init();
    }

    /**
     * Initialize and add UI components.
     */
    private void init() {
        buildingsEditor = new BuildingsEditor(this);
        inspector = new BuildingInspector();

        listView = new ListView(this, editor.getCity().getMap());
        JScrollPane listScrollPane = new JScrollPane(listView);
        listScrollPane.setBorder(BorderFactory.createEmptyBorder());

        JSplitPane subPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        subPanel.setTopComponent(inspector);
        subPanel.setBottomComponent(listScrollPane);
        subPanel.setResizeWeight(0.7);

        setTopComponent(buildingsEditor);
        setBottomComponent(subPanel);
        setResizeWeight(0.1);
    }

    /**
     * Set the building that is displayed in the inspector.
     *
     * @param b The building to be displayed.
     */
    public void setInspectorBuilding(Building b) {
        inspector.displayBuilding(b);
    }

    /**
     * Set the building displayed in the list view.
     */
    public void setListViewBuilding(Building b) {
        listView.setSelectedBuilding(b);
    }

    /**
     * Update the edit panel with the given selected building.
     *
     * @param b The building to update the panel with.
     */
    public void setSelectedBuilding(Building b) {
        setInspectorBuilding(b);
        setListViewBuilding(b);
    }

    /**
     * Update the editor's selected building.
     *
     * @param b The building to select.
     */
    public void updateSelectedBuilding(Building b) {
        editor.setSelectedBuilding(b);
    }

    /**
     * Update the display to properly reflect program state.
     */
    public void update() {
        updateMoneyDisplay(editor.getCity().getMoney());
        updateListView();
    }

    /**
     * Update the add panel's money label.
     *
     * @param money The value of money to be displayed.
     */
    public void updateMoneyDisplay(int money) {
        buildingsEditor.setMoney(money);
    }

    /**
     * Update the list view.
     */
    public void updateListView() {
        listView.setMap(editor.getCity().getMap());
        listView.update();
    }

    /**
     * Enable "add mode" in the editor.
     *
     * @param buildingType The type of building to be added.
     */
    public void enableAddMode(BuildingType buildingType) {
        editor.enableAddMode(buildingType);
    }

    /**
     * Enable remove mode in the editor.
     */
    public void removeSelectedBuilding() {
        editor.removeSelectedBuilding();
    }

    /**
     * Display the buying building in the building inspector.
     *
     * @param b The building to be bought.
     */
    public void setBuyingBuilding(Building b) {
        inspector.setBuyingBuilding(b);
    }

    /**
     * Display a cost error in the building inspector.
     */
    public void showCostError() {
        inspector.showCostError();
    }

    /**
     * Display the remove error in the building inspector.
     */
    public void showRemoveError() {
        inspector.showRemoveError();
    }
}
