package org.dreambot.articron.behaviour.poh;

import org.dreambot.articron.api.controller.impl.node.NodeTree;

import java.util.function.BooleanSupplier;

public class POHTree extends NodeTree {

    public POHTree(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "POHTree";
    }
}
