package model.kassabon;

import model.KassaVerkoop;

/**
 * @author Vanhaeren Corentin
 */

public class FooterKorting extends KassabonDecorator {
    private KassaVerkoop kassaVerkoop;

    public FooterKorting(Component component) {
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
        bon +=  "Prijs zonder korting:" + (kassaVerkoop.berekenPrijsMetKorting() - kassaVerkoop.berekenKorting()) + " €" + "\n";
        bon +=  "Korting:" + kassaVerkoop.berekenKorting() + " €" + "\n";
        //System.out.println(bon);
        return bon;
    }
}
