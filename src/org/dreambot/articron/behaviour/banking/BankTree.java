package org.dreambot.articron.behaviour.banking;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.NodeTree;

import java.util.function.BooleanSupplier;

public class BankTree extends NodeTree {

    public BankTree(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Banking";
    }

    @Override
    public int onLoop(APIProvider api) {
        if (api.getDB().getBank().isOpen() && api.getUtil().getBankManager().getCache().isTracking()) {
            api.getUtil().getBankManager().getCache().update();
        }
        return super.onLoop(api);

    }
}
