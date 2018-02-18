package org.dreambot.articron.behaviour.deathwalk;

import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class WalkToDeathBank extends Node {

    public WalkToDeathBank(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Deathwalking to bank";
    }

    @Override
    public int onLoop(APIProvider api) {
        BankLocation closest = api.getDB().getBank().getClosestBankLocation();
        //MethodProvider.log("Closest bank = " + closest.toString());
        if (api.getDB().getWalking().walk(closest.getCenter())) {
            return 600;
        }
        return CronConstants.BASE_SLEEP;
    }
}
