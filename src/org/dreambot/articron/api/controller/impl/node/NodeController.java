package org.dreambot.articron.api.controller.impl.node;

import org.dreambot.api.data.GameState;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronConstants;


public class NodeController  {

    private NodeTree rootNode;

    public NodeController() {
        rootNode = new RootNode();
    }

    public void commit(Node... elements) {
        rootNode.addChildren(elements);
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
