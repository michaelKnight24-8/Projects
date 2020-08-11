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


// little utility class for displaying alerts to the screen
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

    // for displaying to the user that they have left fields empty in any of the pages
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

        //contain the information for all the different boxes
        VBox nurseBox, doctorBox, drugsBox, reasonBox, notesBox;

        GridPane apDetails = new GridPane();
        apDetails.setVgap(20);
        apDetails.setPadding(new Insets(30,0,0,30));

        header = new Label("Appointment Summary");
        header.setStyle("-fx-font: 40 Tahoma");

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

    //same thing as for the appointment, except now for the surgeries
    public static void displaySurgicalData(Surgery s) {

        Stage window = new Stage();
        //labels for the information
        Label surgeonL, surgeonT, surgeryTypeL, surgeryTypeT, rnL, rnT, scrubL,
                scrubT, resultsL, resultsT, header, topLabel;
        VBox surgeonBox, rnBox, typeBox, scrubBox, resultsBox;

        GridPane sDetails = new GridPane();
        sDetails.setVgap(20);
        sDetails.setPadding(new Insets(30,0,0,30));

        header = new Label("Surgery Summary");
        header.setStyle("-fx-font: 40 Tahoma");

        //first init the vboxes that hold the information
        surgeonBox = new VBox();
        rnBox = new VBox();
        typeBox = new VBox();
        scrubBox = new VBox();
        resultsBox = new VBox();

        //init all the labels, as well as style them as needed
        topLabel = new Label(s.getDate() + " - " + s.getTime());
        topLabel.setStyle("-fx-font: 24 Tahoma");

        //surgeon stuff
        surgeonL = new Label("Surgeon");
        surgeonT = new Label(s.getSurgeon());
        surgeonT.setPadding(new Insets(0,0,0,20));
        surgeonL.setStyle("-fx-font: 16 Tahoma");
        surgeonBox.getChildren().addAll(surgeonL, surgeonT);

        //rn stuff
        rnL = new Label("RN");
        rnT = new Label(s.getRN());
        rnT.setPadding(new Insets(0,0,0,20));
        rnL.setStyle("-fx-font: 16 Tahoma");
        rnBox.getChildren().addAll(rnL, rnT);

        //surgery type stuff
        surgeryTypeL = new Label("Surgery Type");
        surgeryTypeT = new Label(s.getSurgeryType() );
        surgeryTypeT.setPadding(new Insets(0,0,0,20));
        surgeryTypeL.setStyle("-fx-font: 16 Tahoma");
        typeBox.getChildren().addAll(surgeryTypeL, surgeryTypeT);

        //scrub nurse stuff
        scrubL = new Label("Scrub Nurse");
        scrubT = new Label(s.getScrub());
        scrubT.setPadding(new Insets(0,0,0,20));
        scrubT.setWrapText(true);
        scrubL.setStyle("-fx-font: 16 Tahoma");
        scrubBox.getChildren().addAll(scrubL, scrubT);

        //notes stuff
        resultsL = new Label("Results");
        resultsT = new Label(s.getResults());
        resultsT.setMaxWidth(400);
        resultsT.setWrapText(true);
        resultsT.setPadding(new Insets(0,0,0,20));
        resultsL.setStyle("-fx-font: 16 Tahoma");
        resultsBox.getChildren().addAll(resultsL, resultsT);

        //now add them in
        sDetails.addRow(0, header);
        sDetails.addRow(1, new Separator());
        sDetails.addRow(2, topLabel);
        sDetails.addRow(3, surgeonBox);
        sDetails.addRow(4, rnBox);
        sDetails.addRow(5, scrubBox);
        sDetails.addRow(6, typeBox);
        sDetails.addRow(7, resultsBox);
        sDetails.setHgap(5);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Surgery Summary ");
        window.setScene(new Scene(sDetails,500,600));
        window.showAndWait();
    }

    // confirms if the user is sure that they want to exit the program so that any data that
    // that is not saved can be saved
    public static void confirmClose() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirm close");

        VBox content = new VBox(30);
        content.setPadding(new Insets(20,0,0,0));

        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(0,0,0,80));

        Label title = new Label("You sure that you want to close?");
        title.setMaxWidth(300);
        title.setWrapText(true);
        title.setPadding(new Insets(0,0,0,30));
        title.setStyle("-fx-font: 24 Tahoma");

        Button ok = new Button("OK");
        ok.setStyle("-fx-background-color: lightskyblue;");
        ok.setMinSize(40,20);
        ok.setOnAction(e ->  {
            save = true;
            window.close();
        });

        Button close = new Button("NO");
        close.setStyle("-fx-background-color: lightskyblue;");
        close.setMinSize(40,20);
        close.setOnAction(e -> {
            save = false;
            window.close();
        });

        hbox.getChildren().addAll(ok, close);
        content.getChildren().addAll(title, hbox);
        window.setScene(new Scene(content,300,150));
        window.showAndWait();
    }
}
