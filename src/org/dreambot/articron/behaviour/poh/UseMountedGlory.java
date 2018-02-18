package org.dreambot.articron.behaviour.poh;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class UseMountedGlory extends Node {

    public UseMountedGlory(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Mounted glory teleport";
    }

    @Override
    public int onLoop(APIProvider api) {
        if (api.getUtil().useGloryOnWall()) {
             MethodProvider.sleepUntil(() -> CronConstants.BANK_AREA.contains(api.getDB().getLocalPlayer()), 600);
        }
        return CronConstants.BASE_SLEEP;
    }
}
