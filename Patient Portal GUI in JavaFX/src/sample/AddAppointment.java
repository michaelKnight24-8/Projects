package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddAppointment {
    public String date, patientName, nurse, doctor, drugsPrescribed,
            additionalRemarks, reasonForAppointment, time, room;
    public Label dateL, patientNameL, nurseL, doctorL, drugsPrescribedL,
            additionalRemarksL, reasonForAppointmentL, timeL, roomL;
    public PText dateT, patientNameT, nurseT, doctorT, timeT, roomT;
    public TextArea reasonForAppointmentT, additionalRemarksT, drugsPrescribedT;
    public GridPane mainLayout;
    public Button save;
    public boolean newRecord;
    private Connection conn;
    private Appointment appointment;
    private boolean editing;

    public AddAppointment(String date, Connection conn) {
        this.conn = conn;
        this.date = date;
        mainLayout = new GridPane();
        initLabels();
        initText();
        save = new Button("SAVE");
        initLayout();
        editing = false;
    }

    //for when you are adding to the appointment
    public AddAppointment(String date, Appointment appointment, Connection conn) {
        this(date, conn);
        this.appointment = appointment;
        setTextFields();
    }

    public void display() {
        if (editing) deleteFromDataBase();
        Scene scene = new Scene(mainLayout, 500, 540);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add An Appointment");
        window.setScene(scene);

        save.setOnAction(e -> {
            saveData();
            window.close();
        });

        window.showAndWait();
    }

    private void initLabels() {
        dateL = new Label("Date");
        patientNameL = new Label("Patient");
        nurseL = new Label("Nurse");
        doctorL = new Label("Doctor");
        drugsPrescribedL = new Label("Drugs Prescribed");
        additionalRemarksL = new Label("Additional Remarks");
        reasonForAppointmentL = new Label("Reason For Appointment");
        timeL = new Label("Time");
        roomL = new Label("Room");
    }

    private void initText() {
        dateT = new PText(75);
        dateT.setText(this.date);
        dateT.setEditable(false);
        patientNameT = new PText(200);
        timeT = new PText(50);
        nurseT = new PText(200);
        doctorT = new PText(200);
        roomT = new PText(50);

        drugsPrescribedT = new TextArea();
        drugsPrescribedT.setMinHeight(40);
        drugsPrescribedT.setMaxWidth(200);
        drugsPrescribedT.setWrapText(true);

        additionalRemarksT = new TextArea();
        additionalRemarksT.setMinHeight(40);
        additionalRemarksT.setMaxWidth(200);
        additionalRemarksT.setWrapText(true);

        reasonForAppointmentT = new TextArea();
        reasonForAppointmentT.setMinHeight(40);
        reasonForAppointmentT.setMaxWidth(200);
        reasonForAppointmentT.setWrapText(true);
    }

    private void initLayout() {
        HBox saveBtn = new HBox();
        saveBtn.setPadding(new Insets(0, 0, 30, 150));
        saveBtn.getChildren().add(save);
        save.setStyle("-fx-background-color: lightskyblue");

        HBox labels = new HBox(60);
        HBox textFields = new HBox(20);
        labels.getChildren().addAll(dateL, timeL, roomL);
        textFields.getChildren().addAll(dateT, timeT, roomT);

        mainLayout.addRow(0, patientNameL, labels);
        mainLayout.addRow(1, patientNameT, textFields);
        mainLayout.addRow(2, nurseL, doctorL);
        mainLayout.addRow(3, nurseT, doctorT);
        mainLayout.addRow(4, reasonForAppointmentL, drugsPrescribedL);
        mainLayout.addRow(5, reasonForAppointmentT, drugsPrescribedT);
        mainLayout.addRow(6, additionalRemarksL);
        mainLayout.addRow(7, additionalRemarksT);
        mainLayout.addRow(8, new Label(""), saveBtn);
        mainLayout.setVgap(20);
        mainLayout.setHgap(10);
        mainLayout.setPadding(new Insets(30, 0, 0, 30));
    }

    private void setTextFields() {
        patientNameT.setText(appointment.getPatientName());
        timeT.setText(appointment.getTime());
        nurseT.setText(appointment.getNurse());
        roomT.setText(appointment.getRoom());
        doctorT.setText(appointment.getDoctor());
        drugsPrescribedT.setText(appointment.getDrugsPrescribed());
        additionalRemarksT.setText(appointment.getAdditionalRemarks());
        reasonForAppointmentT.setText(appointment.getReasonForAppointment());
        dateT.setText(appointment.getDate());
        editing = true;
    }

    private void saveData() {
        patientName = patientNameT.getText();
        time = timeT.getText();
        date = DateFormat.fixDate(dateT.getText());
        room = roomT.getText();
        nurse = nurseT.getText();
        doctor = doctorT.getText();
        drugsPrescribed = drugsPrescribedT.getText();
        additionalRemarks = additionalRemarksT.getText();
        reasonForAppointment = reasonForAppointmentT.getText();

        if (patientName.equals("") || time.equals("") || date.equals("")) {
            Alert.display();
        } else {
            //save OR update the file depending on what the user is doing!
            saveToDatabase();
        }
    }

    //save the new record
    public void saveToDatabase() {

        try {
            String SQL = "INSERT INTO appointments (date, patientName, nurse, doctor," +
                    " drugsPrescribed, additionalRemarks, reasonForAppointment, time, room)" +
                    " VALUES (?,?,?,?,?,?,?,?,?);";

            PreparedStatement pstm = conn.prepareStatement(SQL);
            pstm.setString(1, date);
            pstm.setString(2, patientName);
            pstm.setString(3, nurse);
            pstm.setString(4, doctor);
            pstm.setString(5, drugsPrescribed);
            pstm.setString(6, additionalRemarks);
            pstm.setString(7, reasonForAppointment);
            pstm.setString(8, time);
            pstm.setString(9, room);
            //do it!
            pstm.executeUpdate();

        } catch (SQLException err) {
            System.out.println(err);
        }
    }

    public void deleteFromDataBase() {

        try {
            String SQL = "DELETE FROM appointments WHERE patientName = ? AND nurse = ? AND room = ?";
            PreparedStatement pstm = conn.prepareStatement(SQL);
            pstm.setString(1, appointment.getPatientName());
            pstm.setString(2, appointment.getNurse());
            pstm.setString(3, appointment.getRoom());
            pstm.executeUpdate();

        } catch (SQLException error) {
            System.out.println("SQL ERROR: " + error);
        }
    }
}
