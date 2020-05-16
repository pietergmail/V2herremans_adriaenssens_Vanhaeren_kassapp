package model.korting;

import javafx.collections.ObservableList;
import model.Artikel;

public class Duurstekorting implements KortingStrategy {
    private double percentage;

    @Override
    public double getKorting(ObservableList<Artikel> list) {
        Artikel artikel = list.get(0);
        for(Artikel a: list){
            if(a.getPrijs() > a.getPrijs()){
                artikel = a;
            }
        }
        return artikel.getPrijs() * percentage;
    }

    public Duurstekorting(double percentage){
        this.percentage = percentage;
    }
}
