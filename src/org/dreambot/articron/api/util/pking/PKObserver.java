package org.dreambot.articron.api.util.pking;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.articron.api.APIProvider;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PKObserver implements Runnable{

    private APIProvider api;
    private boolean observing;
    private ExecutorService executor;

    public PKObserver(APIProvider api) {
        this.api = api;
        executor =  Executors.newSingleThreadExecutor();
    }

    public void start() {
        this.observing = true;
        executor.submit(this);
    }

    public void stop() {
        this.observing = false;
        executor.shutdownNow();
    }

    @Override
    public void run() {
        while(this.observing) {
            try {
                if (api.getUtil().isInWild()) {
                    List<Player> players = api.getDB().getPlayers().all(Objects::nonNull);
                    for (Player p : players) {
                        if (p.isSkulled() && api.getUtil().canBeAttackedBy(p)) {
                            MethodProvider.log("[PK-OBSERVER] Found new threat: " + p.getName());
                            api.getUtil().getAntiPkController().addPker(new PlayerKiller(p.getName(), api.getDB().getClient().getCurrentWorld()));
                        }
                    }
                }
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                this.observing = false;
            }
        }
    }
}
