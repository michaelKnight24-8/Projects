package sample;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SurgeryWindow {

    public static void display(Scene scene) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Surgeries schedule for selected day");
        window.setScene(scene);
        window.setMinWidth(830);
        window.setMaxWidth(950);
        window.showAndWait();
    }
}
