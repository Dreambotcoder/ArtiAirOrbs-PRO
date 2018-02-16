package org.dreambot.articron.ui.swing.child;

import org.dreambot.articron.ui.swing.HFrame;
import org.dreambot.articron.ui.swing.HPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by: Niklas
 * Date: 20.09.2017
 * Alias: Dinh
 * Time: 18:24
 */

public class HComboBoxIconEditor extends BasicComboBoxEditor {
    private JLabel displayLabel;
    private JPanel elementPanel;
    private Object selectedItem;

    HComboBoxIconEditor(Font font) {
        displayLabel = new HLabel(JTextField.LEFT);
        displayLabel.setBorder(new EmptyBorder(0, 5, 0, 0));
        displayLabel.setOpaque(false);
        displayLabel.setFont(font);
        displayLabel.setForeground(HFrame.FOREGROUND);
        elementPanel = new HPanel(new BorderLayout());
        elementPanel.add(displayLabel, BorderLayout.CENTER);
        elementPanel.setBackground(HFrame.ELEMENT_BG);
        setIcon(new ImageIcon(new BufferedImage(36, 32, BufferedImage.TYPE_INT_ARGB)));
    }

    public void setIcon(Icon icon) {
        displayLabel.setIcon(icon);
    }

    public Component getEditorComponent() {
        return elementPanel;
    }

    public Object getItem() {
        return selectedItem;
    }

    public void setItem(Object item) {
        this.selectedItem = item;
        displayLabel.setText(item.toString());
    }
}