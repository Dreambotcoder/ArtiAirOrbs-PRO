package org.dreambot.articron.dinh.ui.tab.settings.sub;

import org.dreambot.articron.dinh.swing.HPanel;
import org.dreambot.articron.dinh.swing.child.HLabel;
import org.dreambot.articron.dinh.swing.child.HSlider;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 18:53
 */

public class TeleportSelection extends HPanel {
    private HSlider slider;

    public TeleportSelection(Border border, int orientation, int min, int max, int initial) {
        super(new BorderLayout(), border);
        HLabel label = new HLabel("Teleport below 90% Hitpoints", JTextField.CENTER);
        add(label, BorderLayout.NORTH);
        add(slider = new HSlider(orientation, min, max, initial), BorderLayout.SOUTH);
        slider.addChangeListener(listener -> {
            int amount = getSelected();
            label.setText("Teleport below " + amount + "% Hitpoints");
        });
        slider.setMinorTickSpacing(5);
    }

    public int getSelected() {
        return slider.getValue();
    }
}
