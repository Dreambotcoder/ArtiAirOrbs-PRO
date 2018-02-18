package org.dreambot.articron.api.controller.impl.node.loaders;

import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.NodeController;
import org.dreambot.articron.api.controller.impl.node.NodeLoader;
import org.dreambot.articron.behaviour.craft.TickMakeOrbs;
import org.dreambot.articron.data.ScriptMode;
import org.dreambot.articron.ui.dinh.ui.MainUI;

public class TestMode implements NodeLoader {
    @Override
    public ScriptMode getMode() {
        return ScriptMode.TEST;
    }

    @Override
    public void load(NodeController controller, APIProvider api, MainUI ui) {
        //api.getDB().getClient().getInstance().addEventListener(new TickMakeOrbs(api, () ->true));
        api.getUtil().setTeleportCondition(() -> {
            if (api.getSettings().isUseGlory()) {
                Item ammy = api.getDB().getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot());
                return ammy != null && ammy.getName().contains("Amulet of glory") && !ammy.getName().equals("Amulet of glory");
            } else {
                return api.getDB().getInventory().contains("Teleport to house");
            }
        });
        controller.commit(
                new TickMakeOrbs(() -> api.getUtil().atObelisk() && api.getDB().getInventory().contains("Unpowered orb"))
        );
    }
}
