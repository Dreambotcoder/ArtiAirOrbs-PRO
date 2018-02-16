package org.dreambot.articron.api.controller.impl.node.loaders;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.NodeController;
import org.dreambot.articron.api.controller.impl.node.NodeLoader;
import org.dreambot.articron.data.ScriptMode;

public class GrandExchangeMode implements NodeLoader {

    @Override
    public ScriptMode getMode() {
        return ScriptMode.GRAND_EXCHANGE;
    }

    @Override
    public void load(NodeController controller, APIProvider api) {

    }
}
