package model.korting;

import javafx.collections.ObservableList;
import model.Artikel;

import java.util.ArrayList;

/**
 * @author Pieter Herremans, Vanhaeren Corentin
 */

public class Groepkorting implements KortingStrategy{
    private double percentage;
    private String groep;

    @Override
    public double getKorting(ArrayList<Artikel> list) {
        double korting = 0;
        for (Artikel a: list) {
            if(a.getGroep().toLowerCase().equals(groep.toLowerCase())){
                korting += a.getPrijs()*percentage;
            }
        }
        return korting;
    }

    public Groepkorting(Double percentage, String groep){
        this.percentage = percentage/100;
        this.groep = groep;
    }

}
