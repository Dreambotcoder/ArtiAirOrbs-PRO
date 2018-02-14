package org.dreambot.articron.behaviour.misc;

import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;

import java.util.function.BooleanSupplier;

public class PrayMelee extends Node {

    public PrayMelee(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Enabling pray";
    }

    @Override
    public int onLoop(APIProvider api) {
        if (api.getDB().getPrayer().toggle(true, Prayer.PROTECT_FROM_MELEE)) {
            return 600;
        }
        return 600;
    }
}
