package model;

import controller.Observer;

/**
 * @author Vanhaeren Corentin
 */

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void updateAddArtikel(Artikel artikel);
    void updateRemoveArtikel(int i);
    void setOnHold();
    void setOffHold();
    void betaal();
    void annuleer();
    //void update(String eventype);
}