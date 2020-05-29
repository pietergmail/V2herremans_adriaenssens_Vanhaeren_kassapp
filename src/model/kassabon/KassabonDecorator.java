package model.kassabon;

/**
 * @author Vanhaeren Corentin
 */

public class KassabonDecorator implements Component {
    private Component wrappee;
    private Double totaal;
    private Double korting;

    KassabonDecorator(Component component){
        this.wrappee = component;
        this.totaal = component.getTotaal();
        this.korting = component.getKorting();
    }

    @Override
    public String genereerKassabon(){
        return wrappee.genereerKassabon();
    }

    @Override
    public Double getTotaal() {
        return totaal;
    }

    @Override
    public Double getKorting() {
        return korting;
    }

    ;
}
