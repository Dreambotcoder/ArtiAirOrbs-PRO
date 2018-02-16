package org.dreambot.articron.api.controller.impl.node;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.data.ScriptMode;
import org.dreambot.articron.ui.dinh.ui.MainUI;

public interface NodeLoader {
    ScriptMode getMode();
    void load(NodeController controller, APIProvider api, MainUI ui);

}
