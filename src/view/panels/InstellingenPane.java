package view.panels;

import controller.InstellingController;
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

import java.util.Properties;

public class InstellingenPane  extends GridPane implements EventHandler<javafx.event.ActionEvent> {


    private ComboBox combobox;
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

        combobox = new ComboBox();

        combobox.getItems().addAll(LoadSaveEnum.values());
        this.add(combobox, 5, 0, 2, 2);


        saveButton = new Button("Save");
        this.add(saveButton, 7, 0, 2, 2);

        saveButton.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == saveButton) {
            controller.setProperty("property.filetype", combobox.getValue().toString());
            System.out.println(combobox.getValue().toString());
        }
    }

}
