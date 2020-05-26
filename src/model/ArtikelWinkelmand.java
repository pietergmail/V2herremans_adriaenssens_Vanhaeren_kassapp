package model;

import java.lang.reflect.Array;
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

    public ArtikelWinkelmand(String omschrijving, double prijs, int aantal){
        setOmschrijving(omschrijving);
        setPrijs(prijs);
        setAantal(aantal);
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
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

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtikelWinkelmand that = (ArtikelWinkelmand) o;
        return Double.compare(that.prijs, prijs) == 0 && Objects.equals(omschrijving, that.omschrijving);

    }

    public static ArrayList<ArtikelWinkelmand> ArtikelVoorKlant(ArrayList<Artikel> winkelmand){
        ArrayList<ArtikelWinkelmand> viewWinkelmand = new ArrayList<>();
        for(Artikel artikel : winkelmand){
            ArtikelWinkelmand artikelWinkelmand = new ArtikelWinkelmand(artikel.getOmschrijving(), artikel.getPrijs(), 1);
            if(viewWinkelmand.contains(artikelWinkelmand)){
                int i = viewWinkelmand.indexOf(artikelWinkelmand);
                ArtikelWinkelmand a = viewWinkelmand.get(i);
                a.addAantal();
                viewWinkelmand.set(i, a);
            }
            else viewWinkelmand.add(artikelWinkelmand);
        }
        return viewWinkelmand;
    }
}