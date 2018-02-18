package org.dreambot.articron.ui.dinh.ui.tab.settings.sub;

import org.dreambot.articron.ui.dinh.swing.HFrame;
import org.dreambot.articron.ui.dinh.swing.HPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 14:56
 */

public class TeleportSettings extends HPanel {

    private HButtons teleportOptions;
    private HButtonGroup teleportPanel;
    private TeleportSelection selection;

    public TeleportSettings(Border border) {
        super(new GridBagLayout(), border);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weighty = 1;

        add(teleportPanel = new HButtonGroup(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2), "Amulet of Glory", "POH tablet"), constraints);
        add(teleportOptions = new HButtons(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2), "Stamina potions", "Tick charge", "Pray for demons"), constraints);
        add(selection = new TeleportSelection(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2), JSlider.HORIZONTAL, 0, 100, 30), constraints);
    }

    public HButtons getAdditionalSettings() {
        return teleportOptions;
    }

    public HButtonGroup getTeleportPanel() {
        return teleportPanel;
    }

    public TeleportSelection getSelection() {
        return selection;
    }
}
