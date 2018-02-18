package org.dreambot.articron.behaviour.banking;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;

import java.util.function.BooleanSupplier;

public class SingularWithdraw extends Node {

    private String itemName;

    public SingularWithdraw(String itemName,BooleanSupplier condition) {
        super(condition);
        this.itemName = itemName;
    }

    @Override
    public String getStatus() {
        return "Withdrawing " + itemName;
    }

    @Override
    public int onLoop(APIProvider api) {
        if (api.getDB().getBank().withdraw(itemName,1)) {
            MethodProvider.sleepUntil(()  -> api.getDB().getInventory().contains(itemName),1000);
        }
        return 600;
    }
}
