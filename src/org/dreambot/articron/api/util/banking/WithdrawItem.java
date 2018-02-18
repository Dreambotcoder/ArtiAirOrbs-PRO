package org.dreambot.articron.api.util.banking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntSupplier;

public class WithdrawItem {

    private String itemName;
    private IntSupplier amount;
    private List<String> deviations;

    public WithdrawItem(String itemName, IntSupplier amount, String... deviations) {
       this.itemName = itemName;
       this.amount = amount;
       this.deviations = new ArrayList<>();
       Collections.addAll(this.deviations, deviations);
    }

    public WithdrawItem(String itemName, int amount, String... deviations) {
        this(itemName, () -> amount, deviations);
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
        return amount.getAsInt();
    }
}
