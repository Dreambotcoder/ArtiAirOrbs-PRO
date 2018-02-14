package org.dreambot.articron.dinh.ui.tab.info;

import org.dreambot.articron.dinh.swing.HFrame;
import org.dreambot.articron.dinh.swing.HPanel;
import org.dreambot.articron.dinh.swing.child.HButton;
import org.dreambot.articron.dinh.swing.child.HLabel;
import org.dreambot.articron.dinh.swing.child.HScrollPane;
import org.dreambot.articron.dinh.swing.child.HTextArea;
import org.dreambot.articron.dinh.ui.MainUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class InformationPanel extends HPanel {

    private HTextArea log;

    public InformationPanel(Border border, MainUI main) {
        super(new BorderLayout(), border);
        HPanel information = new HPanel(new GridLayout(0, 1));
        information.add(new HLabel("Script revision: 0.1"));
        information.add(new HLabel("Script author: Articron"));
        information.add(new HLabel("Script UI author: Dinh"));
        HPanel top = new HPanel(new BorderLayout());
        HPanel buttons = new HPanel(new GridLayout(0, 2, 5, 0));
        HButton button = new HButton("Script Thread", listener -> {
            try {
                Desktop.getDesktop().browse(new URI("https://dreambot.org/forums/index.php/user/60711-dinh/"));
            } catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        buttons.add(button);
        buttons.add(new HButton("Start", listener -> {

            /*
             * Your code goes here
             */

            main.dispose();
        }));
        top.add(information, BorderLayout.WEST);
        top.add(buttons, BorderLayout.EAST);
        top.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        add(top, BorderLayout.NORTH);

        HScrollPane change = new HScrollPane(log = new HTextArea(clog));
        add(change, BorderLayout.CENTER);
        change.setBorder(new EmptyBorder(0, 3, 3, 3));
        change.setBackground(HFrame.BACKGROUND);

        HScrollPane scroll = new HScrollPane(new HTextArea(welcome));
        add(scroll, BorderLayout.SOUTH);
        scroll.setBorder(new EmptyBorder(0, 3, 3, 3));
        scroll.setBackground(HFrame.BACKGROUND);
    }

    public HTextArea getLog() {
        return log;
    }

    private final String welcome = "Thank you for your purchase!\nPlease feel free to suggest any additional features in the official script thread.\nIf you find any bugs, please report them to me ASAP to have them finished in a timely fashion.";
    private final String clog = "Changelog\n\nV0.1:\n    * Initial release";

}
