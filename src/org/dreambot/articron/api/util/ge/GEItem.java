package org.dreambot.articron.api.util.ge;

import java.util.function.IntSupplier;

public class GEItem {

    private String itemName;
    private int price;
    private IntSupplier amount;
    private boolean shouldSell;

    public GEItem(String itemName, int amount, int price, boolean sell) {
        this.amount = () -> amount;
        this.itemName = itemName;
        this.price = price;
        this.shouldSell = sell;
    }

    public GEItem(String itemName, IntSupplier amount, int price, boolean sell) {
        this.amount = amount;
        this.itemName = itemName;
        this.price = price;
        this.shouldSell = sell;
    }

    public String getItemName() {
        return itemName;
    }

    public int getAmount() {
        return amount.getAsInt();
    }

    public int getPrice() {
        return price;
    }

    public boolean isSelling() {
        return shouldSell;
    }

    @Override
    public String toString() {
        return this.itemName +" X"+getAmount();
    }
}
