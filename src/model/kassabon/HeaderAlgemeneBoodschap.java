package model.kassabon;

/**
 * @author Vanhaeren Corentin
 */

public class HeaderAlgemeneBoodschap extends KassabonDecorator{
    private String boodschap;

    public HeaderAlgemeneBoodschap(Component component) {
        super(component);
        setBoodschap(boodschap);
    }

    public String getBoodschap() {
        return boodschap;
    }

    public void setBoodschap(String boodschap) {
        this.boodschap = boodschap;
    }

    @Override
    public String kassabon() {
        String bon = "";
        bon += boodschap + "\n";
        //bon += "***********************************" + "\n";
        bon += super.kassabon();
        //System.out.println(bon);
        return bon;
    }
}
