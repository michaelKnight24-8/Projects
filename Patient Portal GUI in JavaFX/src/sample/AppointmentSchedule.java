package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppointmentSchedule {

    public TableView <Appointment> table;
    public VBox vbox;
    public String day;
    public Connection conn;

    public AppointmentSchedule(String day, Connection conn) {
        table = new TableView<>();
        vbox = new VBox(5);
        this.day = day;
        this.conn = conn;
        initTable();
        display();
    }

    private ObservableList<Appointment> getAppointments() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Connection conn = DBUtil.getConnection();
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery("SELECT * FROM appointments");
            while (rs.next()) {
                appointments.add(new Appointment(rs.getString("date"), rs.getString("patientName"),
                        rs.getString("nurse"), rs.getString("doctor"), rs.getString("drugsPrescribed"),
                        rs.getString("additionalRemarks"), rs.getString("reasonForAppointment"),
                        rs.getString("time"), rs.getString("room")));
            }
        } catch (SQLException error){
            System.out.println("SQL ERROR: " + error);
        }
        return appointments;
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Appointments scheduled for selected day");
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
        ContextMenu menu = new ContextMenu();
        MenuItem view = new MenuItem("View/Edit");
        view.setOnAction(e -> {
            AddAppointment aa = new AddAppointment(this.day, table.getSelectionModel().getSelectedItem(), conn);
            aa.display();
        });
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(e -> {
            AddAppointment aa = new AddAppointment(this.day, table.getSelectionModel().getSelectedItem(), conn);
            aa.deleteFromDataBase();
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        });

        menu.getItems().addAll(view, new SeparatorMenuItem(), delete);
        table.setOnMouseClicked(e -> {
            if (table.getSelectionModel().getSelectedItem() != null)
                menu.show(table, e.getScreenX(), e.getScreenY());
        });

        vbox.getChildren().addAll(label, table);
    }
}
