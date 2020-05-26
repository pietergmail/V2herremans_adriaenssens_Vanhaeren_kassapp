package model.database;

import javafx.scene.control.Button;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Artikel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Vanhaeren Corentin
 */

public class LoadSaveContext {
    private StrategyLoadSave strategyLoadSave;
    private Map<String, Artikel> artikels;
    //private Properties properties = new Properties();
    //private String path = "src" + File.separator + "bestanden" + File.separator + "KassaApp.properties";


    public LoadSaveContext(StrategyLoadSave strategyLoadSave) throws DatabaseException, IOException, BiffException {
        this.strategyLoadSave = strategyLoadSave;
        artikels = new HashMap<>();

        ArrayList<Artikel> a = strategyLoadSave.load();
        for (Artikel artikel: a){
            artikels.put(artikel.getCode(), artikel);
        }
    }

    public Artikel getArtikel(String code){
        if(artikels.containsKey(code)) return artikels.get(code);
        else{
            throw new IllegalArgumentException("Artikel niet in database.");
        }
    }

    /*
    public String getProperty(){
        String key = "property.filetype";
        String value = null;
        try(InputStream input = new FileInputStream(new File(path))) {
            properties.load(input);
            value = properties.getProperty(key);
        }catch (Exception e){
            e.getMessage();
        }
        return value;
    }

     */

    public void setStrategyLoadSave(StrategyLoadSave strategyLoadSave){
        this.strategyLoadSave = strategyLoadSave;
    }

    /*
    public StrategyLoadSave setLoadStrategy(){
        LoadSaveEnum saveStrategy = LoadSaveEnum.valueOf(getProperty());
        return new LoadSaveFactory().ChooseSaveType(saveStrategy);
    }

     */


/*
    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        strategyLoadSave = setLoadStrategy();
        return strategyLoadSave.load();
    }

    public void save(ArrayList<Artikel> artikelen) throws IOException, DomainException, WriteException, BiffException {
        strategyLoadSave = setLoadStrategy();
        strategyLoadSave.save(artikelen);
    }

 */

    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        //strategyLoadSave = setLoadStrategy();
        return strategyLoadSave.load();
    }

    public void save(ArrayList<Artikel> artikelen) throws IOException, DomainException, WriteException, BiffException {
        //strategyLoadSave = setLoadStrategy();
        strategyLoadSave.save(artikelen);
    }
}