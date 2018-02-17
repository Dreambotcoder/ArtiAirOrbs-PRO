package org.dreambot.articron.api.util.ge;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.articron.api.APIProvider;

import java.util.*;

public class ExchangeHandler {

    private APIProvider api;

    private Set<String> deficits;

    public ExchangeHandler(APIProvider api) {
        this.api = api;
        this.deficits = new HashSet<>();
    }

    public void setDeficits(String... deficits) {
        List<String> def = Arrays.asList(deficits);
        if (def.contains("Cosmic rune") || def.contains("Unpowered orb")) {
            this.deficits.add("Cosmic rune");
            this.deficits.add("Unpowered orb");
        }
        Collections.addAll(this.deficits,deficits);
    }

}
