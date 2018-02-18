package org.dreambot.articron.behaviour.craft;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class TickMakeOrbs extends Node {


    public TickMakeOrbs(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Tick charging";
    }

    @Override
    public int onLoop(APIProvider api) {
        if (!isValid()) {
            return CronConstants.BASE_SLEEP;
        }
        GameObject obelisk = api.getUtil().getObelisk();
        api.getDB().getMouse().setAlwaysHop(true);
        MethodProvider.sleep(600);
        if (!api.getUtil().getMakeHandler().isOpen() || api.getDB().getLocalPlayer().getAnimation() == CronConstants.CHARGE_ANIM) {
            if (api.getDB().getMagic().castSpellOn(Normal.CHARGE_AIR_ORB, obelisk)) {
                MethodProvider.sleepUntil(() -> api.getUtil().getMakeHandler().isOpen(), 600);
                MethodProvider.sleepUntil(() -> {
                    MethodProvider.sleep(0,100);
                    api.getDB().getKeyboard().type(" ",false);
                    return  !api.getUtil().getMakeHandler().isOpen();
                }, 600);
            }
        }
        if (api.getUtil().getMakeHandler().isOpen()) {
            api.getDB().getKeyboard().type(" ",false);
                 MethodProvider.sleep(Calculations.random(200,300));
        }
        api.getDB().getMouse().setAlwaysHop(false);
        return 1;
    }

}
