package view.panels;


/**
 * @author Sateur Maxime, Herremans Pieter
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
    private KassaviewController kassaviewController;
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

    public InstellingenPane(KassaviewController kassaviewController){
        this.kassaviewController = kassaviewController;

        //database
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
            }

        });
        //


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
                    if(!geldigegroep(kassaviewController) && !groeptxt.getText().trim().isEmpty()){
                        warningmessage += "De ingegeven groep bestaat niet. \n";
                        groeptxt.clear();
                    }
                } catch (DatabaseException databaseException) {
                    databaseException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (BiffException biffException) {
                    biffException.printStackTrace();
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
                    kassaviewController.setProperty("property.filetype", "TEKST");
                }
                if(chbxdatabase.getValue().equals("Excel")){
                    kassaviewController.setProperty("property.filetype", "EXCEL");
                }
                //


                //opslagen van korting (en ervoor zorgen dat er geen lege velden kunnen worden opgeslagen)
                if(!percentagetxt.getText().trim().isEmpty() && percentagetxt.getText() != null){
                    kassaviewController.setProperty("property.percentagekorting", percentagetxt.getText());
                }
                if(!bedargtxt.getText().trim().isEmpty() && bedargtxt.getText() != null){
                    kassaviewController.setProperty("property.drempelbedragkorting", bedargtxt.getText());
                }
                if(!groeptxt.getText().trim().isEmpty() && groeptxt.getText() != null){
                    kassaviewController.setProperty("property.groepkorting", groeptxt.getText());
                }
                //
            }
            //

        });
        //


        //selecteerd huidige keuze bij opstart databank
        String d = kassaviewController.getProperty("property.filetype");
        if(d.equalsIgnoreCase("tekst")){
            chbxdatabase.getSelectionModel().select(0);
        }
        else{
            chbxdatabase.getSelectionModel().select(1);
        }
        //


        //selcteerd huidige instelling bij opstart korting
        String k = kassaviewController.getProperty("property.typekorting");
        if(k.equalsIgnoreCase("geen")){
            chbxkorting.getSelectionModel().select(0);
        }
        if(k.equalsIgnoreCase("drempelkorting")){
            chbxkorting.getSelectionModel().select(1);
            percentagetxt.setText(kassaviewController.getProperty("property.percentagekorting").trim());
            bedargtxt.setText(kassaviewController.getProperty("property.drempelbedragkorting").trim());
        }
        if(k.equalsIgnoreCase("duurstekorting")){
            percentagetxt.setText(kassaviewController.getProperty("property.percentagekorting").trim());
            bedargtxt.setText(kassaviewController.getProperty("property.drempelbedragkorting").trim());
            chbxkorting.getSelectionModel().select(2);
        }
        if(k.equalsIgnoreCase("groepkorting")){
            chbxkorting.getSelectionModel().select(3);
            percentagetxt.setText(kassaviewController.getProperty("property.percentagekorting").trim());
            groeptxt.setText(kassaviewController.getProperty("property.groepkorting").trim());
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

    public boolean geldigpercentage(){
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

    public boolean geldigegroep(KassaviewController kassaviewController) throws DatabaseException, IOException, BiffException {
        ArrayList<Artikel> producten = kassaviewController.loadinMemory();
        boolean geldig = false;
        for (Artikel a : producten){
            if(a.getGroep().trim().equalsIgnoreCase(groeptxt.getText().trim())){
                geldig = true;
            }
        }
        return geldig;
    }

}




/*
public class InstellingenPane  extends GridPane implements EventHandler<javafx.event.ActionEvent> {

    private static String CHOICE_PROPERTY_NAME = "choice";
    private ComboBox<LoadSaveEnum> combobox;
    private ObjectProperty<KortingEnum> kortingchoice = new SimpleObjectProperty<>();
    private Properties properties = new Properties();
    private Button saveButton;
    private Button KortingButton;
    private KassaviewController controller;
    private String path = "src" + File.separator + "bestanden" + File.separator + "Save.properties";
    private Label percentage;
    private TextField percentagetxt;
    private Label bedrag;
    private TextField bedargtxt;
    private Label groep;
    private TextField groeptxt;

    public InstellingenPane(KassaviewController controller) throws DatabaseException {
        this.controller = controller;
        VBox p1 = new VBox(5);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        Label label1 = new Label("Producten:");
        label1.setPadding(new Insets(5,5,5,5));
        label1.setFont(new Font("Arial", 20));
        this.add(new Label("Welk bestand wilt u gebruiken ?"), 0,0, 1, 1);

        combobox = new ComboBox<LoadSaveEnum>();

        combobox.getItems().addAll(LoadSaveEnum.values());
        this.add(combobox, 5, 0, 2, 2);


        saveButton = new Button("Save");
        this.add(saveButton, 7, 0, 2, 2);

        saveButton.setOnAction(this);

        RadioButton Geenkorting = new RadioButton( "Geen korting");
        Geenkorting.getProperties().put( CHOICE_PROPERTY_NAME, KortingEnum.GEEN );

        RadioButton Drempelkorting = new RadioButton( "Drempelkorting");
        Drempelkorting.getProperties().put( CHOICE_PROPERTY_NAME, KortingEnum.DREMPELKORTING );

        RadioButton Duurstekorting = new RadioButton( "Duurstekorting" );
        Duurstekorting.getProperties().put( CHOICE_PROPERTY_NAME, KortingEnum.DUURSTEKORTING );

        RadioButton GroepKorting = new RadioButton( "GroepKorting" );
        GroepKorting.getProperties().put( CHOICE_PROPERTY_NAME, KortingEnum.GROEPKORTING );

        ToggleGroup tg = new ToggleGroup();
        tg.getToggles().addAll(Geenkorting, Drempelkorting, Duurstekorting, GroepKorting);

        //code listener

        tg.selectedToggleProperty().addListener( (obs,ov,nv) -> {
            kortingchoice.set( (KortingEnum)nv.getProperties().get(CHOICE_PROPERTY_NAME) );
        });

        kortingchoice.addListener( (obs,ov,nv) -> {
            if( nv != null ) {
                tg.getToggles()
                        .stream()
                        .filter( (rb) -> rb.getProperties().get(CHOICE_PROPERTY_NAME).equals(nv) )
                        .forEach( tg::selectToggle );
            }
        });

        kortingchoice.set(KortingEnum.GROEPKORTING);

        KortingButton = new Button("Save Korting");

        percentage = new Label("percentage");
        percentagetxt = new TextField();
        bedrag = new Label("bedrag");
        bedargtxt = new TextField();
        groep = new Label("groep");
        groeptxt = new TextField();

        this.add(Drempelkorting, 0,3,2,2);
        this.add(Duurstekorting, 0,5,2,2);
        this.add(GroepKorting, 0,7,2,2);
        this.add(Geenkorting, 0, 9,2,2);
        this.add(percentage, 0, 11, 2, 2);
        this.add(percentagetxt, 1, 11, 2, 2);
        this.add(bedrag, 0, 13,2,2);
        this.add(bedargtxt, 1, 13,2,2);
        this.add(groep, 0,15,2,2);
        this.add(groeptxt, 1,15,2,2);
        this.add(KortingButton, 0, 17,2,2);

        KortingButton.setOnAction(this);


    }

    @Override
    public void handle(ActionEvent event) {
        //zit nog een bug in dat wanneer er naar KassaApp.properties wordt geschreven deze leeg maakt, dit kan bij volgende restart voor bugs zorgen.
        if (event.getSource() == saveButton) {
            controller.setProperty("property.filetype", combobox.getValue().toString());
            System.out.println(combobox.getValue().toString());
        } else if(event.getSource() == KortingButton){
            controller.setProperty("property.typekorting", kortingchoice.get().toString());
            if(!percentagetxt.getText().isEmpty() && percentagetxt.getText() != null){
                controller.setProperty("property.percentagekorting", percentagetxt.getText());
            }
            if(!bedargtxt.getText().isEmpty() && bedargtxt.getText() != null){
                controller.setProperty("property.drempelbedragkorting", bedargtxt.getText());
            }
            if(!groeptxt.getText().isEmpty() && groeptxt.getText() != null){
                controller.setProperty("property.groepkorting", groeptxt.getText());
            }
            controller.setKortingStrategy();
            System.out.println(kortingchoice.get());
        }
    }
}

 */
