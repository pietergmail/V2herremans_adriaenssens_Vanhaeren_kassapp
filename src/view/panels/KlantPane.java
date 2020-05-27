package view.panels;

import controller.KlantviewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Artikel;
import model.ArtikelWinkelmand;
import model.Observable;

import java.awt.*;
import java.util.ArrayList;

public class KlantPane extends GridPane {
    private TableView<ArtikelWinkelmand> table;
    private ObservableList<ArtikelWinkelmand> winkelmand;

    private Label totaleprijs;
    private Label korting;
    private Label betalen;

    public KlantPane(KlantviewController controller){
        controller.setPane(this);
        this.winkelmand = FXCollections.observableArrayList(new ArrayList<>());
        this.setTable();

        //Setting labels
        this.setLTotalePrijs();
        this.setLKorting();
        this.setLBetalen();

        table.setItems(this.winkelmand);

        VBox p1 = new VBox(10);
        p1.getChildren().addAll(table, totaleprijs, korting, betalen);
        //p1.setAlignment(Pos.CENTER);
        VBox.setMargin(table, new Insets(0, 0, 0, 10));
        VBox.setMargin(totaleprijs, new Insets(0, 0, 0, 150));
        VBox.setMargin(korting, new Insets(0, 0, 0, 145));
        VBox.setMargin(betalen, new Insets(0, 0, 0, 60));
        p1.setPadding(new Insets(10));

        this.getChildren().addAll(p1);

    }

    private void setTable(){
        table = new TableView<>();
        table.setRowFactory( t -> {
            TableRow<ArtikelWinkelmand> row = new TableRow<>();
            return row;
        });

        TableColumn<ArtikelWinkelmand, String> columnOmschrijving = new TableColumn<>("Omschrijving");
        columnOmschrijving.setMinWidth(200);
        columnOmschrijving.setCellValueFactory(new PropertyValueFactory<>("Omschrijving"));

        TableColumn<ArtikelWinkelmand, Double> columnPrijs = new TableColumn<>("Prijs");
        columnPrijs.setMinWidth(100);
        columnPrijs.setCellValueFactory(new PropertyValueFactory<>("Prijs"));

        TableColumn<ArtikelWinkelmand, Integer> columnAantal = new TableColumn<>("Aantal");
        columnAantal.setMinWidth(75);
        columnAantal.setCellValueFactory(new PropertyValueFactory<>("aantal"));

        table.getColumns().addAll(columnOmschrijving,columnPrijs,columnAantal);
    }

    private void setLTotalePrijs() {
        totaleprijs = new Label("Totaal: ");
        this.add(totaleprijs, 0, 3);
    }

    private void setLBetalen(){
        betalen = new Label("Totale prijs met korting: ");
        this.add(betalen, 0, 4);
    }

    private void setLKorting(){
        korting = new Label("Korting: ");
        this.add(korting, 0, 5);
    }

    public void setWinkelmand(ArrayList<Artikel> winkelmand){
        ObservableList test = FXCollections.observableArrayList(ArtikelWinkelmand.ArtikelVoorKlant(winkelmand));
        table.setItems(FXCollections.observableArrayList(ArtikelWinkelmand.ArtikelVoorKlant(winkelmand)));
        table.refresh();
    }

    public void SetTotalePrijs(double totalePrijs){this.totaleprijs.setText("Totaal: " + totalePrijs);}

    public void setKorting(double korting){this.korting.setText("Korting: " + korting);}

    public void setBetalen(double teBetalen){this.korting.setText("Te Betalen: " + teBetalen);}

}
