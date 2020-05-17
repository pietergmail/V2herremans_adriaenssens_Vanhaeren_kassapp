package view.panels;


/**
 * @author Sateur Maxime
 */

import controller.KassaviewController;
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


    private ComboBox<LoadSaveEnum> combobox;
    private Properties properties = new Properties();
    private Button saveButton;
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

        this.add(new Label("Kies welke korting u wilt aanmaken:"),0,6,1,1);

        /*combo = new ComboBox();
        combo.getItems().addAll(KortingEnum.values());
        this.add(combo,0,7,1,1);
        if(controller.getProperty("prop.typekorting").equals("DUURSTEKORTING")){
            combo.setValue(KortingEnum.DUURSTEKORTING);
            showCorrectBoxes();
        }
        switch (controller.getProperty("prop.typekorting")){
            case "DUURSTEKORTING":
                combo.setValue(KortingEnum.DUURSTEKORTING);
                break;
            case "DREMPELKORTING":
                combo.setValue(KortingEnum.DREMPELKORTING);
                break;
            case "GROEPSKORTING":
                combo.setValue(KortingEnum.GROEPSKORTING);
                break;
        }
        showCorrectBoxes();*/

    }

    @Override
    public void handle(ActionEvent event) {
        try {
            LoadSaveEnum loadSaveEnum = LoadSaveEnum.valueOf(combobox.getValue().toString());
            controller.setLoadSaveStrategy(loadSaveEnum);
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
