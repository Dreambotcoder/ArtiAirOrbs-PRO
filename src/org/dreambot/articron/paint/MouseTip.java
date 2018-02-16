package org.dreambot.articron.paint;

import org.dreambot.articron.api.APIProvider;

import java.awt.*;

public class MouseTip {

    private APIProvider api;
    private PaintManager parent;

    private final Font MAIN_FONT;
    private final Color BACKGROUND, BORDER;

    public MouseTip(APIProvider api, PaintManager parent) {
        this.api = api;
        this.parent = parent;
        MAIN_FONT = new Font("Arial",Font.PLAIN, 10);
        BACKGROUND = new Color(37/256, 37/256, 37/256,0.3f);
        BORDER = new Color(46 /256, 204/256, 113/256, 0.8f);
    }

    public void paint(Graphics2D g) {
        int x = api.getDB().getMouse().getX();
        int y = api.getDB().getMouse().getY();
        g.setColor(BACKGROUND);
        g.setFont(MAIN_FONT);
        int length = g.getFontMetrics().stringWidth("Status: " +parent.getStatus());
        g.fillRoundRect(x,y-20,length + 10, 20,10,5);
        g.setColor(BORDER);
        g.drawRoundRect(x,y-20,length+10,20,10,5);
        g.setColor(Color.WHITE);
        g.drawString("Status: " + parent.getStatus(),x + 5, y - 4);
    }
}
