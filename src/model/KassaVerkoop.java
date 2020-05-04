package model;

import java.util.ArrayList;

/**
 * @author Pieter Herremans, Vanhaeren Corentin
 */

public class KassaVerkoop implements Subject{

    private ArrayList<Artikel> winkelmandje;
    private ArrayList<Artikel> hold;
    private ArrayList<Observer> observers = new ArrayList<>();

    public KassaVerkoop() {
        this.winkelmandje = new ArrayList<>();
    }

    public ArrayList<Artikel> getWinkelmandje() {
        return winkelmandje;
    }

    public void setWinkelmandje(ArrayList<Artikel> winkelmandje) {
        this.winkelmandje = winkelmandje;
    }

    public ArrayList<Artikel> getHold() {
        return hold;
    }

    public void setHold(ArrayList<Artikel> hold) {
        this.hold = hold;
    }

    //public void addToWinkelmandje(Artikel artikel){ this.winkelmandje.add(artikel);}

    public void removeFromWinkelMandje(int index) { this.winkelmandje.remove(index);}

    public double getTotalPrijs(){
        double total = 0;
        for(Artikel artikel : winkelmandje){
            if(artikel != null) total+=artikel.getPrijs();
        }
        return total;
    }

    //
    public void addArtikelWinkelkar(Artikel artikel){
        winkelmandje.add(artikel);
        notifyObservers("add_product_winkelkar", artikel);
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
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = observers.get(i);;
            observer.update(eventType, artikel);
        }
    }

}
