package application;
	
import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;
import jxl.read.biff.BiffException;
import model.KassaVerkoop;
import model.database.DatabaseException;
import view.KassaView;
import view.KlantView;

import java.io.IOException;

/**
* @author Zeno Adriaansen, Vanhaeren Corentin, , Sateur Maxime
*/

public class KassaAppMain extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {
			InstellingController instellingController = new InstellingController();
			ProductController productController = new ProductController(instellingController);
			KassaVerkoop kassaVerkoop = new KassaVerkoop();
			LogController logController = new LogController();
			KassaviewController kassaviewController = new KassaviewController(kassaVerkoop, instellingController, productController, logController);
			KassaView kassaView = new KassaView(kassaviewController);
			KlantviewController klantviewController = new KlantviewController(kassaVerkoop);
			KlantView klantView = new KlantView(klantviewController);
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}


/*
		try {

			InstellingController instellingController = new InstellingController();
			KassaviewController kassaviewController = new KassaviewController(kassaVerkoop, instellingController);
			KlantviewController klantviewController = new KlantviewController(kassaviewController);

			KassaView kassaView = new KassaView(kassaviewController);
			KlantView klantView = new KlantView(klantviewController);
			kassaVerkoop.addObserver(kassaviewController);
			//kassaVerkoop.addObserver(klantviewController);
			//kassaVerkoop.addObserver(kassaView);

		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
*/
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
