package org.dreambot.articron.behaviour.banking;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class CloseBank extends Node {

    public CloseBank(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Closing bank";

    }

    @Override
    public int onLoop(APIProvider api) {
        if (api.getUtil().getBankManager().getCache().isTracking()) {
            api.getUtil().getBankManager().getCache().update();
        }
        if (api.getDB().getBank().close()) {
            MethodProvider.sleepUntil(() -> !api.getDB().getBank().isOpen(),600);
        }
        return CronConstants.BASE_SLEEP;
    }
}
