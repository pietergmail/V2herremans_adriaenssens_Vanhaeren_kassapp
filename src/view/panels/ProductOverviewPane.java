package view.panels;

import controller.KassaviewController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import jxl.read.biff.BiffException;
import model.Artikel;
import model.database.DatabaseException;
import java.io.IOException;

/**
 * @author Vanhaeren Corentin
 */

public class ProductOverviewPane extends GridPane {
	private TableView<Artikel> table;
	private KassaviewController controller;


	public ProductOverviewPane(KassaviewController controller) throws DatabaseException, IOException, BiffException {

		this.controller = controller;
		controller.setProductPane(this);
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        Label label1 = new Label("Producten:");
        label1.setFont(new Font("Arial", 20));

        this.setTable();

		table.setItems(FXCollections.observableArrayList(controller.loadData()));
        this.getChildren().addAll(label1, table);
		}

		private void setTable(){
			table = new TableView<>();
			table.setRowFactory( t -> new TableRow<>());

			TableColumn<Artikel, String> columnCode = new TableColumn<>("Code");
			columnCode.setMinWidth(50);
			columnCode.setCellValueFactory(new PropertyValueFactory<Artikel, String>("code"));

			TableColumn<Artikel, String> columnOmschrijving = new TableColumn<>("Omschrijving");
			columnOmschrijving.setMinWidth(150);
			columnOmschrijving.setCellValueFactory(new PropertyValueFactory<Artikel, String>("omschrijving"));

			TableColumn<Artikel, String> columnGroep = new TableColumn<>("Groep");
			columnGroep.setMinWidth(100);
			columnGroep.setCellValueFactory(new PropertyValueFactory<Artikel, String>("groep"));

			TableColumn<Artikel, Double> columnPrijs = new TableColumn<>("Prijs");
			columnPrijs.setMinWidth(100);
			columnPrijs.setCellValueFactory(new PropertyValueFactory<Artikel, Double>("prijs"));

			TableColumn<Artikel, Integer> columnVoorraad = new TableColumn<>("Voorraad");
			columnVoorraad.setMinWidth(100);
			columnVoorraad.setCellValueFactory(new PropertyValueFactory<Artikel, Integer>("Voorraad"));

			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			table.getColumns().addAll(columnCode, columnOmschrijving, columnGroep, columnPrijs, columnVoorraad);
		}

		public void updateProducts() throws DatabaseException, IOException, BiffException {
			table.setItems(FXCollections.observableArrayList(controller.loadData()));
			table.refresh();
	}
}