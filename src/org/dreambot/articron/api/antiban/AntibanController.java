package org.dreambot.articron.api.antiban;

import org.dreambot.api.methods.Calculations;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class AntibanController {

    private Map<BooleanSupplier, AbstractAntiban> antibans = new HashMap<>();
    private APIProvider api;

    private double fatigue = 23.4f, seed;


    public AntibanController(APIProvider api,double seed) {
        this.seed = seed;
        this.api = api;
    }

    public double getFatigue() {
        return fatigue;
    }

    public void addAntiBan(AbstractAntiban antiban) {
        addAntiBan(() -> true, antiban);
    }

    public void addAntiBan(BooleanSupplier when, AbstractAntiban antiban) {
        this.antibans.put(when,antiban);
    }

    public boolean shouldExecute() {
        return false;
    }

    public int execute() {
        List<AbstractAntiban> validAntibans = new ArrayList<>();
        for (Map.Entry<BooleanSupplier, AbstractAntiban> entry  : this.antibans.entrySet()) {
            if (entry.getKey().getAsBoolean())
                validAntibans.add(entry.getValue());
        }
        if (validAntibans.isEmpty())
            return CronConstants.BASE_SLEEP;
        return validAntibans.get(Calculations.random(0,validAntibans.size() - 1)).execute(api);
    }

     public int getFatiguedSleep(int range) {
        return (int) Math.ceil(range * (fatigue / 100));
    }



}
