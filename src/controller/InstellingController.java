package controller;

import model.database.*;
import model.korting.KortingContext;
import model.korting.KortingEnum;
import model.korting.KortingFactory;
import model.korting.KortingStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Pieter Herremans, Vanhaeren Corentin, Sateur Maxime
 */
public class InstellingController {


    private KortingContext kortingContext = new KortingContext();
    private String path = "src" + File.separator + "bestanden" + File.separator + "KassaApp.properties";
    private Properties properties;



    public InstellingController() {
        this.properties = new Properties();
        File file = new File(path);
        setKortingStrategy();
    }


    public KortingContext getKortingContext() {
        return kortingContext;
    }

    void setProperty(String key, String value){
        try (OutputStream output = new FileOutputStream(new File(path))) {
            properties.setProperty(key, value);
            properties.store(output, "properties");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    String getProperty(String key){
        String value = null;
        try (InputStream input = new FileInputStream(new File(path))) {
            properties.load(input);
            value = properties.getProperty(key);
        } catch (IOException e) {
            e.getMessage();
        }
        return value;
    }

    void setLoadSaveStrategy(LoadSaveEnum loadSaveEnum){
        if(loadSaveEnum == null) throw new IllegalArgumentException("De loadsavekeuze is leeg");
        System.out.println("Loadsavestrategy: " + loadSaveEnum.toString());
        this.setProperty("property.filetype", loadSaveEnum.toString());
    }

    StrategyLoadSave getLoadSaveStrategy(){
        LoadSaveEnum loadSaveEnum = LoadSaveEnum.valueOf(getProperty("property.filetype"));
        if (loadSaveEnum.equals(LoadSaveEnum.EXCEL)){
            System.out.println("inladen langs EXCEL");
            return new ExcelLoadSaveStrategy();
        }else if (loadSaveEnum.equals(LoadSaveEnum.TEKST)){
            System.out.println("inladen langs TEKST");
            return new LoadsaveArtikeltekst();
        }else{
            System.out.println("property niet gelezen, fout in het bestand");
            return new LoadsaveArtikeltekst();
        }
    }

    public void setTypeKorting(KortingEnum kortingEnum){
        if(kortingEnum == null) throw new IllegalArgumentException("Kortingenum is empty.");
        System.out.println("typekorting: " + kortingEnum);
        this.setProperty("property.typekorting", kortingEnum.toString());
    }

    void setKortingStrategy(){
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
        kortingContext.setKortingStrategy(new KortingFactory().kortingFactory(kortingStrategy, t));
    }

    KortingStrategy getKortingStrategy(){
        KortingEnum keuze = KortingEnum.valueOf(getProperty("property.typekorting"));
        ArrayList<Object> args = new ArrayList<>();
        switch(keuze){
            case GROEPKORTING:
                //kan meer efficient geschreven worden
                String temp = getProperty("property.percentagekorting");
                Double temp2 = Double.parseDouble(temp);
                args.add(temp2);
                args.add(null);
                args.add(getProperty("property.groepkorting"));
                break;
            case DREMPELKORTING:
                args.add(Double.parseDouble(getProperty("property.percentagekorting")));
                args.add(Double.parseDouble(getProperty("property.drempelbedragkorting")));
                break;
            case DUURSTEKORTING:
                String tem = getProperty("property.percentagekorting");
                Double tem2 = Double.parseDouble(tem);
                args.add(tem2);
                break;
            case GEEN:
                break;
        }
        return new KortingFactory().kortingFactory(keuze, args);
    }
}

