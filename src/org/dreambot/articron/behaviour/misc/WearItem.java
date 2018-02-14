package org.dreambot.articron.behaviour.misc;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronUtil;

import java.util.function.BooleanSupplier;

public class WearItem extends Node {

    private String[] itemNames;

    public WearItem(BooleanSupplier condition, String... items) {
        super(condition);
        this.itemNames = items;
    }

    @Override
    public String getStatus() {
        return "Equipping item";
    }

    @Override
    public int onLoop(APIProvider api) {
        Item item = api.getDB().getInventory().get(itemNames);
        if (item != null) {
            if (!api.getDB().getTabs().isOpen(Tab.INVENTORY)) {
                if (api.getDB().getTabs().open(Tab.INVENTORY)) {
                    MethodProvider.sleepUntil(() -> api.getDB().getTabs().isOpen(Tab.INVENTORY), 600);
                }
            }
            int count = api.getDB().getInventory().getEmptySlots();
            if (item.interact("Wear")) {
                MethodProvider.sleepUntil(() -> count != api.getDB().getInventory().getEmptySlots(), 5000);
            }
        }
        return CronUtil.BASE_SLEEP;
    }
}
