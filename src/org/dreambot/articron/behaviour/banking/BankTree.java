package org.dreambot.articron.behaviour.banking;

import org.dreambot.articron.api.controller.impl.node.NodeTree;

import java.util.function.BooleanSupplier;

public class BankTree extends NodeTree {

    public BankTree(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Banking";
    }
}
