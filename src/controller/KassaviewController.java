package controller;

import jxl.read.biff.BiffException;
import model.*;

import model.database.*;
import model.korting.KortingContext;
import view.KassaView;
import view.KlantView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class KassaviewController {

    private KassaController kassaController;

    public KassaviewController(KassaController kassaController) {
        this.kassaController = kassaController;
    }

    public void addProductKassaVerkoop(Artikel artikel) {
        kassaController.addProductKassaVerkoop(artikel);
    }

    public void removeProductKassaVerkoop(Artikel artikel) { kassaController.removeProductKassaVerkoop(artikel);}

    public void setOnHold(){
        kassaController.setOnHold();
    }

    public void setOffHold(){
        kassaController.setOffHold();
    }

    public Artikel getArtikel(String code) throws DatabaseException, IOException, BiffException {
        return kassaController.getArtikel(code);
    }

    public double totaalPrijs() {
        return kassaController.totaalPrijs();
    }

    /*
    public ArrayList<ArtikelWinkelmand> getWinkelmandMetAantal() {
        return kassaController.getWinkelmandMetAantal();
    }

     */


    public void update(String eventType, Artikel artikel) {
        kassaController.update(eventType, artikel);
    }

    /*
    @Override
    public void update(String eventype) {
        kassaController.update(eventype);
    }

     */

    public void setProperty(String key, String value) {
        kassaController.setProperty(key, value);
    }

    public String getProperty(String key) {
        return kassaController.getProperty(key);
    }



    public ArrayList<Artikel> getWinkelmandje() {
        return kassaController.getWinkelmandje();
    }

    public KassaVerkoop getKassaVerkoop() {
        return kassaController.getKassaVerkoop();
    }

    public void setKassaVerkoop(KassaVerkoop kassaVerkoop) {
        kassaController.setKassaVerkoop(kassaVerkoop);
    }

    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        return kassaController.load();
    }

    public ArrayList<Artikel> loadinMemory() throws IOException, DatabaseException, BiffException {
        return kassaController.loadinMemory();
    }

    public void setKortingStrategy(){
        kassaController.setKortingStrategy();
    }

    public double totaalPrijsKorting(){
        return kassaController.totaalPrijsKorting();
    }
}
