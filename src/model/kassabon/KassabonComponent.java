package model.kassabon;

import model.ArtikelWinkelmand;
import model.KassaVerkoop;

import java.util.ArrayList;

/**
 * @author Vanhaeren Corentin
 */

public class KassabonComponent implements Component {
    private KassaVerkoop kassaVerkoop;

    public KassabonComponent(KassaVerkoop kassaVerkoop){
        setKassaVerkoop(kassaVerkoop);
    }

    public void setKassaVerkoop(KassaVerkoop kassaVerkoop) {
        this.kassaVerkoop = kassaVerkoop;
    }

    @Override
    public String kassabon() {
        String kassabon = "";
        ArrayList<ArtikelWinkelmand> winkelmand = kassaVerkoop.getWinkelmandMetAantal();
        kassabon += "***********************************" + "\n\n";
        kassabon += "Omschrijving       Aantal     Prijs" + "\n";
        kassabon += "***********************************" + "\n";
        for (ArtikelWinkelmand a: winkelmand) {
            //omschrijving
            int l = a.getOmschrijving().length();
            //String x = "                                ";
            //x = x.replace(x.substring(0, 19), a.getOmschrijving());
            String x = "";
            x += a.getOmschrijving();
            for (int i = 20-l ; i > 0; i--){
                x += " ";
            }
            //aantal
            String y = "";
            l = Integer.toString(a.getAantal()).length();
            for (int i = 5-l; i > 0; i--){
                y += " ";
            }
            y += Integer.toString(a.getAantal());
            //prijs
            String z = "";
            l = Double.toString(a.getPrijs()).length();
            for (int i = 10-l; i > 0; i--){
                z += " ";
            }
            z += Double.toString(a.getPrijs());
            String xyz = x + y + z + "\n";
            kassabon += xyz;
        }
        kassabon += "***********************************" + "\n";
        kassabon += "Betaald (inclusief korting) : " + kassaVerkoop.berekenPrijsMetKorting() + " €" + "\n";
        //System.out.println(kassabon);
        return kassabon;
    }
}
