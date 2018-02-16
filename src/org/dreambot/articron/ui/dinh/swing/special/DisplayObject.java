package org.dreambot.articron.ui.dinh.swing.special;

import java.awt.image.BufferedImage;

public class DisplayObject {
    private BufferedImage image;
    private String text;
    private int id;

    public DisplayObject(String text, BufferedImage image, int id) {
        this.text = text;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public String toString() {
        return text;
    }
}
