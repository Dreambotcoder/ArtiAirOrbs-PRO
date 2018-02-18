package org.dreambot.articron.api.controller.impl.node.loaders;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.NodeController;
import org.dreambot.articron.api.controller.impl.node.NodeLoader;
import org.dreambot.articron.api.util.CronConstants;
import org.dreambot.articron.api.util.banking.ItemSet;
import org.dreambot.articron.behaviour.banking.*;
import org.dreambot.articron.behaviour.craft.MakeAction;
import org.dreambot.articron.behaviour.craft.MakeOrbs;
import org.dreambot.articron.behaviour.craft.TickMakeOrbs;
import org.dreambot.articron.behaviour.deathwalk.DeathTree;
import org.dreambot.articron.behaviour.deathwalk.WalkToDeathBank;
import org.dreambot.articron.behaviour.emergency.EatFood;
import org.dreambot.articron.behaviour.misc.*;
import org.dreambot.articron.behaviour.poh.POHTree;
import org.dreambot.articron.behaviour.poh.UseMountedGlory;
import org.dreambot.articron.behaviour.traversal.DrinkStamina;
import org.dreambot.articron.behaviour.traversal.WalkToObelisk;
import org.dreambot.articron.behaviour.traversal.teleport.GloryTele;
import org.dreambot.articron.behaviour.traversal.teleport.TabTeleport;
import org.dreambot.articron.data.Edible;
import org.dreambot.articron.data.ScriptMode;
import org.dreambot.articron.ui.dinh.ui.MainUI;

public class WorkMode implements NodeLoader {

    @Override
    public ScriptMode getMode() {
        return ScriptMode.WORK;
    }

    @Override
    public void load(NodeController controller, APIProvider api, MainUI ui) {

        int orbCount = api.getSettings().getOrbcount();
        int eatPercentage = api.getSettings().getEatPercentage();
        boolean staminas = api.getSettings().isUseStamina();
        boolean stopWithoutSupplies = api.getSettings().isStopWithoutSupplies();
        boolean useGlory = api.getSettings().isUseGlory();
        Edible selectedFood = api.getSettings().getSelectedFood();

        api.getUtil().setEatingThreshold(eatPercentage);


        api.getUtil().setTeleportCondition(() -> {
            if (useGlory) {
                Item ammy = api.getDB().getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot());
                return ammy != null && ammy.getName().contains("Amulet of glory") && !ammy.getName().equals("Amulet of glory");
            } else {
                return api.getDB().getInventory().contains("Teleport to house");
            }
        });

        api.getUtil().getBankManager().setSet("food", new ItemSet(api, () -> api.getUtil().hasLowHP(), () -> {
            if (stopWithoutSupplies) {
                api.getNodeController().stopScript("Out of supplies");
            } else {
                //api.getUtil().getGrandExchange().setDeficits(api.getUtil().getBankManager().getDeficits());
                api.getUtil().getGrandExchange().readCache(api.getUtil().getBankManager().getCache());
                api.getNodeController().setMode(ScriptMode.GRAND_EXCHANGE);
            }
        }));
        api.getUtil().getBankManager().getSet("food").addItem(selectedFood.toString(),() -> Edible.required(selectedFood, api.getDB().getSkills()));
        MethodProvider.log("Edible = " + selectedFood.toString());
        MethodProvider.log("Use glory = " + useGlory);

        api.getUtil().getBankManager().setSet("normal", new ItemSet(api, () -> api.getUtil().hasTeleport() &&
                !api.getUtil().hasLowHP(), () -> {
            if (stopWithoutSupplies) {
                api.getNodeController().stopScript("Out of supplies");
            } else {
                //api.getUtil().getGrandExchange().setDeficits(api.getUtil().getBankManager().getDeficits());
                api.getUtil().getGrandExchange().readCache(api.getUtil().getBankManager().getCache());
                api.getNodeController().setMode(ScriptMode.GRAND_EXCHANGE);
            }
        }));

        api.getUtil().getBankManager().getSet("normal").addItem("Cosmic rune", (orbCount * 3));
        if (staminas) {
            api.getUtil().getBankManager().getSet("normal").addItem("Stamina potion(4)", 1,
                    CronConstants.createNumericStrings("Stamina potion(%s)", 1, 4));
        }

        api.getUtil().getBankManager().getSet("normal").addItem("Unpowered orb", orbCount);

        api.getUtil().getBankManager().setSet("no_glory", new ItemSet(api, () -> !api.getUtil().hasTeleport()
                && !api.getUtil().hasLowHP(),() -> {
            if (stopWithoutSupplies) {
                api.getNodeController().stopScript("Out of supplies");
            } else {
               // api.getUtil().getGrandExchange().setDeficits(api.getUtil().getBankManager().getDeficits());
                api.getUtil().getGrandExchange().readCache(api.getUtil().getBankManager().getCache());
                api.getNodeController().setMode(ScriptMode.GRAND_EXCHANGE);
            }
        }));

        if (useGlory) {
            api.getUtil().getBankManager().getSet("no_glory").addItem(
                    "Cosmic rune", (orbCount * 3)
            );
            api.getUtil().getBankManager().getSet("no_glory").addItem(
                    "Amulet of glory(6)", 1, CronConstants.createNumericStrings("Amulet of glory(%s)", 1, 5)
            );
            api.getUtil().getBankManager().getSet("no_glory").addItem(
                    "Unpowered orb", orbCount
            );
        } else {
            api.getUtil().getBankManager().getSet("no_glory").addItem(
                    "Cosmic rune", (orbCount * 3)
            );
            if (staminas) {
                api.getUtil().getBankManager().getSet("no_glory").addItem("Stamina potion(4)", 1,
                        CronConstants.createNumericStrings("Stamina potion(%s)", 1, 4));
            }
            api.getUtil().getBankManager().getSet("no_glory").addItem("Teleport to house", 1);
            api.getUtil().getBankManager().getSet("no_glory").addItem(
                    "Unpowered orb", orbCount
            );

        }


