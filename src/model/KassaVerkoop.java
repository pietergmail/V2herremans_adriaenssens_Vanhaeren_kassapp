package model;

import controller.Observer;
import javafx.collections.FXCollections;
import model.KassaState.KassaVerkoopDone;
import model.KassaState.KassaVerkoopNew;
import model.KassaState.KassaVerkoopState;
import model.korting.KortingContext;
import model.korting.KortingStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * @author Pieter Herremans, Vanhaeren Corentin, Sateur Maxime
 */

public class KassaVerkoop implements Observable {
    private ArrayList<Observer> observers;
    private KassaVerkoopState kassaState;
    private KortingStrategy korting;
    private ArrayList<ArtikelWinkelmand> winkelmand;
    private ArrayList<ArtikelWinkelmand> winkelmandonhold = new ArrayList<>();
    private ArrayList<ArtikelWinkelmand> winkelmandbetaal = new ArrayList<>();
    private ArrayList<Artikel> winkelmandbetaallist = new ArrayList<>();
    private ArrayList<Artikel> winkelmandlist = new ArrayList<>();
    private ArrayList<Artikel> winkelmandonholdlist = new ArrayList<>();
    private int onHoldCounter;

    public KassaVerkoop() {
        this.observers = new ArrayList<>();
        this.winkelmand = new ArrayList<>();
        this.kassaState = new KassaVerkoopNew(this);
    }

    public KortingStrategy getKorting() {
        return korting;
    }

    public void setKorting(KortingStrategy korting) {
        this.korting = korting;
        System.out.println("Kortingstrategie: " + korting.toString());
    }

    public void setWinkelmandje(ArrayList<Artikel> winkelmandje) {
        getWinkelmandje();
    }

    public double getTotalPrijs(){
        double total = 0;
        for(Artikel artikel : getWinkelmandje()){
            if(artikel != null) total+=artikel.getPrijs();
        }
        BigDecimal round = BigDecimal.valueOf(total);
        round = round.setScale(2, RoundingMode.HALF_UP);
        return round.doubleValue();
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
        this.winkelmandlist.add(artikel);
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
    }

    public void remove(int index){
        this.winkelmandlist.remove(index);
    }


    public void setKassaState(KassaVerkoopState kassaState){
        this.kassaState = kassaState;
    }

    public void setOnHold() {
        winkelmandonhold.clear();
        winkelmandonholdlist.clear();
        winkelmandonhold.addAll(winkelmand);
        winkelmandonholdlist.addAll(winkelmandlist);
        winkelmand.clear();
        winkelmandlist.clear();
        notifyObservers();
        onHoldCounter = 0;
    }

    public void setOffHold() {
        winkelmand.clear();
        winkelmandlist.clear();
        winkelmand.addAll(winkelmandonhold);
        winkelmandlist.addAll(winkelmandonholdlist);
        notifyObservers();
    }

    public void pasWinkelkarAan(){
        winkelmand.clear();
        winkelmandlist.clear();
        winkelmand.addAll(winkelmandbetaal);
        winkelmandlist.addAll(winkelmandbetaallist);
    }



    public boolean artikelAlreadyAdded(Artikel artikel){
        boolean containsArtikel = false;
        for (ArtikelWinkelmand a: winkelmand) {
            if (a.getCode().equals(artikel.getCode())){//code van a is null en geeft een error
                containsArtikel = true;
            }
        }
        //System.out.println(containsArtikel + " bevat?");
        return containsArtikel;
    }


    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void updateAddArtikel(Artikel artikel) {
        this.addArtikelWinkelkar(artikel);
        notifyObservers();
    }

    @Override
    public void updateRemoveArtikel(Artikel artikel){
        this.removeArtikelWinkelkar(artikel);
        notifyObservers();
    }

    @Override
    public void updateRemoveArtikel2(int i) {
        this.remove(i);
    }

    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(this);
        }
    }

    public ArrayList<Artikel> getWinkelmandje(){
      return winkelmandlist;
    }

    public ArrayList<ArtikelWinkelmand> getWinkelmandMetAantal(){
        return winkelmand;
    }

    public double berekenPrijsMetKorting(){
        double korting = berekenKorting();
        double initprijs = getTotalPrijs();

        if( initprijs - korting < 0){
            return 0;
        }
        else {
            BigDecimal round = BigDecimal.valueOf(initprijs - korting);
            round = round.setScale(2, RoundingMode.HALF_UP);
            return round.doubleValue();
        }
    }

    public double berekenKorting(){

        if(winkelmandlist.size() == 0) return 0;

        else {
            double kortingbedrag = korting.getKorting(FXCollections.observableArrayList(this.getWinkelmandje()));
            BigDecimal round = BigDecimal.valueOf(kortingbedrag);
            round = round.setScale(2, RoundingMode.HALF_UP);
            return round.doubleValue();
        }
    }

    public void betaal(){
        new KassaVerkoopNew(this).Done();
        setKassaState(new KassaVerkoopDone(this));
        winkelmandbetaal.clear();
        winkelmandbetaal.addAll(winkelmand);
        winkelmandbetaallist.addAll(winkelmandlist);
        winkelmand.clear();
        winkelmandlist.clear();
        onHoldCounter++;
        notifyObservers();
        if(onHoldCounter == 3){
            winkelmandonhold.clear();
            winkelmandonholdlist.clear();
        }
    }

    public void annuleer(){
        new KassaVerkoopNew(this).setNew();
        setKassaState(new KassaVerkoopNew(this));
        winkelmand.clear();
        winkelmandlist.clear();
        notifyObservers();
    }


}
