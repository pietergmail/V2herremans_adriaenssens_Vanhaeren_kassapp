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
        return 0;
    }

    public Geenkorting(){
    }
}
