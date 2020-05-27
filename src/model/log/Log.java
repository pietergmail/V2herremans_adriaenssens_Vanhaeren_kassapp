package model.log;

import model.database.LoadSaveEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getTijdtip() {
        return tijdtip;
    }

    public void setTijdtip(LocalTime tijdtip) {
        this.tijdtip = tijdtip;
    }

    public double getTotaalbedrag() {
        return totaalbedrag;
    }

    public void setTotaalbedrag(double totaalbedrag) {
        this.totaalbedrag = totaalbedrag;
    }

    public double getKorting() {
        return korting;
    }

    public void setKorting(double korting) {
        this.korting = korting;
    }

    public double getTebetalenbedrag() {
        return tebetalenbedrag;
    }

    public void setTebetalenbedrag(double tebetalenbedrag) {
        this.tebetalenbedrag = tebetalenbedrag;
    }

    public String toString(){
        String x = getDatum() + " " + getTijdtip() + " " + getTotaalbedrag() + " " + getKorting() + " " + getTebetalenbedrag();
        return x;
    }
}
