package org.dreambot.articron.dinh.ui.tab.settings.sub;

import org.dreambot.articron.dinh.loader.HImageLoader;
import org.dreambot.articron.dinh.swing.HFrame;
import org.dreambot.articron.dinh.swing.HPanel;
import org.dreambot.articron.dinh.swing.child.HLabel;
import org.dreambot.articron.dinh.swing.special.RewardIcon;

import javax.swing.*;
import java.awt.*;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 15:39
 */


public class Display extends HPanel {

    private HLabel label;

    Display(int id, int amount) {
        setLayout(new BorderLayout());
        JLabel image = new JLabel(new ImageIcon(new RewardIcon(HImageLoader.loadSprite(id))));
        image.setBorder(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2));
        add(image, BorderLayout.CENTER);
        add(label = new HLabel(String.valueOf(amount), JLabel.CENTER), BorderLayout.SOUTH);
    }

    public void update(int amount) {
        label.setText(String.valueOf(amount));
    }
}