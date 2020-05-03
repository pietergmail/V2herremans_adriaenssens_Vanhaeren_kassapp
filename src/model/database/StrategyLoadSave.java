package model.database;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Artikel;

import java.io.IOException;
import java.util.ArrayList;

public interface StrategyLoadSave {
    ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException;
    void save(ArrayList<Artikel> artikelen) throws IOException, DomainException, WriteException, BiffException;
}
