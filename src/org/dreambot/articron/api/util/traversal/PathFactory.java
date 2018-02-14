package org.dreambot.articron.api.util.traversal;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.conditional.SupplierGroup;

public class PathFactory {

    public static CustomPath createPath(APIProvider context, SupplierGroup group, PathNode... nodes) {
        CustomPath path = new CustomPath(context, group);
        for (PathNode node : nodes) {
            path.addNode(node);
        }
        return path;
    }


}
