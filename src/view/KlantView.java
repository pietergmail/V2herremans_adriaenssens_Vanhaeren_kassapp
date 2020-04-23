package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;	

public class KlantView {
	private Stage stage = new Stage();		
		
	public KlantView(){			
		stage.setTitle("KLANT VIEW");
		stage.setResizable(false);		
		stage.setX(775);
		stage.setY(20);
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500);			
		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();		
	}
}
