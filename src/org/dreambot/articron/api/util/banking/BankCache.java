package org.dreambot.articron.api.util.banking;

import org.dreambot.articron.api.APIProvider;

import java.util.HashMap;
import java.util.Map;

public class BankCache {

    private APIProvider api;

    private Map<String,Integer> cache;

    private boolean track;

    BankCache(APIProvider api) {
        this.api = api;
        this.cache = new HashMap<>();
        track = false;
    }

    public void setTracked(boolean tracked) {
        this.track = tracked;
    }

    public void update() {
        for (Map.Entry<String,Integer> entry : cache.entrySet()) {
            String name = entry.getKey();
            int count = api.getDB().getBank().count(name);
            this.cache.put(name,count);
        }
    }

    public void trackItem(String itemName) {
        this.cache.put(itemName,-1);
    }

    public int getCount(String itemName) {
        if (this.cache.containsKey(itemName))
            return this.cache.get(itemName);
        return -1;
    }

    public void stopTracking(String itemName) {
        this.cache.remove(itemName);
    }

    public boolean isTracking() {
        return track;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,Integer> entry : this.cache.entrySet()) {
            sb.append("[").append(entry.getKey()).append(": ").append(entry.getValue()).append("] ");
        }
        return sb.toString();
    }
}
