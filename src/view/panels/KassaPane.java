package view.panels;


import controller.KassaviewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import jxl.read.biff.BiffException;
import model.Artikel;
import model.database.DatabaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class KassaPane extends GridPane {
    private KassaviewController kassaviewController;
    private Label voegtoe;

    private ObservableList<Artikel> winkelmandje;
    private TextField artikelcode;
    private Button voegartikelToe;
    private Label totaleprijs;
    private TableView table;
    private Button onhold;
    private Button restoreonhold;
    private Label totalekorting;
    private Label totaleprijskorting;
    private Button AFSLUITEN;

    private Label verwijder;
    private Button verwijderartikel;
    private TextField verwijderartikelcode;
    private BetaalPane betaalPane;
    private Label titel;
    //private Scene scene = new Scene(this);
    //private List<Artikel> winkelmand = new ArrayList<>();
    //private List<Artikel> winkelmandonhold = new ArrayList<>();

    public KassaPane(KassaviewController kassaviewController) {
        kassaviewController.setPane(this);
        this.kassaviewController = kassaviewController;
        this.winkelmandje = FXCollections.observableArrayList(new ArrayList<>());

        this.setBox();
        this.setTabel();
        this.setvoegartikelToe();
        this.setartikelcode();
        this.setOnHoldButton();
        this.setRestoreonholdButton();
        this.setAFSLUITEN();
    }

    private void setAFSLUITEN(){
        AFSLUITEN.setOnAction(e -> {
            if(this.kassaviewController.getWinkelmandje() != null){
                System.out.println("afsluiten");
                betaalPane = new BetaalPane(kassaviewController);
                //moet uitgevoerd worden na dat betaalpane is uitgevoerd=
            }
        });
    }

    private void setRestoreonholdButton(){
        restoreonhold.setOnAction(e -> {
            //System.out.println("restore on hold");
            restoreonhold.setDisable(true);
            setRestoreonhold();
        });
    }

    private void setOnHoldButton(){
        onhold.setOnAction(e -> {
            //System.out.println("onhold");
            restoreonhold.setDisable(false);
            setOnhold();
        });
    }

    private void setartikelcode(){
        //voor met enter event
        artikelcode.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                addProduct();
            }
        });
    }

    private void setvoegartikelToe() {
        //voor met knop event
        voegartikelToe.setOnAction(e -> addProduct());
    }

    private void addProduct() {
        try {
            kassaviewController.addProductKassaVerkoop(artikelcode.getText());
            artikelcode.requestFocus();
            onhold.setDisable(false);
            AFSLUITEN.setDisable(false);
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
            //throw new IllegalArgumentException("fout bij het toevoegen product.");
        }
        table.refresh();
        artikelcode.clear();
    }

    private void setBox(){
        HBox p2 = new HBox(10);
        voegtoe = new Label("Voeg artikelcode in:");
        artikelcode = new TextField();
        voegartikelToe = new Button("voeg toe");
        p2.getChildren().addAll(voegtoe, artikelcode, voegartikelToe);
        //p2.setAlignment(Pos.CENTER);
        p2.setPadding(new Insets(10, 0, 0, 22));

        VBox p3 = new VBox(10);
        table = new TableView<>();
        totaleprijs = new Label("Totale prijs: ");
        totalekorting = new Label("Totale korting: ");
        totaleprijskorting = new Label("Totale Prijs met korting: ");
        VBox.setMargin(totaleprijs, new Insets(0, 0, 0, 126));
        VBox.setMargin(totalekorting, new Insets(0, 0, 0, 111));
        VBox.setMargin(totaleprijskorting, new Insets(0, 0, 0, 62));

        p3.getChildren().addAll(table, totaleprijs, totalekorting, totaleprijskorting);
        //p3.setAlignment(Pos.CENTER);
        p3.setPadding(new Insets(10));
        //table.setItems(producten);

        VBox p5 = new VBox(10);
        onhold = new Button("zet on hold");
        restoreonhold = new Button("restore on hold");
        AFSLUITEN = new Button("AFSLUITEN");
        restoreonhold.setDisable(true);
        onhold.setDisable(true);
        AFSLUITEN.setDisable(true);
        //restoreonhold.setVisible(false);
        onhold.setPrefWidth(100);
        restoreonhold.setPrefWidth(100);
        AFSLUITEN.setPrefWidth(100);
        p5.getChildren().addAll(onhold, restoreonhold, AFSLUITEN);
        p5.setAlignment(Pos.CENTER);
        p5.setPadding(new Insets(10));

        HBox p4 = new HBox(10);
        p4.getChildren().addAll(p3, p5);
        p4.setAlignment(Pos.CENTER);
        p4.setPadding(new Insets(10));

        VBox p1 = new VBox(10);
        titel = new Label("Kassa:");
        titel.setPadding(new Insets(5,5,0,5));
        titel.setFont(new Font("Arial", 20));

        p1.getChildren().addAll(titel, p2, p4);
        //p1.setAlignment(Pos.CENTER);
        p1.setPadding(new Insets(10));

        this.getChildren().addAll(p1);
    }


    //onodig, zit in de controller
    /*public void voegProductToe(KassaviewController kassaviewController) throws DatabaseException {
        try {
            kassaviewController.addProductKassaVerkoop(kassaviewController.getArtikel(artikelcode.getText()));
            table.getItems().clear();
            //winkelmand.add(kassaviewController.getArtikel(artikelcode.getText()));
            //table.getItems().addAll(winkelmand);
            table.getItems().addAll(kassaviewController.getWinkelmandje());
            updateTotaalPrijs(kassaviewController);
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }*/


    public void setWinkelmandje(ArrayList<Artikel> winkelmandje){
        this.winkelmandje = FXCollections.observableArrayList(winkelmandje);
        table.setItems(this.winkelmandje);
    }

    public void updateTotaalPrijs(double totaalprijs) {
        totaleprijs.setText("Totale prijs: " + totaalprijs);
    }

    public void updateTotaalPrijsKorting(double prijs){
        totaleprijskorting.setText("Totale prijs met korting: " + prijs);
    }

    public void updateTotaalKorting(double prijs){
        totalekorting.setText("Totale korting: " + prijs);
    }

    private void addVerwijderButtonToTable(KassaviewController kassaviewController) {
        TableColumn<Artikel, Void> colBtn = new TableColumn("Verwijder");

        Callback<TableColumn<Artikel, Void>, TableCell<Artikel, Void>> cellFactory = new Callback<TableColumn<Artikel, Void>, TableCell<Artikel, Void>>() {
            @Override
            public TableCell<Artikel, Void> call(final TableColumn<Artikel, Void> param) {
                final TableCell<Artikel, Void> cell = new TableCell<Artikel, Void>() {

                    private final Button btn = new Button("verwijder");

                    {
                        btn.setOnAction((k) -> {
                            kassaviewController.removeProductKassaVerkoop(getIndex());
                            //extra code om de tabel te refreshen

                            /*table.getItems().clear();
                            table.getItems().addAll(kassaviewController.getWinkelmandje());
                            //table.getItems().clear();
                            //winkelmand.remove(getTableView().getItems().get(getIndex()));
                            //table.getItems().addAll(winkelmand);
                            //table.getItems().addAll(kassaviewController.getWinkelmandje());
                            //
                            //table.getItems().remove(getIndex());
                            //winkelmand = new ArrayList<>(table.getItems());
                            //table.getItems().clear();
                            //table.getItems().addAll(winkelmand);
                            updateTotaalPrijs(kassaviewController);*/
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setMinWidth(50);
        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);

    }

    private void setOnhold() {
        kassaviewController.setOnHold();
        /*
        kassaviewController.setOnHold();
        table.getItems().clear();
        table.getItems().addAll(kassaviewController.getWinkelmandje());
        updateTotaalPrijs(kassaviewController);
        //winkelmandonhold.clear();
        //winkelmandonhold.addAll(winkelmand);
        //winkelmand.clear();
        //totaleprijs.setText("totaal: ");
        //System.out.println(winkelmandonhold);*/
    }

    private void setRestoreonhold() {
        kassaviewController.setOffHold();
        /*
        kassaviewController.setOffHold();
        table.getItems().clear();
        table.getItems().addAll(kassaviewController.getWinkelmandje());
        //winkelmand.clear();
        //winkelmand.addAll(winkelmandonhold);
        //table.getItems().addAll(winkelmand);
        updateTotaalPrijs(kassaviewController);*/
    }

    //Unnecesary in controller
    /*
    public void update(){
        table.getItems().clear();
        table.getItems().addAll(kassaviewController.getWinkelmandje());
        updateTotaalPrijs(kassaviewController);
    }
    */


    public void setTabel(){
        TableColumn<Artikel, String> columnOmschrijving = new TableColumn<>("Omschrijving");
        columnOmschrijving.setMinWidth(200);
        columnOmschrijving.setCellValueFactory(new PropertyValueFactory<Artikel, String>("omschrijving"));

        TableColumn<Artikel, Double> columnPrijs = new TableColumn<>("Prijs");
        columnPrijs.setMinWidth(100);
        columnPrijs.setCellValueFactory(new PropertyValueFactory<Artikel, Double>("prijs"));

        table.getColumns().addAll(columnOmschrijving, columnPrijs);

        addVerwijderButtonToTable(kassaviewController);
    }
}