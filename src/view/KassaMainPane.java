package view;


import controller.KassaviewController;
import controller.ProductController;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import jxl.read.biff.BiffException;
import model.KassaVerkoop;
import view.panels.InstellingenPane;
import model.database.DatabaseException;
import view.panels.KassaPane;
import view.panels.LogPane;
import view.panels.ProductOverviewPane;

import java.io.IOException;

/**
 * @author Vanhaeren Corentin, Herremans Pieter
 */

class KassaMainPane extends BorderPane {
    KassaMainPane(KassaviewController kassaviewController) throws DatabaseException, IOException, BiffException {

        KassaPane kassaPane = new KassaPane(kassaviewController);
        ProductOverviewPane artikelPane = new ProductOverviewPane(kassaviewController);
        InstellingenPane instellingPane = new InstellingenPane(kassaviewController);
        LogPane logPane = new LogPane(kassaviewController);

        TabPane tabPane = new TabPane();
        Tab kassaTab = new Tab("Kassa", kassaPane);
        Tab artikelTab = new Tab("Artikelen", artikelPane);
        Tab instellingTab = new Tab("Instellingen", instellingPane);
        Tab logTab = new Tab("Log", logPane);

        tabPane.getTabs().add(kassaTab);
        tabPane.getTabs().add(artikelTab);
        tabPane.getTabs().add(instellingTab);
        tabPane.getTabs().add(logTab);
        this.setCenter(tabPane);
    }
}
