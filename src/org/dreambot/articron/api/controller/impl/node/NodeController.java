package org.dreambot.articron.api.controller.impl.node;

import org.dreambot.api.data.GameState;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.loaders.WorkMode;
import org.dreambot.articron.api.util.CronConstants;
import org.dreambot.articron.data.ScriptMode;

import java.util.ArrayList;
import java.util.List;


public class NodeController  {

    private NodeTree rootNode;
    private APIProvider api;

    private List<NodeLoader> loaders;

    public NodeController(APIProvider api) {
        rootNode = new RootNode();
        this.api = api;
        this.loaders = new ArrayList<>();
        addLoaders();
    }

    private void addLoaders() {
        loaders.add(new WorkMode());

    }

    public void commit(Node... elements) {
        rootNode.addChildren(elements);
    }

    public void setMode(ScriptMode mode) {
        for (NodeLoader loader : loaders) {
            if (loader.getMode() == mode) {
                rootNode.empty();
                loader.load(this,api);
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



    public Integer onLoop(APIProvider parameter) {
        if (parameter.getDB().getClient().getGameState() == GameState.LOGGED_IN) {
            return rootNode.onLoop(parameter);
        }
        return CronConstants.BASE_SLEEP;
    }

}
