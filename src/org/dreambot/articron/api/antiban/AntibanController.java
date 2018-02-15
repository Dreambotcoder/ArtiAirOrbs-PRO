package org.dreambot.articron.api.antiban;

import org.dreambot.articron.api.APIProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class AntibanController {

    private Map<BooleanSupplier, AbstractAntiban> antibans = new HashMap<>();
    private APIProvider api;

    public static double FATIGUE;

    static {
        FATIGUE = 20.4f;
    }

    public AntibanController(APIProvider api) {
        this.api = api;
    }

    public void addAntiBan(AbstractAntiban antiban) {
        addAntiBan(() -> true, antiban);
    }

    public void addAntiBan(BooleanSupplier when, AbstractAntiban antiban) {
        this.antibans.put(when,antiban);
    }


}
