package org.dreambot.articron.api.util;

import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.banking.BankManager;
import org.dreambot.articron.api.util.banking.ItemSet;
import org.dreambot.articron.api.util.concurrency.ChargeChecker;
import org.dreambot.articron.api.util.makeWidget.MakeHandler;
import org.dreambot.articron.api.util.antipk.AntiPkController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScriptUtil {

    private final int OBELISK_ID = 2152;


    private APIProvider api;
    private BankManager bankManager;
    private MakeHandler makeWidget;
    private int potThreshold = 30, eatThreshold = 50;
    private ChargeChecker checker;
    private ExecutorService executor;
    private AntiPkController antiPkController;

    public ScriptUtil(APIProvider api) {
        this.api = api;
        this.makeWidget = new MakeHandler(api);
        this.bankManager = new BankManager(api);
        this.antiPkController = new AntiPkController(api);
    }

    public boolean atObelisk() {
        return  getObelisk() != null;
    }

    public boolean shouldPray() {
        if (api.getDB().getSkills().getBoostedLevels(Skill.PRAYER) <= 0)
            return false;
        NPC demon = api.getDB().getNpcs().closest("Black demon");
        return demon != null && demon.distance() <= 7;
    }

    public boolean hasTeleport() {
        Item ammy = api.getDB().getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot());
        return ammy != null && ammy.getName().contains("Amulet of glory") && !ammy.getName().equals("Amulet of glory");
    }

    public boolean shouldPot() {
        return api.getUtil().getPotThreshold() >= api.getDB().getWalking().getRunEnergy()
                && api.getDB().getInventory().contains(item -> item.getName().contains("Stamina potion"));
    }

    public GameObject getObelisk() {
        return api.getDB().getGameObjects().closest(OBELISK_ID);
    }

    public boolean shouldTeleport() {
        return (!api.getDB().getInventory().contains("Cosmic rune") || !api.getDB().getInventory().contains("Unpowered orb"))
                || api.getUtil().getAntiPkController().pkerExists() && api.getDB().getLocalPlayer().isInCombat()
                && api.getDB().getLocalPlayer().getCharacterInteractingWithMe() instanceof Player;
    }

    public boolean gettingHit() {
        int[] splats = api.getDB().getLocalPlayer().getHitSplatCycles();
        for (int splat : splats) {
            if (splat != 0)
                return true;
        }
        return false;
    }

    public boolean wildyWidgetOpen() {
        WidgetChild c = api.getDB().getWidgets().getWidgetChild(CronConstants.WILDERNESS_SCREEN_MAIN, CronConstants.ACCEPT_WILD_CHILD);
        return (c != null) && c.isVisible();
    }
    public MakeHandler getMakeHandler() {
        return makeWidget;
    }

    public boolean isStaminaBoosted() {
        return ((api.getDB().getPlayerSettings().getConfig(638) >> 19) & 0b1) == 1;
    }


    public boolean shouldBank() {
        ItemSet set = api.getUtil().getBankManager().getValidSet();
        return set != null && set.hasNext()
         && CronConstants.BANK_AREA.contains(api.getDB().getLocalPlayer());
    }

    public void startChargeChecker() {
        this.executor = Executors.newSingleThreadExecutor();
        this.checker = new ChargeChecker(api);
        this.executor.submit(this.checker);
    }

    public boolean hasStaff() {
        Item t = api.getDB().getEquipment().getItemInSlot(EquipmentSlot.WEAPON.getSlot());
        return t != null && t.getName().contains("Staff");
    }
    public boolean isCharging() {
        return checker != null && checker.isCharging();
    }


    public BankManager getBankManager() {
        return bankManager;
    }

    public void setPotThreshold(int potThreshold) {
        this.potThreshold = potThreshold;
    }

    public int getPotThreshold() {
        return potThreshold;
    }

    public void setEatingThreshold(int eatThreshold) {
        this.eatThreshold = eatThreshold;
    }

    public boolean hasLowHP() {
        int currentHP = api.getDB().getSkills().getBoostedLevels(Skill.HITPOINTS);
        int maxHP = api.getDB().getSkills().getRealLevel(Skill.HITPOINTS);
        return CronConstants.getPercentage(currentHP,maxHP) <= eatThreshold;
    }

    public int getEatingThreshold() {
        return this.eatThreshold;
    }

    public AntiPkController getAntiPkController() {
        return antiPkController;
    }
}
