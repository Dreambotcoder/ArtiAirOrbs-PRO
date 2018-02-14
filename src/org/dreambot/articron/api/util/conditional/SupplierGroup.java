package org.dreambot.articron.api.util.conditional;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

public class SupplierGroup {

    private List<BooleanSupplier> conditions;

    public SupplierGroup() {
        conditions = new ArrayList<>();
    }

    public SupplierGroup put(BooleanSupplier... supplier) {
        this.conditions.addAll(Arrays.asList(supplier));
        return this;
    }

    public boolean getAsBoolean() {
        if (conditions.isEmpty())
            return false;
        for (BooleanSupplier s : conditions) {
            if (s.getAsBoolean()) {
                return true;
            }
        }
        return false;
    }
}
