package model.korting;

import javafx.collections.ObservableList;
import model.Artikel;

public class Groepkorting implements KortingStrategy{
    private double percentage;
    private String groep;

    @Override
    public double getKorting(ObservableList<Artikel> list) {
        double korting = 0;
        for (Artikel a: list) {
            if(a.getGroep().toLowerCase().equals(groep.toLowerCase())){
                korting += a.getPrijs()*percentage;
            }
        }
        return korting;
    }

    public Groepkorting(Double percentage, String groep){
        this.percentage = percentage;
        this.groep = groep;
    }
}
