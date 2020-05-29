package model.kassabon;

/**
 * @author Vanhaeren Corentin
 */

public class HeaderAlgemeneBoodschap extends KassabonDecorator{
    private String boodschap;

    public HeaderAlgemeneBoodschap(Component component, String boodschap) {
        super(component);
        setBoodschap(boodschap);
    }

    public String getBoodschap() {
        return boodschap;
    }

    private void setBoodschap(String boodschap) {
        this.boodschap = boodschap;
    }

    @Override
    public String genereerKassabon() {
        String bon = "";
        bon += boodschap + "\n";
        //bon += "***********************************" + "\n";
        bon += super.genereerKassabon();
        //System.out.println(bon);
        return bon;
    }
}
