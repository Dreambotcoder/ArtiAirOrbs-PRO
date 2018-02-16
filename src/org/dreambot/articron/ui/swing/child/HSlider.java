package org.dreambot.articron.ui.swing.child;

import org.dreambot.articron.ui.swing.HFrame;

import javax.swing.*;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 15:03
 */

public class HSlider extends JSlider {

    public HSlider(int orientation, int min, int max, int initial) {
        super(orientation, min, max, initial);
        setBackground(HFrame.BACKGROUND);
        setMinorTickSpacing(1);
        setPaintTicks(true);
        setPaintLabels(true);
        setFocusable(false);
        setUI(new HSliderUI(this));
    }
}
