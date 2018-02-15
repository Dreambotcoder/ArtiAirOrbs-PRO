package org.dreambot.articron.api.antiban;

import org.dreambot.articron.api.APIProvider;

import java.util.HashMap;
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

     public int getFatiguedSleep(int range) {
        return (int) Math.ceil(range * (fatigue / 100));
    }



}
