package org.dreambot.articron.api.antiban.impl;

import org.dreambot.api.methods.Calculations;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.antiban.AbstractAntiban;

public class GoAFK extends AbstractAntiban {

    @Override
    public int execute(APIProvider api) {
        if (api.getDB().getMouse().moveMouseOutsideScreen()) {
            return api.getAntibanController().getFatiguedSleep(Calculations.random(0,100));
        }
        return 100;
    }
}
