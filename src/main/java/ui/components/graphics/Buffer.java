package ui.components.graphics;

import model.Position;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * An image buffer that can be drawn on.
 */
public class Buffer {
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 1000;

    private final int width;
    private final int height;
    private final BufferedImage image;
    private final Graphics2D graphics;

    /**
     * Create a new buffer with default dimensions.
     */
    public Buffer() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
    }

    /**
     * Clear the buffer to the given background color and reset the brush
     *
     * @param clearColor The color to clear the buffer with.
     */
    public void clear(Color clearColor) {
        graphics.setColor(clearColor);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        resetBrush();
    }

    /**
     * Reset buffer drawer color and stroke (brush).
     */
    public void resetBrush() {
        setStrokeSize(1);
        graphics.setColor(Color.BLACK);
    }

    /**
     * Set the stroke size of the buffer's brush.
     *
     * @param size The new brush size.
     */
    public void setStrokeSize(int size) {
        graphics.setStroke(new BasicStroke(size));
    }

    /**
     * Draw an outlined rectangle at the given position with
     * the given width and height.
     *
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @param width  The rectangle's width.
     * @param height The rectangle's height.
     */
    public void drawRect(int x, int y, int width, int height) {
        graphics.drawRect(x, y, width, height);
    }

    /**
     * // Draws a solid rectangle at the given position with
     * // the given width and height.
     *
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @param width  The rectangle's width.
     * @param height The rectangle's height.
     */
    public void fillRect(int x, int y, int width, int height) {
        graphics.fillRect(x, y, width, height);
    }


    /**
     * Takes a position on a given rect (with width and height)
     * and returns that position scaled relative to this buffer.
     *
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @param width  The rectangle's width.
     * @param height The rectangle's height.
     * @return The scaled {@link Position} in the buffer's coordinates.
     */
    public Position scaleToBuffer(int x, int y, int width, int height) {
        double scaleX = x / (double) width;
        double scaleY = y / (double) height;
        int bufferX = (int) (scaleX * getWidth());
        int bufferY = (int) (scaleY * getHeight());
        return new Position(bufferX, bufferY);
    }

    /**
     * Set the color of the buffer's brush.
     *
     * @param c The new brush color.
     */
    public void setColor(Color c) {
        graphics.setColor(c);
    }

    public Image getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
