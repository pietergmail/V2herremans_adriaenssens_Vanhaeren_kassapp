package model.database;

import model.Artikel;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Herremans Pieter
 */

public class LoadsaveArtikeltekst implements StrategyLoadSave{

    @Override
    public ArrayList<Artikel> load() throws DatabaseException {
        ArrayList<Artikel> artikelen = new ArrayList<>();
        try{
            Scanner scannerFile = getFile();
            scannerFile.useDelimiter("\n");
            while (scannerFile.hasNextLine()){
                Scanner scannerLijn = new Scanner(scannerFile.nextLine());
                scannerLijn.useDelimiter(",");
                try{
                    Artikel obj = (Artikel) getObject(scannerLijn);
                    artikelen.add(obj);
                } catch (Exception e){
                    throw new DatabaseException(e.toString() + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return artikelen;
    }

    @Override
    public void save(ArrayList<Artikel> artikelen) throws IOException {
        PrintWriter print_line = null;
        try {
            FileWriter write = getFileWriter();
            assert write != null;
            print_line = new PrintWriter(write);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(print_line).close();
    }

    private Scanner getFile() throws FileNotFoundException {
        String path = "src" + File.separator + "bestanden" + File.separator + "artikel.txt";
        return new Scanner(new File(path));
    }


    private Object getObject(Scanner scannerlijn) {
        String code = scannerlijn.next();
        String omschrijving = scannerlijn.next();
        String groep = scannerlijn.next();
        double prijs = Double.parseDouble(scannerlijn.next());
        int voorraad = Integer.parseInt(scannerlijn.next().replace(" ", ""));//anders error omdat er een spatie staat ipv. een integer
        return new Artikel(code, omschrijving, groep, prijs, voorraad);
    }

    private FileWriter getFileWriter() throws IOException {
        return null;
    }

}


