package org.dreambot.articron.api;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.articron.api.antiban.AntibanController;
import org.dreambot.articron.api.controller.impl.node.NodeController;
import org.dreambot.articron.api.util.CronConstants;
import org.dreambot.articron.api.util.ScriptSettings;
import org.dreambot.articron.api.util.ScriptUtil;
import org.dreambot.articron.api.util.traversal.CustomPath;
import org.dreambot.articron.paint.PaintManager;
import org.dreambot.articron.sys.CronTray;

public class APIProvider {

    private AbstractScript script;
    private NodeController nodeController;
    private CustomPath obeliskPath;
    private ScriptUtil util;
    private PaintManager paintManager;
    private AntibanController antibanController;
    private CronTray cronTray;
    private ScriptSettings settings;
    private Timer timer;
    private boolean started;

    public APIProvider(AbstractScript script) {
        this.script = script;
        this.nodeController = new NodeController(this);
        this.util = new ScriptUtil(this);
        this.paintManager = new PaintManager(this);
        this.antibanController = new AntibanController(this, getDB().getClient().seededRandom());
        this.cronTray = new CronTray(this);
        //this.timer = new Timer();
        this.settings = new ScriptSettings();
    }

    public void startScript() {
        this.timer = new Timer();
        this.started = true;
    }

    public boolean hasStarted() {
        return this.started;
    }
    public String getRuntime() {
        return timer.formatTime();
    }

    public int getOrbsPerHour() {
        return timer.getHourlyRate(CronConstants.ORBS_CREATED);
    }

    public AbstractScript getDB() {
        return script;
    }

    public ScriptManifest getManifest() {
        return script.getManifest();
    }

    public NodeController getNodeController() {
        return nodeController;
    }

    public void registerPath(CustomPath path) {
        this.obeliskPath = path;
    }

    public CustomPath getObeliskPath() {
        return obeliskPath;
    }

    public ScriptUtil getUtil() {
        return util;
    }

    public PaintManager getPaintManager() {
        return paintManager;
    }

    public AntibanController getAntibanController() {
        return antibanController;
    }

    public CronTray getCronTray() {
        return cronTray;
    }

    public ScriptSettings getSettings() {
        return settings;
    }
}
