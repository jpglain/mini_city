package ui;

import java.awt.*;

/**
 * Class containing constants related to the user interface that is used by multiple components.
 */
public class UIConstants {
    public static final String WINDOW_TITLE = "Mini City";
    public static final int WIDTH = 1500;
    public static final int HEIGHT = 1250;
    public static final int STANDARD_MARGIN = 15;
    private static final int GUI_SCALE = 2;
    public static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 16 * GUI_SCALE);
    public static final Font REGULAR_FONT = new Font("Arial", Font.PLAIN, 12 * GUI_SCALE);
    public static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 12 * GUI_SCALE);
    public static final Font ITALIC_FONT = new Font("Arial", Font.ITALIC, 12 * GUI_SCALE);
}
