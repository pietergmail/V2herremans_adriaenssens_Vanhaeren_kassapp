package view;

import controller.KassaviewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Artikel;
import model.ArtikelWinkelmand;
import model.KassaVerkoop;
import model.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KlantView implements Observer {
	private Stage stage = new Stage();
	//KassaVerkoop kassaVerkoop = new KassaVerkoop();
	KassaviewController kassaviewController;
	TableView table = new TableView();
	ArrayList<ArtikelWinkelmand> winkelmand;
	Label totaleprijs;

		
	public KlantView(KassaviewController kassaviewController){
		winkelmand = new ArrayList<>();
		//this.kassaVerkoop = kassaVerkoop;
		this.kassaviewController = kassaviewController;
		stage.setTitle("KLANT VIEW");
		stage.setResizable(false);		
		stage.setX(775);
		stage.setY(20);
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500);			
		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();





		TableColumn<Artikel, String> columnOmschrijving = new TableColumn<>("Omschrijving");
		columnOmschrijving.setMinWidth(200);
		columnOmschrijving.setCellValueFactory(new PropertyValueFactory<Artikel, String>("omschrijving"));

		TableColumn<Artikel, Double> columnPrijs = new TableColumn<>("Prijs");
		columnPrijs.setMinWidth(100);
		columnPrijs.setCellValueFactory(new PropertyValueFactory<Artikel, Double>("prijs"));


        TableColumn<Artikel, Integer> columnAantal = new TableColumn<>("Aantal");
        columnAantal.setMinWidth(50);
        columnAantal.setCellValueFactory(new PropertyValueFactory<Artikel, Integer>("aantal"));


		table.getColumns().addAll(columnOmschrijving, columnPrijs, columnAantal);
		VBox p1 = new VBox();



		VBox p2 = new VBox(10);
		totaleprijs = new Label("Totale prijs:");
		p2.getChildren().addAll(totaleprijs);
		p2.setAlignment(Pos.CENTER);
		p2.setPadding(new Insets(10));

		p1.getChildren().addAll(table, p2);
		p1.setPadding(new Insets(10));
		root.getChildren().addAll(p1);





	}


	@Override
	public void update(String eventType, Artikel artikel) {
		//table = new TableView();
		table.getItems().removeAll(this.winkelmand);
		winkelmand = new ArrayList<>();
		winkelmand = kassaviewController.getWinkelmandMetAantal();
		table.getItems().addAll(winkelmand);
		//System.out.println("test");
		updateTotaalPrijs(kassaviewController);
	}

	@Override
	public void update(String eventype) {
		table.getItems().removeAll(this.winkelmand);
		winkelmand = new ArrayList<>();
		winkelmand = kassaviewController.getWinkelmandMetAantal();
		table.getItems().addAll(winkelmand);
		totaleprijs.setText("totaal: ");
	}

	public void updateTotaalPrijs(KassaviewController kassaviewController){
		double prijs = kassaviewController.totaalPrijs();
		totaleprijs.setText("totaal: " + prijs);
	}
}
