package controller;

import jxl.read.biff.BiffException;
import model.Artikel;
import model.KassaVerkoop;
import model.Observer;

import model.database.*;
import view.KassaView;
import view.KlantView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Vanhaeren Corentin
 */

public class KassaviewController implements Observer{
    private KassaView kassaView;
    private KassaVerkoop kassaVerkoop;
    private InstellingController instellingController;
    private LoadsaveArtikeltekst loadsaveArtikeltekst;
    LoadSaveContext loadSaveContext = new LoadSaveContext();
    //LoadSaveFactory loadSaveFactory;
    //StrategyLoadSave strategyLoadSave;
/*
    public KassaviewController(KassaVerkoop kassaVerkoop, KassaView kassaView){
        this.kassaVerkoop = kassaVerkoop;
        this.kassaView = kassaView;
    }

 */
    public KassaviewController(KassaVerkoop kassaVerkoop, InstellingController instellingController) throws DatabaseException {
        this.kassaVerkoop = kassaVerkoop;
        this.instellingController = instellingController;
        loadsaveArtikeltekst = new LoadsaveArtikeltekst();
        //kassaView = new KassaView(this);
    }

//methodes voor view
    public void addProductKassaVerkoop(Artikel artikel){
        kassaVerkoop.addArtikelWinkelkar(artikel);
    }

    public Artikel getArtikel(String code) throws DatabaseException, IOException, BiffException {
        for (Artikel a :loadSaveContext.load()){
            if (a.getCode().equals(code)){
                return a;
            }
        }
        throw new IllegalArgumentException("product niet gevonden");
    }

    public double totaalPrijs(){
        return kassaVerkoop.getTotalPrijs();
    }

    @Override
    public void update(String eventType, Artikel artikel) {
        if(eventType.equals("add_product_winkelkar")){
            //code te laten uitvoeren
            //kassaView.updateTableview(this);
            System.out.println("Product " + artikel.getOmschrijving() + " is toegevoegd.");
        }
    }

    public void setProperty(String key,String value){
        instellingController.setProperty(key,value);
    }

    public String getProperty(String key){
        return instellingController.getProperty(key);
    }

    public ArrayList<Artikel> getWinkelmandje(){
        return kassaVerkoop.getWinkelmandje();
    }
}
