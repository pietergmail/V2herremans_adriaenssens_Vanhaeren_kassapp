package model.korting;

import model.Artikel;

import java.util.ArrayList;

public class Geenkorting implements KortingStrategy{
    @Override
    public double getKorting(ArrayList<Artikel> list) {
        double total = 0;
        for(Artikel artikel : list){
            if(artikel != null) total+=artikel.getPrijs();
        }
        return total;
    }

    public Geenkorting(){
    }
}
