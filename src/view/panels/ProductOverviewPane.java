package view.panels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import jxl.read.biff.BiffException;
import model.Artikel;
import model.database.DatabaseException;
import model.database.LoadSaveContext;
import model.database.LoadsaveArtikeltekst;
import model.database.StrategyLoadSave;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.input.KeyCode.O;

/**
 * @author Vanhaeren Corentin
 */

public class ProductOverviewPane extends GridPane {
	private TableView<Artikel> table;
	private LoadSaveContext loadSaveContext = new LoadSaveContext();

	public ProductOverviewPane() throws DatabaseException, IOException, BiffException {


		ObservableList<Artikel> producten = FXCollections.observableArrayList(new ArrayList<>(loadSaveContext.load()));
		VBox p1 = new VBox(5);
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        Label label1 = new Label("Producten:");
        label1.setPadding(new Insets(5,5,5,5));
        label1.setFont(new Font("Arial", 20));

		//this.add(new Label("Producten:"), 0, 0, 1, 1);

		table = new TableView<Artikel>();
		table.setItems(producten);

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
		columnVoorraad.setMinWidth(50);
		columnVoorraad.setCellValueFactory(new PropertyValueFactory<Artikel, Integer>("voorraad"));
		table.getColumns().addAll(columnCode, columnOmschrijving, columnGroep, columnPrijs, columnVoorraad);


		/*table.setRowFactory(rij-> {
			TableRow<Artikel> row = new TableRow<>();
			//Artikel artikel = row.getItem();
			return row;
		});

		 */
		p1.getChildren().addAll(label1, table);
		this.getChildren().addAll(p1);



		//System.out.println(producten);
		/*LoadsaveArtikeltekst loadsaveArtikeltekst = new LoadsaveArtikeltekst();
		try {
			ArrayList<Object> list = loadsaveArtikeltekst.load();
			int col = 1;
			for (Object o: list) {
				this.add(new Label(o.toString()), 0, col, 1, 1);
				col ++;
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println("Database Not Found ERROR.");
			this.add(new Label("Database Not Found ERROR."), 0, 0, 30, 1);

		}

		 */
	}
	
	

}
