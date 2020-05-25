package model;
/**
 * @author Vanhaeren Corentin
 */

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void updateByAddArtikel(Artikel artikel);
    void updateByRemoveArtikel(int i);
    void setOnHold();
    void setOffHold();
    void betaal();
    void annuleer();
    void notifyObservers(String eventType, Artikel artikel);
    //void notifyObservers(String eventType);
}