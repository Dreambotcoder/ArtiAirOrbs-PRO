package org.dreambot.articron.ui.articron.custom;

import org.dreambot.articron.ui.dinh.swing.HFrame;
import org.dreambot.articron.ui.dinh.swing.HPanel;
import org.dreambot.articron.ui.dinh.swing.child.HLabel;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends HPanel {

    private String message;
    private LogType type;

    private HLabel picture;
    private HPanel details;

    private final Color DEFAULT_TEXT = new Color(189, 195, 199);

    public LogPanel(String message, String runtime, LogType type) {
        this.message = message;
        this.type = type;
        setLayout(new BorderLayout());
        details = new HPanel();
        details.setLayout(new GridLayout(0, 1, 5, 0));
        picture = new HLabel();
        picture.setIcon(new ImageIcon(HFrame.createResizedCopy(type.getImage(),40,40,true)));
        HLabel run = new HLabel("At runtime: " + runtime);
        run.setFont(new Font(run.getFont().getName(), Font.ITALIC, run.getFont().getSize()));
        HLabel title = new HLabel(type.getTitle());
        title.setForeground(type.getTitleColor());
        HLabel msg = new HLabel("Message: " + message);
        msg.setForeground(DEFAULT_TEXT);
        details.setBorder(BorderFactory.createEmptyBorder(0,10,0,5));
        details.add(title);
        details.add(msg);
        details.add(run);
        picture.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));
        add(picture, BorderLayout.WEST);
        add(details,BorderLayout.CENTER);
        setBorder(BorderFactory.createMatteBorder(0,0,1,0,HFrame.HOV_GREEN));
    }

}
