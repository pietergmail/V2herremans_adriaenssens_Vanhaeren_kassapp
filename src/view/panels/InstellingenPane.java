package view.panels;


/**
 * @author Sateur Maxime, Herremans Pieter
 */

import controller.KassaviewController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.database.DatabaseException;
import model.database.LoadSaveEnum;

import java.io.*;
import javafx.event.ActionEvent;
import model.korting.KortingEnum;

import java.util.Properties;

public class InstellingenPane  extends GridPane implements EventHandler<javafx.event.ActionEvent> {

    private static String CHOICE_PROPERTY_NAME = "choice";
    private ComboBox<LoadSaveEnum> combobox;
    private ObjectProperty<KortingEnum> kortingchoice = new SimpleObjectProperty<>();
    private Properties properties = new Properties();
    private Button saveButton;
    private Button KortingButton;
    private KassaviewController controller;
    private String path = "src" + File.separator + "bestanden" + File.separator + "Save.properties";


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

        RadioButton Drempelkorting = new RadioButton( "Drempelkorting");
        Drempelkorting.getProperties().put( CHOICE_PROPERTY_NAME, KortingEnum.DREMPELKORTING );

        RadioButton Duurstekorting = new RadioButton( "Duurstekorting" );
        Duurstekorting.getProperties().put( CHOICE_PROPERTY_NAME, KortingEnum.DUURSTEKORTING );

        RadioButton GroepKorting = new RadioButton( "GroepKorting" );
        GroepKorting.getProperties().put( CHOICE_PROPERTY_NAME, KortingEnum.GROEPSKORTING );

        ToggleGroup tg = new ToggleGroup();
        tg.getToggles().addAll(Drempelkorting, Duurstekorting, GroepKorting);

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

        kortingchoice.set(KortingEnum.GROEPSKORTING);

        KortingButton = new Button("Print Choice");

        this.add(Drempelkorting, 0,3,2,2);
        this.add(Duurstekorting, 0,5,2,2);
        this.add(GroepKorting, 0,7,2,2);
        this.add(KortingButton, 0, 9,2,2);

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
            System.out.println(kortingchoice.get());
        }
    }
}
