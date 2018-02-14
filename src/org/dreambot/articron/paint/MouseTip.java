package org.dreambot.articron.paint;

import org.dreambot.articron.api.APIProvider;

import java.awt.*;

public class MouseTip {

    private APIProvider api;
    private PaintManager parent;

    public MouseTip(APIProvider api, PaintManager parent) {
        this.api = api;
        this.parent = parent;
    }

    public void paint(Graphics2D g) {
        int x = api.getDB().getMouse().getX();
        int y = api.getDB().getMouse().getY();
        g.setColor(new Color(0.4f,0.2f,0.2f, 0.6f));
        g.setFont(new Font("Arial",Font.PLAIN, 10));
        int length = g.getFontMetrics().stringWidth("Status: " +parent.getStatus());
        g.fillRoundRect(x,y-20,length + 10, 20,10,5);
        g.setColor(new Color(0.7f,0.2f,0.2f, 0.8f));
        g.drawRoundRect(x,y-20,length+10,20,10,5);
        g.setColor(Color.WHITE);
        g.drawString("Status: " + parent.getStatus(),x + 5, y - 4);
    }
}
