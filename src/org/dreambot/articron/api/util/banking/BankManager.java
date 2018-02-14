package org.dreambot.articron.api.util.banking;

import org.dreambot.api.wrappers.items.Item;
import org.dreambot.articron.api.APIProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class BankManager {

    private APIProvider api;
    private List<WithdrawItem> withdrawList;

    public BankManager(APIProvider api) {
        this.api = api;
        withdrawList = new ArrayList<>();
    }

    public boolean shouldDeposit() {
        List<String> itemNames = getItemNames();
        for (Item item : api.getDB().getInventory().all(Objects::nonNull)) {
            if (!itemNames.contains(item.getName())) {
                return true;
            }
        }
        return false;
    }

    public int realCount() {
        int count = 0;
        for (Item item : api.getDB().getInventory().all(Objects::nonNull)) {
            count += item.getAmount();
        }
        return count;
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WithdrawItem item : withdrawList) {
            if (item.shouldWithdraw()) {
                names.add(item.getItemName());
                names.addAll(item.getDeviations());
            }
        }
        return names;
    }

    public boolean hasAllItems() {
        for (WithdrawItem item : withdrawList) {
            if (item.shouldWithdraw()) {
                if (!item.hasItem()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addItem(String itemName, int amount, BooleanSupplier condition) {
        this.withdrawList.add(new WithdrawItem(itemName, amount, condition,
                () -> api.getDB().getInventory().contains(itemName) && api.getDB().getInventory().count(itemName) >= amount));
    }

    public void addItem(String itemName, int amount, BooleanSupplier condition, BooleanSupplier has) {
        this.withdrawList.add(new WithdrawItem(itemName, amount, condition, has));
    }

    public void addItem(String itemName, int amount, BooleanSupplier condition, String... deviations) {
        this.withdrawList.add(new WithdrawItem(itemName, amount, condition,
                () -> api.getDB().getInventory().contains(itemName) &&
                        api.getDB().getInventory().count(itemName) >= amount)
                .setDeviations(deviations));
    }


    public void addItem(String itemName, int amount, BooleanSupplier condition, BooleanSupplier has, String... deviations) {
        this.withdrawList.add(new WithdrawItem(itemName, amount, condition, has).setDeviations(deviations));
    }
    public void removeItem(String itemName) {
        this.withdrawList.removeIf(item -> item.getItemName().equals(itemName));
    }

    public boolean withdrawNext() {
        for (WithdrawItem item : withdrawList) {
            if (item.shouldWithdraw() &&  !item.hasItem()) {
                int count = api.getDB().getInventory().count(item.getItemName());
                if (count < item.getAmount()) {
                    Item bankItem = api.getDB().getBank().get(item.getItemName());
                    if (bankItem != null) {
                        int toWithdraw = item.getAmount() - count;
                        if (!bankItem.isStackable() && (toWithdraw >= api.getDB().getInventory().getEmptySlots())) {
                            if (toWithdraw == 1) {
                                return api.getDB().getBank().withdraw(item.getItemName());
                            }
                            return api.getDB().getBank().withdrawAll(item.getItemName());
                        }
                        return api.getDB().getBank().withdraw(item.getItemName(), item.getAmount() - count);
                    }
                }
            }
        }
        return false;
    }

}
