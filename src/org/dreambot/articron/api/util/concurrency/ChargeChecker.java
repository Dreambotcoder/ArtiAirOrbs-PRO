package org.dreambot.articron.api.util.concurrency;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronConstants;


public class ChargeChecker implements Runnable {

    private APIProvider provider;

    private boolean charging;

    public ChargeChecker(APIProvider provider) {
        this.provider = provider;
        charging = true;
    }

    @Override
    public void run() {
        int animCount = 0;
        while (charging) {
            try {
                //MethodProvider.log("Picked up charging");
                if (provider.getDB().getDialogues().canContinue()) {
                    charging = false;
                    //MethodProvider.log("Dialogue open, we aren't charging");
                    return;
                }

                if(provider.getDB().getLocalPlayer().isInCombat()) {
                    charging = false;
                    return;
                }

                if (!provider.getDB().getInventory().contains("Unpowered orb")) {
                    charging = false;
                    //MethodProvider.log("No orbs left");
                    return;
                }

                if (provider.getDB().getLocalPlayer().getAnimation() != CronConstants.CHARGE_ANIM) {
                    animCount++;
                } else {
                    animCount = 0;
                }

                if(animCount > 4) {
                    //MethodProvider.log("No animations picked up recently");
                    charging = false;
                    return;
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //MethodProvider.log("We stopped charging");
    }

    public boolean isCharging() {
        return charging;
    }
}
