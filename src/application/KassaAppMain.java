package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import view.KassaView;
import view.KlantView;

public class KassaAppMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		KassaView kassaView = new KassaView();
		KlantView klantView = new KlantView();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
