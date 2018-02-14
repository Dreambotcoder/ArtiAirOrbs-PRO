package org.dreambot.articron.behaviour.emergency;

import org.dreambot.articron.api.controller.impl.node.NodeTree;

import java.util.function.BooleanSupplier;

public class EatTree extends NodeTree {

    public EatTree(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "EatingTree";
    }

}
