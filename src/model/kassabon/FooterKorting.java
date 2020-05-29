package model.kassabon;

import javafx.collections.ObservableList;
import model.Artikel;
import model.KassaVerkoop;

import java.util.ArrayList;

/**
 * @author Vanhaeren Corentin
 */

public class FooterKorting extends KassabonDecorator {
    //private KassaVerkoop kassaVerkoop; this has no place here

    public FooterKorting(Component component) {
        super(component);

    }

    @Override
    public String genereerKassabon() {
        String bon = "";
        bon += super.genereerKassabon();
        bon +=  "Prijs zonder korting:" + super.getTotaal() + " €" + "\n";
        bon +=  "Korting:" + (super.getKorting()) + " €" + "\n";
        return bon;
    }
}
