package org.dreambot.articron.ui.dinh.ui;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.ui.dinh.loader.HImageLoader;
import org.dreambot.articron.ui.dinh.swing.HFrame;
import org.dreambot.articron.ui.dinh.swing.child.HTabbedPane;
import org.dreambot.articron.ui.dinh.ui.tab.exchange.ExchangePanel;
import org.dreambot.articron.ui.dinh.ui.tab.info.InformationPanel;
import org.dreambot.articron.ui.dinh.ui.tab.settings.SettingPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainUI extends HFrame {
    private InformationPanel informationPanel;
    private ExchangePanel exchangePanel;
    private SettingPanel settingPanel;
    private APIProvider api;

    public MainUI(APIProvider api, BufferedImage icon) {
        super(api == null ? "Debug" : api.getManifest().name() + "V"+api.getManifest().version(),icon);
        this.api = api;
        JTabbedPane tabbedPane = new HTabbedPane();
        contentPane.add(tabbedPane, BorderLayout.CENTER); //
        tabbedPane.addTab("Info", informationPanel = new InformationPanel(api,BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2), this));
        tabbedPane.addTab("Settings", settingPanel = new SettingPanel(api,BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2), new EmptyBorder(5, 5, 5, 5))));

        contentPane.setPreferredSize(new Dimension(670, 350));
        prepare(null);

    }

    public static void main(String[] args) {
        new MainUI(null, HImageLoader.loadImage("https://i.imgur.com/SGA9et4.png"));
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
