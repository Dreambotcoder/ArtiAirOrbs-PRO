package org.dreambot.articron.api.util.banking;


import org.dreambot.articron.api.APIProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;

public class WithdrawItem {

    private String itemName;
    private int amount;
    private List<String> deviations;
    private BooleanSupplier condition, has;

    public WithdrawItem(APIProvider api, String itemName, int amount, BooleanSupplier condition) {
        this(
                itemName,
                amount,
                condition,
                () -> api.getDB().getInventory().contains(itemName) && api.getDB().getInventory().count(itemName) >= amount
        );
    }

    public WithdrawItem(String itemName, int amount, BooleanSupplier condition, BooleanSupplier has) {
        this.itemName = itemName;
        this.amount = amount;
        this.condition = condition;
        this.has = has;
        this.deviations = new ArrayList<>();
    }

    public WithdrawItem setDeviations(String... deviations) {
        Collections.addAll(this.deviations, deviations);
        return this;
    }

    public boolean hasItem() {
        return has.getAsBoolean();
    }

    public List<String> getDeviations() {
        return deviations;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean shouldWithdraw() {
        return condition.getAsBoolean();
    }

    public int getAmount() {
        return amount;
    }
}
