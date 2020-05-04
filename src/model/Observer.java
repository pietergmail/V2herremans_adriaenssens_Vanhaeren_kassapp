package model;
/**
 * @author Vanhaeren Corentin
 */

public interface Observer {
    void update(String eventType, Artikel artikel);
}
