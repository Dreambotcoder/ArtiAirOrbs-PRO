package org.dreambot.articron.dinh.swing.child;

import org.dreambot.articron.dinh.swing.HFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by: Niklas
 * Date: 20.09.2017
 * Alias: Dinh
 * Time: 19:07
 */

public class HCheckBox extends JCheckBox {

    public HCheckBox(String text) {
        this(text, HFrame.ELEMENT_BG);
    }

    public HCheckBox(String text, Color background) {
        super(text);
        setBackground(background);
        setForeground(HFrame.FOREGROUND);
        setBorderPainted(false);
        setFocusPainted(false);
        setFocusable(false);
    }

}
