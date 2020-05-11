package model.KassaState;

import model.KassaVerkoop;

/**
 * @author Sateur Maxime
 */

public class KassaVerkoopNew implements KassaVerkoopState{
    private KassaVerkoop kassaVerkoop;

    public KassaVerkoopNew(KassaVerkoop kassaVerkoop){
        this.kassaVerkoop = kassaVerkoop;
    }

    public void setNew(){
        System.out.println();
    }

    public void setOnHold(){
        kassaVerkoop.setKassaState(new KassaVerkoopOnHold(kassaVerkoop));
    }

    public void setOffHold(){
        System.out.println();
    }

    public void Done(){
        kassaVerkoop.setKassaState(new KassaVerkoopDone(kassaVerkoop));
    }
}
