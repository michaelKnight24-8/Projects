package sample;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CalendarPane {
    public static void display(Scene scene) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("View Scheduled Surgeries");
        window.setScene(scene);
        window.showAndWait();
    }
}
