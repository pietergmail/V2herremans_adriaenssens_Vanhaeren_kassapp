package view;

import controller.KassaviewController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jxl.read.biff.BiffException;
import model.database.DatabaseException;

import java.io.IOException;

public class KassaView {
	private Stage stage = new Stage();
	private KassaviewController kassaviewController;
		
	public KassaView(KassaviewController kassaviewController) throws DatabaseException, IOException, BiffException {
		this.kassaviewController = kassaviewController;
		stage.setTitle("KASSA VIEW");
		stage.setResizable(false);		
		stage.setX(20);
		stage.setY(20);
		Group root = new Group();
		Scene scene = new Scene(root, 750, 500);
		BorderPane borderPane = new KassaMainPane(this.kassaviewController);
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());
		root.getChildren().add(borderPane);
		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();		
	}
}