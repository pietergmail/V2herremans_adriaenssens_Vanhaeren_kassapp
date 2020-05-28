package view.panels;



import controller.KassaviewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.database.DatabaseException;
import model.database.DomainException;
import model.kassabon.*;

import javax.swing.*;
import java.sql.SQLOutput;

class BetaalPane extends GridPane {

    private Stage stage = new Stage();
    private KassaPane kassaPane;
    private KassaviewController controller;
    private TextField textfield;
    private Component kassabon;

    BetaalPane(KassaviewController controller){
        this.controller = controller;
        stage.setTitle("BETALEN");
        this.setPrefHeight(200);
        this.setPrefWidth(400);
        this.setVgap(5);
        this.setHgap(5);

        this.Buttons();
        this.Textfield();
        this.Labels();

        Scene scene = new Scene(this);
        stage.setScene(scene);
        stage.show();
    }

    private void Labels() {
        Label tekst = new Label("Bedrag: " + controller.totalePrijsMetKorting());
        this.add(tekst, 1, 0, 1, 1);

        Label eindprijs = new Label();
        this.add(eindprijs, 2, 0, 1, 1);

        Label prijszonderkorting = new Label();
        this.add(prijszonderkorting, 3, 0, 1, 1);

        Label korting = new Label();
        this.add(korting, 4, 0, 1, 1);
    }


    private void Buttons() {
        Button betaal = new Button("Betaal");
        betaal.setOnAction(new BetaalHandler());
        betaal.isDefaultButton();
        this.add(betaal, 1, 3, 1, 1);

        Button cancel = new Button("Cancel");
        cancel.setOnAction(new CancelHandler());
        this.add(cancel, 2, 3, 1, 1);

        Button annuleer = new Button("Annuleer");
        annuleer.setOnAction(new AnnuleerHandler());
        this.add(annuleer, 3, 3, 1, 1);

        Button open = new Button("Open");
        open.setOnAction(new OpenHandler());
        this.add(open, 4, 3, 1, 1);
    }




    private void Textfield() {
        textfield = new TextField();
        this.add(textfield, 1, 5, 1, 1);
    }

    private class BetaalHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            if(!textfield.getText().trim().isEmpty()){
                System.out.println("betaling gestart");
                try{
                    double bedrag = Double.parseDouble(textfield.getText());

                    kassabon = new KassabonComponent(controller.getArtikels(), controller.getKortingStrategy());
                    controller.betaal();
                    System.out.println("betaal");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);


                    System.out.println(controller.generateRekening(kassabon));
                    alert.setContentText(controller.generateRekening(kassabon));
                    alert.showAndWait();

                }catch (Exception e) {
                    System.out.println("fout bij betaling");
                    System.out.println(e.getMessage());
                } catch (DatabaseException e) {
                    e.printStackTrace();
                } catch (DomainException e) {
                    e.printStackTrace();
                }
                stage.close();
            }
        }
    }

    private class OpenHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event){
            System.out.println("betaling gecanceled");
            kassaPane.enableVerwijderen();
        }
    }

    private class CancelHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event){
            System.out.println("betaling gecanceled");
            stage.close();
        }
    }

    public class AnnuleerHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            try{
                System.out.println("betaling geannuleerd");
                controller.annuleer();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            stage.close();
        }
    }
}