package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jxl.read.biff.BiffException;
import model.Artikel;
import model.Observer;
import model.database.DatabaseException;
import model.database.LoadSaveContext;
import model.database.LoadSaveEnum;
import model.database.LoadSaveFactory;
import model.korting.KortingContext;
import model.korting.KortingEnum;
import model.korting.KortingFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Pieter Herremans, Vanhaeren Corentin
 */
public class InstellingController {


    private KortingContext kortingContext = new KortingContext();
    private String path = "src" + File.separator + "bestanden" + File.separator + "KassaApp.properties";
    private File file;
    private Properties properties = new Properties();



    public InstellingController() {
        this.properties = new Properties();
        file = new File(path);
        setKortingStrategy();
    }


    public KortingContext getKortingContext() {
        return kortingContext;
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

    public void setKortingStrategy(){

        String key = "property.typekorting";
        ArrayList<Object> t = new ArrayList<>();
        //slot1: percentage korting, slot2: drempelbedrag korting, slot3: groep korting
        double percentage = Double.parseDouble(getProperty("property.percentagekorting"));
        t.add(percentage);
        double bedarg = Double.parseDouble(getProperty("property.drempelbedragkorting"));
        t.add(bedarg);
        String groep = getProperty("property.groepkorting");
        t.add(groep);

        KortingEnum kortingStrategy = KortingEnum.valueOf(getProperty(key));
        //KortingStrategy kortingStrategy1 = new Geenkorting();
        //kortingContext.setKortingStrategy(kortingStrategy);
        //kortingContext.setKortingStrategy(new KortingFactory().kortingFactory(kortingStrategy));
        kortingContext.setKortingStrategy(new KortingFactory().kortingFactory(kortingStrategy, t));
        //KortingContext kortingContext = new KortingContext();
        //kortingContext.setKortingStrategy(new Geenkorting());
        //System.out.println(args + " korting array");
        //System.out.println(KortingEnum.valueOf(getProperty(key)) + " enum");
        //System.out.println(kortingStrategy + " test");
        //KortingFactory kortingFactory = new KortingFactory();
        //System.out.println(kortingFactory.KortingFactory(kortingStrategy, args) + " test factory");
        // System.out.println(t.size() + " lenght t");
        //System.out.println(kortingContext + " korting contect");
    }
}

