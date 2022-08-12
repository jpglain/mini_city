package ui.components.list;

import model.Map;
import model.buildings.Building;
import ui.UIConstants;
import ui.components.BuildingsPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * An alternate way of viewing buildings in the city,
 * provides a list view of all buildings in the city
 */
public class ListView extends JPanel {
    private final BuildingsPanel buildingsPanel;
    private Map map;
    private JList<ListItem> list;

    /**
     * Create a new building list view with the given map.
     *
     * @param buildingsPanel The parent {@link BuildingsPanel}
     * @param map            The map of buildings that this list view displays.
     */
    public ListView(BuildingsPanel buildingsPanel, Map map) {
        this.buildingsPanel = buildingsPanel;
        this.map = map;
        init();
    }

    /**
     * Initialize and add components.
     */
    private void init() {
        setBackground(Color.WHITE);
        setLayout(new GridLayout(1, 1));

        list = new JList<>();
        list.setCellRenderer(new CellRenderer());
        list.addListSelectionListener(new SelectionListener());

        add(list);
        addBuildings();
    }

    /**
     * Add all buildings on the map to the list.
     */
    private void addBuildings() {
        DefaultListModel<ListItem> model = new DefaultListModel<>();
        for (Building b : map.getBuildings()) {
            model.addElement(new ListItem(b));
        }
        list.setModel(model);
    }

    /**
     * Update list view to match program state,
     * makes sure list appears the same as before.
     */
    public void update() {
        int index = list.getSelectedIndex();
        addBuildings();
        list.setSelectedIndex(index);
    }

    /**
     * Returns a list item given a list index.
     *
     * @param index The index of the building item.
     */
    private ListItem indexToListItem(int index) {
        return list.getModel().getElementAt(index);
    }

    /**
     * Set the selected building in the list view.
     *
     * @param b The building to select.
     */
    public void setSelectedBuilding(Building b) {
        if (b != null) {
            list.setSelectedIndex(map.getBuildings().indexOf(b));
        } else {
            list.clearSelection();
        }
    }

    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Selection listener handling changes in list item selections.
     */
    private class SelectionListener implements ListSelectionListener {
        /**
         * Update the building in the parent buildings panel when new item is selected.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = list.getSelectedIndex();
            if (index != -1) {
                Building b = indexToListItem(list.getSelectedIndex()).getBuilding();
                buildingsPanel.setSelectedBuilding(b);
                buildingsPanel.updateSelectedBuilding(b);
            }
        }
    }

    /**
     * Cell renderer handling how a list item is rendered.
     */
    private class CellRenderer extends DefaultListCellRenderer {
        /**
         * Return a label that visually represents a list item.
         */
        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setText(indexToListItem(index).getBuilding().getName());
            label.setFont(UIConstants.REGULAR_FONT);
            label.setBorder(BorderFactory.createEmptyBorder());
            return label;
        }
    }
}
