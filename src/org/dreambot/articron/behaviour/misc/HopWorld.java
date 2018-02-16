package org.dreambot.articron.behaviour.misc;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.world.World;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class HopWorld extends Node{

    public HopWorld(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Hopping to safe world";
    }

    @Override
    public int onLoop(APIProvider api) {
        World rand = api.getDB().getWorlds().getRandomWorld
                (
                    w -> !api.getUtil().getAntiPkController().getBadWorlds().contains(w.getID()) &&
                            !w.isF2P() &&
                            !w.isHighRisk() &&
                            !w.isLastManStanding() &&
                            !w.isPVP() &&
                            !w.isTournamentWorld() &&
                            !w.isDeadmanMode()
                );

        if (api.getDB().getWorldHopper().hopWorld(rand)) {
            MethodProvider.sleepUntil(() -> api.getDB().getClient().getCurrentWorld() == rand.getID(), 10000);
        }
        return CronConstants.BASE_SLEEP;
    }
}
