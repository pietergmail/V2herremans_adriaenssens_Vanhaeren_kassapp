package controller;

import model.KassaVerkoop;
import view.panels.KlantPane;

/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class KlantviewController implements Observer {
    private KlantPane pane;

    public KlantviewController(KassaVerkoop kassaVerkoop) {
        kassaVerkoop.addObserver(this);
    }

    public void setPane(KlantPane pane){
        this.pane = pane;
    }

    @Override
    public void update(KassaVerkoop verkoop) {
        pane.setWinkelmand(verkoop.getWinkelmandje());
        pane.setBetalen(verkoop.getTotalPrijs());
        pane.setKorting(verkoop.berekenKorting());
        pane.setBetalen(verkoop.berekenPrijsMetKorting());
    }
}
