package org.dreambot.articron.behaviour.traversal;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronUtil;

import java.util.function.BooleanSupplier;

public class DrinkStamina extends Node {

    public DrinkStamina(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Drinking stamina";
    }

    @Override
    public int onLoop(APIProvider api) {
        Item pot = api.getDB().getInventory().get(i -> i.getName().contains("Stamina potion"));
        if (!api.getDB().getTabs().isOpen(Tab.INVENTORY)) {
            if (api.getDB().getTabs().open(Tab.INVENTORY)) {
                MethodProvider.sleepUntil(() -> api.getDB().getTabs().isOpen(Tab.INVENTORY), 600);
            }
        }
        int percentage = api.getDB().getWalking().getRunEnergy();
        if (pot != null && pot.interact("Drink")) {
            MethodProvider.sleepUntil(() -> percentage != api.getDB().getWalking().getRunEnergy(), 600);
        }
        return CronUtil.BASE_SLEEP;
    }
}
