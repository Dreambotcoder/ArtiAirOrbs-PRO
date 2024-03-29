package org.dreambot.articron.ui.dinh.ui.tab.settings.sub;

import org.dreambot.articron.ui.dinh.swing.HPanel;

import java.awt.*;

/**
 * Created by: Niklas
 * Date: 16.02.2018
 * Alias: Dinh
 * Time: 14:44
 */

public class StaticExchangePanel extends HPanel {

    private StaticItem[] items = new StaticItem[5];
    private StaticItem sell;

    public StaticExchangePanel() {
        super(new GridLayout(0, 6, 2, 0));
        add(items[0] = new StaticItem(567));
        add(items[1] = new StaticItem(564));
        add(items[2] = new StaticItem(12625));
        add(items[3] = new StaticItem(11978));
        add(items[4] = new StaticItem(315));
        add(sell = new StaticItem(573));
        for (int i = 2; i < items.length; i++) {
            items[i].getAmount().setText("");
            items[i].getAmount().setEditable(true);
        }
    }

    public void updateFood(int id) {
        items[4].update(id);
    }

    public StaticItem[] get() {
        return items;
    }

    public void toggle(boolean editable) {
        for (int i = 0; i < 2; i++) {
            if (!editable) {
                items[i].getAmount().setText("DYNAMIC");
            } else {
                items[i].getAmount().setText("");
            }
            items[i].getAmount().setEditable(editable);
        }
    }

    public StaticItem getSell() {
        return sell;
    }
}
