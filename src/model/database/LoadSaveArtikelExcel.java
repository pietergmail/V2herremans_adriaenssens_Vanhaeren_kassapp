package model.database;

import jxl.read.biff.BiffException;
import model.Artikel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.*;

/**
 * @author Pieter Herremans
 */

public class LoadSaveArtikelExcel implements StrategyLoadSave {


    @Override
    public ArrayList<Artikel> load() throws IOException, DatabaseException{
        ArrayList<Artikel> artikelen = new ArrayList<>();
        Workbook workbook = null;

        try{
             workbook = Workbook.getWorkbook(getFile());

             Sheet sheet = workbook.getSheet(0);//chooses first excel sheet
             Cell cell1 = sheet.getCell(0, 0);
             int i = 0;
             while(sheet.getCell(0, i).getContents() != null){//checkt of de volgende rij niet null moet in for lus gaan maar ik weet niet hoe dat moet
                 Cell code = sheet.getCell(0, i);
                 Cell omschrijving = sheet.getCell(1,i);
                 Cell groep = sheet.getCell(2, i);
                 Cell prijs = sheet.getCell(3, i);
                 Cell voorraad = sheet.getCell(4, i);
                 artikelen.add(new Artikel(code.getContents(), omschrijving.getContents(), groep.getContents(), Double.parseDouble(prijs.getContents()),Integer.parseInt(voorraad.getContents())));
                 i++;
             }
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null){
                workbook.close();
            }
        }
        return artikelen;
    }

    @Override
    public void save(ArrayList<Artikel> artikelen) throws IOException, DomainException {

    }

    File getFile() {
        return new File("src"+File.separator+"bestanden"+File.separator+"artikel.xls");
    }
}
