package org.dreambot.articron.api.util.traversal;

import org.dreambot.articron.api.APIProvider;
import org.dreambot.articron.api.util.conditional.SupplierGroup;

import java.util.*;


public class CustomPath {

    List<PathNode> path;
    PathNode parent;
    SupplierGroup stopConditions;
    APIProvider context;

    public CustomPath(APIProvider context, SupplierGroup stopConditions) {
        path = new ArrayList<>();
        this.context = context;
        this.stopConditions = stopConditions;
    }

    public void addNode(PathNode node) {
        if (parent != null) {
            parent.setChild(node);
        }
        path.add(node);
        parent = node;
    }

    public void clear() {
        path.clear();
    }

    /*public List<PathNode> getPath() {
        return path;
    }*/

    public CustomPath reverse() {
        CustomPath reversed = new CustomPath(context,stopConditions);
        for (int i = path.size() - 1; i >= 0; i--) {
            reversed.addNode(path.get(i));
        }
        return reversed;
    }

    private Comparator<PathNode> nodeComparator =
            (o1, o2) -> (int) (o1.getTile().distance() - o2.getTile().distance());


    private PathNode getNext() {
        List<PathNode> sorted = this.path;
        sorted.sort(nodeComparator);
        PathNode closest = sorted.get(0);
        while (closest != null && closest.hasPassed(context)) {
            closest = closest.getNext();
        }
        return closest;
    }

    public boolean traverse() {
        PathNode next = getNext();
        return next != null && next.traverse(context);
    }


    private int indexOf(PathNode node) {
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).getTile().equals(node.getTile())) {
                return i;
            }
        }
        return -1;
    }

}