        controller.commit(
        new DefaultZoom(
                () -> api.getDB().getClientSettings().getExactZoomValue() != CronConstants.DEFAULT_ZOOM
        ),
                new POHTree(() -> api.getUtil().atPOH()).addChildren(
                        new UseMountedGlory(() -> true)
                ),

                new HopWorld(
                        () -> !api.getDB().getLocalPlayer().isInCombat() && api.getUtil().getAntiPkController().shouldHop()
                        && !api.getUtil().getMakeHandler().isOpen()
                ),
                new BankTree(() ->api.getUtil().hasLowHP()).addChildren(
                        useGlory ?
                        new GloryTele(() -> !CronConstants.BANK_AREA.contains(api.getDB().getLocalPlayer())) : new TabTeleport(() -> !CronConstants.BANK_AREA.contains(api.getDB().getLocalPlayer())),
                        new OpenBank(() -> !api.getDB().getBank().isOpen() && !api.getDB().getInventory().contains(item -> item != null && item.hasAction("Eat"))),
                        new DepositTask(() -> api.getDB().getBank().isOpen() && api.getUtil().getBankManager().shouldDeposit()),
                        new WithdrawAction(() -> api.getDB().getBank().isOpen() && !api.getDB().getInventory().contains(item -> item != null && item.hasAction("Eat"))),
                        new CloseBank(() -> api.getDB().getBank().isOpen()),
                        new EatFood(selectedFood.toString(),() -> !api.getDB().getBank().isOpen() && api.getDB().getInventory().contains(item -> item != null && item.hasAction("Eat")))
                ),

                new BankTree(() -> api.getUtil().shouldBank()).addChildren(
                        new OpenBank(() -> !api.getDB().getBank().isOpen()),
                        new DepositTask(() -> api.getDB().getBank().isOpen() && api.getUtil().getBankManager().shouldDeposit()),
                        new WithdrawAction(() -> api.getDB().getBank().isOpen() && !api.getDB().getInventory().contains("Air orb") && !api.getUtil().getBankManager().hasAllItems())

                ),
                new ClickWildy(
                        () ->  api.getUtil().wildyWidgetOpen()
                ),
                new CloseBank(() -> api.getDB().getBank().isOpen())
        );
        if (staminas) {
            controller.commit(
                    new DrinkStamina(
                            () -> api.getUtil().shouldPot() && !api.getUtil().atObelisk()
                    )
            );
        }
        if (useGlory) {
            controller.commit(new WearItem(() -> !api.getUtil().hasTeleport() && api.getDB().getInventory().contains(
                    CronConstants.createNumericStrings("Amulet of glory(%s)",1,6)), CronConstants.GLORIES)
            );
        }
        controller.commit(
                new WearItem(
                        () -> !api.getUtil().hasStaff(), "Staff of air"
                ),
                new DeathTree(() -> !api.getUtil().hasTeleport() &&
                        !api.getUtil().atPOH() &&
                        !CronConstants.BANK_AREA.contains(api.getDB().getLocalPlayer())).addChildren(
                        new WalkToDeathBank(() -> !api.getDB().getBank().getClosestBankLocation().getArea(10).contains(api.getDB().getLocalPlayer())),
                        new OpenBank(() -> !api.getDB().getBank().isOpen() && api.getDB().getBank().getClosestBankLocation().getArea(10).contains(api.getDB().getLocalPlayer())),
                        new SingularWithdraw(api.getSettings().isUseGlory() ? "Amulet of glory(6)" : "Teleport to house", () -> api.getDB().getBank().isOpen())
                )
        );
        if (api.getSettings().isPrayDemons()) {
            controller.commit(
                    new PrayMelee(
                            () -> api.getUtil().shouldPray() && !api.getDB().getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)
                    ),
                    new DisablePray(
                            () -> !api.getUtil().shouldPray() && api.getDB().getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)
                    )
            );
        }
        controller.commit(
                useGlory ?
                        new GloryTele(
                                () -> api.getUtil().shouldTeleport() && !CronConstants.BANK_AREA.contains(api.getDB().getLocalPlayer())
                        )
                        :
                        new TabTeleport(
                                () -> api.getUtil().shouldTeleport() && !CronConstants.BANK_AREA.contains(api.getDB().getLocalPlayer())
                        ),

                new WalkToObelisk(
                        () -> !api.getUtil().atObelisk() && !api.getUtil().shouldBank()
                )
        );
        if (!api.getSettings().isTickCharge()) {
            controller.commit(
                    new MakeOrbs(
                            () -> api.getUtil().atObelisk() && !api.getUtil().getMakeHandler().isOpen()
                                    && !api.getUtil().isCharging() && api.getDB().getInventory().contains("Unpowered orb")
                    ),
                    new MakeAction(
                            () -> api.getUtil().atObelisk() && api.getUtil().getMakeHandler().isOpen()
                                    && !api.getUtil().isCharging()
                    )
            );
        } else {
            controller.commit(
                    new TickMakeOrbs(
                            () -> api.getUtil().atObelisk() &&
                                    api.getDB().getInventory().contains("Unpowered orb"))
            );
        }
    }
}
