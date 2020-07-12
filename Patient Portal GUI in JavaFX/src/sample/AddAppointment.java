package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddAppointment {
    public String date, patientName, patientEmail, nurse, doctor, drugsPrescribed,
            additionalRemarks, reasonForAppointment;
    public Label dateL, patientNameL, patientEmailL, nurseL, doctorL, drugsPrescribedL,
            additionalRemarksL, reasonForAppointmentL;
    public PText dateT, patientNameT, patientEmailT, nurseT, doctorT;
    public TextArea reasonForAppointmentT, additionalRemarksT, drugsPrescribedT;
    public GridPane mainLayout;
    public Button save;

    public AddAppointment() {
        mainLayout = new GridPane();
        initLabels();
        initText();
        save = new Button("SAVE");
        initLayout();
        display();
    }

    public void display() {
        Scene scene = new Scene(mainLayout, 800,400);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add An Appointment");
        window.setScene(scene);
        window.showAndWait();
    }

    private void initLabels() {
        dateL = new Label("Date");
        patientNameL = new Label("Patient");
        patientEmailL = new Label("Email");
        nurseL = new Label("Nurse");
        doctorL = new Label("Doctor");
        drugsPrescribedL = new Label("Drugs Prescribed");
        additionalRemarksL = new Label("Additional Remarks");
        reasonForAppointmentL = new Label("Reason For Appointment");
    }

    private void initText() {
        dateT = new PText(50);
        patientNameT = new PText(200);
        patientEmailT = new PText(200);
        nurseT = new PText(200);
        doctorT = new PText(200);

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

    private void saveData() {

    }

    private void initLayout() {

    }
}
