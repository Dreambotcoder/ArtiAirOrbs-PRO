package org.dreambot.articron;

import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.CronConstants;
import org.dreambot.articron.api.util.conditional.SupplierGroup;
import org.dreambot.articron.api.util.traversal.CustomPath;
import org.dreambot.articron.api.util.traversal.PathFactory;
import org.dreambot.articron.api.util.traversal.impl.ObjectNode;
import org.dreambot.articron.api.util.traversal.impl.WalkNode;
import org.dreambot.articron.ui.dinh.loader.HImageLoader;
import org.dreambot.articron.ui.dinh.ui.MainUI;

import java.awt.*;


@ScriptManifest(category = Category.MAGIC, name = "Orb", author = "Articron", version = 1.5D)
public class ArtiAirOrber extends AbstractScript implements InventoryListener {

    private APIProvider api;
    private MainUI ui;

    @Override
    public void onPaint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
       // g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       // g2.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        if (api.getPaintManager() != null) {
            api.getPaintManager().onPaint(g2);

        }
    }

    @Override
    public void onStart() {
        getRandomManager().disableSolver(RandomEvent.DISMISS);
        //getRandomManager().disableSolver(RandomEvent.);
        //MethodProvider.log(""+getClient().seededRandom());
        api = new APIProvider(this);
        CustomPath obeliskPath = PathFactory.createPath(api,
                new SupplierGroup(),
                new WalkNode(3094,3470) {

                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return getMap().isTileOnMap(getNext().getTile()) ||
                                getNext().getTile().distance() < 10;
                    }

                    @Override
                    public int getMaxRadius() {
                        return 1;
                    }
                },
                new ObjectNode("Trapdoor","Open", new Tile(3097,3468), new Tile(3095,3469)) {

                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return !exists(api) || getNext().exists(api);
                    }

                },
                new ObjectNode("Trapdoor","Climb-down", new Tile(3097,3468), new Tile(3095,3469)) {

                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return getLocalPlayer().getTile().equals(new Tile(3096,9867));
                    }

                    @Override
                    public boolean ifDistant(APIProvider api) {
                        return api.getDB().getWalking().walk(new Tile(3095,3469));
                    }
                },
                new WalkNode(3103,9909) {
                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return api.getDB().getMap().isTileOnMap(getNext().getTile());
                    }
                },
                new ObjectNode("Gate", "Open", 3103,9909) {
                    @Override
                    public int distance() {
                        return 15;
                    }

                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return api.getDB().getMap().canReach(getNext().getTile()) || !exists(api);
                    }
                },
                new WalkNode(3132,9916) {
                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return getNext().getTile().distance() <= 5 || getNext().exists(api);
                    }
                },
                new ObjectNode("Gate","Open", 3132,9917) {
                    @Override
                    public int distance() {
                        return 8;
                    }

                    @Override
                    public boolean hasPassed(APIProvider api) {
                        NPC n = api.getDB().getNpcs().closest("Thug");
                        return (n != null && api.getDB().getMap().canReach(n)) || super.hasPassed(api);
                    }
                },
               // new WalkNode(3131,9925),
                //new WalkNode(3112,9933),
                new WalkNode(3107,9942) {
                    @Override
                    public boolean hasPassed(APIProvider api) {
                        return getNext().traverse(api);
                    }
                },
                new ObjectNode("Gate","Open", new Tile(3105,9944), new Tile(3106,9943)) {

                    @Override
                    public int distance() {
                        return 15;
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

                    @Override
                    public int distance() {
                        return 8;
                    }
                }
                //new WalkNode(3088,3570)
        );
        api.registerPath(obeliskPath);
        ui = new MainUI(api, HImageLoader.loadImage("https://i.imgur.com/SGA9et4.png"));
        api.getCronTray().setVisible(true);
        api.getUtil().getAntiPkController().getObserver().start();
        api.getCronTray().sendMessage("Session started!","Thanks for your purchase!");
        // api.getCronTray().getLogs().addLog("Started ArtiAirOrb PRO 1.0!", api.getRuntime(), LogType.OTHER);**/
        /**api.getUtil().getBankManager().setSet("food", new ItemSet(api, () -> api.getUtil().hasLowHP()));
        api.getUtil().getBankManager().getSet("food").addItem(Edible.LOBSTER.toString(),
                () -> Edible.required(Edible.LOBSTER,getSkills()));
        api.getUtil().getBankManager().setSet("normal", new ItemSet(api, () -> api.getUtil().hasTeleport() &&
        !api.getUtil().hasLowHP()));
        api.getUtil().getBankManager().getSet("normal").addItem("Cosmic rune", 78);
        api.getUtil().getBankManager().getSet("normal").addItem("Stamina potion(4)", 1,
                CronConstants.createNumericStrings("Stamina potion(%s)",1,4));
        api.getUtil().getBankManager().getSet("normal").addItem("Unpowered orb", 26);
        api.getUtil().getBankManager().setSet("no_glory", new ItemSet(api, () -> !api.getUtil().hasTeleport()
        && !api.getUtil().hasLowHP()));
        api.getUtil().getBankManager().getSet("no_glory").addItem(
                "Cosmic rune", 78
        );
        api.getUtil().getBankManager().getSet("no_glory").addItem(
                "Amulet of glory(6)", 1, CronConstants.createNumericStrings("Amulet of glory(%s)",1,5)
        );
        api.getUtil().getBankManager().getSet("no_glory").addItem(
                "Unpowered orb", 26
        );**/
        /**
        api.getUtil().getBankManager().getCache().trackItem("Unpowered orb");
        api.getUtil().getBankManager().getCache().trackItem("Cosmic rune");
        api.getUtil().getBankManager().getCache().setTracked(true);
        api.getNodeController().setMode(ScriptMode.WORK);
        getWalking().setRunThreshold(api.getUtil().getPotThreshold());**/

    }

    @Override
    public int onLoop() {
        return api.getNodeController().onLoop(api);
    }

    @Override
    public void onExit() {
        api.getUtil().getAntiPkController().getObserver().stop();
        getClient().getInstance().setDrawMouse(true);
        api.getUtil().getBankManager().getCache().setTracked(false);
        if (api.getCronTray().isVisible()) {
            api.getCronTray().sendMessage("Script shutdown", "Reason: Clean shutdown");
        }
        api.getCronTray().setVisible(false);
    }

    @Override
    public void onItemChange(Item[] items) {
        for (Item item : items) {
            if (item.getName().equals("Air orb") && !getBank().isOpen()) {
                CronConstants.ORBS_CREATED++;
            }
        }
    }
}
