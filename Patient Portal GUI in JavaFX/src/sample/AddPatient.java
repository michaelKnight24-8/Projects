package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddPatient {
    public Stage window;
    public Scene addPatient, homePage;
    public PLabel fnL, lnL, miL, emailL, heightL, weightL, numberL,
            address1L, address2L, emergencyL, dobL, sexL, relationL;
    public Label header;
    public PText fnT, lnT, miT,emailT, heightT, weightT, numberT, address1T, address2T, emergencyT, relationT;
    public ComboBox sexC;
    public DatePicker date;
    public Button saveBtn, closeBtn, back;
    public Patient patient;
    public String firstName, lastName, middleInitial, email, number, DOB, emergencyNumber, address,
            height, weight, sex, emergencyRelation;
    private Connection conn;
    private boolean editing;
    private ListView <Appointment> appointmentsHolder;
    private ListView <Surgery> surgeriesHolder;

    //for when deleting the patient from the database
    public AddPatient(Connection conn, Patient patient) {
        this.conn = conn;
        this.patient = patient;
        deleteFromDatabase();
    }

    public AddPatient(Scene homePage, Button back, Connection conn) {
        this.homePage = homePage;
        this.back = back;
        this.conn = conn;
        window = new Stage();
        window.setTitle("Add A Patient");
        editing = false;
        //get things ready for the labels
        initLabels();
        initTextFields();

        //box on top for the header
        VBox topHeader = new VBox();

        //hbox for the buttons at the bottom
        HBox buttons = new HBox();

        saveBtn = new Button("SAVE");
        closeBtn = new Button("CLOSE");

        closeBtn.setOnAction(e -> window.close());
        closeBtn.setStyle("-fx-background-color: red;");
        buttons.setSpacing(20);
        saveBtn.setStyle("-fx-background-color: lightskyblue;");
        saveBtn.setOnAction(e -> savePatient());

        buttons.getChildren().addAll(saveBtn, back, closeBtn);
        buttons.setPadding(new Insets(10, 0,30, 340));

        window.setOnCloseRequest(e -> {
            e.consume();
            boolean save = Alert.closeProgram();
            if (save) savePatient();
        });
        //main layout for the window
        BorderPane mainLayout = new BorderPane();

        date = new DatePicker();
        header = new Label("Add A Patient");
        header.setStyle("-fx-font: 24 arial;");
        Separator separator = new Separator();
        separator.setPadding(new Insets(5,0,0,30));
        header.setPadding(new Insets(20,0,0, 30));

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

        //for the appointment history
        Label appointments = new Label("Appointment History");
        appointments.setStyle("-fx-font: 24 arial;");
        VBox apt = new VBox(10);
        apt.setPadding(new Insets(30,10,0,0));
        appointmentsHolder = new ListView<>();
        appointmentsHolder.setMinSize(300,200);
        appointmentsHolder.setMaxSize(300,200);

        //for the surgeries history
        apt.getChildren().addAll(appointments, appointmentsHolder);
        //now add the text field to the gridpane
        mainLayout.setCenter(gp);
        mainLayout.setRight(apt);


        addPatient = new Scene(mainLayout, 600,600);
    }

    public AddPatient(Scene homePage, Button home, Connection conn, Patient patient) {
        this(homePage, home, conn);
        this.patient = patient;
        fillTextFields();
        editing = true;
        fillAppointmentHistory();
    }

    public Scene getScene() { return addPatient; }

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

    private void fillSurgeryHistory() {

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
    public void savePatient() {
        //first get all the values
        firstName = fnT.getText();
        lastName = lnT.getText();
        middleInitial = miT.getText().toUpperCase();
        email = emailT.getText();
        number = numberT.getText();
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
        if (firstName.equals("") || lastName.equals("") || middleInitial.equals("") ||
                email.equals("") || number.equals("") || emergencyNumber.equals("") || address.equals("") ||
                height.equals("") || weight.equals("") || emergencyRelation.equals("") || sex.equals("")) {
            Alert.display();
        }
        else {
            String name = firstName + " " + lastName;
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
        back.fire();
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
