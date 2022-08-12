package ui.components.graphics;

import model.Map;
import model.Position;
import model.buildings.Building;
import model.exceptions.BuildingNotFoundException;
import ui.CityEditor;
import ui.util.ColorMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Class that is responsible for displaying a visual representation of a {@link model.City} map.
 */
public class MapView extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(216, 233, 168);
    private static final int HIGHLIGHT_SIZE = 2;
    private static final int ROW_COUNT = 25;
    private static final int COLUMN_COUNT = 25;
    private final CityEditor editor;
    private final Buffer buffer;
    private int cellWidth;
    private int cellHeight;
    private EditMode mode;
    private Map map;
    private Building selectedBuilding;

    /**
     * Creates a new graphics panel representing a {@link model.City} map.
     *
     * @param editor The parent {@link CityEditor}.
     */
    public MapView(CityEditor editor) {
        super();
        this.editor = editor;
        this.map = editor.getCity().getMap();
        this.buffer = new Buffer();

        init();
        buffer.clear(BACKGROUND_COLOR);
    }

    /**
     * Create buffer and initialize graphics panel and fields.
     */
    private void init() {
        enableSelectMode();

        cellWidth = buffer.getWidth() / COLUMN_COUNT;
        cellHeight = buffer.getHeight() / ROW_COUNT;

        MapMouseListener listener = new MapMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    /**
     * Draws all buildings in the city onto the buffer.
     */
    private void drawMap() {
        List<Building> buildings = map.getBuildings();
        for (Building b : buildings) {
            Position screenPosition = getBuildingCoordinates(b);
            Color buildingColor = ColorMap.getBuildingColor(b.getBuildingType());
            buffer.setColor(buildingColor);
            buffer.fillRect(screenPosition.getX(), screenPosition.getY(), cellWidth, cellHeight);
        }
        buffer.resetBrush();
    }

    /**
     * Highlights (puts a border around) the current
     * selected building on the map.
     */
    private void highlightSelectedBuilding() {
        if (selectedBuilding == null) {
            return;
        }

        Position p = getBuildingCoordinates(selectedBuilding);
        int x = p.getX();
        int y = p.getY();

        buffer.setStrokeSize(HIGHLIGHT_SIZE);
        buffer.drawRect(x, y, cellWidth, cellHeight);
        buffer.resetBrush();
    }

    /**
     * Draws a "building placer", an outline of the building image
     * that follows the mouse cursor, helping show where a building
     * is about to be placed.
     *
     * @param x The x grid coordinate of the placer.
     * @param y The y grid coordinate of the placer.
     */
    private void drawBuildingPlacer(int x, int y) {
        Position bufferPos = buffer.scaleToBuffer(x, y, getWidth(), getHeight());
        Position snapPos = snapToNearestCell(bufferPos.getX(), bufferPos.getY());

        buffer.setColor(ColorMap.getBuildingColor(editor.getAddBuildingType()));
        buffer.setStrokeSize(HIGHLIGHT_SIZE);
        buffer.drawRect(snapPos.getX(), snapPos.getY(), cellWidth, cellHeight);

        buffer.resetBrush();
    }

    /**
     * Converts the building's "virtual" position
     * into coordinates relative to the buffer.
     *
     * @param b The building to get the position of.
     */
    private Position getBuildingCoordinates(Building b) {
        Position p = b.getPosition();
        return new Position(p.getX() * cellWidth, p.getY() * cellHeight);
    }

    /**
     * Converts a mouse position into a grid position.
     *
     * @param x The mouse's x screen coordinate.
     * @param y The mouse's y screen coordinate.
     */
    private Position getClickedPosition(int x, int y) {
        int gridX = x / (getWidth() / COLUMN_COUNT);
        int gridY = y / (getHeight() / ROW_COUNT);
        return new Position(gridX, gridY);
    }

    /**
     * Sets the current selected building to the one that is under
     * the mouse cursor when it is clicked.
     *
     * @param x The mouse's x screen coordinate.
     * @param y The mouse's y screen coordinate.
     */
    private Building getClickedBuilding(int x, int y) throws BuildingNotFoundException {
        Position gridPos = getClickedPosition(x, y);
        return map.getBuildingAtPosition(gridPos);
    }

    /**
     * Returns the top-left coordinate of the cell
     * closest to the given position (relative to the buffer).
     *
     * @param x The x buffer coordinate.
     * @param y The y buffer coordinate.
     */
    private Position snapToNearestCell(int x, int y) {
        int cellX = x / cellWidth;
        int cellY = y / cellHeight;
        return new Position(cellX * cellWidth, cellY * cellHeight);
    }

    /**
     * Initiate adding a building when add mode is enabled
     * and the mouse is clicked on the map.
     *
     * @param x The x buffer coordinate.
     * @param y The y buffer coordinate.
     */
    private void addBuildingAtPosition(int x, int y) {
        enableSelectMode();
        Position gridPos = getClickedPosition(x, y);
        editor.addBuilding(gridPos);
    }

    /**
     * Update the selected building when the mouse is clicked,
     * given the mouse cursor's position.
     *
     * @param x The x mouse coordinate.
     * @param y The y mouse coordinate.
     */
    public void setSelectedBuilding(int x, int y) {
        try {
            selectedBuilding = getClickedBuilding(x, y);
        } catch (BuildingNotFoundException exception) {
            selectedBuilding = null;
        }
    }

    public void setSelectedBuilding(Building b) {
        selectedBuilding = b;
    }

    /**
     * Set the edit mode to {@link EditMode#ADD}.
     */
    public void enableAddMode() {
        mode = EditMode.ADD;
        resetSelectedBuilding();
    }

    /**
     * Set the edit mode to {@link EditMode#SELECT}.
     */
    public void enableSelectMode() {
        mode = EditMode.SELECT;
    }

    /**
     * Clears the current selected building.
     */
    private void resetSelectedBuilding() {
        selectedBuilding = null;
        paint();
    }

    /**
     * Update the map view's graphics.
     */
    public void update() {
        map = editor.getCity().getMap();
        paint();
    }

    /**
     * Paint the buffer onto the graphics panel
     *
     * @param g The graphics object to paint to.
     */
    @Override
    public void paint(Graphics g) {
        if (g == null) {
            return;
        }
        drawMap();

        if (mode.equals(EditMode.ADD)) {
            Point mousePos = getMousePosition();
            if (mousePos != null) {
                drawBuildingPlacer(mousePos.x, mousePos.y);
            }
        } else if (mode.equals(EditMode.SELECT)) {
            highlightSelectedBuilding();
        }

        drawBuffer(g);
    }

    /**
     * Wrapper for {@link MapView#paint(Graphics)}, paints on current graphics object for the panel,
     * clears buffer before painting
     */
    private void paint() {
        buffer.clear(BACKGROUND_COLOR);
        paint(getGraphics());
    }

    /**
     * Draws the current buffer image onto the given graphics object.
     * MODIFIES g.
     *
     * @param g The graphics object to draw to.
     */
    private void drawBuffer(Graphics g) {
        g.drawImage(buffer.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Mouse event listener for the graphics panel.
     */
    private class MapMouseListener extends MouseAdapter {
        /**
         * Get the building under the mouse cursor when it is clicked,
         * and perform appropriate action depending on the edit mode.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            setSelectedBuilding(e.getX(), e.getY());

            switch (mode) {
                case ADD:
                    addBuildingAtPosition(e.getX(), e.getY());
                case SELECT:
                    editor.setSelectedBuilding(selectedBuilding);
            }

            paint();
        }

        /**
         * Draw a building placer if in add mode.
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            paint();
        }

        /**
         * Behave the same as when mouse is moved.
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            mouseMoved(e);
        }
    }
}
