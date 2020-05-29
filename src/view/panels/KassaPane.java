package view.panels;


import controller.KassaviewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;
import model.Artikel;
import java.util.ArrayList;


/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class KassaPane extends GridPane {
    private KassaviewController kassaviewController;

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
                kassaviewController.setBetaalPane(kassaviewController);
                restoreonhold.setDisable(true);
                onhold.setDisable(true);
                AFSLUITEN.setDisable(true);
            }
        });
    }

    private void setRestoreonholdButton(){
        restoreonhold.setOnAction(e -> {
            restoreonhold.setDisable(true);
            onhold.setDisable(false);
            setRestoreonhold();
        });
    }

    private void setOnHoldButton(){
        onhold.setOnAction(e -> {
            restoreonhold.setDisable(false);
            onhold.setDisable(true);
            setOnhold();
        });
    }

    public void setAFSLUITENDisabled(boolean bool){
        AFSLUITEN.setDisable(bool);
    }

    public void setRestoreonholdDisabled(boolean bool){
        restoreonhold.setDisable(bool);
    }

    public void setOnholdDisabled(boolean bool){
        onhold.setDisable(bool);
    }



    private void setartikelcode(){
        artikelcode.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                addProduct();
            }
        });
    }

    private void setvoegartikelToe() {
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
        }
        table.refresh();
        artikelcode.clear();
    }

    private void setBox(){
        HBox p2 = new HBox(10);
        Label voegtoe = new Label("Voeg artikelcode in:");
        artikelcode = new TextField();
        voegartikelToe = new Button("voeg toe");
        p2.getChildren().addAll(voegtoe, artikelcode, voegartikelToe);
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
        p3.setPadding(new Insets(10));

        VBox p5 = new VBox(10);
        onhold = new Button("zet on hold");
        restoreonhold = new Button("restore on hold");
        AFSLUITEN = new Button("AFSLUITEN");

        restoreonhold.setDisable(true);
        onhold.setDisable(true);
        AFSLUITEN.setDisable(true);
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
        Label titel = new Label("Kassa:");
        titel.setPadding(new Insets(5,5,0,5));
        titel.setFont(new Font("Arial", 20));

        p1.getChildren().addAll(titel, p2, p4);
        p1.setPadding(new Insets(10));

        this.getChildren().addAll(p1);
    }


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
        TableColumn colBtn = new TableColumn("Verwijder");

        Callback<TableColumn<Artikel, Void>, TableCell<Artikel, Void>> cellFactory = new Callback<TableColumn<Artikel, Void>, TableCell<Artikel, Void>>() {
            @Override
            public TableCell<Artikel, Void> call(final TableColumn<Artikel, Void> param) {
                final TableCell<Artikel, Void> cell = new TableCell<Artikel, Void>() {

                    private final Button btn = new Button("verwijder");

                    {
                        btn.setOnAction((k) -> {
                            kassaviewController.removeProductKassaVerkoop(getIndex());
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
        onhold.setDisable(true);
    }

    private void setRestoreonhold() {
        kassaviewController.setOffHold();
    }


    private void setTabel(){
        TableColumn<Artikel, String> columnOmschrijving = new TableColumn<>("Omschrijving");
        columnOmschrijving.setMinWidth(200);
        columnOmschrijving.setCellValueFactory(new PropertyValueFactory<>("omschrijving"));

        TableColumn<Artikel, Double> columnPrijs = new TableColumn<>("Prijs");
        columnPrijs.setMinWidth(100);
        columnPrijs.setCellValueFactory(new PropertyValueFactory<>("prijs"));

        table.getColumns().addAll(columnOmschrijving, columnPrijs);

        addVerwijderButtonToTable(kassaviewController);
    }
}