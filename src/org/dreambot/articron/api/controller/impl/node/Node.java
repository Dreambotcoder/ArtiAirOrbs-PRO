package org.dreambot.articron.api.controller.impl.node;

import org.dreambot.articron.api.APIProvider;

import java.util.function.BooleanSupplier;

public abstract class Node {

    private BooleanSupplier condition;

    public Node(BooleanSupplier condition) {
        this.condition = condition;
    }

    public boolean isValid() {
        return condition.getAsBoolean();
    }

    public abstract String getStatus();
    public abstract int onLoop(APIProvider api);
}
