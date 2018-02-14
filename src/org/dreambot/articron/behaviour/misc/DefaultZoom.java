package org.dreambot.articron.behaviour.misc;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronUtil;

import java.util.function.BooleanSupplier;

public class DefaultZoom extends Node {

    public DefaultZoom(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "YOU ARE NOT ON DEFAULT ZOOM!";
    }

    @Override
    public int onLoop(APIProvider api) {
        if ( api.getDB().getClientSettings().setDefaultZoom()) {
             MethodProvider.sleepUntil(() -> api.getDB().getClientSettings().getExactZoomValue() == CronUtil.DEFAULT_ZOOM,1000);
        }
        return CronUtil.BASE_SLEEP;
    }
}
