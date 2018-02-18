package org.dreambot.articron.ui.dinh.ui.tab.settings.sub;

import org.dreambot.articron.ui.dinh.swing.HPanel;
import org.dreambot.articron.ui.dinh.swing.child.HCheckBox;

import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by: Niklas
 * Date: 14.02.2018
 * Alias: Dinh
 * Time: 14:56
 */

public class OtherSettings extends HPanel {

    private HCheckBox exchange, checkBox;

    private StaticExchangePanel exchangePanel;
    private CardLayout cardLayout;
    private HPanel mainCard;

    public OtherSettings(HCheckBox box, Border border) {
        super(new BorderLayout(), border);

        HPanel sub = new HPanel(new GridLayout(0, 2));
        add(sub, BorderLayout.NORTH);

       /** sub.add(exchange = new HCheckBox("Use Grand Exchange"));
        sub.add(checkBox = new HCheckBox("Buy a fixed amount"));
        checkBox.setVisible(false);

        exchange.setBackground(HFrame.BACKGROUND);
        checkBox.setBackground(HFrame.BACKGROUND);

        add(mainCard = new HPanel(cardLayout = new CardLayout()), BorderLayout.CENTER);


        HPanel empty = new HPanel();
        mainCard.add("EMPTY", empty);
        mainCard.add("EXCHANGE", exchangePanel = new StaticExchangePanel());

        ButtonGroup group = new ButtonGroup();

        group.add(exchange);

        group.add(box);
        box.setSelected(true);

        exchange.addItemListener(listener -> {
            cardLayout.show(mainCard, listener.getStateChange() == ItemEvent.SELECTED ? "EXCHANGE" : "EMPTY");
            checkBox.setVisible(ItemEvent.SELECTED == listener.getStateChange());
        });
        checkBox.addItemListener(listener -> exchangePanel.toggle(listener.getStateChange() == ItemEvent.SELECTED));
        cardLayout.show(mainCard, "EMPTY");**/
    }

    public HCheckBox getExchange() {
        return exchange;
    }

    public HCheckBox getCheckBox() {
        return checkBox;
    }

    public StaticExchangePanel getExchangePanel() {
        return exchangePanel;
    }
}