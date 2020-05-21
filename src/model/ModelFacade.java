package model;

import controller.InstellingController;
import jxl.read.biff.BiffException;
import model.database.*;
import model.korting.*;
import view.KassaView;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import static model.korting.KortingEnum.GEEN;

/**
 * @author Vanhaeren Corentin, Sateur Maxime
 */

public class ModelFacade implements Observer{

    private KassaView kassaView;
    private KassaVerkoop kassaVerkoop;
    private ArtikelWinkelmand artikelWinkelmand;
    private InstellingController instellingController;
    //private LoadsaveArtikeltekst loadsaveArtikeltekst;
    LoadSaveContext loadSaveContext = new LoadSaveContext();
    KortingContext kortingContext = new KortingContext();
    ArrayList<Artikel> producten = new ArrayList<>();

    private Properties properties = new Properties();
    private String path = "src" + File.separator + "bestanden" + File.separator + "KassaApp.properties";

    //LoadSaveFactory loadSaveFactory;
    //StrategyLoadSave strategyLoadSave;
/*
    public KassaviewController(KassaVerkoop kassaVerkoop, KassaView kassaView){
        this.kassaVerkoop = kassaVerkoop;
        this.kassaView = kassaView;
    }

 */
    public ModelFacade(KassaVerkoop kassaVerkoop, InstellingController instellingController) throws DatabaseException {
        this.kassaVerkoop = kassaVerkoop;
        this.instellingController = instellingController;
        setKortingStrategy();
        //kassaView = new KassaView(this);
    }

    //methodes voor view
    public void addProductKassaVerkoop(Artikel artikel) {
        kassaVerkoop.addArtikelWinkelkar(artikel);
    }

    public void removeProductKassaVerkoop(Artikel artikel) {
        kassaVerkoop.removeArtikelWinkelkar(artikel);
    }

    public void setOnHold() {
        kassaVerkoop.setOnHold();
    }

    public void setOffHold() {
        kassaVerkoop.setOffHold();
    }

    public Artikel getArtikel(String code) throws DatabaseException, IOException, BiffException {
        for (Artikel a : this.loadinMemory()) {
            if (a.getCode().equals(code)) {
                return a;
            }
        }
        throw new IllegalArgumentException("product niet gevonden");
    }

    public double totaalPrijs() {
        return kassaVerkoop.getTotalPrijs();
    }

    /*
    public ArrayList<ArtikelWinkelmand> getWinkelmandMetAantal() {
        ArrayList<ArtikelWinkelmand> winkelmand = new ArrayList<>();
        for (Map.Entry<Artikel, Integer> entry : kassaVerkoop.getWinkelmandMap().entrySet()) {
            ArtikelWinkelmand artikel = new ArtikelWinkelmand();
            artikel.setCode(entry.getKey().getCode());
            artikel.setOmschrijving((entry.getKey().getOmschrijving()));
            artikel.setGroep(entry.getKey().getGroep());
            artikel.setPrijs(entry.getKey().getPrijs());
            artikel.setAantal(entry.getValue());
            winkelmand.add(artikel);
        }
        return winkelmand;
    }

     */

@Override
    public void update(String eventType, Artikel artikel) {
        if (eventType.equals("add_product_winkelkar")) {
            //code te laten uitvoeren
            //kassaView.updateTableview(this);
            System.out.println("Product " + artikel.getOmschrijving() + " is toegevoegd.");
        }
        if(eventType.equals("remove_product_winkelkar")){
            System.out.println("Product " + artikel.getOmschrijving() + " is verwijdert.");
        }
        if(eventType.equals("setOnHold")){
            System.out.println("Winkelkar on hold gezet.");
        }
        if(eventType.equals("setOffHold")){
            System.out.println("Winkelkar off hold gezet.");
        }
    }

    /*
    public void update(String eventType) {

    }

     */
/*
    public void setProperty(String key, String value) {
        instellingController.setProperty(key, value);
    }

    public String getProperty(String key) {
        return instellingController.getProperty(key);
    }

 */
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

    ///
    public void setLoadStrategy(String key){
        LoadSaveEnum saveStrategy = LoadSaveEnum.valueOf(getProperty(key));
        loadSaveContext.setStrategyLoadSave((new LoadSaveFactory().ChooseSaveType(saveStrategy)));
    }


    public ArrayList<Artikel> getWinkelmandje() {
        return kassaVerkoop.getWinkelmandje();
    }

    public ArrayList<ArtikelWinkelmand> getWinkelmandMetAantal() {
        return kassaVerkoop.getWinkelmandMetAantal();
    }

    public KassaVerkoop getKassaVerkoop() {
        return kassaVerkoop;
    }

    public void setKassaVerkoop(KassaVerkoop kassaVerkoop) {
        this.kassaVerkoop = kassaVerkoop;
    }

    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        setLoadStrategy("property.filetype");
        return loadSaveContext.load();
    }

    public ArrayList<Artikel> loadinMemory() throws IOException, DatabaseException, BiffException {
        if (producten.isEmpty()) {
            producten = this.load();
        } else {
            return producten;
        }
        return producten;
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

    public double totaalPrijsKorting(){
        return kassaVerkoop.berekenPrijsMetKorting(kortingContext);
    }
}
