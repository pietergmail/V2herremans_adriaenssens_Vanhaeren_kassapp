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
 * @author Vanhaeren Corentin
 */

public class KlantviewController {
    private KassaController kassaController;

    public KlantviewController(KassaController kassaController) {
        this.kassaController = kassaController;
    }

    public double totaalPrijs() {
        return kassaController.totaalPrijs();
    }


    public ArrayList<ArtikelWinkelmand> getWinkelmandje() {
        return kassaController.getWinkelmandMetAantal();
    }


    public void update(String eventType, Artikel artikel) {
        kassaController.update(eventType, artikel);
    }

    public double totaalPrijsKorting(){
        return kassaController.totaalPrijsKorting();
    }
}
