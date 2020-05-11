package model.KassaState;

import model.KassaVerkoop;

/**
 * @author Sateur Maxime
 */

public class KassaVerkoopOnHold implements KassaVerkoopState {
    private KassaVerkoop kassaVerkoop;

    public KassaVerkoopOnHold(KassaVerkoop kassaVerkoop) {
        this.kassaVerkoop = kassaVerkoop;
    }

    public void setNew(){

    }

    public void setOnHold(){

    }

    public void setOffHold(){

    }

    public void Done(){

    }
}
