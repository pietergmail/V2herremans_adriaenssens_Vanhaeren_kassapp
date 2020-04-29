package model.database;

import model.Artikel;

import java.io.IOException;
import java.util.ArrayList;

public interface StrategyLoadSave {
    ArrayList<Artikel> load() throws IOException, DatabaseException;
    void save(ArrayList<Artikel> artikelen) throws IOException, DomainException;
}
