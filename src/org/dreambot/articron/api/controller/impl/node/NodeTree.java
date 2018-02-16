package org.dreambot.articron.api.controller.impl.node;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;

public abstract class NodeTree extends Node {

    private List<Node> children;

    public NodeTree(BooleanSupplier condition) {
        super(condition);
        children = new ArrayList<>();
    }

    public Node addChildren(Node... nodes) {
        Collections.addAll(children, nodes);
        return this;
    }

    public void empty() {
        children.clear();
    }

    public void removeChild(Node n) {
        children.remove(n);
    }

    public Node[] getChildren() {
        return (Node[]) children.toArray();
    }

    @Override
    public int onLoop(APIProvider api) {
        for (Node node : children) {
            if (node.isValid()) {
                api.getPaintManager().setStatus(node.getStatus());
                return node.onLoop(api);
            }
        }
        return CronConstants.BASE_SLEEP;
    }
}
