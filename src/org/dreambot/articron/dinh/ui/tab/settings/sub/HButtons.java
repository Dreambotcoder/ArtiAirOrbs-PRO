package org.dreambot.articron.dinh.ui.tab.settings.sub;

import org.dreambot.articron.dinh.swing.HFrame;
import org.dreambot.articron.dinh.swing.HPanel;
import org.dreambot.articron.dinh.swing.child.HCheckBox;

import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 18:39
 */

public class HButtons extends HPanel {

    private HCheckBox[] array;

    public HButtons(Border border, String... strings) {
        super(new GridLayout(0, 1), border);
        array = new HCheckBox[strings.length];
        for (int i = 0; i < strings.length; i++) {
            add(array[i] = new HCheckBox(strings[i]));
            array[i].setBackground(HFrame.BACKGROUND);
        }
    }

    public List<HCheckBox> getSelected() {
        return Arrays.stream(array).filter(HCheckBox::isSelected).collect(Collectors.toList());
    }

    public HCheckBox get(int index) {
        return array[index];
    }
}
