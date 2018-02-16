package org.dreambot.articron.behaviour.misc;

import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class DisablePray extends Node {

    public DisablePray(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Disabling prayer";
    }

    @Override
    public int onLoop(APIProvider api) {
        if (api.getDB().getPrayer().toggle(false, Prayer.PROTECT_FROM_MELEE)) {
            return CronConstants.BASE_SLEEP;
        }
        return CronConstants.BASE_SLEEP;
    }
}
