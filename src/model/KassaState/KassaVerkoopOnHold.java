package model.KassaState;

import model.KassaVerkoop;

/**
 * @author Sateur Maxime, Herremans Pieter
 */

public class KassaVerkoopOnHold implements KassaVerkoopState {
    private KassaVerkoop kassaVerkoop;

    public KassaVerkoopOnHold(KassaVerkoop kassaVerkoop) {
        this.kassaVerkoop = kassaVerkoop;
    }

    public void setNew(){
        System.out.println("De kassa bestaat al.");
    }

    public void setOnHold(){
        System.out.println("De kassa staat al on hold.");
    }

    public void setOffHold(){
        kassaVerkoop.setKassaState(new KassaVerkoopNew(kassaVerkoop));
    }

    public void Done(){
        System.out.println("De kassa staat on hold.");
    }
}