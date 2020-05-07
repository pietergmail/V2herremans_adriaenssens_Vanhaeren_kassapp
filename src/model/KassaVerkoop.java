package model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Pieter Herremans, Vanhaeren Corentin
 */

public class KassaVerkoop implements Subject{

    private ArrayList<Artikel> hold;
    private ArrayList<Observer> observers = new ArrayList<>();
    private HashMap<Artikel, Integer> winkelmandje;

    public KassaVerkoop() {
        this.winkelmandje = new HashMap<>();
    }

    public void setWinkelmandje(ArrayList<Artikel> winkelmandje) {
        getWinkelmandje();
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

    public int getAantal(Artikel artikel){
        return winkelmandje.get(artikel);
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

    public ArrayList<Artikel> getWinkelmandje(){
        ArrayList<Artikel> list = new ArrayList();
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
}
