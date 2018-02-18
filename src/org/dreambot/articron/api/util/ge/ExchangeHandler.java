package org.dreambot.articron.api.util.ge;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.banking.BankCache;

import java.util.ArrayList;
import java.util.List;

public class ExchangeHandler {

    private APIProvider api;

    private List<GEItem> itemsToBuy;


    public List<GEItem> getItemsToBuy() {
        return itemsToBuy;
    }

    public ExchangeHandler(APIProvider api) {
        this.api = api;
        this.itemsToBuy = new ArrayList<>();
    }

    public void reset() {
        itemsToBuy.clear();
    }

    public void readCache(BankCache cache) {
        int unpoweredOrbCount = cache.getCount("Unpowered orb");
        int cosmicCount = cache.getCount("Cosmic rune");
        int orbPrice = api.getSettings().getUnpoweredOrbs().getPrice().getPrice();
        int cosmicPrice = api.getSettings().getCosmicRunes().getPrice().getPrice();
        if (unpoweredOrbCount < api.getSettings().getOrbcount()) {
            if (api.getSettings().getUnpoweredOrbs().getAmount().getText().equals("DYNAMIC")) {

                itemsToBuy.add(new GEItem("Unpowered orb",
                        () ->
                                calculateRatio(orbPrice,cosmicPrice,
                                        (api.getDB().getInventory().count("Coins") / 100) * 80).getOrbAmount(),
                        orbPrice, true
                        ));
                itemsToBuy.add(new GEItem("Cosmic rune",
                        () ->
                                calculateRatio(orbPrice,cosmicPrice,
                                        (api.getDB().getInventory().count("Coins") / 100) * 80).getCosmicAmount(),
                        cosmicPrice, true
                        ));
            } else {
                itemsToBuy.add(new GEItem("Unpowered orb",
                        Integer.parseInt(api.getSettings().getUnpoweredOrbs().getAmount().getText()),
                        orbPrice, true));
            }
        }
        if (cosmicCount < (api.getSettings().getOrbcount() * 3)) {
            if (api.getSettings().getCosmicRunes().getAmount().getText().equals("DYNAMIC")) {
                itemsToBuy.add(new GEItem("Unpowered orb",
                        () ->
                                calculateRatio(orbPrice,cosmicPrice,
                                        (api.getDB().getInventory().count("Coins") / 100) * 80).getOrbAmount(),
                        orbPrice, true
                ));
                itemsToBuy.add(new GEItem("Cosmic rune",
                        () ->
                                calculateRatio(orbPrice,cosmicPrice,
                                        (api.getDB().getInventory().count("Coins") / 100) * 80).getCosmicAmount(),
                        cosmicPrice, true
                ));
            } else  {
                itemsToBuy.add(new GEItem("Cosmic rune", Integer.parseInt(api.getSettings().getCosmicRunes().getAmount().getText()),
                        cosmicPrice, true));
            }
        }

        int staminas = cache.getCount("Stamina potion(4)");
        if (api.getSettings().isUseStamina() && staminas < 1) {
            itemsToBuy.add(new GEItem("Stamina potion(4)",
                    Integer.parseInt(api.getSettings().getStaminaPots().getAmount().getText()),
                    api.getSettings().getStaminaPots().getPrice().getPrice(), true));
        }

        int glory = cache.getCount("Amulet of glory(6)");
        int tabs = cache.getCount("Teleport to house");
        if (api.getSettings().isUseGlory() && glory < 1) {
            itemsToBuy.add(new GEItem("Amulet of glory(6)",
                    Integer.parseInt(api.getSettings().getTeleportItem().getAmount().getText()),
                    api.getSettings().getTeleportItem().getPrice().getPrice(), false));
        } else if (!api.getSettings().isUseGlory() && tabs < 1) {
            itemsToBuy.add(new GEItem("Teleport to house",
                    Integer.parseInt(api.getSettings().getTeleportItem().getAmount().getText()),
                    api.getSettings().getTeleportItem().getPrice().getPrice(), false));
        }
        int foodCount = cache.getCount(api.getSettings().getSelectedFood().toString());
        if (foodCount < 10) {
            itemsToBuy.add(new GEItem(api.getSettings().getSelectedFood().toString(),
                    Integer.parseInt(api.getSettings().getFoodItem().getAmount().getText()),
                    api.getSettings().getFoodItem().getPrice().getPrice(), false));
        }
    }

    private RatioResult calculateRatio(int orbPrice, int cosmicPrice, int totalGP) {
        int totalPer = orbPrice + (cosmicPrice * 3);
        int offset = totalGP/totalPer;
        return new RatioResult(offset, offset * 3);
    }

    class RatioResult {

        private int orbAmount, cosmicAmount;
        public RatioResult(int price1, int price2) {
            this.orbAmount = price1;
            this.cosmicAmount = price2;
        }

        public int getOrbAmount() {
            return orbAmount;
        }

        public int getCosmicAmount() {
            return cosmicAmount;
        }

        @Override
        public String toString() {
            return "" + getOrbAmount() +" orbs ,"+getCosmicAmount()+" cosmics";
        }
    }
}
