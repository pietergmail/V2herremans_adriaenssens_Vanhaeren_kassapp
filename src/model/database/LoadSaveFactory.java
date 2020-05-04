package model.database;

/**
 * @author Sateur Maxime
 */


public class LoadSaveFactory {

    public StrategyLoadSave ChooseSaveType(LoadSaveEnum saveType) {
        StrategyLoadSave save = null;

        switch(saveType) {
            case EXCEL: save = new ExcelLoadSaveStrategy();
                break;

            case TEKST: save = new LoadsaveArtikeltekst();
                break;
        }
        return save;
    }
}