package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.Artikel;
import model.database.DatabaseException;
import model.database.LoadSaveArtikelExcel_old;
import view.KassaView;
import view.KlantView;

import java.util.ArrayList;

/**
* @author Zeno Adriaansen
*/

public class KassaAppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			KassaView kassaView = new KassaView();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		KlantView klantView = new KlantView();
		LoadSaveArtikelExcel_old loadsaveArtikelexcel = new LoadSaveArtikelExcel_old();
			ArrayList<Artikel> list = loadsaveArtikelexcel.load();
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
