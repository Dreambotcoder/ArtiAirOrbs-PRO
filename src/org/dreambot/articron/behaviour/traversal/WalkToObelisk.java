package org.dreambot.articron.behaviour.traversal;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronUtil;

import java.util.function.BooleanSupplier;

public class WalkToObelisk extends Node{

    public WalkToObelisk(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Walking to obelisk";
    }

    @Override
    public int onLoop(APIProvider api) {
        if (api.getObeliskPath().traverse()) {
            return CronUtil.BASE_SLEEP;
        }
        return 1;
    }
}
