package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jxl.read.biff.BiffException;
import model.*;

import model.database.*;
import model.korting.KortingEnum;
import model.log.Log;
import view.KassaView;
import view.panels.KassaPane;
import view.panels.LogPane;
import view.panels.ProductOverviewPane;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Vanhaeren Corentin, Sateur Maxime
 * cleanup necessary for unused classes
 */

public class KassaviewController implements Observer {

    private KassaView kassaView;
    private KassaVerkoop kassaVerkoop;
    private ArtikelWinkelmand artikelWinkelmand;
    private InstellingController instellingController;
    private LoadSaveContext loadSaveContext;
    private ProductController productController;
    private LogController logController;
    //private LoadsaveArtikeltekst loadsaveArtikeltekst;
    private KassaPane pane;


    private Properties properties = new Properties();
    private String path = "src" + File.separator + "bestanden" + File.separator + "KassaApp.properties";

    public void setloadsaveStrategy(LoadSaveEnum loadSaveEnum){instellingController.setLoadSaveStrategy(loadSaveEnum);}

    public KassaviewController(KassaVerkoop kassaVerkoop, InstellingController instellingController, ProductController productController, LogController logController ) throws DatabaseException, IOException, BiffException {
        this.kassaVerkoop = kassaVerkoop;
        kassaVerkoop.addObserver(this);
        this.productController = productController;
        this.instellingController = instellingController;
        this.logController = logController;
        kassaVerkoop.setKorting(instellingController.getKortingStrategy());//needs re-writing
    }

    public void setPane(KassaPane pane){this.pane = pane;}

    public ObservableList<Artikel> getArtikels(){
        return FXCollections.observableArrayList(kassaVerkoop.getWinkelmandje());
    }

    public void addProductKassaVerkoop(String code) {
        try{
            Artikel a = productController.getArtikel(code);
            if (a.getVoorraad() == 0) throw new IllegalArgumentException("Niet in voorraad");
            else{
                kassaVerkoop.updateAddArtikel(a);
                System.out.println("Kassaverkoop: " + a.getOmschrijving());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void removeProductKassaVerkoop(int index) {
        kassaVerkoop.updateRemoveArtikel(index);
    }

    public ArrayList<Log> getLogs(){
        return logController.getLogs();
    }

    public void setOnHold() {
        kassaVerkoop.setOnHold();
    }

    public void setOffHold() {
        kassaVerkoop.setOffHold();
    }

    public void pasWinkelkarAan(){
        kassaVerkoop.pasWinkelkarAan();
    }

    public void setProperty(String key,String value){
        instellingController.setProperty(key,value);
    }

    public String getProperty(String key){
        return instellingController.getProperty(key);
    }

    public void setProductPane(ProductOverviewPane productOverviewPane){
        productController.setProductPane(productOverviewPane);
    }

    public void setLogPane(LogPane logPane){
        logController.setLogPane(logPane);
    }

    public double totaalPrijs() {
        return kassaVerkoop.getTotalPrijs();
    }

    public void setLoadStrategy(LoadSaveEnum loadSaveEnum){
        instellingController.setLoadSaveStrategy(loadSaveEnum);
    }

    //should not be used, use the one in productcontroller, this one will still works, probably...
    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        return productController.loadArtikels();
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
        return kassaVerkoop.berekenKorting();
    }

    public double totalePrijsMetKorting(){
        return kassaVerkoop.berekenPrijsMetKorting();
    }

    public void betaal(){
        double korting = kassaVerkoop.berekenKorting();
        double totalebedragmetkorting = kassaVerkoop.berekenPrijsMetKorting();
        double totaleprijs = kassaVerkoop.getTotalPrijs();
        Log log = new Log(totaleprijs, korting, totalebedragmetkorting);
        logController.addLog(log);
        System.out.println(log.toString());
        kassaVerkoop.betaal();
        //uitbereiding nodig in labo 10
    }

    public void setTypeKorting(KortingEnum kortingEnum){ instellingController.setTypeKorting(kortingEnum);}

    public void annuleer(){
        kassaVerkoop.annuleer();
    }

    public double getTeBetalen(){
        return kassaVerkoop.berekenPrijsMetKorting();
    }

    public ObservableList<Artikel> loadData() throws DatabaseException, IOException, BiffException {return productController.loadData();}

    public ArrayList<Artikel> loadinMemory() throws IOException, DatabaseException, BiffException {
        return productController.loadArtikels();
    }
    //Replaced with update function
    /*
    public void updateProductsInTable(){this.pane.update();}
    */

    @Override
    public void update(KassaVerkoop verkoop) {
        pane.updateTotaalPrijs(verkoop.getTotalPrijs());
        pane.updateTotaalPrijsKorting(verkoop.berekenPrijsMetKorting());
        pane.setWinkelmandje(verkoop.getWinkelmandje());
        pane.updateTotaalKorting(verkoop.berekenKorting());
    }
}
