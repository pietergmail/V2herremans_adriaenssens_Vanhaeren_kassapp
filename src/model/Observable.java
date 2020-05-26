package model;

import controller.Observer;

/**
 * @author Vanhaeren Corentin
 */

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void updateByAddArtikel(Artikel artikel);
    void updateByRemoveArtikel(int i);
    void setOnHold();
    void setOffHold();
    void betaal();
    void annuleer();
    //void update(String eventype);
}