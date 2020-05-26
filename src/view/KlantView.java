package view;

import controller.KlantviewController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.panels.KlantPane;

public class KlantView {
    private Stage stage = new Stage();

    public KlantView(KlantviewController controller){
        stage.setTitle("KLANT VIEW");
        stage.setResizable(false);
        stage.setX(775);
        stage.setY(20);
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500);
        KlantPane klantPane = new KlantPane(controller);
        controller.setPane(klantPane);
        root.getChildren().add(klantPane);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

    }
}
