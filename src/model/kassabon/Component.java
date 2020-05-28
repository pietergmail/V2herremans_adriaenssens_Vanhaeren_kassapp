package model.kassabon;

/**
 * @author Vanhaeren Corentin
 */

public interface Component {
    String genereerKassabon();
    Double getTotaal();
    Double getKorting();
}
