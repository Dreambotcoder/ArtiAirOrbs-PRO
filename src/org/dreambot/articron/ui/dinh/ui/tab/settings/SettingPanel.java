package org.dreambot.articron.ui.dinh.ui.tab.settings;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.ui.dinh.swing.HFrame;
import org.dreambot.articron.ui.dinh.swing.HPanel;
import org.dreambot.articron.ui.dinh.swing.child.HCheckBox;
import org.dreambot.articron.ui.dinh.swing.child.HImageComboBox;
import org.dreambot.articron.ui.dinh.swing.special.DisplayObject;
import org.dreambot.articron.ui.dinh.ui.tab.settings.sub.MainSettings;
import org.dreambot.articron.ui.dinh.ui.tab.settings.sub.TeleportSettings;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 13:39
 */

public class SettingPanel extends HPanel {

    private MainSettings mainSettings;
    private TeleportSettings teleportSettings;
    private APIProvider api;

    public SettingPanel(APIProvider api, Border border) {
        super(new BorderLayout(), border);
        this.api = api;
        add(teleportSettings = new TeleportSettings(getBorder("Teleport")), BorderLayout.EAST);
        add(mainSettings = new MainSettings(teleportSettings.getAdditionalSettings().get(2), getBorder("Main")), BorderLayout.CENTER);

        HImageComboBox comboBox = mainSettings.getMandatorySettings().getFoodSelection().getFoodBox();
        comboBox.addActionListener(listener -> {
            DisplayObject object = (DisplayObject) comboBox.getSelectedItem();
            if (object == null) return;
            comboBox.getEditor().setIcon(new ImageIcon((object.getImage())));
            mainSettings.getMandatorySettings().getFoodSelection().updateName(object.toString());
            mainSettings.getMandatorySettings().getFoodSelection().updateHeal(object.getId());
            mainSettings.getOtherSettings().getExchangePanel().updateFood(object.getId());
            mainSettings.getOtherSettings().getExchangePanel().get()[4].getPrice().update(object.getId());
        });

        HCheckBox checkBox = getTeleportSettings().getTeleportPanel().getArray()[0];
        checkBox.addItemListener(listener -> {
            if (listener.getStateChange() == ItemEvent.SELECTED) {
                mainSettings.getOtherSettings().getExchangePanel().get()[3].update(11978);
                mainSettings.getOtherSettings().getExchangePanel().get()[3].getPrice().update(11978);
            } else {
                mainSettings.getOtherSettings().getExchangePanel().get()[3].update(8013);
                mainSettings.getOtherSettings().getExchangePanel().get()[3].getPrice().update(8013);
            }
        });
    }

    public static TitledBorder getBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2), title);
        border.setTitleColor(HFrame.FOREGROUND);
        return border;
    }


    public MainSettings getMainSettings() {
        return mainSettings;
    }

    public TeleportSettings getTeleportSettings() {
        return teleportSettings;
    }
}
