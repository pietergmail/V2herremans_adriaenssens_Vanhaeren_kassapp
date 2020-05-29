package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Vanhaeren Corentin
 * Zorgt voor de klantview zijn artikels correct worden weergegeven met aantal, voor de rest doet dit niks.
 */

public class ArtikelWinkelmand {
    private String omschrijving;
    private double prijs;
    private int aantal;

    private ArtikelWinkelmand(String omschrijving, double prijs, int aantal){
        setOmschrijving(omschrijving);
        setPrijs(prijs);
        setAantal(aantal);
    }

    private void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    private void setAantal(int aantal) {
        this.aantal = aantal;
    }

    private void addAantal(){this.aantal = aantal + 1;}

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
            ArtikelWinkelmand artikelWinkelmand = new ArtikelWinkelmand(a.getOmschrijving(), a.getPrijs(), 1);
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