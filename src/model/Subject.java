package model;
/**
 * @author Vanhaeren Corentin
 */

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String eventType, Artikel artikel);

}
