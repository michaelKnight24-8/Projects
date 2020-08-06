package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddPatient {
    public Stage window;
    public Scene addPatient;
    public PLabel fnL, lnL, miL, emailL, heightL, weightL, numberL,
            address1L, address2L, emergencyL, dobL, sexL, relationL;
    public Label header;
    public PText fnT, lnT, miT,emailT, heightT, weightT, numberT, address1T, address2T, emergencyT, relationT;
    public ComboBox sexC;
    public DatePicker date;
    public Button saveBtn;
    public Patient patient;
    public String firstName, lastName, middleInitial, email, number, DOB, emergencyNumber, address,
            height, weight, sex, emergencyRelation;
    private Connection conn;
    private ListView <Appointment> appointmentsHolder;
    private ListView <Surgery> surgeriesHolder;

    //for when deleting the patient from the database
    public AddPatient(Connection conn, Patient patient, boolean delete) {
        this.conn = conn;
        this.patient = patient;
        deleteFromDatabase();
    }

    // the base constuctor
    public AddPatient(Connection conn) {
        this.conn = conn;
        window = new Stage();
        window.setTitle("Add A Patient");

        //get things ready for the labels
        initLabels();
        initTextFields();

        //box on top for the header
        VBox topHeader = new VBox();

        saveBtn = new Button("SAVE");
        saveBtn.setStyle("-fx-background-color: lightskyblue;");

        //hbox for the buttons at the bottom
        HBox buttons = new HBox();
        buttons.setSpacing(20);
        buttons.getChildren().addAll(saveBtn);
        buttons.setPadding(new Insets(0, 0,30, 60));

        //main layout for the window
        BorderPane mainLayout = new BorderPane();

        date = new DatePicker();

        header = new Label("Add A Patient");
        header.setStyle("-fx-font: 24 arial;");
        header.setPadding(new Insets(20,0,0, 30));

        Separator separator = new Separator();
        separator.setPadding(new Insets(5,0,0,30));

        //now add the things to the layouts
        topHeader.getChildren().addAll(header,separator);
        mainLayout.setTop(topHeader);
        mainLayout.setBottom(buttons);
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(105,105,105),
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);
        mainLayout.setBackground(background);
        GridPane gp = new GridPane();
        gp.setVgap(10);
        gp.setHgap(20);
        gp.setPadding(new Insets(30,0,0,60));
        //now add the labels to the gridpane
        gp.addColumn(0, fnL,fnT,emailL,emailT,dobL,date,heightL,heightT,
                numberL,numberT,address1L,address1T,address2L,address2T);
        gp.addRow(0, lnL, miL);
        gp.addRow(1, lnT, miT);
        gp.addRow(2, sexL);
        gp.addRow(3, sexC);
        gp.addRow(6, weightL);
        gp.addRow(7, weightT);
        gp.addRow(8, emergencyL, relationL);
        gp.addRow(9, emergencyT, relationT);

        VBox apt = new VBox(10);
        apt.setPadding(new Insets(30,10,0,0));

        //for the appointment history
        appointmentsHolder = new ListView<>();
        appointmentsHolder.setMinSize(300,200);
        appointmentsHolder.setMaxSize(300,200);
        Label appointments = new Label("Appointment History");
        appointments.setStyle("-fx-font: 24 arial;");
        VBox apts = new VBox(5);
        apts.getChildren().addAll(appointments, appointmentsHolder);

        //for the surgical history
        Label surgeries = new Label("Surgical History");
        surgeries.setStyle("-fx-font: 24 arial;");
        surgeriesHolder = new ListView<>();
        surgeriesHolder.setMinSize(300,200);
        surgeriesHolder.setMaxSize(300,200);
        VBox surges = new VBox(5);
        surges.getChildren().addAll(surgeries, surgeriesHolder);

        VBox history = new VBox(20);
        history.getChildren().addAll(apts, surges);
        //for the surgeries history
        apt.getChildren().addAll(history);
        //now add the text field to the gridpane
        mainLayout.setCenter(gp);
        mainLayout.setRight(apt);

        addPatient = new Scene(mainLayout, 600,600);
    }

    public AddPatient(Connection conn, Patient patient) {
        this(conn);
        this.patient = patient;
        fillTextFields();
        fillAppointmentHistory();
        fillSurgeryHistory();
    }

    public boolean allFieldsFilledOut() {
        if (firstName.equals("") || lastName.equals("") || middleInitial.equals("") ||
                email.equals("") || number.equals("") || emergencyNumber.equals("") || address.equals("") ||
                height.equals("") || weight.equals("") || emergencyRelation.equals("") || sex.equals(""))
            return false;

        return true;
    }

    //initialize all the labels
    public void initLabels() {
        fnL = new PLabel("First Name");
        lnL = new PLabel("Last Name");
        miL = new PLabel("Middle Initial");
        emailL = new PLabel("Email");
        heightL = new PLabel("Height");
        weightL = new PLabel("Weight");
        numberL = new PLabel("Contact Number");
        address1L = new PLabel("Street Address");
        address2L = new PLabel("Street Address Line 2");
        emergencyL = new PLabel("Emergency Contact Number");
        dobL = new PLabel("Date Of Birth");
        sexL = new PLabel("Sex");
        relationL = new PLabel("Relation To Patient");
    }

    //fill in the appointment history, if there is one for the patient
    private void fillAppointmentHistory() {
        //first query the database for the appointments that correspond to the current patient
        //that we are examining. Then add the matches to the list view
        String SQL = "SELECT * FROM appointments WHERE patientName = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(SQL);
            String name = patient.getFirstName() + " " + patient.getLastName();
            pstm.setString(1, name);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                System.out.println("Got here boi");
                appointmentsHolder.getItems().add(new Appointment(rs.getString("date"),
                        rs.getString("patientName"), rs.getString("nurse"), rs.getString("doctor"),
                        rs.getString("drugsPrescribed"), rs.getString("additionalRemarks"),
                        rs.getString("reasonForAppointment"), rs.getString("time"), rs.getString("room")));
            }
        }
        catch (SQLException error) {
            System.out.println("SQL ERROR: "+ error);
        }
        appointmentsHolder.setStyle("-fx-font: 24 arial;");
        appointmentsHolder.setOnMouseClicked(e -> {
            Alert.displayAppointmentData(appointmentsHolder.getSelectionModel().getSelectedItem());
        });
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMaxWidth(1000);
        window.setMaxHeight(700);
        window.setMinHeight(600);
        window.setMinWidth(900);

        window.setOnCloseRequest(e -> {
            e.consume();
            Alert.confirmClose();
            if (Alert.save)
                window.close();
        });

        saveBtn.setOnAction(e -> {
            if (savePatient())
                window.close();
        });

        window.setTitle("Add Patient");
        window.setScene(addPatient);
        window.show();

    }

    //do the same thing that we did for the appointment history, but this time for the
    //surgical history for the given patient
    private void fillSurgeryHistory() {
        String SQL = "SELECT * FROM surgery WHERE patient = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(SQL);
            String name = patient.getFirstName() + " " + patient.getLastName();
            pstm.setString(1, name);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                surgeriesHolder.getItems().add(new Surgery(rs.getInt("ORoom"), rs.getString("surgeon"),
                        rs.getString("anes"), rs.getString("surgeryType"), rs.getString("patient"),
                        rs.getString("time"), rs.getString("RN"), rs.getString("scrub"), rs.getString("patientRoom"),
                        rs.getString("date"), rs.getString("results")));
            }
        }
        catch (SQLException error) {
            System.out.println("SQL ERROR: "+ error);
        }
        surgeriesHolder.setStyle("-fx-font: 24 arial;");
        surgeriesHolder.setOnMouseClicked(e -> {
            Alert.displaySurgicalData(surgeriesHolder.getSelectionModel().getSelectedItem());
        });
    }

    //initialize the text fields
    public void initTextFields() {
        fnT = new PText(150);
        lnT = new PText(150);
        miT = new PText(25);
        emailT = new PText(150);
        heightT = new PText(50);
        weightT = new PText(50);
        numberT = new PText(130);
        address1T = new PText(200);
        address2T = new PText(200);
        emergencyT = new PText(130);
        relationT = new PText(100);

        Pattern pattern = Pattern.compile(".{0,1}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        miT.setTextFormatter(formatter);
        //sex drop down
        sexC = new ComboBox();
        sexC.getItems().addAll("Male", "Female", "Other");
    }

    //save the patient to a patient object, then return it
    public boolean savePatient() {
        //first get all the values
        firstName = fnT.getText();
        lastName = lnT.getText();
        middleInitial = miT.getText().toUpperCase();
        email = emailT.getText();
        number = numberT.getText();
        if (!date.getEditor().getText().equals(""))
            DOB = DateFormat.fixDate(date.getEditor().getText());
        emergencyNumber = emergencyT.getText();
        address = address1T.getText() + " " + address2T.getText();
        height = heightT.getText();
        weight = weightT.getText();
        try {
            sex = sexC.getSelectionModel().getSelectedItem().toString();
        } catch(NullPointerException error) {
            Alert.display();
        }
        emergencyRelation = relationT.getText();

        //then make sure that they have been entered in, so a nurse doesn't accidentally forget
        //to fill one out
        if (!allFieldsFilledOut()) {
            Alert.display();
            return false;
        } else {
            String sql = "INSERT INTO patient (firstName, lastName, middleInitial, email," +
                    " number, DOB, emergencyNumber, address, height, weight, sex, emergencyRelation) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, middleInitial);
                pstmt.setString(4, email);
                pstmt.setString(5, number);
                pstmt.setString(6, DOB);
                pstmt.setString(7, emergencyNumber);
                pstmt.setString(8, address);
                pstmt.setString(9, height);
                pstmt.setString(10, weight);
                pstmt.setString(11, sex);
                pstmt.setString(12, emergencyRelation);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println("SQL Error: " + e);
            }
        }

        return true;
    }

    private void fillTextFields() {

        //first just delete the entry from the database,
        //to be inserted in later as a new record

        deleteFromDatabase();
        fnT.setText(patient.getFirstName());
        lnT.setText(patient.getLastName());
        miT.setText(patient.getMiddleInitial());
        emailT.setText(patient.getEmail());
        heightT.setText(patient.getHeight());
        weightT.setText(patient.getWeight());
        numberT.setText(patient.getNumber());
        address1T.setText(patient.getAddress());
        emergencyT.setText(patient.getEmergencyNumber());
        relationT.setText(patient.getEmergencyRelation());
        date.setValue(DateFormat.formatDate(patient.getDOB()));

        int index;
        String sexPatient = patient.getSex();
        if (sexPatient.equals("Male"))
            index = 0;
        else if (sexPatient.equals("Female"))
            index = 1;
        else
            index = 2;
        sexC.getSelectionModel().select(index);
    }

    public void deleteFromDatabase() {

        String SQL = "DELETE FROM patient WHERE firstName = ? AND lastName = ? AND email = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(SQL);
            pstm.setString(1, patient.getFirstName());
            pstm.setString(2, patient.getLastName());
            pstm.setString(3, patient.getEmail());
            pstm.executeUpdate();

        } catch (SQLException error) {
            System.out.println("SQL ERROR: " + error);
        }
    }
}
