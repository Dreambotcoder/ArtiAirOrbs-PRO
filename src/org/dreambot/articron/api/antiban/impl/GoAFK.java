package org.dreambot.articron.api.antiban.impl;

import org.dreambot.api.methods.Calculations;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.antiban.AbstractAntiban;
import org.dreambot.articron.api.util.CronUtil;

public class GoAFK extends AbstractAntiban {

    @Override
    public int execute(APIProvider api) {
        if (api.getDB().getMouse().moveMouseOutsideScreen()) {
            return CronUtil.getFatiguedSleep(Calculations.random(100));
        }
        return 100;
    }
}
