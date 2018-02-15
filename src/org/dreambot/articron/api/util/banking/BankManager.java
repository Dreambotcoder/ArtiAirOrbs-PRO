package org.dreambot.articron.api.util.banking;

import org.dreambot.api.wrappers.items.Item;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronUtil;

import java.util.*;
import java.util.function.BooleanSupplier;

public class BankManager {

    private APIProvider api;
    private Map<String,ItemSet> bankCases;

    public BankManager(APIProvider api) {
        this.api = api;
        bankCases = new HashMap<>();
    }

    public ItemSet getSet(String name) {
        return bankCases.get(name);
    }

    public void setSet(String name, ItemSet entry) {
        bankCases.put(name,entry);
    }

    public ItemSet getValidSet() {
        for (Map.Entry<String,ItemSet> entry : bankCases.entrySet()) {
            if (entry.getValue().isValid()) {
                CronUtil.BANKSET = entry.getKey();
                return entry.getValue();
            }
        }
        return null;
    }


    public int realCount() {
        int count = 0;
        for (Item item : api.getDB().getInventory().all(Objects::nonNull)) {
            count += item.getAmount();
        }
        return count;
    }


    public boolean shouldDeposit() {
        ItemSet set = getValidSet();
        return set != null && set.shouldDeposit();
    }


    public boolean hasAllItems() {
        return getValidSet() == null || !getValidSet().hasNext();
    }
}
