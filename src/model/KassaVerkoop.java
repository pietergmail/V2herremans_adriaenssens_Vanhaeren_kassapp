package model;

import controller.KassaviewController;
import javafx.collections.FXCollections;
import model.KassaState.KassaVerkoopNew;
import model.KassaState.KassaVerkoopOnHold;
import model.KassaState.KassaVerkoopState;
import model.korting.KortingContext;
import model.korting.KortingStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Pieter Herremans, Vanhaeren Corentin, Sateur Maxime
 */

public class KassaVerkoop implements Subject{

    private ArrayList<Observer> observers;
    private KassaVerkoopState kassaState;
    private KortingStrategy korting;
    private ArrayList<ArtikelWinkelmand> winkelmand;
    private ArrayList<ArtikelWinkelmand> winkelmandonhold = new ArrayList<>();
    private ArrayList<Artikel> winkelmandlist = new ArrayList<>();
    private ArrayList<Artikel> winkelmandonholdlist = new ArrayList<>();


    public KassaVerkoop() {
        this.observers = new ArrayList<>();
        this.winkelmand = new ArrayList<>();
        this.kassaState = new KassaVerkoopNew(this);
    }

    public void setWinkelmandje(ArrayList<Artikel> winkelmandje) {
        getWinkelmandje();
    }

    public double getTotalPrijs(){
        double total = 0;
        for(Artikel artikel : getWinkelmandje()){
            if(artikel != null) total+=artikel.getPrijs();
        }
        return total;
    }

public void addArtikelWinkelkar(Artikel artikel){
    if (artikelAlreadyAdded(artikel)){
        for (ArtikelWinkelmand a: winkelmand) {
            if (a.getCode().equals(artikel.getCode())){
                a.setAantal(a.getAantal()+1);
            }
        }
    }
    else{
        ArtikelWinkelmand artikelWinkelmand = new ArtikelWinkelmand(artikel.getCode(), artikel.getOmschrijving(), artikel.getGroep(), artikel.getPrijs(), 1);
        winkelmand.add(artikelWinkelmand);
    }
    winkelmandlist.add(artikel);
    notifyObservers("add_product_winkelkar", artikel);
}

public void removeArtikelWinkelkar(Artikel artikel){
    for (int i = 0; i < winkelmand.size(); i++) {
        ArtikelWinkelmand a = winkelmand.get(i);
        if (a.getCode().equals(artikel.getCode())){
            //System.out.println("test");
            if(a.getAantal() > 1){
                int aantal = a.getAantal();
                aantal-=1;
                a.setAantal(aantal);
            }
            else {
                //winkelmand.remove(a);
                winkelmand.remove(i);
            }
        }
    }
    winkelmandlist.remove(artikel);
    notifyObservers("remove_product_winkelkar", artikel);
}


    public void setKassaState(KassaVerkoopState kassaState){
        this.kassaState = kassaState;
    }

    public KassaVerkoopState getKassaState(){return kassaState;}

    public void setOnHold() {
        winkelmandonhold.clear();
        winkelmandonholdlist.clear();
        winkelmandonhold.addAll(winkelmand);
        winkelmandonholdlist.addAll(winkelmandlist);
        winkelmand.clear();
        winkelmandlist.clear();
        notifyObservers("setOnHold", null);
    }

    public void setOffHold() {
        winkelmand.clear();
        winkelmandlist.clear();
        winkelmand.addAll(winkelmandonhold);
        winkelmandlist.addAll(winkelmandonholdlist);
        notifyObservers("setOffHold", null);
    }

    public boolean artikelAlreadyAdded(Artikel artikel){
        boolean containsArtikel = false;
        for (ArtikelWinkelmand a: winkelmand) {
            if (a.getCode().equals(artikel.getCode())){
                containsArtikel = true;
            }
        }
        //System.out.println(containsArtikel + " bevat?");
        return containsArtikel;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String eventType, Artikel artikel) {
        for (Observer observer : observers) {
            observer.update(eventType, artikel);
        }
    }

    public ArrayList<Artikel> getWinkelmandje(){
      return winkelmandlist;
    }

    public ArrayList<ArtikelWinkelmand> getWinkelmandMetAantal(){
        return winkelmand;
    }

    public double berekenPrijsMetKorting(KortingContext kortingContext){
        double korting = berekenKorting(kortingContext);
        double initprijs = getTotalPrijs();

        return initprijs - korting;
    }

    public double berekenKorting(KortingContext kortingContext){

        if(winkelmandlist.size() == 0) return 0;

        else {
            double kortingbedrag = kortingContext.getKorting(this.getWinkelmandje());
            BigDecimal round = BigDecimal.valueOf(kortingbedrag);
            round = round.setScale(2, RoundingMode.HALF_UP);
            return round.doubleValue();
        }
    }
}
