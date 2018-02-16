package org.dreambot.articron.paint;

import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.utilities.Timer;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronConstants;

import java.awt.*;

public class PaintManager {

    private APIProvider api;
    private Timer timer;
    private String status = "";
    private MouseTip mouseTip;
    private final Font MAIN_FONT;

    private final Color BACKGROUND_COLOR;
    private final Color BORDER;

    public PaintManager(APIProvider api) {
        this.api = api;
        this.timer = new Timer();
        this.mouseTip = new MouseTip(api,this);
        api.getDB().getSkillTracker().start();
        MAIN_FONT = new Font("Arial",Font.PLAIN, 12);
        BACKGROUND_COLOR = new Color(0.4f,0.2f,0.2f, 0.3f);
        BORDER = new Color(0.7f,0.2f,0.2f, 0.8f);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected String getStatus() {
        return status;
    }

    public void onPaint(Graphics2D g) {
        mouseTip.paint(g);
        int x = 35;
        g.setFont(MAIN_FONT);
        g.setColor(BACKGROUND_COLOR);
        g.fillRoundRect(30,30,230,150,10,10);
        g.setColor(BORDER);
        g.drawRoundRect(30,30,230,150,10,10);

        g.setColor(Color.WHITE);
        g.drawString("Time running: " + timer.formatTime(), x,50);
        g.drawString("Orbs made: " + CronConstants.ORBS_CREATED, x,90);
        g.drawString("Orbs P/H: " + timer.getHourlyRate(CronConstants.ORBS_CREATED), x, 110);
        g.drawString("Magic XP gained: " + api.getDB().getSkillTracker().getGainedExperience(Skill.MAGIC), x, 130);
        g.drawString("Exp /H: " + api.getDB().getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC), x, 150);
       // g.drawString("GameState: " + api.getDB().getClient().getGameState().name(), x, 170);
        ///g.drawString("Wild level: " + api.getUtil().getAntiPkController().getWildernessLevel(), x, 170);
       // g.drawString("Bank set: " + CronConstants.BANKSET, x, 170);
        //g.drawString("Walking node: " + CronConstants.CURRENT_NODE.toString(), x, 170);

        //g.drawString("Zoom: " + api.getDB().getClientSettings().getExactZoomValue(), 30,110);

    }
}
