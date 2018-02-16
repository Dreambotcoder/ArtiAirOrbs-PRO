package org.dreambot.articron.behaviour.craft;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;
import org.dreambot.articron.api.util.makeWidget.MakeMode;

import java.util.function.BooleanSupplier;

public class MakeAction extends Node {

    public MakeAction(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Creating orbs";
    }

    @Override
    public int onLoop(APIProvider api) {
       if (api.getUtil().getMakeHandler().make(MakeMode.MAKE_ALL)) {
           if (MethodProvider.sleepUntil(() -> api.getDB().getLocalPlayer().getAnimation() == CronConstants.CHARGE_ANIM, 3000)) {
               //MethodProvider.log("Glued checker");
               api.getUtil().startChargeChecker();
           }
       }
        return CronConstants.BASE_SLEEP;
    }
}
