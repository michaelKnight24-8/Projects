package sample;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CalendarPane {

    public static final int SURGERY = 1;
    public static final int APPOINTMENT = 2;
    public static final int BOOK_APPOINTMENT = 3;

    public static void display(Scene scene, int context) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        switch (context) {
            case SURGERY:
                window.setTitle("View Scheduled Surgeries");
                break;
            case APPOINTMENT:
                window.setTitle("View Scheduled Appointments");
                break;
            case BOOK_APPOINTMENT:
                window.setTitle("Book An Appointment");
                break;
        }
        window.setScene(scene);
        window.showAndWait();
    }
}
