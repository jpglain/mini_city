package ui.components.button;

import model.buildings.BuildingType;
import ui.components.BuildingSelector;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * A button with text and icon that represents a building.
 */
public class BuildingButton extends BasicButton {
    private final BuildingSelector selector;
    private final BuildingType type;
    private Image image;

    /**
     * Creates a new button with a building's name and
     * its icon (the color of the building on the map).
     *
     * @param selector The parent {@link BuildingSelector}
     * @param text     The button's text.
     * @param type     The {@link BuildingType} this button represents.
     * @param color    The color of the button.
     */
    public BuildingButton(BuildingSelector selector, BuildingType type, String text, Color color) {
        super(text);
        this.selector = selector;
        this.type = type;

        createImage(color);
        init();
    }

    /**
     * Create and set this button's icon image.
     *
     * @param color The color of the button's icon image.
     */
    private void createImage(Color color) {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics imageGraphics = image.getGraphics();
        imageGraphics.setColor(color);
        imageGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        this.image = image;
    }

    /**
     * Initialize UI components.
     */
    private void init() {
        setIcon(new ImageIcon(image));
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setBorder(null);
        addMouseListener(new ButtonMouseListener());
    }

    /**
     * Mouse listener for {@link BuildingButton}.
     */
    private class ButtonMouseListener extends MouseAdapter {
        /**
         * Updates the selected building of the parent selector.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            selector.buttonClicked(type);
        }

        /**
         * Set the bold font when mouse is hovering button.
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            setBorder(new LineBorder(Color.BLACK, 2, true));
        }

        /**
         * Set the regular font when the mouse leaves the button.
         */
        @Override
        public void mouseExited(MouseEvent e) {
            setBorder(null);
        }
    }
}
