package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddSurgery {
    public int OR;
    public String surgeon, anes, surgeryType, patient, time, RN, scrub, patientRoom, date, results;
    public PLabel surgeonL, anesL, surgeryTypeL, patientL, timeL, RNL, scrubL, patientRoomL, ORL, dateL, resultsL;
    public PText surgeonT, anesT, surgeryTypeT, patientT, timeT, RNT, scrubT, patientRoomT, ORT, dateHide;
    public DatePicker datePicker;
    public TextArea resultsT;
    public Button scheduledSurgeries, back, saveBtn;
    public Scene addSurgery, homePage;
    public Stage window;
    public GridPane mainLayout;
    public HBox buttons;
    public Surgery surgery;
    public boolean editing;
    public Connection conn;

    public AddSurgery(Connection conn) {

        this.conn = conn;
        editing = false;
        datePicker = new DatePicker();
        scheduledSurgeries = new Button("View Scheduled Surgeries");
        scheduledSurgeries.setOnAction(e -> {
            Calendar calendar = new Calendar(1, conn);
            CalendarPane.display(calendar.getScene(), 1);
        });
        mainLayout = new GridPane();
        window = new Stage();
        window.setTitle("Schedule A Surgery");
        saveBtn = new Button("ADD");
        saveBtn.setStyle("-fx-background-color: lightskyblue;");
        saveBtn.setOnAction(e -> saveSurgery());

        buttons = new HBox(10);
        buttons.getChildren().addAll(saveBtn);
        buttons.setPadding(new Insets(0,0,20,0));
        initTextFields();
        initLabels();
        initLayout();

        //background color
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(105,105,105),
                CornerRadii.EMPTY, Insets.EMPTY);

        mainLayout.setBackground( new Background(background_fill));
        addSurgery = new Scene(mainLayout, 580,600);
    }

    public AddSurgery(Surgery surgery, Connection conn) {
        this(conn);
        this.surgery = surgery;
        fillTextFields();
        editing = true;
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(addSurgery);
        window.setTitle("Schedule A Surgery");
        window.setMaxWidth(600);
        window.setMaxHeight(600);
        window.setMinHeight(600);
        window.setMinWidth(600);
        window.showAndWait();
    }

    private void initTextFields() {

        surgeonT = new PText(200);
        anesT = new PText(200);
        surgeryTypeT = new PText(100);
        patientT = new PText(200);
        timeT = new PText(50);
        RNT = new PText(200);
        scrubT = new PText(200);
        patientRoomT = new PText(50);
        ORT = new PText(50);
        resultsT = new TextArea();
        resultsT.setMinHeight(100);
        resultsT.setMaxWidth(200);
        resultsT.setWrapText(true);
        dateHide = new PText(50);
        dateHide.setVisible(false);
        dateHide.setText("");
        resultsT.setText("Pending");
    }

    private void initLabels() {
        surgeonL = new PLabel("Surgeon");
        anesL = new PLabel("Anesthesiologist");
        surgeryTypeL = new PLabel("Procedure");
        patientL = new PLabel("Patient Name");
        timeL = new PLabel("Time");
        RNL = new PLabel("Registered Nurse");
        scrubL = new PLabel("Scrub Nurse");
        patientRoomL = new PLabel("Patient Room");
        ORL = new PLabel("O.R.");
        dateL = new PLabel("Date");
        resultsL = new PLabel("Results");
    }

    private void initLayout() {
        VBox schedule = new VBox();
        datePicker.setPadding(new Insets(20,0,0,0));
        schedule.getChildren().addAll(datePicker, scheduledSurgeries);
        schedule.setAlignment(Pos.TOP_LEFT);
        mainLayout.addRow(0, patientL);
        mainLayout.addRow(1, patientT);
        mainLayout.addRow(2, surgeryTypeL, dateL, timeL);
        mainLayout.addRow(3, surgeryTypeT, schedule, timeT);
        mainLayout.addRow(4, surgeonL, anesL);
        mainLayout.addRow(5, surgeonT, anesT);
        mainLayout.addRow(6, RNL, scrubL);
        mainLayout.addRow(7, RNT, scrubT);
        mainLayout.addRow(8, ORL, patientRoomL);
        mainLayout.addRow(9, ORT, patientRoomT);
        mainLayout.addRow(10, resultsL);
        mainLayout.addRow(11, resultsT, dateHide);
        mainLayout.addRow(13, new Label(""), new Label(""), buttons);
        mainLayout.setHgap(20);
        mainLayout.setVgap(10);
        mainLayout.setPadding(new Insets(20, 0, 0, 30));
    }

    private void fillTextFields() {
        surgeonT.setText(surgery.getSurgeon());
        anesT.setText(surgery.getAnes());
        surgeryTypeT.setText(surgery.getSurgeryType());
        patientT.setText(surgery.getPatient());
        timeT.setText(surgery.getTime());
        RNT.setText(surgery.getRN());
        scrubT.setText(surgery.getScrub());
        patientRoomT.setText(surgery.getPatientRoom());
        ORT.setText(Integer.toString(surgery.getOR()));
        resultsT.setText(surgery.getResults());
        dateHide.setText(surgery.getDate());
    }

    //first check to make sure that all the fields have been entered in
    private void saveSurgery() {

        //now get the data that was entered in

        if (surgeonT.getText().equals("") || anesT.getText().equals("") || surgeryTypeT.getText().equals("") ||
                patientT.getText().equals("") || timeT.getText().equals("") || RNT.getText().equals("")
                || scrubT.getText().equals("") || patientRoomT.getText().equals("") || ORT.getText().equals("")) {
            Alert.display();
        } else {
            String sql = "INSERT INTO surgery (ORoom, surgeon, anes," +
                    " surgeryType, patient, time, RN, scrub, patientRoom, date, results) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            if (conn == null)
                conn = DBUtil.getConnection();
            try {
                if (editing) deleteFromDatabase(conn);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(ORT.getText()));
                pstmt.setString(2, surgeonT.getText());
                pstmt.setString(3, anesT.getText());
                pstmt.setString(4, surgeryTypeT.getText());
                pstmt.setString(5, patientT.getText());
                pstmt.setString(6, timeT.getText());
                pstmt.setString(7, RNT.getText());
                pstmt.setString(8, scrubT.getText());
                pstmt.setString(9, patientRoomT.getText());

                if (!editing) {
                    date = datePicker.getValue().getMonthValue() +
                            "/" + datePicker.getValue().getDayOfMonth() +
                            "/" + datePicker.getValue().getYear();
                } else {
                    date = dateHide.getText();
                }
                pstmt.setString(10, date);
                pstmt.setString(11, resultsT.getText());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e);
            }
        }
        editing = false;
    }

    private void deleteFromDatabase(Connection conn) {
        //delete it form the database, then call the save function and save it as
        //a new one!
        String sql = "DELETE FROM surgery WHERE ORoom = ? and patient = ? and surgeryType = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setInt(1, surgery.getOR());
            pstmt.setString(2, surgery.getPatient());
            pstmt.setString(3, surgery.getSurgeryType());
            // execute the delete statement
            pstmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQL ERROR: " + e);
        }
    }
}
