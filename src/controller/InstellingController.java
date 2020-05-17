package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Artikel;
import model.Observer;

import java.io.*;
import java.util.Properties;

/**
 * @author Pieter Herremans
 */
public class InstellingController {
    private String path = "src" + File.separator + "bestanden" + File.separator + "KassaApp.properties";
    private File file;
    private Properties properties = new Properties();

    public InstellingController() {
        this.properties = new Properties();
        file = new File(path);
    }

    public void setProperty(String key, String value){
        try (OutputStream output = new FileOutputStream(new File(path))) {
            properties.setProperty(key, value);
            properties.store(output, "properties");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public String getProperty(String key){
        String value = null;
        try (InputStream input = new FileInputStream(new File(path))) {
            properties.load(input);
            value = properties.getProperty(key);
        } catch (IOException e) {
            e.getMessage();
        }
        return value;
    }


}

