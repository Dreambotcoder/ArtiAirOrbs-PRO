package org.dreambot.articron.api.util.pking;

import org.dreambot.articron.api.APIProvider;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AntiPkController {

    private final Lock LOCK = new ReentrantLock();

    private Set<PlayerKiller> pkers;
    private PKObserver observer;
    private APIProvider api;

    public AntiPkController(APIProvider api) {
        this.pkers = new HashSet<>();
        this.api = api;
        this.observer = new PKObserver(api);
    }

    public void addPker(PlayerKiller pker) {
        LOCK.lock();
        this.pkers.add(pker);
        LOCK.unlock();
    }

    public List<Integer> getBadWorlds() {
        LOCK.lock();
        Set<Integer> worlds = new HashSet<>();
        for (PlayerKiller pker : pkers) {
            worlds.add(pker.getWorld());
        }
        LOCK.unlock();
        return new ArrayList<>(worlds);
    }


    public boolean shouldHop() {
        LOCK.lock();
        for (PlayerKiller pker : pkers) {
            if (pker.getWorld() == api.getDB().getClient().getCurrentWorld()) {
                return true;
            }
        }
        LOCK.unlock();
        return false;
    }

    public boolean pkerExists() {
        LOCK.lock();
        for (PlayerKiller pker : pkers) {
            if (api.getDB().getPlayers().all(Objects::nonNull).stream().anyMatch(p -> p.getName().equals(pker.getUsername()))) {
                return true;
            }
        }
        LOCK.unlock();
        return false;
    }

    public PKObserver getObserver() {
        return observer;
    }
}
