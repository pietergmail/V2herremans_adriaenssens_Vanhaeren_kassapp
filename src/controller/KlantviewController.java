package controller;

import jxl.read.biff.BiffException;
import model.Artikel;
import model.ArtikelWinkelmand;
import model.ModelFacade;
import model.Observer;
import model.database.DatabaseException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Vanhaeren Corentin
 */

public class KlantviewController {
    private ModelFacade modelFacade;

    public KlantviewController(ModelFacade modelFacade) {
        this.modelFacade = modelFacade;
    }

    public double totaalPrijs() {
        return modelFacade.totaalPrijs();
    }


    public ArrayList<ArtikelWinkelmand> getWinkelmandje() {
        return modelFacade.getWinkelmandMetAantal();
    }


    public void update(String eventType, Artikel artikel) {
        modelFacade.update(eventType, artikel);
    }

    public double totaalPrijsKorting(){
        return modelFacade.totaalPrijsKorting();
    }
}
