package ui.components;

import model.buildings.BuildingType;
import ui.UIConstants;
import ui.components.button.RemoveButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static ui.UIConstants.STANDARD_MARGIN;

/**
 * Manages components related to adding and removing buildings in a {@link model.City}.
 */
public class BuildingsEditor extends JPanel {
    private final BuildingsPanel buildingsPanel;
    private JLabel moneyLabel;

    /**
     * Create a new building editor.
     *
     * @param buildingsPanel The parent {@link BuildingsPanel}.
     */
    public BuildingsEditor(BuildingsPanel buildingsPanel) {
        super();
        this.buildingsPanel = buildingsPanel;
        init();
    }

    /**
     * Initialize UI components.
     */
    private void init() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel addLabel = new JLabel("Add a new building");
        addLabel.setFont(UIConstants.HEADER_FONT);
        addLabel.setBorder(new EmptyBorder(STANDARD_MARGIN, STANDARD_MARGIN, STANDARD_MARGIN, STANDARD_MARGIN));

        BuildingSelector selector = new BuildingSelector(this);
        JScrollPane selectorScrollPane = new JScrollPane(selector);
        selectorScrollPane.setBorder(BorderFactory.createEmptyBorder());

        moneyLabel = new JLabel();
        moneyLabel.setFont(UIConstants.BOLD_FONT);
        moneyLabel.setBorder(new EmptyBorder(STANDARD_MARGIN, STANDARD_MARGIN, STANDARD_MARGIN, STANDARD_MARGIN));

        RemoveButton removeButton = new RemoveButton(this);

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BorderLayout());
        subPanel.setBackground(Color.WHITE);
        subPanel.add(moneyLabel, BorderLayout.PAGE_START);
        subPanel.add(removeButton, BorderLayout.PAGE_END);

        add(addLabel, BorderLayout.PAGE_START);
        add(selectorScrollPane, BorderLayout.CENTER);
        add(subPanel, BorderLayout.PAGE_END);
    }

    /**
     * Set the city's money that is displayed in this panel.
     *
     * @param money The city's new money value.
     */
    public void setMoney(int money) {
        moneyLabel.setText(String.format("Money: %s", money));
    }

    /**
     * Enable "add building mode" in the edit panel.
     *
     * @param buildingType The type of building that should be added.
     */
    public void enableAddMode(BuildingType buildingType) {
        buildingsPanel.enableAddMode(buildingType);
    }

    /**
     * Enable "remove building mode" in the edit panel.
     */
    public void removeSelectedBuilding() {
        buildingsPanel.removeSelectedBuilding();
    }
}
