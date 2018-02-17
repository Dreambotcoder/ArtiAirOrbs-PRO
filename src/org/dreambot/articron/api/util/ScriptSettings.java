package org.dreambot.articron.api.util;


import org.dreambot.articron.data.Edible;
import org.dreambot.articron.ui.dinh.ui.MainUI;

public class ScriptSettings {

    private boolean loaded;

    private int orbcount;
    private int eatPercentage;
    private boolean useStamina;
    private boolean teleportWhenAttacked;
    private boolean stopWithoutSupplies;
    private boolean useGlory;
    private Edible selectedFood;

    public void consume(MainUI ui) {
        this.orbcount = ui.getSettingPanel().getMainSettings().getMandatorySettings().getSlider().getSelected();
        this.eatPercentage = ui.getSettingPanel().getTeleportSettings().getSelection().getSelected();
        this.useStamina = ui.getSettingPanel().getTeleportSettings().getAdditionalSettings().get(0).isSelected();
        this.teleportWhenAttacked = ui.getSettingPanel().getTeleportSettings().getAdditionalSettings().get(1).isSelected();
        this.stopWithoutSupplies = ui.getSettingPanel().getTeleportSettings().getAdditionalSettings().get(2).isSelected();
        this.useGlory = ui.getSettingPanel().getTeleportSettings().getTeleportPanel().getArray()[0].isSelected();
        this.selectedFood = ui.getSettingPanel().getMainSettings().getMandatorySettings().getFoodSelection().getSelected();
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
}
