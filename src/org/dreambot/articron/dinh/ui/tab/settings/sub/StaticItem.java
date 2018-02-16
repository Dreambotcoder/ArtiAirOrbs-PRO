package org.dreambot.articron.dinh.ui.tab.settings.sub;

import org.dreambot.articron.dinh.loader.HImageLoader;
import org.dreambot.articron.dinh.swing.HFrame;
import org.dreambot.articron.dinh.swing.HPanel;
import org.dreambot.articron.dinh.swing.child.HHintPriceTextField;
import org.dreambot.articron.dinh.swing.child.HHintTextField;
import org.dreambot.articron.dinh.swing.child.HLabel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by: Niklas
 * Date: 16.02.2018
 * Alias: Dinh
 * Time: 14:43
 */

public class StaticItem extends HPanel {

    private int id;
    private HLabel label;
    private HHintTextField amount;
    private HHintPriceTextField price;

    public StaticItem(int id) {
        super(new BorderLayout());
        add(amount = new HHintTextField("Amount", HFrame.ELEMENT_BG, HFrame.FOREGROUND), BorderLayout.NORTH);
        add(price = new HHintPriceTextField(id), BorderLayout.SOUTH);
        add(label = new HLabel(JLabel.CENTER), BorderLayout.CENTER);
        amount.setEditable(false);
        amount.setText("DYNAMIC");
        update(id);

    }

    public void update(int id) {
        this.id = id;
        new Thread(() -> label.setIcon(new ImageIcon(HImageLoader.loadSprite(id)))).start();

    }

    public int getId() {
        return id;
    }

    public HLabel getLabel() {
        return label;
    }

    public HHintTextField getAmount() {
        return amount;
    }

    public HHintPriceTextField getPrice() {
        return price;
    }
}
