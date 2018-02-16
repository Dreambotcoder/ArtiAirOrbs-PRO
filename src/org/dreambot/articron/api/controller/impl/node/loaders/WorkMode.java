package org.dreambot.articron.api.controller.impl.node.loaders;

import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.NodeController;
import org.dreambot.articron.api.controller.impl.node.NodeLoader;
import org.dreambot.articron.api.util.CronConstants;
import org.dreambot.articron.behaviour.banking.*;
import org.dreambot.articron.behaviour.craft.MakeAction;
import org.dreambot.articron.behaviour.craft.MakeOrbs;
import org.dreambot.articron.behaviour.emergency.EatFood;
import org.dreambot.articron.behaviour.emergency.EatTree;
import org.dreambot.articron.behaviour.misc.*;
import org.dreambot.articron.behaviour.traversal.DrinkStamina;
import org.dreambot.articron.behaviour.traversal.WalkToObelisk;
import org.dreambot.articron.behaviour.traversal.teleport.GloryTele;
import org.dreambot.articron.data.ScriptMode;

public class WorkMode implements NodeLoader {

    @Override
    public ScriptMode getMode() {
        return ScriptMode.WORK;
    }

    @Override
    public void load(NodeController controller, APIProvider api) {
        controller.commit(
        new DefaultZoom(
                () -> api.getDB().getClientSettings().getExactZoomValue() != CronConstants.DEFAULT_ZOOM
        ),
                new HopWorld(
                        () -> !api.getDB().getLocalPlayer().isInCombat() && api.getUtil().getAntiPkController().shouldHop()
                ),
                new EatTree(() ->api.getUtil().hasLowHP()).addChildren(
                        new GloryTele(() -> !CronConstants.BANK_AREA.contains(api.getDB().getLocalPlayer())),
                        new OpenBank(() -> !api.getDB().getBank().isOpen() && !api.getDB().getInventory().contains(item -> item != null && item.hasAction("Eat"))),
                        new DepositTask(() -> api.getDB().getBank().isOpen() && api.getUtil().getBankManager().shouldDeposit()),
                        new WithdrawAction(() -> api.getDB().getBank().isOpen() && !api.getDB().getInventory().contains(item -> item != null && item.hasAction("Eat"))),
                        new CloseBank(() -> api.getDB().getBank().isOpen()),
                        new EatFood(() -> !api.getDB().getBank().isOpen() && api.getDB().getInventory().contains(item -> item != null && item.hasAction("Eat")))
                ),

                new BankTree(() -> api.getUtil().shouldBank()).addChildren(
                        new OpenBank(() -> !api.getDB().getBank().isOpen()),
                        new DepositTask(() -> api.getDB().getBank().isOpen() && api.getUtil().getBankManager().shouldDeposit()),
                        new WithdrawAction(() -> api.getDB().getBank().isOpen() && !api.getDB().getInventory().contains("Air orb") && !api.getUtil().getBankManager().hasAllItems())

                ),
                new ClickWildy(
                        () ->  api.getUtil().wildyWidgetOpen()
                ),
                new CloseBank(() -> api.getDB().getBank().isOpen()),

                new DrinkStamina(
                        () -> api.getUtil().shouldPot() && !api.getUtil().atObelisk()
                ),
                new WearItem(() -> !api.getUtil().hasTeleport() && api.getDB().getInventory().contains(
                        CronConstants.createNumericStrings("Amulet of glory(%s)",1,6)), CronConstants.GLORIES),

                new WearItem(
                        () -> !api.getUtil().hasStaff(), "Staff of air"
                ),


                new PrayMelee(
                        () -> api.getUtil().shouldPray() && !api.getDB().getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)
                ),
                new DisablePray(
                        () -> !api.getUtil().shouldPray() && api.getDB().getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)
                ),
                new GloryTele(
                        () -> api.getUtil().shouldTeleport() && !CronConstants.BANK_AREA.contains(api.getDB().getLocalPlayer())
                ),
                new WalkToObelisk(
                        () -> !api.getUtil().atObelisk() && !api.getUtil().shouldBank()
                ),
                new MakeOrbs(
                        () -> api.getUtil().atObelisk() && !api.getUtil().getMakeHandler().isOpen()
                                && !api.getUtil().isCharging() && api.getDB().getInventory().contains("Unpowered orb")
                ),
                new MakeAction(
                        () -> api.getUtil().atObelisk() && api.getUtil().getMakeHandler().isOpen()
                                && !api.getUtil().isCharging()
                )
        );
    }
}
