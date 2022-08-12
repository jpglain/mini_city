package ui.components.button;

import ui.UIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * A basic button that applies some aesthetic changes to the default {@link JButton}.
 */
public class BasicButton extends JButton {
    /**
     * Create a new button with the given text.
     *
     * @param text The button's text.
     */
    public BasicButton(String text) {
        super(text);

        setFocusPainted(false);
        setBackground(Color.WHITE);
        setFont(UIConstants.REGULAR_FONT);
    }
}
