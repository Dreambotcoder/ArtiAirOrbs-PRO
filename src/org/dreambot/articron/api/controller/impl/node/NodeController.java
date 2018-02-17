package org.dreambot.articron.api.controller.impl.node;

import org.dreambot.api.data.GameState;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.loaders.GrandExchangeMode;
import org.dreambot.articron.api.controller.impl.node.loaders.WorkMode;
import org.dreambot.articron.api.util.CronConstants;
import org.dreambot.articron.data.ScriptMode;
import org.dreambot.articron.ui.dinh.ui.MainUI;

import java.util.ArrayList;
import java.util.List;


public class NodeController  {

    private NodeTree rootNode;
    private APIProvider api;
    private List<NodeLoader> loaders;
    private MainUI ui;

    public NodeController(APIProvider api) {
        rootNode = new RootNode();
        this.api = api;
        this.loaders = new ArrayList<>();
        addLoaders();
    }

    public void consume(MainUI ui) {
        this.ui = ui;
        setMode(ScriptMode.WORK);
    }

    private void addLoaders() {
        loaders.add(new WorkMode());
        loaders.add(new GrandExchangeMode());
    }

    public void commit(Node... elements) {
        rootNode.addChildren(elements);
    }

    public void setMode(ScriptMode mode) {
        rootNode.empty();
        api.getUtil().getBankManager().clear();
        if (!api.hasStarted()) {
            api.startScript();
        }
        for (NodeLoader loader : loaders) {
            if (loader.getMode() == mode) {
                loader.load(this,api, this.ui);
            }
        }
        CronConstants.SCRIPT_MODE = mode;
    }


    public void remove(String... elements) {
        for (String name : elements) {
            for (Node n : rootNode.getChildren()) {
                if (n.getClass().getSimpleName().equalsIgnoreCase(name)) {
                    rootNode.removeChild(n);
                }
            }
        }
    }

    public void stopScript(String reason) {
        rootNode.empty();
        rootNode.addChildren(new ShutdownNode(reason));
    }



    public Integer onLoop(APIProvider parameter) {
        if (parameter.getDB().getClient().getGameState() == GameState.LOGGED_IN) {
            return rootNode.onLoop(parameter);
        }
        return CronConstants.BASE_SLEEP;
    }

}
