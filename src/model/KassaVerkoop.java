package model;

import java.util.ArrayList;

/**
 * @author Pieter Herremans
 */

public class KassaVerkoop {

    private ArrayList<Artikel> winkelmandje;
    private ArrayList<Artikel> hold;

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

    public void addToWinkelmandje(Artikel artikel){ this.winkelmandje.add(artikel);}

    public void removeFromWinkelMandje(int index) { this.winkelmandje.remove(index);}

    public double getTotalPrijs(){
        double total = 0;
        for(Artikel artikel : winkelmandje){
            if(artikel != null) total+=artikel.getPrijs();
        }
        return total;
    }
}
