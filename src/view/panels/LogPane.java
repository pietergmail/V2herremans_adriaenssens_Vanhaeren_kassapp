package view.panels;

import controller.KassaviewController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.ArtikelWinkelmand;
import model.log.Log;

import java.time.LocalDate;
import java.time.LocalTime;

public class LogPane extends GridPane {

    private TableView<Log> table;
    private KassaviewController kassaviewController;
    public LogPane(KassaviewController kassaviewController) {
        this.kassaviewController = kassaviewController;
        kassaviewController.setLogPane(this);
        VBox p1 = new VBox(10);
        Label label1 = new Label("Log:");
        label1.setPadding(new Insets(10));
        label1.setFont(new Font("Arial", 20));
        p1.getChildren().addAll(label1);
        this.setTable();


        this.getChildren().addAll(p1);
    }

    private void setTable(){
        table = new TableView<>();
        table.setRowFactory( t -> {
            TableRow<Log> row = new TableRow<>();
            return row;
        });

        TableColumn<Log, LocalDate> columnDatum = new TableColumn<>("Datum");
        columnDatum.setMinWidth(200);
        columnDatum.setCellValueFactory(new PropertyValueFactory<>("Datum"));

        TableColumn<Log, LocalTime> columnTijd = new TableColumn<>("Tijd");
        columnTijd.setMinWidth(200);
        columnTijd.setCellValueFactory(new PropertyValueFactory<>("Tijd"));


        TableColumn<Log, Double> columnTotaalBedrag = new TableColumn<>("Totaal");
        columnTotaalBedrag.setMinWidth(200);
        columnTotaalBedrag.setCellValueFactory(new PropertyValueFactory<>("Totaal"));

        TableColumn<Log, Double> columnTotaleKorting = new TableColumn<>("Korting");
        columnTotaleKorting.setMinWidth(200);
        columnTotaleKorting.setCellValueFactory(new PropertyValueFactory<>("Korting"));

        TableColumn<Log, Double> columnTotaalTeBetalen = new TableColumn<>("Te Betalen");
        columnTotaalTeBetalen.setMinWidth(200);
        columnTotaalTeBetalen.setCellValueFactory(new PropertyValueFactory<>("Te Betalen"));

        table.getColumns().addAll(columnDatum, columnTijd, columnTotaalBedrag, columnTotaleKorting, columnTotaalTeBetalen);
    }



    public void updateLogTable(){
        table.setItems(FXCollections.observableArrayList(kassaviewController.getLogs()));
        table.refresh();
    }

}
