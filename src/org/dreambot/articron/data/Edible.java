package org.dreambot.articron.data;

import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;

import java.util.Arrays;
import java.util.Optional;

/*
 * Created by: Niklas
 * Date: 14.04.2017
 * Alias: Dinh
 * Time: 11:57
 */

public enum Edible {
    SHRIMP(3.0, 315),
    COOKED_CHICKEN(3.0, 2140),
    SARDINE(3.0, 325),
    COOKED_MEAT(3.0, 2142),
    BREAD(5.0, 2309),
    HERRING(5.0, 347),
    MACKEREL(6.0, 355),
    TROUT(7.0, 333),
    COD(7.0, 339),
    PIKE(8.0, 351),
    ROAST_BEAST_MEAT(8.0, 9988),
    PINEAPPLE_PUNCH(9.0, 2048),
    SALMON(9.0, 329),
    TUNA(10.0, 361),
    JUG_OF_WINE(11.0, 1993),
    RAINBOW_FISH(11.0, 10136),
    STEW(11.0, 2003),
    CAKE(4.0, 1891),
    MEAT_PIE(6.0, 2327),
    LOBSTER(12.0, 379),
    BASS(13.0, 365),
    PLAIN_PIZZA(7.0, 2289),
    SWORDFISH(14.0, 373),
    POTATO_WITH_BUTTER(14.0, 6703),
    CHOCOLATE_CAKE(5.0, 1897),
    TANGLED_TOADS_LEGS(15.0, 2187),
    POTATO_WITH_CHEESE(16.0, 6705),
    MEAT_PIZZA(8.0, 2293),
    MONKFISH(16.0, 7946),
    ANCHOVY_PIZZA(9.0, 2297),
    COOKED_KARAMBWAN(18.0, 3144),
    CURRY(19.0, 2011),
    UGTHANKI_KEBAB(19.0, 1885),
    MUSHROOM_POTATO(20.0, 7058),
    SHARK(20.0, 385),
    SEA_TURTLE(21.0, 397),
    PINEAPPLE_PIZZA(11.0, 2301),
    MANTA_RAY(22.0, 391),
    TUNA_POTATO(22.0, 7060),
    DARK_CRAB(22.0, 11936),
    ANGLERFISH(22.0, 13441);

    private double heal;
    private int id;

    Edible(double heal, int id) {
        this.heal = heal;
        this.id = id;
    }

    public double getHeal() {
        return heal;
    }

    public int getId() {
        return id;
    }

    public static int required(Edible edible, Skills skills) {
        return (int) Math.ceil((skills.getRealLevel(Skill.HITPOINTS) - skills.getBoostedLevels(Skill.HITPOINTS)) / edible.getHeal());
    }

    public static Optional<Edible> find(int id) {
        return Arrays.stream(Edible.values()).filter(edible -> edible.getId() == id).findAny();
    }

    @Override
    public String toString() {
        char[] name = this.name().toCharArray();
        for (int i = 2; i < name.length + 1; i++) {
            int position = i - 1;
            char previous = name[position - 1];
            if (previous != 95) {
                name[position] = Character.toLowerCase(name[position]);
            } else {
                name[position - 1] = (char) 32;
            }
        }
        return new String(name);
    }
}
