package view;

import controller.KassaviewController;
import controller.KlantviewController;
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
	//KassaviewController kassaviewController;
	KlantviewController klantviewController;
	TableView table = new TableView();
	//ArrayList<ArtikelWinkelmand> winkelmand = new ArrayList<>();
	//ArrayList<ArtikelWinkelmand> winkelmandonhold = new ArrayList<>();
	Label totaleprijs;
	Label totalekorting;
	Label totaleprijskorting;


	public KlantView(KlantviewController klantviewController){
		//winkelmand = new ArrayList<>();
		//winkelmandOnHold = new ArrayList<>();
		//this.kassaVerkoop = kassaVerkoop;
		//this.kassaviewController = kassaviewController;
		this.klantviewController = klantviewController;
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
		totalekorting = new Label("Totale korting:");
		totaleprijskorting = new Label("Totale prijs met korting");
		p2.getChildren().addAll(totaleprijs, totalekorting, totaleprijskorting);
		p2.setAlignment(Pos.CENTER);
		p2.setPadding(new Insets(10));

		p1.getChildren().addAll(table, p2);
		p1.setPadding(new Insets(10));
		root.getChildren().addAll(p1);

	}

	@Override
	public void update(String eventType, Artikel artikel) {

		if(eventType.equals("add_product_winkelkar")){
			addProduct(klantviewController);
			//updateTotaalPrijs(klantviewController);
			//System.out.println("test");
		}
		if(eventType.equals("remove_product_winkelkar")){
			removeProduct(klantviewController);
			//updateTotaalPrijs(klantviewController);
		}
		if(eventType.equals("setOnHold")){
			setOnhold(klantviewController);
		}
		if(eventType.equals("setOffHold")){
			setRestoreonhold(klantviewController);
			//updateTotaalPrijs(klantviewController);
		}


/*
		table.getItems().clear();
		table.getItems().addAll(klantviewController.getWinkelmandje());
		//System.out.println(klantviewController.getWinkelmandje() + " winkelmand");
		updateTotaalPrijs(klantviewController);

 */
	}

	public void updateTotaalPrijs(KlantviewController klantviewController){
		double prijs = klantviewController.totaalPrijs();
		totaleprijs.setText("Totale prijs: " + prijs);
		updateTotaalKorting(klantviewController);
		updateTotaalPrijsKorting(klantviewController);
	}

	public void updateTotaalKorting(KlantviewController klantviewController){
		double prijs = klantviewController.totaleKorting();
		totalekorting.setText("Totale korting: " + prijs);
	}

	public void updateTotaalPrijsKorting(KlantviewController kassaviewController){
		double prijs = klantviewController.totalePrijsMetKorting();
		totaleprijskorting.setText("Totale prijs met korting: " + prijs);
	}

	public void addProduct(KlantviewController klantviewController){
		table.getItems().clear();
		table.getItems().addAll(klantviewController.getWinkelmandje());
		updateTotaalPrijs(klantviewController);
	}

	public void removeProduct(KlantviewController klantviewController){
		table.getItems().clear();
		table.getItems().addAll(klantviewController.getWinkelmandje());
		updateTotaalPrijs(klantviewController);
	}


	private void setOnhold(KlantviewController klantviewController) {
		table.getItems().clear();
		table.getItems().addAll(klantviewController.getWinkelmandje());
		updateTotaalPrijs(klantviewController);
	}

	private void setRestoreonhold(KlantviewController klantviewController) {
		table.getItems().clear();
		table.getItems().addAll(klantviewController.getWinkelmandje());
		updateTotaalPrijs(klantviewController);
	}

/*
	@Override
	public void update(String eventType, Artikel artikel) {
		if(eventType.equals("add_product_winkelkar")){
			addProduct(kassaviewController, artikel);
			updateTotaalPrijs(kassaviewController);
			//System.out.println("test");
		}
		if(eventType.equals("remove_product_winkelkar")){
			removeProduct(kassaviewController, artikel);
			updateTotaalPrijs(kassaviewController);
		}
		if(eventType.equals("setOnHold")){
			setOnhold(kassaviewController);
		}
		if(eventType.equals("setOffHold")){
			setRestoreonhold(kassaviewController);
			updateTotaalPrijs(kassaviewController);
		}

		//table = new TableView();
		//table.getItems().removeAll(this.winkelmand);
		//winkelmand = new ArrayList<>();
		//winkelmand = kassaviewController.getWinkelmandMetAantal();
		//table.getItems().addAll(winkelmand);
		//System.out.println("test");
		//updateTotaalPrijs(kassaviewController);
	}


	public void updateTotaalPrijs(KassaviewController kassaviewController){
		double prijs = kassaviewController.totaalPrijs();
		totaleprijs.setText("totaal: " + prijs);
	}

	public void addProduct(KassaviewController kassaviewController, Artikel artikel){
		table.getItems().clear();
		if (artikelAlreadyAdded(artikel)){
			for (ArtikelWinkelmand a: winkelmand) {
				if (a.getCode().equals(artikel.getCode())){
					a.setAantal(a.getAantal()+1);
				}
			}
			table.getItems().addAll(winkelmand);
		}
		else {
			ArtikelWinkelmand artikelWinkelmand = new ArtikelWinkelmand();
			artikelWinkelmand.setCode(artikel.getCode());
			artikelWinkelmand.setOmschrijving(artikel.getOmschrijving());
			artikelWinkelmand.setGroep(artikel.getGroep());
			artikelWinkelmand.setPrijs(artikel.getPrijs());
			artikelWinkelmand.setAantal(1);
			winkelmand.add(artikelWinkelmand);
			table.getItems().addAll(winkelmand);
		}
	}

	public void removeProduct(KassaviewController kassaviewController, Artikel artikel){
		table.getItems().clear();
		for (int i = 0; i < winkelmand.size(); i++) {
		    ArtikelWinkelmand a = winkelmand.get(i);
			if (a.getCode().equals(artikel.getCode())){
				//System.out.println("test");
				if(a.getAantal() > 1){
				    int aantal = a.getAantal();
				    aantal-=1;
					a.setAantal(aantal);
                    table.getItems().addAll(winkelmand);
				}
				else {
					//winkelmand.remove(a);
                    winkelmand.remove(i);
                    table.getItems().addAll(winkelmand);
				}
			}
		}

		//table.getItems().addAll(winkelmand);

	}


	private void setOnhold(KassaviewController kassaviewController) {
		//kassaviewController.setOnHold();
		table.getItems().clear();
		//table.getItems().addAll(kassaviewController.getWinkelmandje());
		winkelmandonhold.clear();
		winkelmandonhold.addAll(winkelmand);
		winkelmand.clear();
		totaleprijs.setText("totaal: ");
	}

	private void setRestoreonhold(KassaviewController kassaviewController) {
		//kassaviewController.setOffHold();
		table.getItems().clear();
		//table.getItems().addAll(kassaviewController.getWinkelmandje());
		winkelmand.clear();
		winkelmand.addAll(winkelmandonhold);
		table.getItems().addAll(winkelmand);

	}

	public boolean artikelAlreadyAdded(Artikel artikel){
		boolean containsArtikel = false;
		for (ArtikelWinkelmand a: winkelmand) {
			if (a.getCode().equals(artikel.getCode())){
				containsArtikel = true;
			}
		}
		//System.out.println(containsArtikel + " bevat?");
		return containsArtikel;
	}
 */
}