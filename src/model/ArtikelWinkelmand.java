package model;

/**
 * @author Vanhaeren Corentin
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

    public ArtikelWinkelmand(){

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
}