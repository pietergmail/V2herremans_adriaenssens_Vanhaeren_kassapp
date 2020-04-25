package view.panels;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.database.DatabaseException;
import model.database.LoadsaveArtikeltekst;

import java.util.ArrayList;


public class ProductOverviewPane extends GridPane {
	//private TableView<Product> table;
	
	
	public ProductOverviewPane() {
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
		this.add(new Label("Products:"), 0, 0, 1, 1);
		LoadsaveArtikeltekst loadsaveArtikeltekst = new LoadsaveArtikeltekst();
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
	}
	
	

}
