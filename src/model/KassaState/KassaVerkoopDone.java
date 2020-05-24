package model.KassaState;

import model.KassaVerkoop;

/**
 * @author Sateur Maxime, Herremans Pieter
 */

public class KassaVerkoopDone implements KassaVerkoopState {
    private KassaVerkoop kassaVerkoop;

    public KassaVerkoopDone(KassaVerkoop kassaVerkoop) {
        this.kassaVerkoop = kassaVerkoop;
    }

    public void setNew(){
        kassaVerkoop.setKassaState(new KassaVerkoopNew(kassaVerkoop));
    }

    public void setOnHold(){
        System.out.println("Kassaverkoop is done.");
    }

    public void setOffHold(){
        System.out.println("Kassaverkoop is done.");
    }

    public void Done(){
        System.out.println("Kassaverkoop is done.");
    }
}