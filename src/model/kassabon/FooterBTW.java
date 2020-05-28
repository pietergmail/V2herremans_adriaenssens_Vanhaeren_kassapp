package model.kassabon;

import model.KassaVerkoop;

/**
 * @author Vanhaeren Corentin
 */

public class FooterBTW extends KassabonDecorator{
    public FooterBTW(Component component) {
        super(component);
    }

    @Override
    public String genereerKassabon() {
        String bon = "";
        //bon += "***********************************" + "\n";
        bon += super.genereerKassabon();
        bon +=  "Prijs zonder BTW: " + super.getTotaal()*0.94 + " €" + "\n";
        bon +=  "BTW: " + super.getTotaal()*0.06 + " €" + "\n";
        //System.out.println(bon);
        return bon;
    }
}
