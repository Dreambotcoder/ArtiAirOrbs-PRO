package org.dreambot.articron.dinh.swing.child;

import org.dreambot.articron.dinh.swing.HFrame;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 15:13
 */

public class HSliderUI extends BasicSliderUI {

    private BasicStroke stroke = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f, new float[]{1f, 2f}, 0f);

    HSliderUI(JSlider slider) {
        super(slider);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(14, 16);
    }

    @Override
    public void paintMinorTickForHorizSlider(Graphics g, Rectangle tickBounds, int x) {
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            g.setColor(HFrame.HOV_GREEN);
            g.drawLine(x, 0, x, tickBounds.height / 2);
        }
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke old = graphics2D.getStroke();
        graphics2D.setStroke(stroke);
        graphics2D.setPaint(HFrame.HOV_GREEN);
        if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
            graphics2D.drawLine(trackRect.x, trackRect.y + trackRect.height / 2, trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
        } else {
            graphics2D.drawLine(trackRect.x + trackRect.width / 2, trackRect.y, trackRect.x + trackRect.width / 2, trackRect.y + trackRect.height);
        }
        graphics2D.setStroke(old);
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        int x1 = thumbRect.x + 2;
        int x2 = thumbRect.x + thumbRect.width - 2;
        int width = thumbRect.width - 4;
        int topY = thumbRect.y + thumbRect.height / 2 - thumbRect.width / 3;
        GeneralPath shape = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        shape.moveTo(x1, topY);
        shape.lineTo(x2, topY);
        shape.lineTo((x1 + x2) / 2, topY + width);
        shape.closePath();
        graphics2D.setPaint(HFrame.LOGO_GREEN);
        graphics2D.fill(shape);
        Stroke old = graphics2D.getStroke();
        graphics2D.setStroke(new BasicStroke(2f));
        graphics2D.setPaint(HFrame.SEL_GREEN);
        graphics2D.draw(shape);
        graphics2D.setStroke(old);
    }
}
