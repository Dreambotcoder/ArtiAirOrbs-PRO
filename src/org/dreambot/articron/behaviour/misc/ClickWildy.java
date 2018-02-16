package org.dreambot.articron.behaviour.misc;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class ClickWildy extends Node {

    public ClickWildy(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Solving interface";
    }

    @Override
    public int onLoop(APIProvider api) {
        WidgetChild child = api.getDB().getWidgets().getWidgetChild(475, 11);
        if (child.interact()) {
            MethodProvider.sleepUntil(() -> !child.isVisible(),600);
        }
        return CronConstants.BASE_SLEEP;
    }
}
