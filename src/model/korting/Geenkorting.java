package model.korting;

import javafx.collections.ObservableList;
import model.Artikel;

/**
 * @author Pieter Herremans, Adriaenssens Zeno
 */

public class Geenkorting implements KortingStrategy{
    @Override
    public double getKorting(ObservableList<Artikel> list) {
        return 0;
    }

    Geenkorting(){
    }
}
