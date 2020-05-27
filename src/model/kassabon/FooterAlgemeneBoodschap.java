package model.kassabon;

/**
 * @author Vanhaeren Corentin
 */

public class FooterAlgemeneBoodschap extends KassabonDecorator {
    private String boodschap;

    public FooterAlgemeneBoodschap(Component component) {
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
        //bon += "***********************************" + "\n";
        bon += super.kassabon();
        bon += boodschap + "\n";
        //System.out.println(bon);
        return bon;
    }
}
