package org.dreambot.articron.api.util.traversal.impl;

import org.dreambot.api.methods.map.Tile;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.conditional.SupplierGroup;
import org.dreambot.articron.api.util.traversal.PathNode;

public class WalkNode implements PathNode {

    private Tile tile;

    private PathNode next = null;

    public WalkNode(int x, int y) {
        this(x, y, 0);
    }

    public WalkNode(Tile tile) {
        this.tile = tile;
    }

    public WalkNode(int x, int y, int z) {
        this(new Tile(x, y, z));
    }


    @Override
    public int getX() {
        return tile.getX();
    }

    @Override
    public int getY() {
        return tile.getY();
    }

    @Override
    public int getZ() {
        return tile.getZ();
    }

    @Override
    public Tile getTile() {
        return tile;
    }

    public int getMaxRadius() {
        return 3;
    }

    @Override
    public boolean traverse(APIProvider api) {
        return traverse(api, new SupplierGroup());
    }

    @Override
    public boolean traverse(APIProvider api, SupplierGroup stopConditions) {
        if (hasPassed(api))
            return true;
        Tile dest = api.getDB().getClient().getDestination();
        if (dest != null &&  dest.distance(tile) < 3) {
            return true;
        }
        if (api.getDB().getWalking().walk(tile.getRandomizedTile(getMaxRadius()))) {
            // MethodProvider.sleep(600);
            Tile t = api.getDB().getClient().getDestination();
            /*MethodProvider.sleepUntil(() -> t.distance() <= 8 || hasPassed(api) ||
                        stopConditions.getAsBoolean(), (long) t.distance() * 600);
                        */
            return t != null || traverse(api, stopConditions);
        }
        return false;
    }

    @Override
    public boolean hasPassed(APIProvider api) {
        return getTile().distance() < 8;
    }

    @Override
    public PathNode getNext() {
        return next;
    }

    @Override
    public void setChild(PathNode node) {
        next = node;
    }

    @Override
    public boolean exists(APIProvider api) {
        return true;
    }

    @Override
    public String toString() {
        return "Walking node: " + tile.toString();
    }
}
