package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {
    static boolean save;

    public static void displayInvalidLogin() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("invalid Credentials");
        Label label = new Label("Invalid Credentials");
        Button okBtn = new Button("OK");
        okBtn.setMinWidth(100);
        okBtn.setOnAction(e -> window.close());

        VBox v = new VBox();
        v.getChildren().addAll(label, okBtn);
        window.setScene(new Scene(v,300,150));
        window.showAndWait();
    }

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

    //display the data for the appointment that the person clicked on to view
    public static void displayAppointmentData(Appointment ap) {

        Stage window = new Stage();
        //labels for the information
        Label topLabel, nurseL, nurseT, doctorL, doctorT, drugsL,
                drugsT, reasonL, reasonT, notesL, notesT, header;
        VBox nurseBox, doctorBox, drugsBox, reasonBox, notesBox;
        GridPane apDetails = new GridPane();
        header = new Label("Appointment Summary");
        header.setStyle("-fx-font: 40 Tahoma");
        apDetails.setVgap(20);
        apDetails.setPadding(new Insets(30,0,0,30));
        //first init the vboxes that hold the information
        nurseBox = new VBox();
        doctorBox = new VBox();
        drugsBox = new VBox();
        reasonBox = new VBox();
        notesBox = new VBox();

        //init all the labels, as well as style them as needed
        topLabel = new Label(ap.getDate() + " - " + ap.getTime());
        topLabel.setStyle("-fx-font: 24 Tahoma");

        //nurse stuff
        nurseL = new Label("Nurse");
        nurseT = new Label(ap.getNurse());
        nurseT.setPadding(new Insets(0,0,0,20));
        nurseL.setStyle("-fx-font: 16 Tahoma");
        nurseBox.getChildren().addAll(nurseL, nurseT);

        //doctor stuff
        doctorL = new Label("Doctor");
        doctorT = new Label(ap.getDoctor());
        doctorT.setPadding(new Insets(0,0,0,20));
        doctorL.setStyle("-fx-font: 16 Tahoma");
        doctorBox.getChildren().addAll(doctorL, doctorT);

        //drugs stuff
        drugsL = new Label("Drugs Prescribed");
        drugsT = new Label(ap.getDrugsPrescribed());
        drugsT.setPadding(new Insets(0,0,0,20));
        drugsL.setStyle("-fx-font: 16 Tahoma");
        drugsBox.getChildren().addAll(drugsL, drugsT);

        //reason stuff
        reasonL = new Label("Reason for Appointment");
        reasonT = new Label(ap.getReasonForAppointment());
        reasonT.setPadding(new Insets(0,0,0,20));
        reasonT.setMaxWidth(400);
        reasonT.setWrapText(true);
        reasonL.setStyle("-fx-font: 16 Tahoma");
        reasonBox.getChildren().addAll(reasonL, reasonT);

        //notes stuff
        notesL = new Label("Additional Notes");
        notesT = new Label(ap.getAdditionalRemarks());
        notesT.setMaxWidth(400);
        notesT.setWrapText(true);
        notesT.setPadding(new Insets(0,0,0,20));
        notesL.setStyle("-fx-font: 16 Tahoma");
        notesBox.getChildren().addAll(notesL, notesT);

        //now add them in
        apDetails.addRow(0, header);
        apDetails.addRow(1, new Separator());
        apDetails.addRow(2, topLabel);
        apDetails.addRow(3, nurseBox);
        apDetails.addRow(4, doctorBox);
        apDetails.addRow(5, drugsBox);
        apDetails.addRow(6, reasonBox);
        apDetails.addRow(7, notesBox);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Appointment Summary");
        window.setScene(new Scene(apDetails,500,600));
        window.showAndWait();
    }

    public static boolean closeProgram() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirm close");

        VBox content = new VBox();
        Label title = new Label("You sure that you want to close?");
        Button ok = new Button("OK");
        ok.setOnAction(e -> save = true );

        Button close = new Button("NO");
        close.setOnAction(e -> save = false );
        content.getChildren().addAll(title, ok, close);
        window.setScene(new Scene(content,300,150));
        window.showAndWait();

        return save;
    }
}
