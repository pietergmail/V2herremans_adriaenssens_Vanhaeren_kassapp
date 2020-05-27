package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Vanhaeren Corentin
 * Zorgt voor de klantview zijn artikels correct worden weergegeven met aantal, voor de rest doet dit niks.
 */

public class ArtikelWinkelmand {
   private String code, omschrijving, groep;
    private double prijs;
    private int aantal;

    public ArtikelWinkelmand(String code, String omschrijving, String groep, double prijs, int aantal){
        setCode(code);
        setOmschrijving(omschrijving);
        setGroep(groep);
        setPrijs(prijs);
        setAantal(aantal);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getGroep() {
        return groep;
    }

    public void setGroep(String groep) {
        this.groep = groep;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    public void addAantal(){this.aantal = aantal + 1;}

    //necessary for the contains() function to work in ArtikelVoorKlant
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtikelWinkelmand that = (ArtikelWinkelmand) o;
        return Double.compare(that.prijs, prijs) == 0 && Objects.equals(omschrijving, that.omschrijving);
    }

    public static ArrayList<ArtikelWinkelmand> ArtikelVoorKlant(ArrayList<Artikel> winkelmand){
        ArrayList<ArtikelWinkelmand> viewWinkelmand = new ArrayList<>();
        for(Artikel a : winkelmand){
            ArtikelWinkelmand artikelWinkelmand = new ArtikelWinkelmand(a.getCode(),a.getOmschrijving(),a.getGroep(), a.getPrijs(), 1);
            if(viewWinkelmand.contains(artikelWinkelmand)){
                int i = viewWinkelmand.indexOf(artikelWinkelmand);
                ArtikelWinkelmand aw = viewWinkelmand.get(i);
                aw.addAantal();
                viewWinkelmand.set(i, aw);
            }
            else viewWinkelmand.add(artikelWinkelmand);
        }
        return viewWinkelmand;
    }
}