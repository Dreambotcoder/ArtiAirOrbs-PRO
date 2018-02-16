package org.dreambot.articron.api.util.antipk;

import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronConstants;

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

     public boolean isInWild() {
        WidgetChild c = api.getDB().getWidgets().getWidgetChild(CronConstants.WILDERNESS_LEVEL_MAIN, CronConstants.WILDERNESS_LEVEL_CHILD);
        return c != null && c.getText().contains("Level");
    }


    public int getWildernessLevel() {
        if (!isInWild())
            return -1;
        WidgetChild c = api.getDB().getWidgets().getWidgetChild(CronConstants.WILDERNESS_LEVEL_MAIN, CronConstants.WILDERNESS_LEVEL_CHILD);
        return Integer.parseInt(c.getText().replace("Level: ",""));
    }

    protected Filter<Player> getKillerFilter() {
        return player -> {
            if (player.getName().equals(api.getDB().getLocalPlayer().getName()))
                return false;
            int min = api.getDB().getLocalPlayer().getLevel() - getWildernessLevel();
            int max = api.getDB().getLocalPlayer().getLevel() + getWildernessLevel();
            return player.getLevel() >= min && player.getLevel() <= max && player.isSkulled();
        };
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
