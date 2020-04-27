package model.database;

import model.Artikel;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Herremans Pieter
 */

public abstract class LoadsavetekstTemplate implements StrategyLoadSave{

    abstract Scanner getFile() throws FileAlreadyExistsException, FileNotFoundException;
    abstract Object getObject(Scanner scannerlijn);

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
        } catch (FileAlreadyExistsException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return artikelen;
    }

    abstract FileWriter getFileWriter() throws IOException;

    public final void save(ArrayList<Artikel> artikelen){
        PrintWriter print_line = null;
        try {
            FileWriter write = getFileWriter();
            print_line = new PrintWriter(write);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(print_line).close();
    }

}
