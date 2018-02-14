package org.dreambot.articron.api.util;


import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.articron.api.util.traversal.PathNode;

import java.util.ArrayList;
import java.util.List;

public class CronUtil {

    public static final int BASE_SLEEP = 200;
    public static final int CHARGE_ANIM = 726;
    public static final int DEFAULT_ZOOM = 512;
    public static final Area BANK_AREA ;
    public static PathNode CURRENT_NODE;

    public static int ORBS_CREATED = 0;

    static {
        BANK_AREA = new Area(new Tile(3100,3501), new Tile(3083,3486));
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
