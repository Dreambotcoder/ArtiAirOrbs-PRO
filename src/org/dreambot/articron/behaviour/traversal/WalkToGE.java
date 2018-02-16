package org.dreambot.articron.behaviour.traversal;

import org.dreambot.api.methods.walking.web.node.impl.bank.WebBankArea;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class WalkToGE extends Node {

    public WalkToGE(BooleanSupplier condition) {
        super(condition);
    }

    @Override
    public String getStatus() {
        return "Walking to GE";
    }

    @Override
    public int onLoop(APIProvider api) {
        WebBankArea grandExchange = WebBankArea.GRAND_EXCHANGE;
        if (api.getDB().getWalking().walk(grandExchange.getArea().getRandomTile())) {
            return 600;
        }
        return CronConstants.BASE_SLEEP;
    }
}
