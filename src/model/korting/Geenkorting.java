package model.korting;

import javafx.collections.ObservableList;
import model.Artikel;

import java.util.ArrayList;

/**
 * @author Pieter Herremans, Adriaenssens Zeno
 */

public class Geenkorting implements KortingStrategy{
    @Override
    public double getKorting(ObservableList<Artikel> list) {
        double total = 0;
        for(Artikel artikel : list){
            if(artikel != null) total+=artikel.getPrijs();
        }
        return total;
    }

    public Geenkorting(){
    }
}
