package org.dreambot.articron.behaviour.banking;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronUtil;

import java.util.function.BooleanSupplier;

public class OpenBank extends Node {


    public OpenBank(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Opening the bank";
    }

    @Override
    public int onLoop(APIProvider api) {
        if (api.getDB().getBank().open()) {
            NPC banker = api.getDB().getNpcs().closest("Banker");
                MethodProvider.sleepUntil(() -> api.getDB().getBank().isOpen(), banker != null ? (long) banker.distance() * 600 : 3000);
        }
        return CronUtil.BASE_SLEEP;
    }
}
