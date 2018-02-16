package org.dreambot.articron.behaviour.traversal.teleport;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class TabTeleport extends Node {

    public TabTeleport(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Teleporting";
    }

    @Override
    public int onLoop(APIProvider api) {
        if (api.getDB().getMagic().isSpellSelected()) {
            api.getDB().getMouse().click();
        }
        Item tab = api.getDB().getInventory().get("Teleport to house");
        if (tab != null && tab.interact("Break")) {
            MethodProvider.sleepUntil(() -> !api.getUtil().atObelisk(), 3000);
        }
        return CronConstants.BASE_SLEEP;
    }
}
