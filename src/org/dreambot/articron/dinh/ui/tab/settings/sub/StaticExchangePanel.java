package org.dreambot.articron.dinh.ui.tab.settings.sub;

import org.dreambot.articron.dinh.swing.HPanel;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by: Niklas
 * Date: 16.02.2018
 * Alias: Dinh
 * Time: 14:44
 */

public class StaticExchangePanel extends HPanel {

    private StaticItem[] items = new StaticItem[5];

    public StaticExchangePanel() {
        super(new GridLayout(0, 5, 2, 0));
        add(items[0] = new StaticItem(567));
        add(items[1] = new StaticItem(564));
        add(items[2] = new StaticItem(12625));
        add(items[3] = new StaticItem(11978));
        add(items[4] = new StaticItem(315));
    }

    public void updateFood(int id) {
        items[4].update(id);
    }

    public StaticItem[] get() {
        return items;
    }

    public void toggle(boolean editable) {
        Arrays.stream(items).forEach(item -> {
            if (!editable) {
                item.getAmount().setText("DYNAMIC");
            } else {
                item.getAmount().setText("");
            }
            item.getAmount().setEditable(editable);
        });
    }
}
