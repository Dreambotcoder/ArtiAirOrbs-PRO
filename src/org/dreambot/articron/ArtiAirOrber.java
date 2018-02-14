package org.dreambot.articron;

import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronUtil;
import org.dreambot.articron.api.util.conditional.SupplierGroup;
import org.dreambot.articron.api.util.traversal.CustomPath;
import org.dreambot.articron.api.util.traversal.PathFactory;
import org.dreambot.articron.api.util.traversal.impl.ObjectNode;
import org.dreambot.articron.api.util.traversal.impl.WalkNode;
import org.dreambot.articron.behaviour.banking.*;
import org.dreambot.articron.behaviour.craft.MakeAction;
import org.dreambot.articron.behaviour.craft.MakeOrbs;
import org.dreambot.articron.behaviour.emergency.EatTree;
import org.dreambot.articron.behaviour.misc.*;
import org.dreambot.articron.behaviour.traversal.DrinkStamina;
import org.dreambot.articron.behaviour.traversal.WalkToObelisk;
import org.dreambot.articron.behaviour.traversal.teleport.GloryTele;

import java.awt.*;


@ScriptManifest(category = Category.MAGIC, name = "ArtiAirOrber PRO", author = "Articron", version = 0.1D)
public class ArtiAirOrber extends AbstractScript implements InventoryListener {

    private APIProvider api;


