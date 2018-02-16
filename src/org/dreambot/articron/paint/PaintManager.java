package org.dreambot.articron.paint;

import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.utilities.Timer;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PaintManager {

    private APIProvider api;
    private Timer timer;
    private String status = "";
    private MouseTip mouseTip;
    private final Font MAIN_FONT;

    private final Color BACKGROUND_COLOR;
    private final Color TEXT_COLOR = new Color(189, 195, 199);
    private final Color BORDER;

    public PaintManager(APIProvider api) {
        this.api = api;
        this.timer = new Timer();
        this.mouseTip = new MouseTip(api,this);
        api.getDB().getSkillTracker().start();
        MAIN_FONT = new Font("Arial",Font.PLAIN, 12);
        BACKGROUND_COLOR = new Color(37/256, 37/256, 37/256,0.3f);
        BORDER = new Color(46, 204, 113);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected String getStatus() {
        return status;
    }


    public void onPaint(Graphics2D g) {

        int x = 555;
        g.setFont(MAIN_FONT);
        g.setColor(BACKGROUND_COLOR);
        g.fillRoundRect(545,200,738 - 545,467 - 200,10,10);
        g.setColor(BORDER);
        g.drawRoundRect(545,200,738 - 545,467 - 200,10,10);

        g.setColor(TEXT_COLOR);
        g.drawString("Time running: " + timer.formatTime(), x,230);
        g.drawString("Script mode: " + CronConstants.SCRIPT_MODE, x, 250);

        g.drawString("Orbs made: " + CronConstants.ORBS_CREATED, x,290);
        g.drawString("Orbs P/H: " + timer.getHourlyRate(CronConstants.ORBS_CREATED), x, 310);
        g.drawString("Magic XP gained: " + api.getDB().getSkillTracker().getGainedExperience(Skill.MAGIC), x, 330);
        g.drawString("Exp /H: " + api.getDB().getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC), x, 350);

        g.drawString("Blacklisted Pkers: " + api.getUtil().getAntiPkController().getPkerCount(), x, 390);
       // g.drawString("GameState: " + api.getDB().getClient().getGameState().name(), x, 170);
        ///g.drawString("Wild level: " + api.getUtil().getAntiPkController().getWildernessLevel(), x, 170);
       // g.drawString("Bank set: " + CronConstants.BANKSET, x, 170);
        //g.drawString("Walking node: " + CronConstants.CURRENT_NODE.toString(), x, 170);

        //g.drawString("Zoom: " + api.getDB().getClientSettings().getExactZoomValue(), 30,110);
        mouseTip.paint(g);
    }
}
