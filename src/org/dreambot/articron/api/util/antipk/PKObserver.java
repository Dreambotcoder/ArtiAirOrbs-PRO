package org.dreambot.articron.api.util.antipk;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.articron.api.APIProvider;

import java.util.List;
import java.util.concurrent.*;

public class PKObserver implements Runnable{

    private APIProvider api;
    private ScheduledExecutorService executor;

    public PKObserver(APIProvider api) {
        this.api = api;
    }

    public void start() {
        MethodProvider.log("[PK-OBSERVER] started the observer!");
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(this,0,3, TimeUnit.SECONDS);
    }

    public void stop() {
        executor.shutdownNow();
        MethodProvider.log("[PK-OBSERVER] Stopped the observer!");
    }

    @Override
    public void run() {
        //MethodProvider.log("[PK-OBSERVER] scanning...");
            try {
                if (api.getUtil().getAntiPkController().isInWild()) {
                    List<Player> players = api.getDB().getPlayers().all(api.getUtil().getAntiPkController().getKillerFilter());
                    if (!players.isEmpty()) {
                        for (Player p : players) {
                            MethodProvider.log("[PK-OBSERVER] threat found: " + p.getName());
                            api.getUtil().getAntiPkController().addPker(new PlayerKiller(p.getName(), api.getDB().getClient().getCurrentWorld()));
                        }
                    }
                } else {
                    Thread.sleep(10 * 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                stop();
            }
    }
}
