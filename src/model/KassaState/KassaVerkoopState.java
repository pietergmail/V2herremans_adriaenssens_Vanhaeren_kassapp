package model.KassaState;

/**
 * @author Sateur Maxime
 */

public interface KassaVerkoopState {
    void setOnHold();
    void setOffHold();
    void setNew();
    void Done();
}