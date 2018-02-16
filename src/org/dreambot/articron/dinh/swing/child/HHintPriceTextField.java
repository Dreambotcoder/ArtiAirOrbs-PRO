package org.dreambot.articron.dinh.swing.child;

import org.dreambot.articron.dinh.swing.HFrame;
import org.dreambot.articron.dinh.swing.HPanel;
import org.dreambot.articron.net.PriceCheck;

import java.awt.*;

/**
 * Created by: Niklas
 * Date: 16.02.2018
 * Alias: Dinh
 * Time: 15:38
 */

public class HHintPriceTextField extends HPanel {

    private HHintTextField textField;

    public HHintPriceTextField(int id) {
        super(new BorderLayout());
        add(textField = new HHintTextField("Price", HFrame.ELEMENT_BG, HFrame.FOREGROUND), BorderLayout.CENTER);
        int height = textField.getPreferredSize().height;

        HButton minus = new HButton("-", listener -> {
            if (textField.getText().matches("[0-9]+")) {
                textField.setText(String.valueOf((int) Math.floor(Integer.parseInt(textField.getText()) * 0.95)));
            }
        }, new Dimension(10, height));
        minus.setBorder(null);
        add(minus, BorderLayout.WEST);

        HButton plus = new HButton("+", listener -> {
            if (textField.getText().matches("[0-9]+")) {
                textField.setText(String.valueOf((int) Math.ceil(Integer.parseInt(textField.getText()) * 1.05)));
            }
        }, new Dimension(10, height));
        plus.setBorder(null);
        add(plus, BorderLayout.EAST);

        new Thread(() -> textField.setText(String.valueOf(PriceCheck.getPrice(id)))).start();
    }

    public void update(int id) {
        new Thread(() -> textField.setText(String.valueOf(PriceCheck.getPrice(id)))).start();
    }
}
