package org.dreambot.articron.behaviour.banking;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class WithdrawAction extends Node {

    public WithdrawAction(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Withdrawing items";
    }

    @Override
    public int onLoop(APIProvider api) {
        int count = api.getUtil().getBankManager().realCount();
        if (api.getUtil().getBankManager().getValidSet().solveNext()) {
            MethodProvider.sleepUntil(() -> count != api.getUtil().getBankManager().realCount(), 5000);
        }
        return CronConstants.BASE_SLEEP;
    }
}
