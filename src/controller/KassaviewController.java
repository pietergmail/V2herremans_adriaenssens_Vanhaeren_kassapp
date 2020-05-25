package controller;

import jxl.read.biff.BiffException;
import model.*;

import model.database.*;
import model.korting.KortingContext;
import model.korting.KortingEnum;
import model.korting.KortingFactory;
import view.KassaView;
import view.KlantView;
import view.panels.KassaPane;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class KassaviewController implements Observer{

    private KassaView kassaView;
    private KassaVerkoop kassaVerkoop;
    private ArtikelWinkelmand artikelWinkelmand;
    private InstellingController instellingController;
    private LoadSaveContext loadSaveContext = new LoadSaveContext();
    private ArrayList<Artikel> producten = new ArrayList<>();
    //private LoadsaveArtikeltekst loadsaveArtikeltekst;
    private KassaPane pane;


    private Properties properties = new Properties();
    private String path = "src" + File.separator + "bestanden" + File.separator + "KassaApp.properties";


    public KassaviewController(KassaVerkoop kassaVerkoop, InstellingController instellingController)  throws DatabaseException {
        this.kassaVerkoop = kassaVerkoop;
        this.instellingController = instellingController;

    }

    public void addProductKassaVerkoop(Artikel artikel) {
        kassaVerkoop.addArtikelWinkelkar(artikel);
    }

    public void removeProductKassaVerkoop(Artikel artikel) {
        kassaVerkoop.removeArtikelWinkelkar(artikel);
    }

    public void setOnHold() {
        kassaVerkoop.setOnHold();
    }

    public void setOffHold() {
        kassaVerkoop.setOffHold();
    }

    public void setProperty(String key,String value){
        instellingController.setProperty(key,value);
    }

    public String getProperty(String key){
        return instellingController.getProperty(key);
    }

    public Artikel getArtikel(String code) throws DatabaseException, IOException, BiffException {
        for (Artikel a : this.loadinMemory()) {
            if (a.getCode().equals(code)) {
                return a;
            }
        }
        throw new IllegalArgumentException("product niet gevonden");
    }


    public double totaalPrijs() {
        return kassaVerkoop.getTotalPrijs();
    }

    @Override
    public void update(String eventType, Artikel artikel) {
        if (eventType.equals("add_product_winkelkar")) {
            //code te laten uitvoeren
            //kassaView.updateTableview(this);
            System.out.println("Product " + artikel.getOmschrijving() + " is toegevoegd.");
        }
        if(eventType.equals("remove_product_winkelkar")){
            System.out.println("Product " + artikel.getOmschrijving() + " is verwijdert.");
        }
        if(eventType.equals("setOnHold")){
            System.out.println("Winkelkar on hold gezet.");
        }
        if(eventType.equals("setOffHold")){
            System.out.println("Winkelkar off hold gezet.");
        }
    }


    public void setLoadStrategy(String key){
        LoadSaveEnum saveStrategy = LoadSaveEnum.valueOf(getProperty(key));
        loadSaveContext.setStrategyLoadSave((new LoadSaveFactory().ChooseSaveType(saveStrategy)));
    }

    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        setLoadStrategy("property.filetype");
        return loadSaveContext.load();
    }


    public ArrayList<Artikel> loadinMemory() throws IOException, DatabaseException, BiffException {
        if (producten.isEmpty()) {
            producten = this.load();
        } else {
            return producten;
        }
        return producten;
    }

    public void setKortingStrategy(){
        instellingController.setKortingStrategy();
    }

    public ArrayList<Artikel> getWinkelmandje() {
        return kassaVerkoop.getWinkelmandje();
    }

    public ArrayList<ArtikelWinkelmand> getWinkelmandMetAantal() {
        return kassaVerkoop.getWinkelmandMetAantal();
    }

    public KassaVerkoop getKassaVerkoop() {
        return kassaVerkoop;
    }

    public void setKassaVerkoop(KassaVerkoop kassaVerkoop) {
        this.kassaVerkoop = kassaVerkoop;
    }

    public double Kortingprijs(){
        return kassaVerkoop.berekenKorting(instellingController.getKortingContext());
    }

    public double totalePrijsMetKorting(){
        return kassaVerkoop.berekenPrijsMetKorting(instellingController.getKortingContext());
    }

    public void betaal(){
        kassaVerkoop.betaal();
        this.updateProductsInTable();
    }

    public void annuleer(){
        kassaVerkoop.annuleer();
        this.updateProductsInTable();
    }

    public void setPane(KassaPane pane){this.pane = pane;}

    public void updateProductsInTable(){this.pane.update();}

}
