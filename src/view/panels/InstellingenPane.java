package view.panels;

import controller.KassaviewController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jxl.read.biff.BiffException;
import model.Artikel;
import model.database.DatabaseException;

import java.io.*;
import model.korting.KortingEnum;
import java.util.ArrayList;


/**
 * @author Sateur Maxime, Herremans Pieter, Vanhaeren Corentin
 */

public class InstellingenPane extends GridPane{
    private KassaviewController controller;
    private ChoiceBox chbxdatabase;
    private ChoiceBox chbxkorting;
    private TextField percentagetxt;
    private TextField bedargtxt;
    private TextField groeptxt;
    private Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1)));
    private Button save;
    private RadioButton headerboodschap;
    private RadioButton headerdatumtijd;
    private RadioButton footerboodschap;
    private RadioButton footerkorting;
    private RadioButton footerBTW;
    private Label kassabon;
    private Label header;
    private Label footer;
    private TextField headerboodschaptxt;
    private TextField footerboodschaptxt;
    private boolean headerboodschapbl;
    private boolean headerdatumtijdbl;
    private boolean footerboodschapbl;
    private boolean footerkortingbl;
    private boolean footerBTWbl;
    private VBox p10;
    private VBox p11;
    public InstellingenPane(KassaviewController controller){
        this.controller = controller;

        this.setbox();

        save.setOnAction(e-> saveinstellingen());
        save.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveinstellingen();
            }
        });




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
        if(k.equalsIgnoreCase("geenkorting")){
            chbxkorting.getSelectionModel().select(0);
        }
        if(k.equalsIgnoreCase("drempelkorting")){
            chbxkorting.getSelectionModel().select(1);
            percentagetxt.setText(controller.getProperty("property.percentagekorting").trim());
            bedargtxt.setText(controller.getProperty("property.drempelbedragkorting").trim());
        }
        if(k.equalsIgnoreCase("duurstekorting")){
            chbxkorting.getSelectionModel().select(2);
            percentagetxt.setText(controller.getProperty("property.percentagekorting").trim());
            bedargtxt.setText(controller.getProperty("property.drempelbedragkorting").trim());
        }
        if(k.equalsIgnoreCase("groepkorting")){
            chbxkorting.getSelectionModel().select(3);
            percentagetxt.setText(controller.getProperty("property.percentagekorting").trim());
            groeptxt.setText(controller.getProperty("property.groepkorting").trim());
        }
        //


        //selecteerd huidige instelling bij opstart kassabon
        headerboodschap.setSelected(Boolean.parseBoolean(controller.getProperty("property.headerboodschap")));
        headerdatumtijd.setSelected(Boolean.parseBoolean(controller.getProperty("property.headerdatumtijd")));
        footerboodschap.setSelected(Boolean.parseBoolean(controller.getProperty("property.footerboodschap")));
        footerkorting.setSelected(Boolean.parseBoolean( controller.getProperty("property.footerkorting")));
        footerBTW.setSelected(Boolean.parseBoolean(controller.getProperty("property.footerBTW")));

        if (headerboodschap.isSelected()) {
            headerboodschaptxt.setText(controller.getProperty("property.headerboodschaptext"));
            headerboodschapbl = true;
            p10.getChildren().clear();
            p10.getChildren().addAll(kassabon, header, headerboodschap, headerboodschaptxt, headerdatumtijd);
        }

        if (footerboodschap.isSelected()){
            footerboodschaptxt.setText(controller.getProperty("property.footerboodschaptext"));
            footerboodschapbl = true;
            p11.getChildren().clear();
            p11.getChildren().addAll(footer, footerboodschap, footerboodschaptxt, footerBTW, footerkorting);
        }
        //

        //
    }


    //opslagen van instellingen
    private void saveinstellingen(){

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
            if(geldigpercentage() && !percentagetxt.getText().trim().isEmpty()){
                warningmessage += "Het percentage moet tussen 0 en 100 liggen. \n";
                percentagetxt.clear();
            }
            if(!geldigbedarg() && !bedargtxt.getText().trim().isEmpty()){
                warningmessage += "Het Bedarg moet groter zijn dan 0. \n";
                bedargtxt.clear();
            }
            if (headerboodschap.isSelected()) {
                if(headerboodschaptxt.getText().trim().isEmpty() || headerboodschaptxt.getText() == null){
                    warningmessage += "Headerboodschap veld moet ingevuld zijn. \n";
                }
            }
            if (footerboodschap.isSelected()){
                if(footerboodschaptxt.getText().trim().isEmpty() || footerboodschaptxt.getText() == null){
                    warningmessage += "Footerboodschap veld moet ingevuld zijn. \n";
                }
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

            if(geldigpercentage() && !percentagetxt.getText().trim().isEmpty()){
                warningmessage += "Het percentage moet tussen 0 en 100 liggen. \n";
                percentagetxt.clear();
            }

            if (headerboodschap.isSelected()) {
                if(headerboodschaptxt.getText().trim().isEmpty() || headerboodschaptxt.getText() == null){
                    warningmessage += "Headerboodschap veld moet ingevuld zijn. \n";
                }
            }
            if (footerboodschap.isSelected()){
                if(footerboodschaptxt.getText().trim().isEmpty() || footerboodschaptxt.getText() == null){
                    warningmessage += "Footerboodschap veld moet ingevuld zijn. \n";
                }
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
            if (percentagetxt.getText().trim().isEmpty() || percentagetxt.getText() == null) {
                warningmessage += "Percentage veld moet ingevuld zijn. \n";
            }
            if (groeptxt.getText().trim().isEmpty() || groeptxt.getText() == null) {
                warningmessage += "Groep veld moet ingevuld zijn. \n";
            }
            if (geldigpercentage() && !percentagetxt.getText().trim().isEmpty()) {
                warningmessage += "Het percentage moet tussen 0 en 100 liggen. \n";
                percentagetxt.clear();
            }
            //
            try {
                if (!geldigegroep() && !groeptxt.getText().trim().isEmpty()) {
                    warningmessage += "De ingegeven groep bestaat niet. \n";
                    groeptxt.clear();
                }
            } catch (DatabaseException | IOException | BiffException databaseException) {
                databaseException.printStackTrace();
            }
            //
            if (headerboodschap.isSelected()) {
                if(headerboodschaptxt.getText().trim().isEmpty() || headerboodschaptxt.getText() == null){
                    warningmessage += "Headerboodschap veld moet ingevuld zijn. \n";
                }
            }
            if (footerboodschap.isSelected()){
                if(footerboodschaptxt.getText().trim().isEmpty() || footerboodschaptxt.getText() == null){
                    warningmessage += "Footerboodschap veld moet ingevuld zijn. \n";
                }
            }
            if (!warningmessage.trim().isEmpty()) {
                warning.setContentText(warningmessage);
                warning.showAndWait();
            } else {
                popupsave();
            }
        }
        if (keuze.equals("Geenkorting")){
            if (headerboodschap.isSelected()) {
                if(headerboodschaptxt.getText().trim().isEmpty() && headerboodschaptxt.getText() == null){
                    warningmessage += "Headerboodschap veld moet ingevuld zijn. \n";
                }
            }
            if (footerboodschap.isSelected()){
                if(footerboodschaptxt.getText().trim().isEmpty() && footerboodschaptxt.getText() == null){
                    warningmessage += "Footerboodschap veld moet ingevuld zijn. \n";
                }
            }
            if (!warningmessage.trim().isEmpty()) {
                warning.setContentText(warningmessage);
                warning.showAndWait();
            } else {
                popupsave();
            }
        }

        //


        //bij geen fouten, worden database instelling opgeslagen
        if(warningmessage.trim().isEmpty()){
            //opslagen van database
            if(chbxdatabase.getValue().equals("Tekst")){
                controller.setProperty("property.filetype", "TEKST");
            }
            if(chbxdatabase.getValue().equals("Excel")){
                controller.setProperty("property.filetype", "EXCEL");
            }
            //


            //opslagen van korting

            //opslagen van korting velden (en ervoor zorgen dat er geen lege velden kunnen worden opgeslagen)
            if(!percentagetxt.getText().trim().isEmpty() && percentagetxt.getText() != null){
                controller.setProperty("property.percentagekorting", percentagetxt.getText());
            }
            if(!bedargtxt.getText().trim().isEmpty() && bedargtxt.getText() != null){
                controller.setProperty("property.drempelbedragkorting", bedargtxt.getText());
            }
            if(!groeptxt.getText().trim().isEmpty() && groeptxt.getText() != null){
                controller.setProperty("property.groepkorting", groeptxt.getText());
            }

            //opslagen van kortingstype
            if(!((String) chbxkorting.getValue()).isEmpty()){
                controller.setTypeKorting(KortingEnum.valueOf(((String) chbxkorting.getValue()).toUpperCase()));
            }

            controller.setProperty("property.headerboodschap",  Boolean.toString(headerboodschapbl));
            controller.setProperty("property.headerdatumtijd", Boolean.toString(headerdatumtijdbl));
            controller.setProperty("property.footerboodschap", Boolean.toString(footerboodschapbl));
            controller.setProperty("property.footerkorting", Boolean.toString(footerkortingbl));
            controller.setProperty("property.footerBTW", Boolean.toString(footerBTWbl));

            if(!headerboodschaptxt.getText().trim().isEmpty() && headerboodschaptxt.getText() != null){
                controller.setProperty("property.headerboodschaptext", headerboodschaptxt.getText());
            }
            if(!footerboodschaptxt.getText().trim().isEmpty() && footerboodschaptxt.getText() != null){
                controller.setProperty("property.footerboodschaptext", footerboodschaptxt.getText());
            }
            //
            //
        }
        //
    }
    //

    private void popupsave(){
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
        return !geldig;
    }

    private boolean geldigbedarg(){
        boolean geldig = false;
        if(!bedargtxt.getText().trim().isEmpty()){
            if (Double.parseDouble(bedargtxt.getText().trim()) >= 0){
                geldig = true;
            }
        }
        return geldig;
    }

    private boolean geldigegroep() throws DatabaseException, IOException, BiffException {
        ArrayList<Artikel> producten = controller.loadinMemory();
        boolean geldig = false;
        for (Artikel a : producten){
            if(a.getGroep().trim().equalsIgnoreCase(groeptxt.getText().trim())){
                geldig = true;
            }
        }
        return geldig;
    }

    private void setbox() {
        //settingup all of the ui
        //database
        VBox p2 = new VBox(10);
        Label databsae = new Label("Database:");
        databsae.setFont(new Font(15));
        Label databasetxt = new Label("Selecteer gewenste database:");
        VBox.setMargin(databasetxt, new Insets(0, 0, 0, 5));
        chbxdatabase = new ChoiceBox<>();
        chbxdatabase.getItems().addAll("Tekst", "Excel");

        VBox.setMargin(chbxdatabase, new Insets(0, 0, 0, 5));
        p2.setMinSize(400, 120);
        p2.setBorder(border);
        p2.setPadding(new Insets(10));
        p2.getChildren().addAll(databsae, databasetxt, chbxdatabase);
        //


        //korting
        VBox p3 = new VBox(10);
        Label korting = new Label("Korting:");
        korting.setFont(new Font(15));
        Label kortingtxt = new Label("Selecteer gewenste korting:");
        VBox.setMargin(kortingtxt, new Insets(0, 0, 0, 5));
        chbxkorting = new ChoiceBox<>();
        chbxkorting.getItems().addAll("Geenkorting", "Drempelkorting", "Duurstekorting", "Groepkorting");

        VBox.setMargin(chbxkorting, new Insets(0, 0, 0, 5));
        p3.setMinSize(410, 200);
        p3.setBorder(border);
        p3.setPadding(new Insets(10));
        p3.getChildren().addAll(korting, kortingtxt, chbxkorting);


        //korting percentage
        HBox p4 = new HBox(10);
        Label percentage = new Label("Percentage (%):");
        percentagetxt = new TextField();
        p4.setPadding(new Insets(0, 0, 0, 10));
        p4.getChildren().addAll(percentage, percentagetxt);
        //


        //korting bedrag
        HBox p5 = new HBox(10);
        Label bedrag = new Label("Bedrag (€):");
        bedargtxt = new TextField();
        p5.setPadding(new Insets(0, 0, 0, 10));
        p5.getChildren().addAll(bedrag, bedargtxt);
        //


        //korting groep
        HBox p6 = new HBox(10);
        Label groep = new Label("Groep:");
        groeptxt = new TextField();
        p6.setPadding(new Insets(0, 0, 0, 10));
        p6.getChildren().addAll(groep, groeptxt);
        //


        //database + korting
        VBox p7 = new VBox(10);
        p7.getChildren().addAll(p2, p3);
        //


        //kassabon header
        p10 = new VBox(10);
        header = new Label("Header:");
        header.setFont(new Font(13));
        VBox.setMargin(header, new Insets(0, 0, 0, 5));
        headerboodschap = new RadioButton("Headerboodschap");
        VBox.setMargin(headerboodschap, new Insets(0, 0, 0, 10));
        headerboodschaptxt = new TextField();
        headerboodschaptxt.setPromptText("Vul headerboodschap in");
        headerboodschaptxt.setMaxWidth(200);
        VBox.setMargin(headerboodschaptxt, new Insets(0, 0, 0, 33));
        headerdatumtijd = new RadioButton("Datum & tijd");
        VBox.setMargin(headerdatumtijd, new Insets(0, 0, 0, 10));

        p10.getChildren().addAll(header, headerboodschap, headerdatumtijd);
        //


        //kassabon footer
        p11 = new VBox(10);
        footer = new Label("Footer:");
        footer.setFont(new Font(13));
        VBox.setMargin(footer, new Insets(0, 0, 0, 5));
        footerboodschap = new RadioButton("Footerboodschap");
        VBox.setMargin(footerboodschap, new Insets(0, 0, 0, 10));
        footerboodschaptxt = new TextField();
        footerboodschaptxt.setPromptText("Vul footerboodschap in");
        footerboodschaptxt.setMaxWidth(200);
        VBox.setMargin(footerboodschaptxt, new Insets(0, 0, 0, 33));
        footerBTW = new RadioButton("FooterBTW");
        VBox.setMargin(footerBTW, new Insets(0, 0, 0, 10));
        footerkorting = new RadioButton("Footerkorting");
        VBox.setMargin(footerkorting, new Insets(0, 0, 0, 10));

        p11.getChildren().addAll(footer, footerboodschap, footerBTW, footerkorting);
        //

        //kassabon
        VBox p8 = new VBox(10);
        kassabon = new Label("Kassabon");
        kassabon.setFont(new Font(15));

        p8.setMinSize(310, 260);
        p8.setBorder(border);
        p8.setPadding(new Insets(10));
        p8.getChildren().addAll(kassabon, p10, p11);
        //



        //subhoofd
        HBox p9 = new HBox(10);
        p9.getChildren().addAll(p7, p8);
        //

        //hoofd
        VBox p1 = new VBox(10);
        Label titel = new Label("Instellingen:");
        titel.setFont(new Font(20));
        save = new Button("Save");
        save.setStyle("-fx-background-color: lightgray; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-radius: 10px;  -fx-font-size: 14px;");
        save.setMinSize(70, 30);
        VBox.setMargin(save, new Insets(17, 0, 0, 330));

        p1.setPadding(new Insets(10));
        p1.getChildren().addAll(titel, p9, save);
        this.getChildren().addAll(p1);


        //bij keuze korting verschijnen de juiste velden
        chbxkorting.setOnAction(e -> {
            String keuze = (String) chbxkorting.getValue();
            if (keuze.equals("Drempelkorting")) {
                try {
                    p3.getChildren().removeAll(p4, p5, p6);
                    p3.getChildren().add(p4);
                    p3.getChildren().add(p5);
                    percentagetxt.clear();
                    bedargtxt.clear();
                    groeptxt.clear();
                } catch (IllegalArgumentException m) {

                }
            }
            if (keuze.equals("Duurstekorting")) {
                try {
                    p3.getChildren().removeAll(p4, p5, p6);
                    p3.getChildren().add(p4);
                    percentagetxt.clear();
                    bedargtxt.clear();
                    groeptxt.clear();
                } catch (IllegalArgumentException m) {

                }
            }
            if (keuze.equals("Groepkorting")) {
                try {
                    p3.getChildren().removeAll(p4, p5, p6);
                    p3.getChildren().add(p4);
                    p3.getChildren().add(p6);
                    percentagetxt.clear();
                    bedargtxt.clear();
                    groeptxt.clear();
                } catch (IllegalArgumentException m) {

                }
            }
            if (keuze.equals("Geenkorting")) {
                try {
                    p3.getChildren().removeAll(p4, p5, p6);
                    percentagetxt.clear();
                    bedargtxt.clear();
                    groeptxt.clear();
                } catch (IllegalArgumentException m) {

                }
            }

        });
        //


        //radiobuttons actions
        headerboodschap.setOnAction(e -> {
            if (headerboodschap.isSelected()) {
                headerboodschapbl = true;
                p10.getChildren().clear();
                p10.getChildren().addAll(kassabon, header, headerboodschap, headerboodschaptxt, headerdatumtijd);
                //p8.getChildren().addAll(kassabon, header, headerboodschap, headerboodschaptxt, headerdatumtijd, footer, footerboodschap, footerboodschaptxt, footerBTW, footerkorting)
            }
            if (!headerboodschap.isSelected()) {
                headerboodschapbl = false;
                p10.getChildren().clear();
                p10.getChildren().addAll(kassabon, header, headerboodschap, headerdatumtijd);
            }
        });

        headerdatumtijd.setOnAction(e -> {
            if (headerdatumtijd.isSelected()) {
                headerdatumtijdbl = true;
            }
            if (!headerdatumtijd.isSelected()) {
                headerdatumtijdbl = false;
            }
        });

        footerboodschap.setOnAction(e -> {
            if (footerboodschap.isSelected()) {
                footerboodschapbl = true;
                p11.getChildren().clear();
                p11.getChildren().addAll(footer, footerboodschap, footerboodschaptxt, footerBTW, footerkorting);
            }
            if (!footerboodschap.isSelected()) {
                footerboodschapbl = false;
                p11.getChildren().clear();
                p11.getChildren().addAll(footer, footerboodschap, footerBTW, footerkorting);
            }
        });

        footerkorting.setOnAction(e -> {
            if (footerkorting.isSelected()) {
                footerkortingbl = true;
            }
            if (!footerkorting.isSelected()) {
                footerkortingbl = false;
            }
        });

        footerBTW.setOnAction(e -> {
            if (footerBTW.isSelected()) {
                footerBTWbl = true;
            }
            if (!footerBTW.isSelected()) {
                footerBTWbl = false;
            }
        });
    }
}