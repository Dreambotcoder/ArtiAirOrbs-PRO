package org.dreambot.articron.ui.dinh.swing.child;

import org.dreambot.articron.net.PriceCheck;
import org.dreambot.articron.ui.dinh.swing.HFrame;
import org.dreambot.articron.ui.dinh.swing.HPanel;

import javax.swing.*;
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
        minus.setHorizontalAlignment(JTextField.CENTER);
        add(minus, BorderLayout.WEST);

        HButton plus = new HButton("+", listener -> {
            if (textField.getText().matches("[0-9]+")) {
                textField.setText(String.valueOf((int) Math.ceil(Integer.parseInt(textField.getText()) * 1.05)));
            }
        }, new Dimension(8, height));
        plus.setBorder(null);
        plus.setHorizontalAlignment(JTextField.CENTER);
        add(plus, BorderLayout.EAST);

        new Thread(() -> textField.setText(String.valueOf(PriceCheck.getPrice(id)))).start();
    }

    public int getPrice() {
        return Integer.parseInt(textField.getText());
    }
    public void update(int id) {
        new Thread(() -> textField.setText(String.valueOf(PriceCheck.getPrice(id)))).start();
    }
}