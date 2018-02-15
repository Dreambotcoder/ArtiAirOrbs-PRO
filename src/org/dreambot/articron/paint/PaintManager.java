package org.dreambot.articron.paint;

import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.utilities.Timer;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronUtil;

import java.awt.*;

public class PaintManager {

    private APIProvider api;
    private Timer timer;
    private String status = "";
    private MouseTip mouseTip;

    public PaintManager(APIProvider api) {
        this.api = api;
        this.timer = new Timer();
        this.mouseTip = new MouseTip(api,this);
        api.getDB().getSkillTracker().start();
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
        g.setFont(new Font("Arial",Font.PLAIN, 12));
        g.setColor(new Color(0.4f,0.2f,0.2f, 0.3f));
        g.fillRoundRect(30,30,230,150,10,10);
        g.setColor(new Color(0.7f,0.2f,0.2f, 0.8f));
        g.drawRoundRect(30,30,230,150,10,10);

        g.setColor(Color.WHITE);
        g.drawString("Time running: " + timer.formatTime(), x,50);
        g.drawString("Orbs made: " + CronUtil.ORBS_CREATED, x,90);
        g.drawString("Orbs P/H: " + timer.getHourlyRate(CronUtil.ORBS_CREATED), x, 110);
        g.drawString("Magic XP gained: " + api.getDB().getSkillTracker().getGainedExperience(Skill.MAGIC), x, 130);
        g.drawString("Exp /H: " + api.getDB().getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC), x, 150);
        g.drawString("Bank set: " + CronUtil.BANKSET, x, 170);
        //g.drawString("Walking node: " + CronUtil.CURRENT_NODE.toString(), x, 170);

        //g.drawString("Zoom: " + api.getDB().getClientSettings().getExactZoomValue(), 30,110);

    }
}
