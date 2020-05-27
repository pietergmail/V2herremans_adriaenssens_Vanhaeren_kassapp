package model.kassabon;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Vanhaeren Corentin
 */

public class HeaderDatumTijd extends KassabonDecorator{
    public HeaderDatumTijd(Component component) {
        super(component);
    }

    @Override
    public String kassabon() {
        String datumtijd = "Datum: "  + LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear() + "   Tijd: " + LocalTime.now().getHour() + ":"+LocalTime.now().getMinute();
        String bon = "";
        bon += datumtijd + "\n";
        //bon += "***********************************" + "\n";
        bon += super.kassabon();
        //System.out.println(bon);
        return bon;
    }
}
