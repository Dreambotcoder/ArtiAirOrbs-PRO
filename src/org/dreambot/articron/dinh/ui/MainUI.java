package org.dreambot.articron.dinh.ui;

import org.dreambot.articron.dinh.loader.HImageLoader;
import org.dreambot.articron.dinh.swing.HFrame;
import org.dreambot.articron.dinh.swing.child.HTabbedPane;
import org.dreambot.articron.dinh.ui.tab.exchange.ExchangePanel;
import org.dreambot.articron.dinh.ui.tab.info.InformationPanel;
import org.dreambot.articron.dinh.ui.tab.settings.SettingPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainUI extends HFrame {
    private InformationPanel informationPanel;
    private ExchangePanel exchangePanel;
    private SettingPanel settingPanel;

    public MainUI(String text, BufferedImage icon) {
        super(text, icon);
        JTabbedPane tabbedPane = new HTabbedPane();
        contentPane.add(tabbedPane, BorderLayout.CENTER); //

        tabbedPane.addTab("Info", informationPanel = new InformationPanel(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2), this));
        tabbedPane.addTab("Settings", settingPanel = new SettingPanel(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2), new EmptyBorder(5, 5, 5, 5))));

        contentPane.setPreferredSize(new Dimension(670, 350));
        prepare(null);

    }

    public static void main(String[] args) {
        new MainUI("ArtiAirOrbs PRO", HImageLoader.loadImage("https://i.imgur.com/SGA9et4.png"));
    }

    public InformationPanel getInformationPanel() {
        return informationPanel;
    }

    public ExchangePanel getExchangePanel() {
        return exchangePanel;
    }

    public SettingPanel getSettingPanel() {
        return settingPanel;
    }
}
