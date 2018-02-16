package org.dreambot.articron.ui.dinh.ui.tab.settings.sub;

import org.dreambot.articron.ui.dinh.swing.HFrame;
import org.dreambot.articron.ui.dinh.swing.HPanel;
import org.dreambot.articron.ui.dinh.swing.child.HCheckBox;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 18:29
 */

public class HButtonGroup extends HPanel {

    private HCheckBox[] array;
    ButtonGroup group;

    public HButtonGroup(Border border, String... strings) {
        super(new GridLayout(0, 1), border);
        group = new ButtonGroup();
        array = new HCheckBox[strings.length];
        for (int i = 0, stringsLength = strings.length; i < stringsLength; i++) {
            add(array[i] = new HCheckBox(strings[i]));
            array[i].setBackground(HFrame.BACKGROUND);
            group.add(array[i]);
        }
        array[0].setSelected(true);
    }

    public Optional<HCheckBox> getOption() {
        return Arrays.stream(array).filter(HCheckBox::isSelected).findAny();
    }

    public HCheckBox[] getArray() {
        return array;
    }
}
