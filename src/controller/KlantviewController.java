package controller;

import jxl.read.biff.BiffException;
import model.Artikel;
import model.ArtikelWinkelmand;
import controller.*;
import model.Observer;
import model.database.DatabaseException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class KlantviewController implements Observer{
    private KassaviewController kassaviewController;

    public KlantviewController(KassaviewController kassaviewController) {
        this.kassaviewController = kassaviewController;
    }

    public double totaalPrijs() {
        return kassaviewController.totaalPrijs();
    }


    public ArrayList<ArtikelWinkelmand> getWinkelmandje() {
        return kassaviewController.getWinkelmandMetAantal();
    }

    @Override
    public void update(String eventType, Artikel artikel) {
        kassaviewController.update(eventType, artikel);
    }

    public double totaleKorting(){
        return kassaviewController.Kortingprijs();
    }

    public double totalePrijsMetKorting(){
        return kassaviewController.totalePrijsMetKorting();
    }
}
