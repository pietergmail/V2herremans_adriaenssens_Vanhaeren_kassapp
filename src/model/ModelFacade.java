package model;

import controller.InstellingController;
import jxl.read.biff.BiffException;
import model.database.DatabaseException;
import model.database.LoadSaveContext;
import model.database.LoadsaveArtikeltekst;
import view.KassaView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class ModelFacade{

    private KassaView kassaView;
    private KassaVerkoop kassaVerkoop;
    private ArtikelWinkelmand artikelWinkelmand;
    private InstellingController instellingController;
    private LoadsaveArtikeltekst loadsaveArtikeltekst;
    LoadSaveContext loadSaveContext = new LoadSaveContext();
    ArrayList<Artikel> producten = new ArrayList<>();
    //LoadSaveFactory loadSaveFactory;
    //StrategyLoadSave strategyLoadSave;
/*
    public KassaviewController(KassaVerkoop kassaVerkoop, KassaView kassaView){
        this.kassaVerkoop = kassaVerkoop;
        this.kassaView = kassaView;
    }

 */
    public ModelFacade(KassaVerkoop kassaVerkoop, InstellingController instellingController) throws DatabaseException {
        this.kassaVerkoop = kassaVerkoop;
        this.instellingController = instellingController;
        //kassaView = new KassaView(this);
    }

    //methodes voor view
    public void addProductKassaVerkoop(Artikel artikel){
        kassaVerkoop.addArtikelWinkelkar(artikel);
    }

    public void removeProductKassaVerkoop(Artikel artikel) {kassaVerkoop.removeArtikelWinkelkar(artikel);}

    public Artikel getArtikel(String code) throws DatabaseException, IOException, BiffException {
        for (Artikel a :this.loadinMemory()){
            if (a.getCode().equals(code)){
                return a;
            }
        }
        throw new IllegalArgumentException("product niet gevonden");
    }

    public double totaalPrijs(){
        return kassaVerkoop.getTotalPrijs();
    }

    public ArrayList<ArtikelWinkelmand> getWinkelmandMetAantal(){
        ArrayList<ArtikelWinkelmand> winkelmand = new ArrayList<>();
        for (Map.Entry<Artikel, Integer> entry: kassaVerkoop.getWinkelmandMap().entrySet()) {
            ArtikelWinkelmand  artikel = new ArtikelWinkelmand();
            artikel.setCode(entry.getKey().getCode());
            artikel.setOmschrijving((entry.getKey().getOmschrijving()));
            artikel.setGroep(entry.getKey().getGroep());
            artikel.setPrijs(entry.getKey().getPrijs());
            artikel.setAantal(entry.getValue());
            winkelmand.add(artikel);
        }
        return winkelmand;
    }


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

    public KassaVerkoop getKassaVerkoop() {
        return kassaVerkoop;
    }

    public void setKassaVerkoop(KassaVerkoop kassaVerkoop) {
        this.kassaVerkoop = kassaVerkoop;
    }

    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        return loadSaveContext.load();
    }

    public ArrayList<Artikel> loadinMemory() throws IOException, DatabaseException, BiffException {
        if (producten.isEmpty()){
            producten = this.load();
        }
        else {
            return producten;
        }
        return producten;
    }
}
