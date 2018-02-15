package org.dreambot.articron.api.util.traversal.impl;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.conditional.SupplierGroup;
import org.dreambot.articron.api.util.traversal.PathNode;

public class ObjectNode implements PathNode {

    private Tile objectLoc;
    private String name, action;
    private PathNode next;

    private Tile walkTile;

    public ObjectNode(String name, String action, Tile loc, Tile walkTile) {
        this.name = name;
        this.action = action;
        this.objectLoc = loc;
        this.walkTile= walkTile;
    }

    public ObjectNode(String name, String action, int x, int y) {
        this(name,action,new Tile(x,y), null);
    }

    public ObjectNode(String name, String action, int x, int y, int z) {
        this(name,action,new Tile(x,y,z), null);
    }

    @Override
    public int getX() {
        return objectLoc.getX();
    }

    @Override
    public int getY() {
        return objectLoc.getY();
    }

    @Override
    public int getZ() {
        return objectLoc.getZ();
    }

    @Override
    public Tile getTile() {
        return objectLoc;
    }

    public GameObject getObject(APIProvider context) {
        return context.getDB().getGameObjects().closest(o -> o != null && o.getName().equalsIgnoreCase(name)
        && o.getTile().equals(objectLoc) && o.hasAction(action));
    }

    public int distance() {
        return 8;
    }

    @Override
    public boolean traverse(APIProvider api) {
        return traverse(api, new SupplierGroup());
    }

    @Override
    public boolean traverse(APIProvider api, SupplierGroup stopConditions) {
        if (hasPassed(api))
            return true;
        if (objectLoc.distance() < 10 && getObject(api) == null)
            return true;
        if (getObject(api) != null) {
            if (getObject(api).getTile().distance() < distance()) {
                return getObject(api).interact(action);
            }
        }
        return ifDistant(api);
    }

    public boolean ifDistant(APIProvider api) {
        return api.getDB().getWalking().walk(objectLoc) && traverse(api);
        /*if (objectLoc.distance() < 8 && getObject(api) != null) {
            return api.getDB().getCamera().rotateToEntity(getObject(api));
        }
        Tile t = api.getDB().getClient().getDestination();
        if (t == null) {
            if (api.getDB().getWalking().walk(objectLoc)) {
                t = api.getDB().getClient().getDestination();
                Tile finalT = t;
                return MethodProvider.sleepUntil(() -> finalT == null || finalT.distance() <= 8, (long) t.distance() * 800);
            }
        }
        Tile finalT1 = t;
        return MethodProvider.sleepUntil(() -> finalT1 == null || finalT1.distance() <= 8, finalT1 == null ? 0 : (long) finalT1.distance() * 800);**/
    }

    @Override
    public boolean hasPassed(APIProvider api) {
        return next != null && api.getDB().getMap().canReach(next.getTile());
    }

    @Override
    public PathNode getNext() {
        return next;
    }

    @Override
    public void setChild(PathNode node) {
        this.next = node;
    }

    @Override
    public boolean exists(APIProvider api) {
        return getObject(api) != null && getObject(api).exists();
    }

    @Override
    public String toString() {
        return "Object node: " + objectLoc.toString();
    }
}
