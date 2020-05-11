package model.KassaState;

import model.KassaVerkoop;

/**
 * @author Sateur Maxime
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
        System.out.println();
    }

    public void setOffHold(){
        System.out.println();
    }

    public void Done(){
        System.out.println();
    }
}
