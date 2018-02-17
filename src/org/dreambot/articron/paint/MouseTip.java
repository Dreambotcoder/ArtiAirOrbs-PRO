package org.dreambot.articron.paint;

import org.dreambot.articron.api.APIProvider;

import java.awt.*;

public class MouseTip {

    private APIProvider api;
    private PaintManager parent;

    private final Font MAIN_FONT;
    private final Color BACKGROUND, BORDER;

    private final Color TIP_OUT = new Color(46, 204, 113);
    private final Color TIP_MID = new Color(0,0,0,0.8f);
    private final Color TEXT_COLOR = new Color(46, 204, 113);


    protected MouseTip(APIProvider api, PaintManager parent) {
        this.api = api;
        this.parent = parent;
        MAIN_FONT = new Font("Arial",Font.PLAIN, 9);
        BACKGROUND = new Color(37/256, 37/256, 37/256,0.5f);
        BORDER = new Color(0.2f,0.8f,0.2f, 0.8f);
        api.getDB().getClient().getInstance().setDrawMouse(false);
    }

    public void paint(Graphics2D g) {
        int x = api.getDB().getMouse().getX();
        int y = api.getDB().getMouse().getY();
        g.setColor(BACKGROUND);
        g.setFont(MAIN_FONT);
        int length = g.getFontMetrics().stringWidth("Status: " +parent.getStatus());
        g.fillRoundRect(x - 5,y-7,length + 20, 14,10,20);
        g.setColor(BORDER);
       // g.drawRoundRect(x - 7,y-7,length+10,14,10,20);
        g.setColor(TEXT_COLOR);
        g.drawString("Status: " + parent.getStatus(),x + 8, y + 3);
        g.setColor(TIP_OUT);
        drawCenteredCircle(g,x,y,14);
        g.setColor(TIP_MID);
        drawCenteredCircle(g,x,y,8);
    }


    private void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
        x = x-(r/2);
        y = y-(r/2);
        g.fillOval(x,y,r,r);
    }

}
