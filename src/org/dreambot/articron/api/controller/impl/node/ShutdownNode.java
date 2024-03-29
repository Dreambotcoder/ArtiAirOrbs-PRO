package org.dreambot.articron.api.controller.impl.node;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.articron.api.APIProvider;

public class ShutdownNode extends Node {

    private String reason;

    public ShutdownNode(String reason) {
        super(() -> true);
        this.reason = reason;
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public int onLoop(APIProvider api) {
        api.getCronTray().sendMessage("Script shutdown", "Reason: " + reason);
        MethodProvider.log("Stopping script: " + reason);
        api.getCronTray().setVisible(false);
        return -1;
    }
}
