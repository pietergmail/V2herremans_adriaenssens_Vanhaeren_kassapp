package view;
/**
 * @author Vanhaeren Corentin
 */

import controller.KassaviewController;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import jxl.read.biff.BiffException;
import model.KassaVerkoop;
import view.panels.InstellingenPane;
import model.database.DatabaseException;
import view.panels.KassaPane;
import view.panels.ProductOverviewPane;

import java.io.IOException;

public class KassaMainPane extends BorderPane {
    public KassaMainPane(KassaviewController kassaviewController) throws DatabaseException {
        TabPane tabPane = new TabPane();
        KassaPane kassaPane = new KassaPane(kassaviewController);
        Tab kassaTab = new Tab("Kassa", kassaPane);
        ProductOverviewPane productOverviewPane = null;
        try {
            productOverviewPane = new ProductOverviewPane();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        Tab artikelTab = new Tab("Artikelen",productOverviewPane);
        InstellingenPane instellingenPane = new InstellingenPane();
        Tab instellingTab = new Tab("Instellingen",instellingenPane);
        Tab logTab = new Tab("Log");
        tabPane.getTabs().add(kassaTab);
        tabPane.getTabs().add(artikelTab);
        tabPane.getTabs().add(instellingTab);
        tabPane.getTabs().add(logTab);
        this.setCenter(tabPane);

    }
}
