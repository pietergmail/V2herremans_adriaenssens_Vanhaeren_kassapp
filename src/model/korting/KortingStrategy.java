package model.korting;

import javafx.collections.ObservableList;
import model.Artikel;

/**
 * @author Pieter Herremans
 */

public interface KortingStrategy {
    double getKorting(ObservableList<Artikel> list);
}
