package controller;

import jxl.read.biff.BiffException;
import model.*;

import model.database.*;
import view.KassaView;
import view.KlantView;
import view.panels.KassaPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class KassaviewController implements Observer {
    private ModelFacade modelFacade;

    public KassaviewController(ModelFacade modelFacade) {
        this.modelFacade = modelFacade;
    }

    public void addProductKassaVerkoop(Artikel artikel) {
        modelFacade.addProductKassaVerkoop(artikel);
    }

    public void removeProductKassaVerkoop(Artikel artikel) { modelFacade.removeProductKassaVerkoop(artikel);}

    public void setOnHold(){
        modelFacade.setOnHold();
    }

    public void setOffHold(){
        modelFacade.setOffHold();
    }

    public Artikel getArtikel(String code) throws DatabaseException, IOException, BiffException {
        return modelFacade.getArtikel(code);
    }

    public double totaalPrijs() {
        return modelFacade.totaalPrijs();
    }

    public double Korting(){
        return modelFacade.Korting();
    }

    public double TeBetalen(){
        return modelFacade.TeBetalen();
    }

    public ArrayList<ArtikelWinkelmand> getWinkelmandMetAantal() {
        return modelFacade.getWinkelmandMetAantal();
    }


    @Override
    public void update(String eventType, Artikel artikel) {
        modelFacade.update(eventType, artikel);
    }

    /*
    @Override
    public void update(String eventype) {
        modelFacade.update(eventype);
    }

     */

    public void setProperty(String key, String value) {
        modelFacade.setProperty(key, value);
    }

    public String getProperty(String key) {
        return modelFacade.getProperty(key);
    }

    public ArrayList<Artikel> getWinkelmandje() {
        return modelFacade.getWinkelmandje();
    }

    public KassaVerkoop getKassaVerkoop() {
        return modelFacade.getKassaVerkoop();
    }

    public void setKassaVerkoop(KassaVerkoop kassaVerkoop) {
        modelFacade.setKassaVerkoop(kassaVerkoop);
    }

    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        return modelFacade.load();
    }

    public ArrayList<Artikel> loadinMemory() throws IOException, DatabaseException, BiffException {
        return modelFacade.loadinMemory();
    }
}
