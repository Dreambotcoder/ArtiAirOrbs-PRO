package org.dreambot.articron.ui.dinh.ui.tab.settings.sub;

import org.dreambot.articron.ui.dinh.swing.HPanel;
import org.dreambot.articron.ui.dinh.swing.child.HCheckBox;
import org.dreambot.articron.ui.dinh.ui.tab.settings.SettingPanel;

import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 14:55
 */

public class MainSettings extends HPanel {

    private MandatorySettings mandatorySettings;
    private OtherSettings otherSettings;

    public MainSettings(HCheckBox box, Border border) {
        super(new GridLayout(0, 1), border);

        add(mandatorySettings = new MandatorySettings(SettingPanel.getBorder("Mandatory")));
        add(otherSettings = new OtherSettings(box, SettingPanel.getBorder("TBD")));
    }

    public MandatorySettings getMandatorySettings() {
        return mandatorySettings;
    }

    public OtherSettings getOtherSettings() {
        return otherSettings;
    }
}
