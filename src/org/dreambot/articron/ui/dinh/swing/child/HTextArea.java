package org.dreambot.articron.ui.dinh.swing.child;

import org.dreambot.articron.ui.dinh.swing.HFrame;

import javax.swing.*;

public class HTextArea extends JTextArea{
	
	public HTextArea(String text) {
		setBackground(HFrame.ELEMENT_BG);
		setForeground(HFrame.FOREGROUND);
		setEditable(false);
		setLineWrap(true);
		setText(text);
	}

}
