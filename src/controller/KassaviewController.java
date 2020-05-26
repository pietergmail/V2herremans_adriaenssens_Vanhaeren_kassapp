package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jxl.read.biff.BiffException;
import model.*;

import model.database.*;
import view.KassaView;
import view.panels.KassaPane;
import view.panels.ProductOverviewPane;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class KassaviewController implements Observer {

    private KassaView kassaView;
    private KassaVerkoop kassaVerkoop;
    private ArtikelWinkelmand artikelWinkelmand;
    private InstellingController instellingController;
    private LoadSaveContext loadSaveContext;
    private ProductController productController;
    //private LoadsaveArtikeltekst loadsaveArtikeltekst;
    private KassaPane pane;


    private Properties properties = new Properties();
    private String path = "src" + File.separator + "bestanden" + File.separator + "KassaApp.properties";


    public KassaviewController(KassaVerkoop kassaVerkoop, InstellingController instellingController, ProductController productController ) throws DatabaseException, IOException, BiffException {
        this.kassaVerkoop = kassaVerkoop;
        kassaVerkoop.addObserver(this);
        this.productController = productController;
        this.instellingController = instellingController;
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
                kassaVerkoop.updateByAddArtikel(a);
                System.out.println("Kassaverkoop: " + a.getOmschrijving());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void removeProductKassaVerkoop(int index) {
        kassaVerkoop.updateByRemoveArtikel(index);
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

    public void setProductPane(ProductOverviewPane productOverviewPane){
        productController.setProductPane(productOverviewPane);
    }

    /*
    public Artikel getArtikel(String code) throws DatabaseException, IOException, BiffException {
        for (Artikel a : this.loadinMemory()) {
            if (a.getCode().equals(code)) {
                return a;
            }
        }
        throw new IllegalArgumentException("product niet gevonden");
    }
*/
    //vervangen door productcontroller

    public double totaalPrijs() {
        return kassaVerkoop.getTotalPrijs();
    }

    public void setLoadStrategy(LoadSaveEnum loadSaveEnum){
        instellingController.setLoadSaveStrategy(loadSaveEnum);
    }

    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        return productController.loadArtikels();
        //should not be used, use the one in productcontroller, this one will still work
    }


    /*public ArrayList<Artikel> loadinMemory() throws IOException, DatabaseException, BiffException {
        if (producten.isEmpty()) {
            producten = this.load();
        } else {
            return producten;
        }
        return producten;
    }
    */
    //vervangt door instellingcontroller

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
        kassaVerkoop.betaal();
        //uitbereiding nodig in labo 10
    }

    public void annuleer(){
        kassaVerkoop.annuleer();
    }

    public double getTeBetalen(){
        return kassaVerkoop.berekenPrijsMetKorting();
    }

    public ObservableList<Artikel> loadData() throws DatabaseException, IOException, BiffException {return productController.loadData();}

    //Replaced with update function
    /*
    public void updateProductsInTable(){this.pane.update();}
    */

    @Override
    public void update(KassaVerkoop verkoop) {
        pane.setWinkelmandje(verkoop.getWinkelmandje());
        pane.updateTotaalPrijs(verkoop.getTotalPrijs());
        pane.updateTotaalKorting(verkoop.berekenKorting());
        pane.updateTotaalPrijsKorting(verkoop.berekenPrijsMetKorting());
    }
}
