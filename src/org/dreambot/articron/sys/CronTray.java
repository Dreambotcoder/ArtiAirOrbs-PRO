package org.dreambot.articron.sys;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronConstants;
import org.dreambot.articron.ui.articron.LogUI;
import org.dreambot.articron.ui.dinh.loader.HImageLoader;
import org.dreambot.articron.ui.dinh.swing.HFrame;

import java.awt.*;

public class CronTray {

    private APIProvider api;
    private SystemTray tray;
    private boolean added;

    private boolean hidePaint, hidePKList = true;
    private TrayIcon trayIcon;
    private PopupMenu popupMenu;


    private final Image ICON;

    public boolean isVisible() {
        return added;
        }
    public CronTray(APIProvider api) {
        this.api = api;
        ICON = HFrame.createResizedCopy(HImageLoader.loadImage("https://i.imgur.com/SGA9et4.png"),
                16,16,true);
        this.tray = SystemTray.getSystemTray();
        createPopupMenu();
        this.trayIcon = new TrayIcon(ICON, "ArtiAirOrb PRO", this.popupMenu);
        this.trayIcon.setImageAutoSize(true);
    }

    private void createPopupMenu() {
        this.popupMenu = new PopupMenu();
        MenuItem blacklist = new MenuItem();
        MenuItem showPaint = new MenuItem();
        MenuItem stopScript = new MenuItem();
        MenuItem showLogs = new MenuItem();
        showLogs.setLabel("Show bot logs");
        blacklist.setLabel("Show PK blacklist");
        showPaint.setLabel("Hide paint");
        stopScript.setLabel("Stop script");
        blacklist.addActionListener(e -> {
            hidePKList = !hidePKList;
            blacklist.setLabel((hidePKList ? "Show" : "Hide").concat(" PK blacklist"));
        });
        showPaint.addActionListener(e -> {
            CronConstants.SHOW_PAINT = !CronConstants.SHOW_PAINT;
            showPaint.setLabel((CronConstants.SHOW_PAINT ? "Hide" : "Show").concat(" paint"));
        });
        stopScript.addActionListener(e -> api.getNodeController().stopScript("Tray shutdown request"));
        this.popupMenu.add(blacklist);
        this.popupMenu.add(showLogs);
        this.popupMenu.add(showPaint);
        this.popupMenu.add(stopScript);

    }

    public void setVisible(boolean visible) {
        if (visible) {
            try {
                if (!added) {
                    this.tray.add(this.trayIcon);
                    added = true;
                }
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } else {
            if (added) {
                this.tray.remove(this.trayIcon);
                added = false;
            }
        }
    }

    public void sendMessage(String title, String message) {
        if (added) {
            this.trayIcon.displayMessage(title,message, TrayIcon.MessageType.NONE);
        }
    }

    public static void main(String... args) throws InterruptedException {
        CronTray tray = new CronTray(null);
        tray.setVisible(true);
        tray.sendMessage("title","message");
        Thread.sleep(10 * 1000);
    }
}
