package org.dreambot.articron.behaviour.deathwalk;

import org.dreambot.articron.api.controller.impl.node.NodeTree;

import java.util.function.BooleanSupplier;

public class DeathTree extends NodeTree {

    public DeathTree(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Deathwalk";
    }
}
