package org.dreambot.articron.api.controller.impl.node;


public class RootNode extends NodeTree {

    public RootNode() {
        super(() -> true);
    }

    @Override
    public String getStatus() {
        return "Root";
    }

}
