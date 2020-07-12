package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AppointmentSchedule {

    public TableView <Appointment> table;
    public VBox vbox;
    public String day;

    public AppointmentSchedule(String day) {
        table = new TableView<>();
        vbox = new VBox(5);
        this.day = day;
        initTable();
        display();
    }

    private ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments.add(new Appointment("today", "michael", "some email", "NUrse me!",
                "Doctor you!", "hard core meth", "He needs HELP",
                "Penis", "1:00pm", "4b"));
        return appointments;
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Surgeries schedule for selected day");
        window.setScene(new Scene(vbox,402,400));
        window.setMinWidth(402);
        window.setMaxWidth(415);
        window.showAndWait();
    }

    private void initTable() {

        //for name column
        TableColumn <Appointment, String> patientName = new TableColumn<>("Patient");
        patientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        patientName.setMinWidth(100);

        //for time column
        TableColumn <Appointment, String> time = new TableColumn<>("Time");
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        time.setMinWidth(100);

        //for nurse column
        TableColumn <Appointment, String> nurse = new TableColumn<>("Nurse");
        nurse.setCellValueFactory(new PropertyValueFactory<>("nurse"));
        nurse.setMinWidth(100);

        //for room number
        TableColumn <Appointment, String> room = new TableColumn<>("Room");
        room.setCellValueFactory(new PropertyValueFactory<>("room"));
        room.setMinWidth(100);

        table.setItems(getAppointments());
        table.getColumns().addAll(patientName, time, nurse, room);

        Label label = new Label("APPOINTMENTS FOR " + this.day);
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(402);
        label.setStyle("-fx-font: 24 Arial;");
        vbox.getChildren().addAll(label, table);
    }
}
