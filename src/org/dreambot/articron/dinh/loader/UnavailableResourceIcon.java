package org.dreambot.articron.dinh.loader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Niklas on 08.05.2017.
 */
public class UnavailableResourceIcon extends BufferedImage {

    public UnavailableResourceIcon() {
        super(36, 36, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = createGraphics();
        graphics2D.setFont(new Font("Arial", Font.BOLD, 10));
        FontMetrics metrics = graphics2D.getFontMetrics();
        int stringX = (36 - metrics.stringWidth("Image")) / 2;
        graphics2D.drawString("Image", stringX, 11);
        stringX = (36 - metrics.stringWidth("not")) / 2;
        graphics2D.drawString("not", stringX, 21);
        stringX = (36 - metrics.stringWidth("found")) / 2;
        graphics2D.drawString("found", stringX, 31);
    }
}
