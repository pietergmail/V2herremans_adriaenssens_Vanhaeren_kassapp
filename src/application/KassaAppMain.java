package application;
	
import controller.KassaviewController;
import javafx.application.Application;
import javafx.stage.Stage;
import jxl.read.biff.BiffException;
import model.Artikel;
import model.KassaVerkoop;
import model.database.DatabaseException;
import model.database.ExcelLoadSaveStrategy;
import view.KassaView;
import view.KlantView;

import java.io.IOException;
import java.util.ArrayList;

/**
* @author Zeno Adriaansen, Vanhaeren Corentin
*/

public class KassaAppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			KassaVerkoop kassaVerkoop = new KassaVerkoop();
			KassaviewController kassaviewController = new KassaviewController(kassaVerkoop);
			kassaVerkoop.addObserver(kassaviewController);
			KassaView kassaView = new KassaView(kassaviewController);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		KlantView klantView = new KlantView();
		ExcelLoadSaveStrategy loadsaveArtikelexcel = new ExcelLoadSaveStrategy();
		ArrayList<Artikel> list = null;
		try {
			list = loadsaveArtikelexcel.load();
		} catch (IOException | BiffException | DatabaseException e) {
			e.printStackTrace();
		}

		assert list != null;
		for (Object o: list) {
				System.out.println("_________________________________");
				System.out.println(o.toString());
				System.out.println("_________________________________");
			}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
