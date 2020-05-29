package model.database;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Artikel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vanhaeren Corentin
 */

public class LoadSaveContext {
    private StrategyLoadSave strategyLoadSave;
    private Map<String, Artikel> artikels;

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

    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        return strategyLoadSave.load();
    }

    public void save(ArrayList<Artikel> artikelen) throws IOException, DomainException, WriteException, BiffException {
        strategyLoadSave.save(artikelen);
    }
}