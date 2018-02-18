package org.dreambot.articron.behaviour.traversal.teleport;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class GloryTele extends Node {

    public GloryTele(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Operating glory amulet";
    }

    @Override
    public int onLoop(APIProvider api) {
        if(api.getDB().getBank().isOpen()) {
            if (api.getDB().getBank().close()) {
                MethodProvider.sleepUntil(() -> !api.getDB().getBank().isOpen(),1000);
            }
        }
        if (api.getDB().getMagic().isSpellSelected()) {
            api.getDB().getMouse().click();
        }
        if (api.getDB().getEquipment().interact(EquipmentSlot.AMULET, "Edgeville")) {
            MethodProvider.sleepUntil(() -> CronConstants.BANK_AREA.contains(api.getDB().getLocalPlayer()), 3000);
        }
        return CronConstants.BASE_SLEEP;
    }
}
