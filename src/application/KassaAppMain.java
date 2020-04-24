package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import jdk.nashorn.internal.codegen.types.ArrayType;
import model.Artikel;
import model.database.DatabaseException;
import model.database.LoadsaveArtikeltekst;
import view.KassaView;
import view.KlantView;

import java.util.ArrayList;

/**
* @author Herremans Pieter
*/

public class KassaAppMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		KassaView kassaView = new KassaView();
		KlantView klantView = new KlantView();
		LoadsaveArtikeltekst loadsaveArtikeltekst = new LoadsaveArtikeltekst();
		try {
			ArrayList<Object> list = loadsaveArtikeltekst.load();
			for (Object o: list) {
				System.out.println("_________________________________");
				System.out.println(o.toString());
				System.out.println("_________________________________");
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println("Database Not Found ERROR.");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
