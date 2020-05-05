package view.panels;


/**
 * @author Sateur Maxime
 */

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.database.DatabaseException;
import model.database.LoadSaveEnum;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.datatransfer.FlavorEvent;
import java.io.*;
import javafx.event.ActionEvent;
import model.database.LoadSaveFactory;
import model.database.StrategyLoadSave;

import java.util.ArrayList;
import java.util.Properties;

public class InstellingenPane  extends GridPane implements EventHandler<javafx.event.ActionEvent> {


    private ComboBox combobox;
    private Properties properties = new Properties();
    private Button saveButton;
    private String path = "src" + File.separator + "bestanden" + File.separator + "Save.properties";


    public InstellingenPane() throws DatabaseException {
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
            setProperty("property.filetype", combobox.getValue().toString());
            System.out.println(combobox.getValue().toString());
        }
    }




    public void setProperty(String key, String value){
        try(OutputStream output = new FileOutputStream(new File(path))) {
            properties.setProperty(key, value);
            properties.store(output, "properties");
        }catch (Exception e){
            e.getMessage();
        }
    }
/*
    public String getProperty(String key){
        String value = null;
        try(InputStream input = new FileInputStream(new File(path))) {
            properties.load(input);
            value = properties.getProperty(key);
        }catch (Exception e){
            e.getMessage();
        }
        return value;
    }

    public StrategyLoadSave setLoadStrategy(){
        LoadSaveEnum saveStrategy = LoadSaveEnum.valueOf(getProperty("property.filetype"));
        return new LoadSaveFactory().ChooseSaveType(saveStrategy);
            }



 */
}
