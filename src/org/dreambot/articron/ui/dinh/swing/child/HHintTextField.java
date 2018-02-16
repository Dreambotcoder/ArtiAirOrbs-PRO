package org.dreambot.articron.ui.dinh.swing.child;

import javax.swing.*;
import java.awt.*;

/**
 * Created by: Niklas Date: 18.10.2017 Alias: Dinh Time: 23:30
 */

public class HHintTextField extends JTextField {

    private String info;

    public HHintTextField(String info, Color background, Color foreground) {
        this.info = info;
        setBorder(null);
        setForeground(foreground);
        setBackground(background);
        setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getText().length() == 0) {
            int h = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            int c0 = getBackground().getRGB();
            int c1 = getForeground().getRGB();
            int m = 0xfefefefe;
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
            g.setColor(new Color(c2, true));
            g.drawString(info, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }

    @Override
    public String toString() {
        return getText();
    }
}
