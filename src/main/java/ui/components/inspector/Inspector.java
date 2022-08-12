package ui.components.inspector;

import model.Summary;
import ui.UIConstants;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.Map;

import static ui.UIConstants.STANDARD_MARGIN;

/**
 * A base inspector panel that can display styled text.
 */
public class Inspector extends JTextPane {
    SimpleAttributeSet attributes;

    /**
     * Create a new inspect panel with default text.
     */
    public Inspector() {
        super();
        setEditable(false);
        setHighlighter(null);
        setMargin(new Insets(STANDARD_MARGIN, STANDARD_MARGIN, STANDARD_MARGIN, STANDARD_MARGIN));

        reset();
        setDefaultText();
    }

    /**
     * Append a string to the inspector panel.
     *
     * @param s The string to append.
     */
    protected void appendString(String s) {
        Document document = getStyledDocument();
        try {
            document.insertString(document.getLength(), s, attributes);
        } catch (BadLocationException e) {
            // continue
        }
    }

    /**
     * Display the name and description of a summary.
     *
     * @param summary The {@link Summary} who's name and description should be displayed.
     */
    protected void displaySummaryHeader(Summary summary) {
        resetStyle();
        setStyleFont(UIConstants.HEADER_FONT);
        appendString(summary.getName() + "\n");
        setStyleFont(UIConstants.ITALIC_FONT);
        appendString(summary.getDescription() + "\n\n");
        resetStyle();
    }

    /**
     * Display the property fields of the given summary.
     *
     * @param summary The {@link Summary} that should be displayed.
     */
    protected void displaySummaryProperties(Summary summary) {
        resetStyle();
        for (Map.Entry<String, String> entry : summary.getFields().entrySet()) {
            appendString(String.format("%s: %s%n", entry.getKey(), entry.getValue()));
        }
    }

    /**
     * Set the text to the default placeholder text.
     */
    protected void setDefaultText() {
        reset();
        setStyleFont(UIConstants.ITALIC_FONT);
    }

    /**
     * Fully resets inspector: clears text and resets style properties.
     */
    protected void reset() {
        clear();
        resetStyle();
    }

    /**
     * Clear inspector panel text.
     */
    protected void clear() {
        setText("");
    }

    /**
     * Reset style attributes and font.
     */
    protected void resetStyle() {
        attributes = new SimpleAttributeSet();
        setCharacterAttributes(attributes, true);
        setStyleFont(UIConstants.REGULAR_FONT);
    }

    /**
     * Set the font for the style attributes.
     *
     * @param f The font that should be set for the style attributes.
     */
    protected void setStyleFont(Font f) {
        StyleConstants.setFontSize(attributes, f.getSize());
        StyleConstants.setFontFamily(attributes, f.getFamily());
        if (f.isBold()) {
            StyleConstants.setBold(attributes, true);
        }
        if (f.isItalic()) {
            StyleConstants.setBold(attributes, false);
            StyleConstants.setItalic(attributes, true);
        }
    }

    /**
     * Set the current text color.
     *
     * @param c The new text color.
     */
    protected void setColor(Color c) {
        StyleConstants.setForeground(attributes, c);
    }
}
