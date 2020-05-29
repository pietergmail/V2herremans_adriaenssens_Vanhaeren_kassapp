package model.kassabon;

import javafx.collections.ObservableList;
import model.Artikel;
import model.ArtikelWinkelmand;
import model.KassaVerkoop;
import model.korting.KortingStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * @author Vanhaeren Corentin
 */

public class KassabonComponent implements Component {
    //private KassaVerkoop kassaVerkoop; corentin wtf is dees, ge moet binnen dit stuk van het model niet in de kassaverkoop zitten, geweet toch hoe mvc werkt?
    private ObservableList<Artikel> artikelObservableList;
    private double totaal;
    private double korting;

    public KassabonComponent(ObservableList<Artikel> artikelObservableList, KortingStrategy kortingStrategy){
        this.artikelObservableList = artikelObservableList;

        for (Artikel a: artikelObservableList) {
            totaal += a.getPrijs();
        }

        this.korting = kortingStrategy.getKorting(artikelObservableList);

    }
    @Override
    public String genereerKassabon() {
        String kassabon = "";
        kassabon += "***********************************" + "\n\n";
        kassabon += "Omschrijving       Aantal     Prijs" + "\n";
        kassabon += "***********************************" + "\n";
        for (Artikel a: ArtikelArraylist()) {
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
            l = Integer.toString(getAantal(a)).length();
            for (int i = 5-l; i > 0; i--){
                y += " ";
            }
            y += Integer.toString(getAantal(a));
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
        kassabon += "Betaald (inclusief korting) : " + (this.getTotaal() - this.getKorting()) + " €" + "\n";
        //System.out.println(kassabon);
        return kassabon;
    }

    private ArrayList<Artikel> ArtikelArraylist(){
        ArrayList<Artikel> aList = new ArrayList<>();
        for(Artikel a : artikelObservableList){
            if(!aList.contains(a)){
                aList.add(a);
            }
        }
        return aList;
    }

    private int getAantal(Artikel artikel){
        int aantal = 0;
        for(Artikel a : artikelObservableList){
            if(a.equals(artikel)) aantal++;
        }
        return aantal;
    }

    @Override
    public Double getTotaal() {
        BigDecimal bd = new BigDecimal(Double.toString(totaal));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public Double getKorting() {
        BigDecimal bd = new BigDecimal(Double.toString(korting));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
