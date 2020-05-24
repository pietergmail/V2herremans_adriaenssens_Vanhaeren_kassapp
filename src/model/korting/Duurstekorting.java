package model.korting;

import javafx.collections.ObservableList;
import model.Artikel;

import java.util.ArrayList;

/**
 * @author Pieter Herremans, Vanhaeren Corentin
 */

public class Duurstekorting implements KortingStrategy {
    private double percentage;

    @Override
    public double getKorting(ArrayList<Artikel> list) {
        Artikel artikel = list.get(0);
        for(Artikel a: list){
            if(a.getPrijs() > artikel.getPrijs()){
                artikel = a;
            }
        }
        return artikel.getPrijs() * percentage;
    }

    public Duurstekorting(double percentage){
        this.percentage = percentage/100;
    }

}