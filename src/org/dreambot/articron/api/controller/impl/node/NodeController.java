package org.dreambot.articron.api.controller.impl.node;

import org.dreambot.articron.api.APIProvider;


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
        return rootNode.onLoop(parameter);
    }

}
