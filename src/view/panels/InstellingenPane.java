package view.panels;


/**
 * @author Sateur Maxime, Herremans Pieter, Vanhaeren Corentin
 */

import controller.InstellingController;
import controller.KassaviewController;
import javafx.animation.PauseTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import jxl.read.biff.BiffException;
import model.Artikel;
import model.database.DatabaseException;
import model.database.LoadSaveEnum;

import java.io.*;
import javafx.event.ActionEvent;
import model.korting.KortingEnum;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Properties;

public class InstellingenPane extends GridPane{
    private KassaviewController controller;
    private Label titel;
    private Label databsae;
    private Label korting;
    private Label databasetxt;
    private Label kortingtxt;
    private ChoiceBox chbxdatabase;
    private ChoiceBox chbxkorting;
    private Label percentage;
    private TextField percentagetxt;
    private Label bedrag;
    private TextField bedargtxt;
    private Label groep;
    private TextField groeptxt;
    Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1)));
    private Button save;

    public InstellingenPane(KassaviewController controller){
        this.controller = controller;

        this.setbox();

        //bij klikken op savebutton worden de instelling opgeslagen

        save.setOnAction(e-> {
            //popup bij niet ingevulde velden
            String warningmessage = "";
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning Dialog");
            warning.setHeaderText(null);
            //


            //bij niet/fout ingevulde velden een popup weergeven of confirmation popup tonen
            String keuze = (String) chbxkorting.getValue();
            if (keuze.equals("Drempelkorting")){
                if(percentagetxt.getText().trim().isEmpty() || percentagetxt.getText() == null){
                    warningmessage += "Percentage veld moet ingevuld zijn. \n";
                }
                if(bedargtxt.getText().trim().isEmpty() || bedargtxt.getText() == null){
                    warningmessage += "Bedarg veld moet ingevuld zijn. \n";
                }
                if(!geldigpercentage() && !percentagetxt.getText().trim().isEmpty()){
                    warningmessage += "Het percentage moet tussen 0 en 100 liggen. \n";
                    percentagetxt.clear();
                }
                if(!geldigbedarg() && !bedargtxt.getText().trim().isEmpty()){
                    warningmessage += "Het Bedarg moet groter zijn dan 0. \n";
                    bedargtxt.clear();
                }
                if (!warningmessage.trim().isEmpty()){
                    warning.setContentText(warningmessage);
                    warning.showAndWait();
                }
                else {
                    popupsave();
                }
            }
            if (keuze.equals("Duurstekorting")){
                if(percentagetxt.getText().trim().isEmpty() || percentagetxt.getText() == null){
                    warningmessage += "Percentage veld moet ingevuld zijn. \n";
                }
                if(bedargtxt.getText().trim().isEmpty() || bedargtxt.getText() == null){
                    warningmessage += "Bedarg veld moet ingevuld zijn. \n";
                }
                if(!geldigpercentage() && !percentagetxt.getText().trim().isEmpty()){
                    warningmessage += "Het percentage moet tussen 0 en 100 liggen. \n";
                    percentagetxt.clear();
                }
                if(!geldigbedarg() && !bedargtxt.getText().trim().isEmpty()){
                    warningmessage += "Het Bedarg moet groter zijn dan 0. \n";
                    bedargtxt.clear();
                }
                if (!warningmessage.trim().isEmpty()){
                    warning.setContentText(warningmessage);
                    warning.showAndWait();
                }
                else {
                    popupsave();
                }
            }
            if (keuze.equals("Groepkorting")) {
                if(percentagetxt.getText().trim().isEmpty() || percentagetxt.getText() == null){
                    warningmessage += "Percentage veld moet ingevuld zijn. \n";
                }
                if(groeptxt.getText().trim().isEmpty() || groeptxt.getText() == null){
                    warningmessage += "Groep veld moet ingevuld zijn. \n";
                }
                if(!geldigpercentage() && !percentagetxt.getText().trim().isEmpty()){
                    warningmessage += "Het percentage moet tussen 0 en 100 liggen. \n";
                    percentagetxt.clear();
                }
                //
                try {
                    if(!groeptxt.getText().trim().isEmpty()){
                        warningmessage += "De ingegeven groep bestaat niet. \n";
                        groeptxt.clear();
                    }
                }catch(Exception ex){
                    throw new IllegalArgumentException(ex);
                }
                //
                if (!warningmessage.trim().isEmpty()){
                    warning.setContentText(warningmessage);
                    warning.showAndWait();
                }
                else {
                    popupsave();
                }
            }
            //


            //bij geen fouten, worden instelling opgeslagen
            if(warningmessage.trim().isEmpty()){
                //opslagen van database
                if(chbxdatabase.getValue().equals("Tekst")){
                    controller.setProperty("property.filetype", "TEKST");
                }
                if(chbxdatabase.getValue().equals("Excel")){
                    controller.setProperty("property.filetype", "EXCEL");
                }
                //


                //opslagen van korting (en ervoor zorgen dat er geen lege velden kunnen worden opgeslagen)
                if(!percentagetxt.getText().trim().isEmpty() && percentagetxt.getText() != null){
                    controller.setProperty("property.percentagekorting", percentagetxt.getText());
                }
                if(!bedargtxt.getText().trim().isEmpty() && bedargtxt.getText() != null){
                    controller.setProperty("property.drempelbedragkorting", bedargtxt.getText());
                }
                if(!groeptxt.getText().trim().isEmpty() && groeptxt.getText() != null){
                    controller.setProperty("property.groepkorting", groeptxt.getText());
                }
                //
            }
            //

        });
        //


        //selecteerd huidige keuze bij opstart databank
        String d = controller.getProperty("property.filetype");
        if(d.equalsIgnoreCase("tekst")){
            chbxdatabase.getSelectionModel().select(0);
        }
        else{
            chbxdatabase.getSelectionModel().select(1);
        }
        //


        //selcteerd huidige instelling bij opstart korting
        String k = controller.getProperty("property.typekorting");
        if(k.equalsIgnoreCase("geen")){
            chbxkorting.getSelectionModel().select(0);
        }
        if(k.equalsIgnoreCase("drempelkorting")){
            chbxkorting.getSelectionModel().select(1);
            percentagetxt.setText(controller.getProperty("property.percentagekorting").trim());
            bedargtxt.setText(controller.getProperty("property.drempelbedragkorting").trim());
        }
        if(k.equalsIgnoreCase("duurstekorting")){
            percentagetxt.setText(controller.getProperty("property.percentagekorting").trim());
            bedargtxt.setText(controller.getProperty("property.drempelbedragkorting").trim());
            chbxkorting.getSelectionModel().select(2);
        }
        if(k.equalsIgnoreCase("groepkorting")){
            chbxkorting.getSelectionModel().select(3);
            percentagetxt.setText(controller.getProperty("property.percentagekorting").trim());
            groeptxt.setText(controller.getProperty("property.groepkorting").trim());
        }
        //

    }

    public void popupsave(){
        //popup venster met bevestiging
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Instellingen opgeslagen");
        alert.showAndWait();
        //https://code.makery.ch/blog/javafx-dialogs-official/
        //
    }

    private boolean geldigpercentage(){
        boolean geldig = false;
        if(!percentagetxt.getText().trim().isEmpty()){
            if (Double.parseDouble(percentagetxt.getText().trim()) > 0 && Double.parseDouble(percentagetxt.getText().trim()) < 100){
                geldig = true;
            }
        }
        return geldig;
    }

    public boolean geldigbedarg(){
        boolean geldig = false;
        if(!bedargtxt.getText().trim().isEmpty()){
            if (Double.parseDouble(bedargtxt.getText().trim()) >= 0){
                geldig = true;
            }
        }
        return geldig;
    }

    //test voor groep niet nodig, korting wordt nul als de groep niet bestaat
    /*public boolean geldigegroep(InstellingController controller) throws DatabaseException, IOException, BiffException {
        ArrayList<Artikel> producten =
        boolean geldig = false;
        for (Artikel a : producten){
            if(a.getGroep().trim().equalsIgnoreCase(groeptxt.getText().trim())){
                geldig = true;
            }
        }
        return geldig;
    }*/

    private void setbox(){
        //settingup all of the ui

        VBox p2 = new VBox(10);
        databsae = new Label("Database:");
        databsae.setFont(new Font(15));
        databasetxt = new Label("Selecteer gewenste database:");
        //databasetxt.setPadding(new Insets(0, 0, 0, 10));
        VBox.setMargin(databasetxt, new Insets(0, 0, 0, 5));
        chbxdatabase  = new ChoiceBox<>();
        chbxdatabase.getItems().addAll("Tekst", "Excel");
        //chbxdatabase.setPadding(new Insets(0, 0, 0, 10));

        VBox.setMargin(chbxdatabase, new Insets(0, 0, 0, 5));
        //Border border1 = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1)));
        p2.setMinSize(400, 120);
        //p2.setPadding(new Insets(0, 0, 10, 10));
        p2.setBorder(border);
        //p2.setAlignment(Pos.CENTER);
        p2.setPadding(new Insets(10));
        p2.getChildren().addAll(databsae, databasetxt, chbxdatabase);
        //


        //korting
        VBox p3 = new VBox(10);
        korting = new Label("Korting:");
        korting.setFont(new Font(15));
        kortingtxt = new Label("Selecteer gewenste korting:");
        VBox.setMargin(kortingtxt, new Insets(0, 0, 0, 5));
        chbxkorting  = new ChoiceBox<>();
        chbxkorting.getItems().addAll("Geenkorting", "Drempelkorting", "Duurstekorting", "Groepkorting");

        VBox.setMargin(chbxkorting, new Insets(0, 0, 0, 5));
        //p3.setAlignment(Pos.CENTER);
        p3.setMinSize(400, 120);
        p3.setBorder(border);
        p3.setPadding(new Insets(10));
        p3.getChildren().addAll(korting, kortingtxt, chbxkorting);
        //


        //percentage
        HBox p4 = new HBox(10);
        percentage = new Label("Percentage (%):");
        percentagetxt = new TextField();
        //percentagetxt.setAlignment(Pos.CENTER_RIGHT);
        //p1.setAlignment(Pos.CENTER);
        p4.setPadding(new Insets(0,0,0,10));
        p4.getChildren().addAll(percentage, percentagetxt);
        //


        //bedrag
        HBox p5 = new HBox(10);
        bedrag = new Label("Bedrag (€):");
        bedargtxt = new TextField();
        //bedargtxt.setAlignment(Pos.CENTER_RIGHT);
        //p1.setAlignment(Pos.CENTER);
        p5.setPadding(new Insets(0,0,0,10));
        p5.getChildren().addAll(bedrag, bedargtxt);
        //


        //groep
        HBox p6 = new HBox(10);
        groep = new Label("Groep:");
        groeptxt = new TextField();
        //p1.setAlignment(Pos.CENTER);
        p6.setPadding(new Insets(0, 0,0, 10));
        p6.getChildren().addAll(groep, groeptxt);
        //


        //hoofd
        VBox p1 = new VBox(10);
        titel = new Label("Instellingen:");
        titel.setFont(new Font(20));
        save = new Button("Save");
        //save.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 10");
        save.setStyle("-fx-background-color: lightgray; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-radius: 10px;  -fx-font-size: 14px;");
        save.setMinSize(70,30);
        //http://tutorials.jenkov.com/javafx/button.html
        //save.setBorder(border);
        //save.setBackground(new Color.LIGHTGRAY);

        //p1.setAlignment(Pos.CENTER);
        p1.setPadding(new Insets(10));
        p1.getChildren().addAll(titel, p2, p3, save);
        this.getChildren().addAll(p1);
        //

        //bij keuze korting verschijnen de juiste velden
        //klein
        chbxkorting.setOnAction(e -> {
            String keuze = (String) chbxkorting.getValue();
            if (keuze.equals("Drempelkorting")){
                try{
                    p3.getChildren().removeAll(p4, p5, p6);
                    p3.getChildren().add(p4);
                    p3.getChildren().add(p5);
                    percentagetxt.clear();
                    bedargtxt.clear();
                    groeptxt.clear();
                }
                catch (IllegalArgumentException m){

                }
            }
            if (keuze.equals("Duurstekorting")){
                try{
                    p3.getChildren().removeAll(p4, p5, p6);
                    p3.getChildren().add(p4);
                    p3.getChildren().add(p5);
                    percentagetxt.clear();
                    bedargtxt.clear();
                    groeptxt.clear();
                }
                catch (IllegalArgumentException m){

                }
            }
            if (keuze.equals("Groepkorting")){
                try{
                    p3.getChildren().removeAll(p4, p5, p6);
                    p3.getChildren().add(p4);
                    p3.getChildren().add(p6);
                    percentagetxt.clear();
                    bedargtxt.clear();
                    groeptxt.clear();
                }
                catch (IllegalArgumentException m){

                }
            }
            if (keuze.equals("Geenkorting")){
                try{
                    p3.getChildren().removeAll(p4, p5, p6);
                    percentagetxt.clear();
                    bedargtxt.clear();
                    groeptxt.clear();
                }
                catch (IllegalArgumentException m){


                }
            }}
        );
    }
}