package org.dreambot.articron.api;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.articron.api.controller.impl.node.NodeController;
import org.dreambot.articron.api.util.ScriptUtil;
import org.dreambot.articron.api.util.traversal.CustomPath;
import org.dreambot.articron.paint.PaintManager;

public class APIProvider {

    private AbstractScript script;
    private NodeController nodeController;
    private CustomPath obeliskPath;
    private ScriptUtil util;
    private PaintManager paintManager;

    public APIProvider(AbstractScript script) {
        this.script = script;
        this.nodeController = new NodeController();
        this.util = new ScriptUtil(this);
        this.paintManager = new PaintManager(this);
    }

    public AbstractScript getDB() {
        return script;
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
}
