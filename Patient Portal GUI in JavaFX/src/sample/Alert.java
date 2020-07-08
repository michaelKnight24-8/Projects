package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {
    public static void display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error!");

        Label topLabel = new Label("All fields are required");
        topLabel.setPadding(new Insets(0,0,0,35));
        Label middleLabel = new Label("You have left a field empty and a");
        Label bottomLabel = new Label("value must be entered");
        bottomLabel.setPadding(new Insets(0,0,0,35));
        bottomLabel.setStyle("-fx-font: 16 Tahoma;");
        middleLabel.setStyle("-fx-font: 16 Tahoma;");
        topLabel.setStyle("-fx-font: 16 Tahoma;");
        Button okBtn = new Button("OK");
        okBtn.setMinWidth(100);

        VBox lay = new VBox();
        VBox button = new VBox();

        button.setPadding(new Insets(20,0,0,60));
        button.getChildren().add(okBtn);
        lay.setPadding(new Insets(20,0,0,40));
        lay.getChildren().addAll(topLabel,middleLabel,bottomLabel, button);

        okBtn.setOnAction(e -> window.close());
        window.setScene(new Scene(lay,300,150));
        window.showAndWait();
    }
}
