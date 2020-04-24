package model.database;

import model.Artikel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;

/**
 * @author Herremans Pieter
 */

public class LoadsaveArtikeltekst extends LoadsavetekstTemplate{


    @Override
    Scanner getFile() throws FileAlreadyExistsException, FileNotFoundException {
        String path = "src" + File.separator + "bestanden" + File.separator + "artikel.txt";
        return new Scanner(new File(path));
    }

    @Override
    Object getObject(Scanner scannerlijn) {
        String code = scannerlijn.next();
        String omschrijving = scannerlijn.next();
        String groep = scannerlijn.next();
        double prijs = Double.parseDouble(scannerlijn.next());
        int voorraad = Integer.parseInt(scannerlijn.next().replace(" ", ""));//anders error omdat er een spatie staat ipv. een integer
        Artikel artikel = new Artikel(code, omschrijving, groep, prijs, voorraad);
        return artikel;
    }

    @Override
    FileWriter getFileWriter() throws IOException {
        return null;
    }
}
