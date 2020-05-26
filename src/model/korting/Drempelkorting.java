package model.korting;

import javafx.collections.ObservableList;
import model.Artikel;

import java.util.ArrayList;

/**
 * @author Pieter Herremans, Vanhaeren Corentin
 */

public class Drempelkorting implements KortingStrategy {
    private double percentage;
    private double kost;


    @Override
    public double getKorting(ObservableList<Artikel> list) {
        double tot = 0;
        for (Artikel a: list){
            tot += a.getPrijs();
        }
        if (tot > kost){
            return tot*percentage;
        }else return 0;
    }

    public Drempelkorting(double percentage, double kost){
        this.percentage = percentage/100;
        this.kost = kost;
    }
}