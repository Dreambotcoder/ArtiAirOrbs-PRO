package org.dreambot.articron.api.util.banking;

import org.dreambot.api.wrappers.items.Item;
import org.dreambot.articron.api.APIProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public class ItemSet {

    private BooleanSupplier when;
    private List<WithdrawItem> items;
    private APIProvider api;
    private Runnable ifOut;

    public ItemSet(APIProvider api, BooleanSupplier when, Runnable ifOut) {
        this.when = when;
        this.items = new ArrayList<>();
        this.api = api;
        this.ifOut = ifOut;
    }

    public ItemSet(APIProvider api, BooleanSupplier when) {
        this(api,when,null);
    }

    public void addItem(String name, int amount, String... deviations) {
        items.add(new WithdrawItem(name,amount,deviations));
    }

    public void addItem(String name, IntSupplier supplier, String... deviations) {
        items.add(new WithdrawItem(name,supplier,deviations));
    }

    private boolean hasItem(WithdrawItem item) {
        int count = api.getDB().getInventory().count(i -> i.getName().equals(item.getItemName()) ||
                item.getDeviations().contains(i.getName()));
        return count == item.getAmount();
    }

    public boolean hasNext() {
        for (WithdrawItem item : items) {
            if (!hasItem(item)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isValid() {
        return when.getAsBoolean();
    }

    private boolean isInSet(String itemName) {
        for (WithdrawItem item : items) {
            if (item.getDeviations().contains(itemName) || item.getItemName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldDeposit() {
        for (Item item : api.getDB().getInventory().all(Objects::nonNull)) {
            if (!isInSet(item.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean deposit() {
        for (Item item : api.getDB().getInventory().all(Objects::nonNull)) {
            if (!isInSet(item.getName())) {
                int count = api.getDB().getInventory().count(item.getName());
                switch (count) {
                    case 1:
                        return api.getDB().getBank().deposit(item.getName());
                    default:
                        return api.getDB().getBank().depositAll(item.getName());

                }
            }
        }
        return false;
    }

    public List<String> getDeficit() {
        List<String> deficits = new ArrayList<>();
       for (WithdrawItem item : items) {
           int offset = item.getAmount() - api.getDB().getInventory().count(i -> i.getName().equals(item.getItemName()) ||
                   item.getDeviations().contains(i.getName()));
           if (offset > 0) {
               if (api.getDB().getBank().count(item.getItemName()) < offset) {
                   deficits.add(item.getItemName());
               }
           }
       }
       return deficits;
    }

    public boolean solveNext() {
        for (WithdrawItem item : items) {
            Item itm = api.getDB().getBank().get(item.getItemName());
            if (!hasItem(item)) {
                int offset = item.getAmount() - api.getDB().getInventory().count(i -> i.getName().equals(item.getItemName()) ||
                        item.getDeviations().contains(i.getName()));
                if (offset > 0) {
                    if (api.getDB().getBank().count(item.getItemName()) < offset) {
                        if (ifOut != null)
                            ifOut.run();
                        else
                            api.getNodeController().stopScript("Unexpected bank logic");
                        return false;
                    }
                    if (offset >= api.getDB().getInventory().getEmptySlots() && !itm.isStackable())
                        return api.getDB().getBank().withdrawAll(item.getItemName());
                    return api.getDB().getBank().withdraw(item.getItemName(), offset);
                }
                else if (offset < 0) {
                    return api.getDB().getBank().deposit(item.getItemName(), (offset * -1));
                }
            }
        }
        return false;
    }
}
