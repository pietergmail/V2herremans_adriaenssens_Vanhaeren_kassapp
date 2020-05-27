package view.panels;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LogPane extends GridPane {
    public LogPane() {
        VBox p1 = new VBox(10);
        Label label1 = new Label("Log:");
        label1.setPadding(new Insets(10));
        label1.setFont(new Font("Arial", 20));
        p1.getChildren().addAll(label1);

        this.getChildren().addAll(p1);
    }

}
