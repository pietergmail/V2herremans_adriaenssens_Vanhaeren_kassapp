package model.korting;

import javafx.collections.ObservableList;
import model.Artikel;

/**
 * @author Vanhaeren Corentin
 */

public class KortingContext {
    private KortingStrategy kortingStrategy;

    public KortingContext(){
    }

    public void setKortingStrategy(KortingStrategy kortingStrategy){
        //System.out.println(kortingStrategy + " strategy");
        this.kortingStrategy = kortingStrategy;
        //this.kortingStrategy = new Geenkorting();
    }

    public double getKorting(ObservableList<Artikel> list) {
        return kortingStrategy.getKorting(list);
        //System.out.println(kortingStrategy + " strategy");
        //return 1000;
    }
}