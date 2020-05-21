package application;
	
import controller.InstellingController;
import controller.KassaviewController;
import controller.KlantviewController;
import javafx.application.Application;
import javafx.stage.Stage;
import jxl.read.biff.BiffException;
import model.Artikel;
import model.KassaVerkoop;
import model.ModelFacade;
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
		KassaVerkoop kassaVerkoop = new KassaVerkoop();

		try {

			InstellingController instellingController = new InstellingController();
			KassaviewController kassaviewController = new KassaviewController(new ModelFacade(kassaVerkoop, instellingController));
			KlantviewController klantviewController = new KlantviewController(new ModelFacade(kassaVerkoop, null));
			ModelFacade modelFacade = new ModelFacade(kassaVerkoop, null);
			KassaView kassaView = new KassaView(kassaviewController);
			KlantView klantView = new KlantView(klantviewController);
			//kassaVerkoop.addObserver(kassaviewController);
			kassaVerkoop.addObserver(klantView);
			//kassaVerkoop.addObserver(klantviewController);
			kassaVerkoop.addObserver(modelFacade);

		} catch (DatabaseException e) {
			e.printStackTrace();
		}

		/*
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

		 */
	}

	public static void main(String[] args) {
		launch(args);
	}
}
