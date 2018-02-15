package org.dreambot.articron.api.antiban.impl;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.antiban.AbstractAntiban;
import org.dreambot.articron.api.antiban.AntibanController;
import org.dreambot.articron.api.util.CronUtil;

import java.util.List;
import java.util.Objects;

public class ExaminePlayer extends AbstractAntiban {

    @Override
    public int execute(APIProvider api) {
        List<Player> players = api.getDB().getPlayers().all(Objects::nonNull);
        Player ranPlayer = players.get(Calculations.random(0,players.size() - 1));
        if (ranPlayer != null) {
            if (api.getDB().getMouse().click(ranPlayer,false)) {
                MethodProvider.sleep((int)Math.ceil(Calculations.random(0,600) * AntibanController.FATIGUE));
            }
        }
        if (api.getDB().getClient().getMenu().isMenuVisible()) {
            if (api.getDB().getMouse().move()) {
                return CronUtil.getFatiguedSleep(Calculations.random(0,600));
            }
        }
        return 100;
    }
}
