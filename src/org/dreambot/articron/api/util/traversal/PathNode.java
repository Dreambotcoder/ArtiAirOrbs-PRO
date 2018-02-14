package org.dreambot.articron.api.util.traversal;

import org.dreambot.api.methods.map.Tile;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.conditional.SupplierGroup;

public interface PathNode {

    int getX();
    int getY();
    int getZ();

    Tile getTile();

    boolean traverse(APIProvider api);
    boolean traverse(APIProvider api, SupplierGroup stopConditions);
    boolean hasPassed(APIProvider api);
    PathNode getNext();
    void setChild(PathNode node);
    boolean exists(APIProvider api);
}
