package view.panels;

import controller.KlantviewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Artikel;
import model.ArtikelWinkelmand;

import java.util.ArrayList;

public class KlantPane extends GridPane {
    private TableView<ArtikelWinkelmand> table;

    private Label totaleprijs;
    private Label korting;
    private Label betalen;

    public KlantPane(KlantviewController controller){
        controller.setPane(this);
        ObservableList<ArtikelWinkelmand> winkelmand = FXCollections.observableArrayList(new ArrayList<>());
        this.setTable();

        this.setLTotalePrijs();
        this.setLKorting();
        this.setLBetalen();

        table.setItems(winkelmand);

        VBox p1 = new VBox(10);
        p1.getChildren().addAll(table, totaleprijs, korting, betalen);
        VBox.setMargin(table, new Insets(0, 0, 0, 10));
        VBox.setMargin(totaleprijs, new Insets(0, 0, 0, 126));
        VBox.setMargin(korting, new Insets(0, 0, 0, 111));
        VBox.setMargin(betalen, new Insets(0, 0, 0, 62));
        p1.setPadding(new Insets(10));

        this.getChildren().addAll(p1);

    }

    private void setTable(){
        table = new TableView<>();
        table.setRowFactory( t -> new TableRow<>());

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
        totaleprijs = new Label("Totale prijs: ");
        this.add(totaleprijs, 0, 4);
    }

    private void setLBetalen(){
        betalen = new Label("Totale prijs met korting: ");
        this.add(betalen, 0, 4);
    }

    private void setLKorting(){
        korting = new Label("Totale Korting: ");
        this.add(korting, 0, 4);
    }

    public void setWinkelmand(ArrayList<Artikel> winkelmand){
        FXCollections.observableArrayList(ArtikelWinkelmand.ArtikelVoorKlant(winkelmand));
        table.setItems(FXCollections.observableArrayList(ArtikelWinkelmand.ArtikelVoorKlant(winkelmand)));
        table.refresh();
    }

    public void setTotalePrijs(double totalePrijs){this.totaleprijs.setText("Totale prijs: " + totalePrijs);}

    public void setKorting(double korting){this.korting.setText("Totale Korting: " + korting);}

    public void setBetalen(double teBetalen){this.betalen.setText("Totale prijs met korting: " + teBetalen);}
}
