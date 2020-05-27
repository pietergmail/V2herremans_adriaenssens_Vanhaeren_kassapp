package model.kassabon;

import model.KassaVerkoop;

/**
 * @author Vanhaeren Corentin
 */

public class FooterBTW extends KassabonDecorator{
    private KassaVerkoop kassaVerkoop;
    public FooterBTW(Component component) {
        super(component);
        setKassaVerkoop(kassaVerkoop);
    }

    public void setKassaVerkoop(KassaVerkoop kassaVerkoop) {
        this.kassaVerkoop = kassaVerkoop;
    }

    @Override
    public String kassabon() {
        String bon = "";
        //bon += "***********************************" + "\n";
        bon += super.kassabon();
        bon +=  "Prijs zonder BTW: " + kassaVerkoop.getTotalPrijs()*0.94 + " €" + "\n";
        bon +=  "BTW: " + kassaVerkoop.getTotalPrijs()*0.06 + " €" + "\n";
        //System.out.println(bon);
        return bon;
    }
}
