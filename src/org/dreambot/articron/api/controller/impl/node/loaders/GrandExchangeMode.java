package org.dreambot.articron.api.controller.impl.node.loaders;

import org.dreambot.api.methods.walking.web.node.impl.bank.WebBankArea;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.NodeController;
import org.dreambot.articron.api.controller.impl.node.NodeLoader;
import org.dreambot.articron.behaviour.banking.OpenBank;
import org.dreambot.articron.behaviour.traversal.WalkToGE;
import org.dreambot.articron.data.ScriptMode;
import org.dreambot.articron.ui.dinh.ui.MainUI;

public class GrandExchangeMode implements NodeLoader {

    @Override
    public ScriptMode getMode() {
        return ScriptMode.GRAND_EXCHANGE;
    }

    @Override
    public void load(NodeController controller, APIProvider api, MainUI ui) {
        controller.commit(
                new WalkToGE(
                        () ->!WebBankArea.GRAND_EXCHANGE.getArea().contains(api.getDB().getLocalPlayer())
                ),
                new OpenBank(
                        () -> WebBankArea.GRAND_EXCHANGE.getArea().contains(api.getDB().getLocalPlayer())
                )
        );
    }
}
