package org.dreambot.articron.api.util.makeWidget;

public enum MakeMode {

    MAKE_1(7), MAKE_5(8), MAKE_10(9), MAKE_CUSTOM(10), MAKE_X(11), MAKE_ALL(12);

    MakeMode(int child) {
        this.child = child;
    }

    private int child;

    public int getChild() {
        return child;
    }

}
