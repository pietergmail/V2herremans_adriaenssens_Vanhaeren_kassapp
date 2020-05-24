package model.KassaState;

import model.KassaVerkoop;

/**
 * @author Sateur Maxime, Herremans Pieter
 */

public class KassaVerkoopNew implements KassaVerkoopState{
    private KassaVerkoop kassaVerkoop;

    public KassaVerkoopNew(KassaVerkoop kassaVerkoop){
        this.kassaVerkoop = kassaVerkoop;
    }

    public void setNew(){
        System.out.println("Is nieuw.");
    }

    public void setOnHold(){
        kassaVerkoop.setKassaState(new KassaVerkoopOnHold(kassaVerkoop));
    }

    public void setOffHold(){
        System.out.println("Is niet on hold.");
    }

    public void Done(){
        kassaVerkoop.setKassaState(new KassaVerkoopDone(kassaVerkoop));
    }
}
