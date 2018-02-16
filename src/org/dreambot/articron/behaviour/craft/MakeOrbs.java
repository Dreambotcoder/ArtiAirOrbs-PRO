package org.dreambot.articron.behaviour.craft;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class MakeOrbs extends Node {


    public MakeOrbs(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Casting on obelisk";
    }

    @Override
    public int onLoop(APIProvider api) {
        GameObject obelisk = api.getUtil().getObelisk();
        if (obelisk != null) {
            if (api.getDB().getMagic().castSpellOn(Normal.CHARGE_AIR_ORB, obelisk)) {
                MethodProvider.sleepUntil(() -> api.getUtil().getMakeHandler().isOpen(), 2000);
            }
        }
        return CronConstants.BASE_SLEEP;
    }
}
