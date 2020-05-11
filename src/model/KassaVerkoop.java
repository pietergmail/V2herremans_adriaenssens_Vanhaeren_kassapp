package model;

import model.KassaState.KassaVerkoopNew;
import model.KassaState.KassaVerkoopOnHold;
import model.KassaState.KassaVerkoopState;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Pieter Herremans, Vanhaeren Corentin, Sateur Maxime
 */

public class KassaVerkoop implements Subject{

    private HashMap<Artikel, Integer> winkelmandjeOnHold;
    private ArrayList<Observer> observers = new ArrayList<>();
    private HashMap<Artikel, Integer> winkelmandje;
    private KassaVerkoopState kassaState;

    public KassaVerkoop() {
        this.winkelmandje = new HashMap<>();
    }

    public void setWinkelmandje(ArrayList<Artikel> winkelmandje) {
        getWinkelmandje();
    }


    //public void addToWinkelmandje(Artikel artikel){ this.winkelmandje.add(artikel);}

    //public void removeFromWinkelMandje(int index) { this.winkelmandje.remove(index);}

    public double getTotalPrijs(){
        double total = 0;
        for(Artikel artikel : getWinkelmandje()){
            if(artikel != null) total+=artikel.getPrijs();
        }
        return total;
    }


    public void addArtikelWinkelkar(Artikel artikel){
        if (artikelAlreadyAdded(artikel)){
            winkelmandje.replace(artikel,  winkelmandje.get(artikel)+1);
        }
        else{
            winkelmandje.put(artikel, 1);
        }
        notifyObservers("add_product_winkelkar", artikel);
    }


    public void removeArtikelWinkelkar(Artikel artikel){
        if(winkelmandje.get(artikel) > 1){
            winkelmandje.replace(artikel, winkelmandje.get(artikel)-1);
        }else{
            winkelmandje.remove(artikel);
        }
        notifyObservers("remove_product_winkelkar", artikel);
    }


    public void setKassaState(KassaVerkoopState kassaState){
        this.kassaState = kassaState;
    }


    public void setOnHold() {
      //testing  System.out.println(winkelmandje.size());
        new KassaVerkoopNew(this).setOnHold();
        setKassaState(new KassaVerkoopOnHold(this));
        winkelmandjeOnHold= new HashMap<>(winkelmandje);
        winkelmandje.clear();
        //testing System.out.println(winkelmandje.size());
        //notifyObservers("setOnHold");
        notifyObservers("setOnHold", null);
    }



    public void setOffHold(){
        winkelmandje = new HashMap<>();
        try{
            winkelmandje.putAll(winkelmandjeOnHold);
            new KassaVerkoopOnHold(this).setOffHold();
        } catch (Exception e) {
            new KassaVerkoopNew(this).setOffHold();
        }
        setKassaState(new KassaVerkoopNew(this));
        winkelmandjeOnHold = new HashMap<>();
        //System.out.println(winkelmandje.size());
        //notifyObservers("setOffHold");
        notifyObservers("setOffHold", null);
    }



    public boolean artikelAlreadyAdded(Artikel artikel){
        boolean containsArtikel = false;
        for (Artikel a: getWinkelmandje()) {
            if (a.getCode().equals(artikel.getCode())){
                containsArtikel = true;
            }
        }
        //System.out.println(containsArtikel + " bevat?");
        return containsArtikel;
    }

/*
    public int getAantal(Artikel artikel){
        return winkelmandje.get(artikel);
    }

 */

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
/*
    @Override
    public void notifyObservers(String eventType) {
        for (Observer observer : observers) {
            observer.update(eventType);
        }
    }

 */


    public ArrayList<Artikel> getWinkelmandje(){
        ArrayList<Artikel> list = new ArrayList<>();
        for (Artikel a:winkelmandje.keySet()) {
            int aantal = winkelmandje.get(a);
            //System.out.println(aantal + " aantal");
            if(aantal > 1){
                for(int i = 0; i < aantal; i++){
                    list.add(a);
                }
            }
            else{
                list.add(a);
            }
        }
        //System.out.println(list + " winkelmand list");
        //System.out.println(winkelmandje + " winkelmand map");
        return list;
    }


    public HashMap<Artikel, Integer> getWinkelmandMap(){
        //System.out.println(winkelmandje);
        return winkelmandje;
    }

}
