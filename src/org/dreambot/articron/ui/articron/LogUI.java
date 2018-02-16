package org.dreambot.articron.ui.articron;

import org.dreambot.articron.ui.articron.custom.LogPanel;
import org.dreambot.articron.ui.articron.custom.LogType;
import org.dreambot.articron.ui.dinh.loader.HImageLoader;
import org.dreambot.articron.ui.dinh.swing.HFrame;
import org.dreambot.articron.ui.dinh.swing.HPanel;
import org.dreambot.articron.ui.dinh.swing.child.HButton;
import org.dreambot.articron.ui.dinh.swing.child.HLabel;
import org.dreambot.articron.ui.dinh.swing.child.HScrollPane;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LogUI extends HFrame {

    private HScrollPane scrollPane;
    private HPanel mainPanel;

    public LogUI(String title, BufferedImage icon) {
        super(title, icon);
        contentPane.setPreferredSize(new Dimension(500, 350));
        contentPane.add(scrollPane = new HScrollPane(mainPanel = new HPanel()), BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(0, 1, 0, 0));
        contentPane.add(new HButton("Clear logs", (e) -> {
            mainPanel.removeAll();
        }), BorderLayout.PAGE_END);
        pack();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        //prepare(null);
    }

    public void addLog(String message, String runtime, LogType type) {
        mainPanel.add(new LogPanel(message,runtime,type), 0);
    }

    public static void main(String... args) {
        new LogUI("Botsession logbook", HImageLoader.loadImage("https://i.imgur.com/SGA9et4.png"));
    }
}
