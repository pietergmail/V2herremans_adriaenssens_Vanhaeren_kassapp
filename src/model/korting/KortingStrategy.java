package model.korting;

import javafx.collections.ObservableList;
import model.Artikel;

import java.util.ArrayList;

/**
 * @author Pieter Herremans, Vanhaeren Corentin
 */

public interface KortingStrategy {
    double getKorting(ArrayList<Artikel> list);
}