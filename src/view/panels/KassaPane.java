package view.panels;


import controller.KassaviewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jxl.read.biff.BiffException;
import model.Artikel;
import model.database.DatabaseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class KassaPane extends GridPane {
    private KassaviewController kassaviewController;
    private Label voegtoe;
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
    //private Scene scene = new Scene(this);
    //private List<Artikel> winkelmand = new ArrayList<>();
    //private List<Artikel> winkelmandonhold = new ArrayList<>();

    public KassaPane(KassaviewController kassaviewController) {
        this.kassaviewController = kassaviewController;
        HBox p2 = new HBox(10);
        voegtoe = new Label("voeg artikelcode in:");
        artikelcode = new TextField();
        voegartikelToe = new Button("voeg toe");
        p2.getChildren().addAll(voegtoe, artikelcode, voegartikelToe);
        //p2.setAlignment(Pos.CENTER);
        p2.setPadding(new Insets(10));

        /*
        HBox p4 = new HBox(20);
        verwijder = new Label("voeg artikelcode in:");
        verwijderartikelcode = new TextField();
        verwijderartikel = new Button("Verwijder");
        p2.getChildren().addAll(verwijder, verwijderartikelcode, verwijderartikel);
        p4.setAlignment(Pos.CENTER);
        p4.setPadding(new Insets(10));

         */
        VBox p3 = new VBox(10);
        table = new TableView<>();
        totaleprijs = new Label("Totale prijs:");
        totalekorting = new Label("Totale korting:");
        totaleprijskorting = new Label("Totale Prijs met korting");
        p3.getChildren().addAll(table, totaleprijs, totalekorting, totaleprijskorting);
        p3.setAlignment(Pos.CENTER);
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


        TableColumn<Artikel, String> columnOmschrijving = new TableColumn<>("Omschrijving");
        columnOmschrijving.setMinWidth(200);
        columnOmschrijving.setCellValueFactory(new PropertyValueFactory<Artikel, String>("omschrijving"));

        TableColumn<Artikel, Double> columnPrijs = new TableColumn<>("Prijs");
        columnPrijs.setMinWidth(100);
        columnPrijs.setCellValueFactory(new PropertyValueFactory<Artikel, Double>("prijs"));

        table.getColumns().addAll(columnOmschrijving, columnPrijs);

        addVerwijderButtonToTable(kassaviewController);
        /*
        TableColumn<Artikel, Integer> columnAantal = new TableColumn<>("Aantal");
        columnAantal.setMinWidth(50);
        columnAantal.setCellValueFactory(new PropertyValueFactory<Artikel, >("Aantal"));

         */

        //table.getColumns().addAll(columnOmschrijving, columnPrijs, columnAantal);


        VBox p1 = new VBox(10);
        p1.getChildren().addAll(p2, p4);
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
                onhold.setDisable(false);
                AFSLUITEN.setDisable(false);
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
                    onhold.setDisable(false);
                    AFSLUITEN.setDisable(false);
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
                    onhold.setDisable(false);
                    AFSLUITEN.setDisable(false);
                } catch (DatabaseException databaseException) {
                    databaseException.printStackTrace();
                }
            }
        });

        onhold.setOnAction(e -> {
            //System.out.println("onhold");
            restoreonhold.setDisable(false);
            setOnhold(kassaviewController);
        });

        restoreonhold.setOnAction(e -> {
            //System.out.println("restore on hold");
            restoreonhold.setDisable(true);
            setRestoreonhold(kassaviewController);
        });

        AFSLUITEN.setOnAction(e -> {
            setAFSLUITEN();
        });

/*
        verwijderartikel.setOnAction(e -> {
            try {
                verwijderProduct(kassaviewController);
                artikelcode.clear();
                artikelcode.requestFocus();
            } catch (DatabaseException databaseException) {
                databaseException.printStackTrace();
            }
        });

 */


    }



    public void voegProductToe(KassaviewController kassaviewController) throws DatabaseException {
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
    }

/*
    public void verwijderProduct(KassaviewController kassaviewController) throws DatabaseException{
        try {
            kassaviewController.removeProductKassaVerkoop(kassaviewController.getArtikel(verwijderartikelcode.getText()));
            table.getItems().clear();
            winkelmand.remove(kassaviewController.getArtikel(verwijderartikelcode.getText()));
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
*/

    public void updateTotaalPrijs(KassaviewController kassaviewController) {
        double prijs = kassaviewController.totaalPrijs();
        totaleprijs.setText("Totale prijs: " + prijs);
        updateTotaalKorting(kassaviewController);
        updateTotaalPrijsKorting(kassaviewController);
    }

    public void updateTotaalPrijsKorting(KassaviewController kassaviewController){
        double prijs = kassaviewController.totalePrijsMetKorting();
        totaleprijskorting.setText("Totale prijs met korting: " + prijs);
    }


    public void updateTotaalKorting(KassaviewController kassaviewController){
        double prijs = kassaviewController.Kortingprijs();
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
                            kassaviewController.removeProductKassaVerkoop(getTableView().getItems().get(getIndex()));
                            table.getItems().clear();
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
                            updateTotaalPrijs(kassaviewController);
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

    private void setOnhold(KassaviewController kassaviewController) {
        kassaviewController.setOnHold();
        table.getItems().clear();
        table.getItems().addAll(kassaviewController.getWinkelmandje());
        updateTotaalPrijs(kassaviewController);
        //winkelmandonhold.clear();
        //winkelmandonhold.addAll(winkelmand);
        //winkelmand.clear();
        //totaleprijs.setText("totaal: ");
        //System.out.println(winkelmandonhold);
    }

    private void setRestoreonhold(KassaviewController kassaviewController) {
        kassaviewController.setOffHold();
        table.getItems().clear();
        table.getItems().addAll(kassaviewController.getWinkelmandje());
        //winkelmand.clear();
        //winkelmand.addAll(winkelmandonhold);
        //table.getItems().addAll(winkelmand);
        updateTotaalPrijs(kassaviewController);
    }

    private void setAFSLUITEN(){
        artikelcode.setDisable(true);
        voegartikelToe.setDisable(true);
        onhold.setDisable(true);
        restoreonhold.setDisable(true);
        AFSLUITEN.setText("BETALEN");
    }



}
