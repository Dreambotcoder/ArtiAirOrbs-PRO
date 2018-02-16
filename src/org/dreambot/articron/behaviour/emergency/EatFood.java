package org.dreambot.articron.behaviour.emergency;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.controller.impl.node.Node;
import org.dreambot.articron.api.util.CronConstants;

import java.util.function.BooleanSupplier;

public class EatFood extends Node {

    private String foodName;

    public EatFood(String foodName,BooleanSupplier condition) {
        super(condition);
        this.foodName = foodName;
    }

    @Override
    public String getStatus() {
        return "Eating food";
    }

    @Override
    public int onLoop(APIProvider api) {


        if (!api.getDB().getTabs().isOpen(Tab.INVENTORY)) {
            if (api.getDB().getTabs().open(Tab.INVENTORY)) {
                MethodProvider.sleepUntil(() -> api.getDB().getTabs().isOpen(Tab.INVENTORY), 600);
            }
        }
        int hp = api.getDB().getSkills().getBoostedLevels(Skill.HITPOINTS);
        while (hp < api.getDB().getSkills().getRealLevel(Skill.HITPOINTS)) {
            Item food = api.getDB().getInventory().get(foodName);
            if (food == null)
                break;
            if (food.interact("Eat")) {
                int finalHp = hp;
                MethodProvider.sleepUntil(() -> finalHp != api.getDB().getSkills().getBoostedLevels(Skill.HITPOINTS), 1200);
            }
            hp = api.getDB().getSkills().getBoostedLevels(Skill.HITPOINTS);
        }
        return CronConstants.BASE_SLEEP;
    }
}
