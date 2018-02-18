package org.dreambot.articron.api.util;


import org.dreambot.articron.data.Edible;
import org.dreambot.articron.ui.dinh.ui.MainUI;
import org.dreambot.articron.ui.dinh.ui.tab.settings.sub.StaticItem;

public class ScriptSettings {

    private boolean loaded;

    private int orbcount;
    private int eatPercentage;
    private boolean useStamina;
    private boolean teleportWhenAttacked;
    private boolean prayDemons;
    private boolean tickCharge;
    private boolean stopWithoutSupplies;
    private boolean useGlory;
    private Edible selectedFood;

    private StaticItem unpoweredOrbs, cosmicRunes, staminaPots, teleportItem, foodItem;


    private int airOrbsellPrice;

    public void consume(MainUI ui) {
        this.orbcount = ui.getSettingPanel().getMainSettings().getMandatorySettings().getSlider().getSelected();
        this.eatPercentage = ui.getSettingPanel().getTeleportSettings().getSelection().getSelected();
        this.useStamina = ui.getSettingPanel().getTeleportSettings().getAdditionalSettings().get(0).isSelected();
        this.teleportWhenAttacked = ui.getSettingPanel().getTeleportSettings().getAdditionalSettings().get(1).isSelected();
        this.prayDemons = ui.getSettingPanel().getTeleportSettings().getAdditionalSettings().get(2).isSelected();
        this.stopWithoutSupplies = true;//ui.getSettingPanel().getTeleportSettings().getAdditionalSettings().get(2).isSelected();
        this.useGlory = ui.getSettingPanel().getTeleportSettings().getTeleportPanel().getArray()[0].isSelected();
        this.selectedFood = ui.getSettingPanel().getMainSettings().getMandatorySettings().getFoodSelection().getSelected();
        this.tickCharge = ui.getSettingPanel().getTeleportSettings().getAdditionalSettings().get(1).isSelected();
        /**if (!this.stopWithoutSupplies) {
            unpoweredOrbs = ui.getSettingPanel().getMainSettings().getOtherSettings().getExchangePanel().get()[0];
            cosmicRunes = ui.getSettingPanel().getMainSettings().getOtherSettings().getExchangePanel().get()[1];
            staminaPots = ui.getSettingPanel().getMainSettings().getOtherSettings().getExchangePanel().get()[2];
            teleportItem = ui.getSettingPanel().getMainSettings().getOtherSettings().getExchangePanel().get()[4];
            foodItem = ui.getSettingPanel().getMainSettings().getOtherSettings().getExchangePanel().get()[5];
            airOrbsellPrice = ui.getSettingPanel().getMainSettings().getOtherSettings().getExchangePanel().getSell().getPrice().getPrice();
        }**/
    }

    public int getOrbcount() {
        return orbcount;
    }

    public int getEatPercentage() {
        return eatPercentage;
    }

    public boolean isUseStamina() {
        return useStamina;
    }

    public boolean isTeleportWhenAttacked() {
        return teleportWhenAttacked;
    }

    public boolean isStopWithoutSupplies() {
        return stopWithoutSupplies;
    }

    public boolean isUseGlory() {
        return useGlory;
    }

    public Edible getSelectedFood() {
        return selectedFood;
    }

    public int getAirOrbsellPrice() {
        return airOrbsellPrice;
    }

    public StaticItem getUnpoweredOrbs() {
        return unpoweredOrbs;
    }

    public StaticItem getCosmicRunes() {
        return cosmicRunes;
    }

    public StaticItem getStaminaPots() {
        return staminaPots;
    }

    public StaticItem getTeleportItem() {
        return teleportItem;
    }

    public StaticItem getFoodItem() {
        return foodItem;
    }

    public boolean isPrayDemons() {
        return prayDemons;
    }

    public boolean isTickCharge() {
        return tickCharge;
    }
}
