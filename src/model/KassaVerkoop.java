package model;

import controller.Observer;
import javafx.collections.FXCollections;
import model.KassaState.KassaVerkoopDone;
import model.KassaState.KassaVerkoopNew;
import model.KassaState.KassaVerkoopState;
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

    public double getTotalPrijs(){
        double total = 0;
        for(Artikel artikel : getWinkelmandje()){
            if(artikel != null) total+=artikel.getPrijs();
        }
        BigDecimal bd = new BigDecimal(Double.toString(total));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void addArtikelWinkelkar(Artikel artikel){
        this.winkelmandlist.add(artikel);
        System.out.println(winkelmandlist.toString());
    }

    private void removeArtikelWinkelkar(int index){
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
    public void updateRemoveArtikel(int i){
        this.removeArtikelWinkelkar(i);
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(this);
        }
    }

    public ArrayList<Artikel> getWinkelmandje(){
      return winkelmandlist;
    }

    public double berekenPrijsMetKorting(){
        double korting = berekenKorting();
        double initprijs = getTotalPrijs();
        BigDecimal bd = new BigDecimal(Double.toString(initprijs - korting));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
