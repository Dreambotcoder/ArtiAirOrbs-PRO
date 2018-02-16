package org.dreambot.articron.dinh.ui.tab.settings.sub;

import org.dreambot.articron.data.Edible;
import org.dreambot.articron.dinh.loader.HImageLoader;
import org.dreambot.articron.dinh.swing.HPanel;
import org.dreambot.articron.dinh.swing.child.HImageComboBox;
import org.dreambot.articron.dinh.swing.child.HLabel;
import org.dreambot.articron.dinh.swing.special.DisplayObject;
import org.dreambot.articron.dinh.swing.special.RewardIcon;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 13:43
 */

public class FoodSelection extends HPanel {

    private HImageComboBox<DisplayObject> foodBox;
    private HLabel name, heal;

    public FoodSelection(Border border) {
        super(new BorderLayout(), border);
        Edible[] edibles = Edible.values();
        DisplayObject[] objects = new DisplayObject[edibles.length];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = new DisplayObject(edibles[i].toString(), HImageLoader.loadSprite(edibles[i].getId()), edibles[i].getId());
        }

        HPanel information = new HPanel(new GridLayout(0, 1));
        name = new HLabel("Name: " + objects[0].toString());
        heal = new HLabel("Heals: " + String.valueOf((int) Edible.find(objects[0].getId()).orElseThrow(() -> new RuntimeException("Failed to fetch object 0")).getHeal()));

        information.add(name);
        information.add(heal);
        add(information, BorderLayout.CENTER);

        add(foodBox = new HImageComboBox<>(objects), BorderLayout.SOUTH);
        foodBox.getEditor().setIcon(new ImageIcon(new RewardIcon(objects[0].getImage())));
    }

    public HImageComboBox<DisplayObject> getFoodBox() {
        return foodBox;
    }

    public void updateName(String name) {
        this.name.setText("Name: " + name);
    }

    public void updateHeal(int id) {
        this.heal.setText("Heals: " + String.valueOf((int) Edible.find(id).orElseThrow(() -> new RuntimeException("Failed to fetch object 0")).getHeal()));
    }

    public Edible getSelected() {
        DisplayObject object = (DisplayObject) foodBox.getSelectedItem();
        if (object == null) return null;
        return Edible.find(object.getId()).orElse(null);
    }
}
