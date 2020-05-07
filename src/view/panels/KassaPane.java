package view.panels;


import controller.KassaviewController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jxl.read.biff.BiffException;
import model.Artikel;
import model.database.DatabaseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Vanhaeren Corentin
 */

public class KassaPane extends GridPane {
    private Label voegtoe;
    private TextField artikelcode;
    private Button voegartikelToe;
    private Label totaleprijs;
    private TableView table;
    //private Scene scene = new Scene(this);
    private List<Artikel> winkelmand = new ArrayList<>();

    public KassaPane(KassaviewController kassaviewController){
        HBox p2 = new HBox(10);
        voegtoe = new Label("voeg artikelcode in:");
        artikelcode = new TextField();
        voegartikelToe = new Button("voeg toe");
        p2.getChildren().addAll(voegtoe, artikelcode, voegartikelToe);
        p2.setAlignment(Pos.CENTER);
        p2.setPadding(new Insets(10));

        VBox p3 = new VBox(10);
        table = new TableView<Artikel>();
        totaleprijs = new Label("Totale prijs:");
        p3.getChildren().addAll(table, totaleprijs);
        p3.setAlignment(Pos.CENTER);
        p3.setPadding(new Insets(10));
        //table.setItems(producten);


        TableColumn<Artikel, String> columnOmschrijving = new TableColumn<>("Omschrijving");
        columnOmschrijving.setMinWidth(200);
        columnOmschrijving.setCellValueFactory(new PropertyValueFactory<Artikel, String>("omschrijving"));

        TableColumn<Artikel, Double> columnPrijs = new TableColumn<>("Prijs");
        columnPrijs.setMinWidth(100);
        columnPrijs.setCellValueFactory(new PropertyValueFactory<Artikel, Double>("prijs"));

        /*
        TableColumn<Artikel, Integer> columnAantal = new TableColumn<>("Aantal");
        columnAantal.setMinWidth(50);
        columnAantal.setCellValueFactory(new PropertyValueFactory<Artikel, >("Aantal"));

         */

        //table.getColumns().addAll(columnOmschrijving, columnPrijs, columnAantal);
        table.getColumns().addAll(columnOmschrijving, columnPrijs);

        VBox p1 = new VBox(10);
        p1.getChildren().addAll(p2, p3);
        p1.setAlignment(Pos.CENTER);
        p1.setPadding(new Insets(10));

        this.getChildren().addAll(p1);

        /*
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                artikelcode.requestFocus();
            }
        });
         */

        voegartikelToe.setOnAction(e -> {
            try {
                voegProductToe(kassaviewController);
                artikelcode.clear();
                artikelcode.requestFocus();
            } catch (DatabaseException databaseException) {
                databaseException.printStackTrace();
            }
        });

        voegartikelToe.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    voegProductToe(kassaviewController);
                    artikelcode.clear();
                    artikelcode.requestFocus();
                } catch (DatabaseException databaseException) {
                    databaseException.printStackTrace();
                }
            }
        });

        artikelcode.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    voegProductToe(kassaviewController);
                    artikelcode.clear();
                    artikelcode.requestFocus();
                } catch (DatabaseException databaseException) {
                    databaseException.printStackTrace();
                }
            }
        });



    }

    public void voegProductToe(KassaviewController kassaviewController) throws DatabaseException{
        try {
            kassaviewController.addProductKassaVerkoop(kassaviewController.getArtikel(artikelcode.getText()));
            table.getItems().clear();
            winkelmand.add(kassaviewController.getArtikel(artikelcode.getText()));
            table.getItems().addAll(winkelmand);
            //table.getItems().addAll(kassaviewController.getWinkelmandje());
            updateTotaalPrijs(kassaviewController);
        }catch (IllegalArgumentException e){
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public void updateTotaalPrijs(KassaviewController kassaviewController){
        double prijs = kassaviewController.totaalPrijs();
        totaleprijs.setText("totaal: " + prijs);
    }



    public void update(KassaviewController kassaviewController){
        //table.refresh();
    }
}
