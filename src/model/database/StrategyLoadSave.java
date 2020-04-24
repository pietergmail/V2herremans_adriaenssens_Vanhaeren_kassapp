package model.database;

import java.io.IOException;
import java.util.ArrayList;

public interface StrategyLoadSave {
    ArrayList<Object> load() throws IOException, DatabaseException;
    void save(ArrayList<Object> objecten) throws IOException;
}
