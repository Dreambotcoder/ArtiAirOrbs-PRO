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

    public WithdrawItem(String itemName, int amount, String... deviations) {
       this.itemName = itemName;
       this.amount = amount;
       this.deviations = new ArrayList<>();
       Collections.addAll(this.deviations, deviations);
    }

    public WithdrawItem setDeviations(String... deviations) {
        Collections.addAll(this.deviations, deviations);
        return this;
    }

    public List<String> getDeviations() {
        return deviations;
    }

    public String getItemName() {
        return itemName;
    }


    public int getAmount() {
        return amount;
    }
}
