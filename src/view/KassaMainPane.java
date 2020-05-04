package view;


import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.database.DatabaseException;
import view.panels.InstellingenPane;
import view.panels.ProductOverviewPane;

public class KassaMainPane extends BorderPane {
	public KassaMainPane() throws DatabaseException {
	    TabPane tabPane = new TabPane(); 	    
        Tab kassaTab = new Tab("Kassa");
        ProductOverviewPane productOverviewPane = new ProductOverviewPane();
        InstellingenPane instellingenPane = new InstellingenPane();
        Tab artikelTab = new Tab("Artikelen",productOverviewPane);
        Tab instellingTab = new Tab("Instellingen",instellingenPane);
        Tab logTab = new Tab("Log");
        tabPane.getTabs().add(kassaTab);
        tabPane.getTabs().add(artikelTab);
        tabPane.getTabs().add(instellingTab);
        tabPane.getTabs().add(logTab);
	    this.setCenter(tabPane);
	}
}
