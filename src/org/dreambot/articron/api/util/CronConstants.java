package org.dreambot.articron.api.util;


import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.articron.api.antiban.AbstractAntiban;
import org.dreambot.articron.api.antiban.AntibanController;
import org.dreambot.articron.api.util.traversal.PathNode;
import org.dreambot.articron.data.ScriptMode;

import java.util.ArrayList;
import java.util.List;

public class CronConstants {

    public static final int BASE_SLEEP = 200;

    public static final int CHARGE_ANIM = 726;

    public static final int DEFAULT_ZOOM = 512;

    public static final Area BANK_AREA ;

    public static boolean SHOW_PAINT = true;

    public static String BANKSET = "";


    public static String[] GLORIES;

    /*
     * Widget constants
     */
    public static final int WILDERNESS_SCREEN_MAIN = 475;
    public static final int ACCEPT_WILD_CHILD = 11;

    public static final int WILDERNESS_LEVEL_MAIN = 90;
    public static final int WILDERNESS_LEVEL_CHILD = 46;

    public static ScriptMode SCRIPT_MODE = null;


    public static int ORBS_CREATED = 0;

    static {
        BANK_AREA = new Area(new Tile(3100,3501), new Tile(3083,3486));
        GLORIES = createNumericStrings("Amulet of glory(%s)",1,6);
    }


    public static String[] createNumericStrings(String string, int min, int max) {
        List<String> strings = new ArrayList<>();
        if (!string.contains("%s")) {
            return new String[] {string};
        } else {
            for (int i = min; i <= max; i++) {
                String toAdd = string.replace("%s",Integer.toString(i));
                //MethodProvider.log(toAdd);
                strings.add(toAdd);
            }
        }
        return strings.toArray(new String[strings.size()]);
    }

    public static int getPercentage(int part, int total) {
        int percent = (( part * 100) / total);
        return percent > 100 ? 100 : percent;
    }


}
