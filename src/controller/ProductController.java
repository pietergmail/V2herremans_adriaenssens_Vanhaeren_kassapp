package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Artikel;
import model.database.DatabaseException;
import model.database.DomainException;
import model.database.LoadSaveContext;
import model.database.StrategyLoadSave;
import view.panels.ProductOverviewPane;

import java.io.IOException;
import java.util.ArrayList;

public class ProductController {

    private LoadSaveContext loadSaveContext;
    private ProductOverviewPane pane;

    public ProductController(InstellingController instellingController) throws DatabaseException, IOException, BiffException {
        StrategyLoadSave loadSaveStrategy = instellingController.getLoadSaveStrategy();
        loadSaveContext = new LoadSaveContext(loadSaveStrategy);
    }

    ObservableList<Artikel> loadData() throws DatabaseException, IOException, BiffException {
        return FXCollections.observableArrayList(this.loadArtikels());
    }

    ArrayList<Artikel> loadArtikels() throws DatabaseException, IOException, BiffException {
    return loadSaveContext.load();
    }

    Artikel getArtikel(String code){
        return loadSaveContext.getArtikel(code);
    }

    void setProductPane(ProductOverviewPane pane){
        this.pane = pane;
    }

    void pasVoorraadAan(Artikel artikel) throws IOException, DatabaseException, BiffException, DomainException, WriteException {
        ArrayList<Artikel> artikels = new ArrayList<>(loadArtikels());

        for(Artikel a : artikels){
            if(a.getCode().equals(artikel.getCode())){
                a.verminderVoorraad();
                break;
            }
        }
        loadSaveContext.save(artikels);
    }

    void updateProducts() throws DatabaseException, IOException, BiffException {
        pane.updateProducts();
    }
}
