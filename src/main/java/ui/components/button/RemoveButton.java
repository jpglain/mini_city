package ui.components.button;

import ui.components.BuildingsEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A button that triggers removing the current selected building.
 */
public class RemoveButton extends BasicButton {
    private final BuildingsEditor buildingsEditor;

    /**
     * Create a new remove button with default text, given a parent building editor.
     */
    public RemoveButton(BuildingsEditor buildingsEditor) {
        super("Remove selected building");
        this.buildingsEditor = buildingsEditor;

        addActionListener(new RemoveButtonListener());
    }

    /**
     * Action listener for {@link RemoveButton}.
     */
    private class RemoveButtonListener implements ActionListener {
        /**
         * Remove the editor's selected building when this button is clicked.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            buildingsEditor.removeSelectedBuilding();
        }
    }
}
