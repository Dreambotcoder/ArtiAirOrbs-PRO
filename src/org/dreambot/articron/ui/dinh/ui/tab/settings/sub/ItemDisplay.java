package org.dreambot.articron.ui.dinh.ui.tab.settings.sub;

import org.dreambot.articron.ui.dinh.swing.HPanel;

import java.awt.*;

public class ItemDisplay extends HPanel {

    private Display left, right;

    public ItemDisplay(int leftID, int rightID) {
        super(new BorderLayout());
        HPanel display = new HPanel(new FlowLayout());
        display.add(left = new Display(leftID, 27));
        display.add(right = new Display(rightID, 81));
        add(display);
    }

    public void updateLeft(int amount) {
        left.update(amount);
    }

    public void updateRight(int amount) {
        right.update(amount);
    }
}
