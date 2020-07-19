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

    public AddAppointment(String date) {
        newRecord = true;
        this.date = date;
        mainLayout = new GridPane();
        initLabels();
        initText();
        save = new Button("SAVE");
        initLayout();
        display();
    }

    //for when you are adding to the appointment
    public AddAppointment(String date, Appointment appointment) {
        this.date = appointment.getDate();
        this.patientName = appointment.getPatientName();
        this.time = appointment.getTime();
        this.nurse = appointment.getNurse();
        this.room = appointment.getRoom();
        this.doctor = appointment.getDoctor();
        this.drugsPrescribed = appointment.getDrugsPrescribed();
        this.additionalRemarks = appointment.getAdditionalRemarks();
        this.reasonForAppointment = appointment.getReasonForAppointment();

        newRecord = false;
        mainLayout = new GridPane();
        initLabels();
        initText();
        save = new Button("SAVE");
        initLayout();
        display();
    }

    public void display() {
        Scene scene = new Scene(mainLayout, 500, 540);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add An Appointment");
        window.setScene(scene);
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

        if (patientName != null)
            setTextFields();

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
        patientNameT.setText(patientName);
        timeT.setText(time);
        nurseT.setText(nurse);
        roomT.setText(room);
        doctorT.setText(doctor);
        drugsPrescribedT.setText(drugsPrescribed);
        additionalRemarksT.setText(additionalRemarks);
        reasonForAppointmentT.setText(reasonForAppointment);
    }

    private void saveData() {
        patientName = patientNameT.getText();
        time = timeT.getText();
        date = dateT.getText();
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
            if (newRecord)
                saveToDatabase();
            else
                updateInDatabase();
        }
    }

    //save the new record
    public void saveToDatabase() {

    }

    //update the existing record
    public void updateInDatabase() {

    }
}
