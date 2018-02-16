package org.dreambot.articron.behaviour.banking;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class DepositTask extends Node {

    public DepositTask(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Depositing items";
    }

    @Override
    public int onLoop(APIProvider api) {
        int count = api.getUtil().getBankManager().realCount();
        if (api.getUtil().getBankManager().getValidSet().deposit()) {
            MethodProvider.sleepUntil(() -> count != api.getUtil().getBankManager().realCount(), 1200);
        }
       /** int count = api.getDB().getInventory().getEmptySlots();
        Item toDeposit =
                api.getDB().getInventory().get(item -> item != null && !api.getUtil().getBankManager().getItemNames().contains(item.getName()));
        if (toDeposit != null) {
            switch (api.getDB().getInventory().count(toDeposit.getName())) {
                case 1:
                    if (api.getDB().getBank().deposit(toDeposit)) {
                        MethodProvider.sleepUntil(() -> count != api.getDB().getInventory().getEmptySlots(), 1200);
                    }
                default:
                    if (api.getDB().getBank().depositAll(toDeposit.getName())) {
                        MethodProvider.sleepUntil(() -> count != api.getDB().getInventory().getEmptySlots(), 1200);
                    }
            }
        }**/
        return CronConstants.BASE_SLEEP;
    }
}
