package org.dreambot.articron.ui.dinh.ui.tab.settings.sub;

import org.dreambot.articron.ui.dinh.swing.HPanel;
import org.dreambot.articron.ui.dinh.ui.tab.settings.SettingPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 20:04
 */

public class MandatorySettings extends HPanel {

    private HSliderPanel slider;
    private FoodSelection foodSelection;

    public MandatorySettings(Border border) {
        super(new GridLayout(0, 2, 5, 0), border);

        add(slider = new HSliderPanel(SettingPanel.getBorder("Withdraw per Trip"), JSlider.HORIZONTAL, 1, 27, 27));
        setPreferredSize(slider.getPreferredSize());

        add(foodSelection = new FoodSelection(BorderFactory.createCompoundBorder(SettingPanel.getBorder("Food to use"), BorderFactory.createEmptyBorder(2, 2, 2, 2))));
    }

    public HSliderPanel getSlider() {
        return slider;
    }

    public FoodSelection getFoodSelection() {
        return foodSelection;
    }
}
