package org.dreambot.articron.api.util.makeWidget;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.articron.api.APIProvider;

public class MakeHandler {

    private APIProvider api;

    private final int MAKE_WIDGET = 270;
    private final int NUM_CHILD = 9;
    private final int MAKE_OPTION = 14;

    public MakeHandler(APIProvider api) {
        this.api = api;
    }

    public boolean isOpen() {
        WidgetChild c = api.getDB().getWidgets().getWidgetChild(MAKE_WIDGET, 0);
        return (c != null) && c.isVisible();
    }

    public MakeMode getActiveMode() {
        if (!isOpen())
            return null;
        for (MakeMode mode : MakeMode.values()) {
            WidgetChild button = api.getDB().getWidgets().getWidgetChild(MAKE_WIDGET, mode.getChild(), NUM_CHILD);
            if (button != null) {
                if (button.getText().contains("<col")) {
                    return mode;
                }
            }
        }
        return MakeMode.MAKE_ALL;
    }

    public boolean make(MakeMode mode) {
        if (getActiveMode() == mode) {
            return make();
        } else {
            return setMode(mode) && make();
        }
    }

    public boolean setMode(MakeMode mode) {
        if (!isOpen())
            return false;
        WidgetChild button = api.getDB().getWidgets().getWidgetChild(MAKE_WIDGET, mode.getChild(), 0);
        return button.isVisible() && button.interact() && getActiveMode() == mode;
    }

    public boolean make() {
        if (!isOpen())
            return false;
        api.getDB().getKeyboard().type(" ");
        return MethodProvider.sleepUntil(() -> !isOpen(), 1000);
    }

    public int getCustomAmount() {
        if (!isOpen())
            return -1;
        WidgetChild button = api.getDB().getWidgets().getWidgetChild(MAKE_WIDGET, MakeMode.MAKE_CUSTOM.getChild());
        if (button.getChildren().length > 0) {
            int num = Integer.parseInt(button.getText().replace("<col=ffffff>", "").replace("</col>", ""));
            return button.isVisible() ? num : -1;
        }
        return -1;
    }

    public boolean hasCustomAmount() {
        return isOpen() && getCustomAmount() != -1;
    }
}
