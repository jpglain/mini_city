package ui.components;

import ui.CityEditor;
import ui.UIConstants;
import ui.components.button.BasicButton;
import ui.components.inspector.CityInspector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * A city panel displays general information about a {@link model.City}.
 */
public class CityPanel extends JPanel {
    private final CityEditor editor;
    private CityInspector inspector;
    private JTextField taxField;
    private JTextField nameField;

    /**
     * Create a new city panel.
     *
     * @param editor The parent {@link CityEditor}
     */
    public CityPanel(CityEditor editor) {
        super();
        this.editor = editor;

        init();
    }

    /**
     * Initialize and add components.
     */
    private void init() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        inspector = new CityInspector();

        addComponents();
        displayCity();
    }

    /**
     * Add child components to this component.
     */
    private void addComponents() {
        JButton updateButton = new BasicButton("Update city");
        updateButton.addActionListener(new UpdateListener());

        JButton saveButton = new BasicButton("Save city");
        saveButton.addActionListener(new SaveButtonListener());

        JButton loadButton = new BasicButton("Load city");
        loadButton.addActionListener(new LoadButtonListener());

        JSeparator separator = new JSeparator();
        separator.setOpaque(true);

        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(6, 1));
        editPanel.add(createTaxForm());
        editPanel.add(createNameForm());
        editPanel.add(updateButton);
        editPanel.add(separator);
        editPanel.add(saveButton);
        editPanel.add(loadButton);

        add(inspector, BorderLayout.CENTER);
        add(editPanel, BorderLayout.PAGE_END);
    }

    /**
     * Returns a new form to edit tax rate.
     */
    private JPanel createTaxForm() {
        JPanel taxForm = new JPanel();
        taxForm.setLayout(new GridLayout(1, 2));

        JLabel label = new JLabel("Edit tax rate (percent)");
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setFont(UIConstants.REGULAR_FONT);

        taxField = new JTextField(String.valueOf(editor.getCity().getTaxRate() * 100));
        taxField.setFont(UIConstants.REGULAR_FONT);
        taxField.addActionListener(new UpdateListener());

        taxForm.add(label);
        taxForm.add(taxField);
        return taxForm;
    }

    /**
     * Returns a new form to edit city name.
     */
    private JPanel createNameForm() {
        JPanel nameForm = new JPanel();
        nameForm.setLayout(new GridLayout(1, 2));

        JLabel label = new JLabel("Edit city name");
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setFont(UIConstants.REGULAR_FONT);

        nameField = new JTextField(editor.getCity().getName());
        nameField.setFont(UIConstants.REGULAR_FONT);
        nameField.addActionListener(new UpdateListener());

        nameForm.add(label);
        nameForm.add(nameField);
        return nameForm;
    }

    /**
     * Update display to match program state.
     */
    public void update() {
        updateTaxField();
        updateNameField();
        displayCity();
    }

    /**
     * Add child components to this component.
     */
    public void displayCity() {
        inspector.displayCity(editor.getCity());
    }

    /**
     * Update the text in the tax field.
     */
    private void updateTaxField() {
        taxField.setText(String.format("%.2f", editor.getCity().getTaxRate() * 100));
    }

    /**
     * Update the text in the name field.
     */
    private void updateNameField() {
        nameField.setText(editor.getCity().getName());
    }

    /**
     * Action listener for the save button.
     */
    private class SaveButtonListener implements ActionListener {
        /**
         * Save city on button press.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                editor.saveCity();
                inspector.showSaveSuccess();
            } catch (IOException ioException) {
                inspector.showSaveError();
            }
        }
    }

    /**
     * Action listener for the load button.
     */
    private class LoadButtonListener implements ActionListener {
        /**
         * Load city on button press.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                editor.loadCity();
                inspector.showLoadSuccess();
            } catch (IOException ioException) {
                inspector.showLoadError();
            }
        }
    }

    /**
     * Action listener for the update button.
     */
    private class UpdateListener implements ActionListener {
        /**
         * Update edited city information.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String nameFieldText = nameField.getText();
            editor.getCity().setName(nameFieldText);

            String taxFieldText = taxField.getText();
            try {
                double newTaxRate = Double.parseDouble(taxFieldText) / 100;
                if (newTaxRate < 0) {
                    update();
                    inspector.showTaxInputError();
                    return;
                }
                editor.getCity().setTaxRate(newTaxRate);
                editor.update();
            } catch (NumberFormatException exception) {
                update();
                inspector.showTaxInputError();
            }
        }
    }
}