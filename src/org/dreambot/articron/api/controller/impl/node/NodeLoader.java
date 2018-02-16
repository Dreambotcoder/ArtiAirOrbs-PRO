package org.dreambot.articron.api.controller.impl.node;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.data.ScriptMode;

public interface NodeLoader {
    ScriptMode getMode();
    void load(NodeController controller, APIProvider api);

}
