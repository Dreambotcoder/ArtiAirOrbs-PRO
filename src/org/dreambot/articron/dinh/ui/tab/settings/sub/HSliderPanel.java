package org.dreambot.articron.dinh.ui.tab.settings.sub;

import org.dreambot.articron.dinh.swing.HPanel;
import org.dreambot.articron.dinh.swing.child.HSlider;

import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 15:05
 */

public class HSliderPanel extends HPanel {

    private HSlider slider;
    private ItemDisplay display;

    public HSliderPanel(Border border, int orientation, int min, int max, int initial) {
        super(new BorderLayout(), border);
        add(slider = new HSlider(orientation, min, max, initial), BorderLayout.SOUTH);
        add(display = new ItemDisplay(567, 564), BorderLayout.CENTER);
        slider.addChangeListener(listener -> {
            int amount = getSelected();
            display.updateLeft(amount);
            display.updateRight(amount * 3);
        });
    }

    public int getSelected() {
        return slider.getValue();
    }

}
