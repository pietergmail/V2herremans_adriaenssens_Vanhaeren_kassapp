package model.kassabon;

import javafx.collections.ObservableList;
import model.Artikel;
import model.ArtikelWinkelmand;
import model.KassaVerkoop;
import model.korting.KortingStrategy;

import java.util.ArrayList;

/**
 * @author Vanhaeren Corentin
 */

public class KassabonComponent implements Component {
    //private KassaVerkoop kassaVerkoop; corentin wtf is dees, ge moet binnen dit stuk van het model niet in de kassaverkoop zitten, geweet toch hoe mvc werkt?
    private ObservableList<Artikel> artikelObservableList;
    private KortingStrategy kortingStrategy;
    private double totaal;
    private double korting;

    public KassabonComponent(ObservableList<Artikel> artikelObservableList, KortingStrategy kortingStrategy){
        this.kortingStrategy = kortingStrategy;
        this.artikelObservableList = artikelObservableList;

        for (Artikel a: artikelObservableList) {
            totaal += a.getPrijs();
        }

        this.korting = this.kortingStrategy.getKorting(artikelObservableList);

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
        kassabon += "Betaald (inclusief korting) : " + (totaal - korting) + " €" + "\n";
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
        return totaal;
    }

    @Override
    public Double getKorting() {
        return korting;
    }
}
