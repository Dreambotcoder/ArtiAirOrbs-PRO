package org.dreambot.articron.ui.swing.child;

import org.dreambot.articron.ui.swing.HFrame;
import org.dreambot.articron.ui.swing.special.DisplayObject;

import javax.swing.*;
import java.awt.*;

public class HComboBoxIconRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel component = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        component.setBackground(isSelected ? HFrame.LOGO_GREEN : HFrame.ELEMENT_BG);
        component.setForeground(HFrame.FOREGROUND);
        DisplayObject object = (DisplayObject) value;
        if (object.getImage() != null) {
            component.setIcon(new ImageIcon(object.getImage()));
        }
        return component;
    }
}