    @Override
    public void onPaint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (api.getPaintManager() != null) {
            api.getPaintManager().onPaint(g2);

        }
    }

    @Override
    public void onStart() {
        api = new APIProvider(this);
        CustomPath obeliskPath = PathFactory.createPath(api,
                new SupplierGroup().put(
                        () -> api.getUtil().shouldPot(),
                        () -> api.getUtil().shouldTeleport(),
                        () -> api.getUtil().wildyWidgetOpen(),
                        () -> api.getUtil().shouldPray() && !api.getDB().getPrayer().isActive(Prayer.PROTECT_FROM_MELEE),
                        () -> api.getUtil().atObelisk(),
                        () -> api.getUtil().hasLowHP()
                ),
                new WalkNode(3094,3470) {
                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return getGameObjects().getTopObjectOnTile(getNext().getTile()).isOnScreen();
                    }

                    @Override
                    public int getMaxRadius() {
                        return 1;
                    }
                },
                new ObjectNode("Trapdoor","Open", new Tile(3097,3468), new Tile(3095,3469)) {

                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return !exists(api);
                    }

                },
                new ObjectNode("Trapdoor","Climb-down", new Tile(3097,3468), new Tile(3095,3469)) {

                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return super.hasPassed(api) || getLocalPlayer().getTile().equals(new Tile(3096,9867));
                    }

                    @Override
                    public boolean ifDistant(APIProvider api) {
                        return api.getDB().getWalking().walk(new Tile(3095,3469));
                    }
                },
                //new WalkNode(3103,9909),
                //new WalkNode(3096, 9907),
                new WalkNode(3105,9909) {
                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return getNext().getTile().distance() <= 12;
                    }
                },
                new ObjectNode("Gate", "Open", 3103,9909) {
                    @Override
                    public int distance() {
                        return 8;
                    }

                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return api.getDB().getMap().canReach(getNext().getTile()) || !exists(api);
                    }
                },
                new WalkNode(3117,9910) {
                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return api.getDB().getMap().canReach(getNext().getTile());
                    }
                },
                new ObjectNode("Gate","Open", 3132,9917) {
                    @Override
                    public int distance() {
                        return 8;
                    }
                },
               // new WalkNode(3131,9925),
                //new WalkNode(3112,9933),
                new WalkNode(3107,9942) {
                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return getNext().getTile().distance() < 8;
                    }
                },
                new ObjectNode("Gate","Open", new Tile(3105,9944), new Tile(3106,9943)) {

                    @Override
                    public int distance() {
                        return 8;
                    }

                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return !exists(api);
                    }

                },
                new WalkNode(3105,9952) {
                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return api.getDB().getMap().canReach(getNext().getTile());
                    }
                },
                new WalkNode(3088,9970) {
                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return api.getDB().getMap().isTileOnMap(getNext().getTile());
                    }
                },
                new ObjectNode("Ladder", "Climb-up", 3088, 9971) {
                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return api.getUtil().atObelisk();
                    }
                }
                //new WalkNode(3088,3570)
        );
        api.registerPath(obeliskPath);
        api.getUtil().getBankManager().addItem("Cosmic rune", 78, () -> true);
        api.getUtil().getBankManager().addItem("Amulet of glory(6)", 1, () -> !api.getUtil().hasGlory());
        api.getUtil().getBankManager().addItem("Stamina potion(4)", 1, () -> api.getUtil().hasGlory(),
                () -> getInventory().contains(item -> item.getName().contains("Stamina potion")),
                CronUtil.createNumericStrings("Stamina potion(%s)",1,4));
        api.getUtil().getBankManager().addItem("Unpowered orb", 26, () -> api.getUtil().hasGlory());
        api.getUtil().getBankManager().addItem("Unpowered orb", 26, () -> !api.getUtil().hasGlory());
        api.getNodeController().commit(

                new DefaultZoom(
                        () -> getClientSettings().getExactZoomValue() != CronUtil.DEFAULT_ZOOM
                ),
                new EatTree(() ->api.getUtil().hasLowHP()).addChildren(
                        new GloryTele(() -> !CronUtil.BANK_AREA.contains(getLocalPlayer())),
                        new OpenBank(() -> !getBank().isOpen() && !getInventory().contains(item -> item != null && item.hasAction("Eat")))
                ),

                new BankTree(() -> api.getUtil().shouldBank()).addChildren(
                        new OpenBank(() -> !getBank().isOpen()),
                        new DepositOrbs(() -> getBank().isOpen() && api.getUtil().getBankManager().shouldDeposit()),
                        new WithdrawAction(() -> getBank().isOpen() && !getInventory().contains("Air orb") && !api.getUtil().getBankManager().hasAllItems())

                ),
                new ClickWildy(
                        () -> {
                            WidgetChild c = api.getDB().getWidgets().getWidgetChild(475, 11);
                            return c != null && c.isVisible();
                        }
                ),
                new CloseBank(() -> getBank().isOpen()),

                new DrinkStamina(
                        () -> api.getUtil().shouldPot() && !api.getUtil().atObelisk()
                ),

                new WearItem(
                        () -> !getEquipment().getItemInSlot(EquipmentSlot.WEAPON.getSlot()).getName().equals("Staff of air"),
                        "Staff of air"
                ),

                new WearItem(() -> !api.getUtil().hasGlory() && getInventory().contains(item ->
                        !item.getName().equalsIgnoreCase("Amulet of glory") &&
                                item.getName().contains("Amulet of glory")),
                        CronUtil.createNumericStrings("Amulet of glory(%s)",1,6)),
                new PrayMelee(
                        () -> api.getUtil().shouldPray() && !api.getDB().getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)
                ),
                new DisablePray(
                        () -> !api.getUtil().shouldPray() && api.getDB().getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)
                ),
                new GloryTele(
                        () -> api.getUtil().shouldTeleport() && !CronUtil.BANK_AREA.contains(getLocalPlayer())
                ),
                new WalkToObelisk(
                        () -> !api.getUtil().atObelisk() && !api.getUtil().shouldBank()
                ),
                new MakeOrbs(
                        () -> api.getUtil().atObelisk() && !api.getUtil().getMakeHandler().isOpen()
                        && !api.getUtil().isCharging()
                ),
                new MakeAction(
                        () -> api.getUtil().atObelisk() && api.getUtil().getMakeHandler().isOpen()
                        && !api.getUtil().isCharging()
                )
        );
        getWalking().setRunThreshold(api.getUtil().getPotThreshold());
    }

    @Override
    public int onLoop() {
        return api.getNodeController().onLoop(api);
    }

    @Override
    public void onItemChange(Item[] items) {
        for (Item item : items) {
            if (item.getName().equals("Air orb") && !getBank().isOpen()) {
                CronUtil.ORBS_CREATED++;
            }
        }
    }
}
