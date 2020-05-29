package model.log;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Sateur Maxime
 */

public class Log {

    private LocalDate datum;
    private LocalTime tijdtip;
    private double totaalbedrag;
    private double korting;
    private double tebetalenbedrag;

    public Log( double totaalbedrag, double korting, double tebetalenbedrag) {
        setDatum(LocalDate.now());
        setTijdtip(LocalTime.now());
        setTotaalbedrag(totaalbedrag);
        setKorting(korting);
        setTebetalenbedrag(tebetalenbedrag);
    }

    private LocalDate getDatum() {
        return datum;
    }

    private void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    private LocalTime getTijdtip() {
        return tijdtip;
    }

    private void setTijdtip(LocalTime tijdtip) {
        this.tijdtip = tijdtip;
    }

    private double getTotaalbedrag() {
        return totaalbedrag;
    }

    private void setTotaalbedrag(double totaalbedrag) {
        this.totaalbedrag = totaalbedrag;
    }

    public double getKorting() {
        return korting;
    }

    public void setKorting(double korting) {
        this.korting = korting;
    }

    private double getTebetalenbedrag() {
        return tebetalenbedrag;
    }

    private void setTebetalenbedrag(double tebetalenbedrag) {
        this.tebetalenbedrag = tebetalenbedrag;
    }

    public String toString(){
        return getDatum() + " " + getTijdtip() + " " + getTotaalbedrag() + " " + getKorting() + " " + getTebetalenbedrag();
    }
}
