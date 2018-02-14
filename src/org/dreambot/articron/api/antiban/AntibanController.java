package org.dreambot.articron.api.antiban;

import org.dreambot.articron.api.APIProvider;

import java.util.ArrayList;
import java.util.List;

public class AntibanController {

    private List<AbstractAntiban> antibans = new ArrayList<>();
    private APIProvider api;

    private double fatigue;

    public AntibanController(APIProvider api) {
        this.api = api;
        this.fatigue = 0;
    }

    public void setFatigue(double fatigue) {
        this.fatigue = fatigue;
    }

    public double getFatigue() {
        return fatigue;
    }
}
