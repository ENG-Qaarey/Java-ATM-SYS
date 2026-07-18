package ATMTuto;

import java.awt.Component;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;

/**
 * Outline-only rounded border. Do not fill here — paintBorder runs after
 * the component paints its text, so a fill would cover typed input and labels.
 */
public class RoundedBorder extends AbstractBorder {
    private final int radius;
    private final Color color;

    public RoundedBorder(int radius, Color color, Color fillColorIgnored) {
        this(radius, color);
    }

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        // Enough room for the stroke + text baseline without clipping
        return new Insets(6, 10, 6, 10);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.top = 6;
        insets.left = 10;
        insets.right = 10;
        insets.bottom = 6;
        return insets;
    }
}
