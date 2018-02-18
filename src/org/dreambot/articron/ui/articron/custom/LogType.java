package org.dreambot.articron.ui.articron.custom;

import org.dreambot.articron.ui.dinh.loader.HImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public enum LogType {
    ERROR("Script error log", new Color(231, 76, 60),HImageLoader.loadImage("http://www.pvhc.net/img142/lbsjptugcqsghgcrvaof.png")),
    PK("Player killing log", new Color(230, 126, 34), HImageLoader.loadImage("https://vignette.wikia.nocookie.net/2007scape/images/a/a4/Dragon_sword_detail.png/revision/latest?cb=20170106165110")),
    BANK_WITHDRAW("Banking log", new Color(46, 204, 113), HImageLoader.loadImage("https://cdn3.iconfinder.com/data/icons/currency-17/24/Pkr-512.png")),
    OTHER("Global log", new Color(52, 152, 219), HImageLoader.loadImage("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Logo_informations.svg/2000px-Logo_informations.svg.png"));

    LogType(String title, Color titleColor, BufferedImage image) {
        this.title = title;
        this.titleColor = titleColor;
        this.image = image;
    }

    private String title;
    private Color titleColor;
    private BufferedImage image;

    public String getTitle() {
        return title;
    }

    public Color getTitleColor() {
        return titleColor;
    }

    public BufferedImage getImage() {
        return image;
    }
}
