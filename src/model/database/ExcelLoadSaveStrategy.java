package model.database;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Artikel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Pieter Herremans
 */

public class ExcelLoadSaveStrategy  implements StrategyLoadSave {
    @Override
    public ArrayList<Artikel> load() throws IOException, DatabaseException, BiffException {
        File file = getFile();
        ExcelPlugin excel = new ExcelPlugin();
        ArrayList<ArrayList<String>> ArtikelenStrings = excel.read(file);

        //change the ArrayList<String> to a bunch of artikels
        ArrayList<Artikel> artikelen =  new ArrayList<>();
        for (ArrayList<String> artikelString: ArtikelenStrings) {
            if(artikelString != null){
                Artikel artikel = new Artikel(artikelString.get(0), artikelString.get(1), artikelString.get(2), Double.parseDouble(artikelString.get(3)), Integer.parseInt(artikelString.get(4)));
                artikelen.add(artikel);
            }
        }
        return artikelen;
    }

    @Override
    public void save(ArrayList<Artikel> artikelen) throws IOException, DomainException, WriteException, BiffException {

        //Change the Arraylist<Artikel> to Arraylist<Arraylist<String>>

        ArrayList<ArrayList<String>> ArtikelenStrings= new ArrayList<>();
        for (Artikel artikel: artikelen){
            if (artikel != null){
                ArrayList<String> artikelstring = new ArrayList<>();
                artikelstring.add(artikel.getCode());
                artikelstring.add(artikel.getOmschrijving());
                artikelstring.add(artikel.getGroep());
                artikelstring.add(String.valueOf(artikel.getPrijs()));
                artikelstring.add(String.valueOf(artikel.getVoorraad()));
                ArtikelenStrings.add(artikelstring);
            }
        }

        File file = getFile();
        ExcelPlugin excel = new ExcelPlugin();
        excel.write(file, ArtikelenStrings);
    }

    private File getFile(){return new File("src"+File.separator+"bestanden"+File.separator+"artikel.xls");}
}
