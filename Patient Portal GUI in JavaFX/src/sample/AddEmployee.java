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
import java.sql.SQLException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddEmployee {
    public Stage window;
    public Scene addEmployee;
    public PLabel fnL, lnL, miL, emailL, numberL,
            address1L, address2L, emergencyL, dobL, sexL, relationL, collegeL, positionL, experienceL;
    public Label header;
    public PText fnT, lnT, miT,emailT, heightT, weightT, numberT, address1T, address2T, emergencyT, relationT,
            collegeT, positionT;
    public TextArea experienceT;
    public ComboBox sexC;
    public DatePicker date;
    public Button saveBtn;
    public Connection conn;
    private Employee employee;
    public String firstName, lastName, middleInitial, email, number, DOB, emergencyNumber, address,
            sex, emergencyRelation, position, college, pastExperience;
    public RadioButton isAdmin;


    // base constructor for when making a new employee
    public AddEmployee(Connection conn) {
        this.conn = conn;
        window = new Stage();

        //get things ready for the labels
        initLabels();
        initTextFields();

        //box on top for the header
        VBox topHeader = new VBox();

        //hbox for the buttons at the bottom
        HBox buttons = new HBox();
        buttons.setSpacing(20);

        isAdmin = new RadioButton("Admin Privileges");

        saveBtn = new Button("ADD");
        saveBtn.setStyle("-fx-background-color: lightskyblue;");
        saveBtn.setOnAction(e -> savePatient());

        buttons.getChildren().addAll(saveBtn);
        buttons.setPadding(new Insets(10, 0,30, 60));

        //main layout for the window
        BorderPane mainLayout = new BorderPane();

        date = new DatePicker();

        header = new Label("Add An Employee");
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

        // this holds all the labels and the text fields I'll be using
        GridPane gp = new GridPane();
        gp.setVgap(10);
        gp.setHgap(20);
        gp.setPadding(new Insets(30,0,0,60));
        //now add the labels to the gridpane
        gp.addColumn(0, fnL,fnT,emailL,emailT,dobL,date,
                numberL,numberT,address1L,address1T,address2L,address2T);
        gp.addRow(0, lnL, miL);
        gp.addRow(1, lnT, miT);
        gp.addRow(2, sexL);
        gp.addRow(3, sexC);
        gp.addRow(8, emergencyL, relationL);
        gp.addRow(9, emergencyT, relationT);
        gp.addRow(10, collegeL, positionL);
        gp.addRow(11, collegeT, positionT);
        gp.addRow(14, experienceL);
        gp.addRow(15, experienceT, isAdmin);

        //now add the text field to the grid-pane
        mainLayout.setCenter(gp);
        addEmployee = new Scene(mainLayout, 700,600);
    }

    //for use when deleting an employee from the database via the search table
    public AddEmployee(Connection conn, Employee employee, boolean delete) {
        this.conn = conn;
        this.employee = employee;
        deleteFromDatabase();
    }

    // for when viewing the file of an existing employee
    public AddEmployee(Connection conn, Employee employee) {
        this(conn);
        this.employee = employee;
        fillFields();
    }

    // displays all the information to the screen
    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMaxWidth(700);
        window.setMaxHeight(700);
        window.setMinHeight(700);
        window.setMinWidth(700);
        window.setOnCloseRequest(e -> {
            e.consume();
            Alert.confirmClose();
            if (Alert.save)
                window.close();
        });

        saveBtn.setOnAction(e -> {
            if ((savePatient()))
                window.close();
        });

        window.setTitle("Add an Employee");
        window.setScene(addEmployee);
        window.show();
    }

    //initialize all the labels
    public void initLabels() {
        fnL = new PLabel("First Name");
        lnL = new PLabel("Last Name");
        miL = new PLabel("Middle Initial");
        emailL = new PLabel("Email");
        numberL = new PLabel("Contact Number");
        address1L = new PLabel("Street Address");
        address2L = new PLabel("Street Address Line 2");
        emergencyL = new PLabel("Emergency Contact Number");
        dobL = new PLabel("Date Of Birth");
        sexL = new PLabel("Sex");
        relationL = new PLabel("Relation To Patient");
        collegeL = new PLabel("College Attended");
        positionL = new PLabel("Employee Position");
        experienceL = new PLabel("Past Experience");
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
        collegeT = new PText(200);
        positionT = new PText(200);
        experienceT = new TextArea();
        experienceT.setMinHeight(40);
        experienceT.setMaxWidth(200);
        experienceT.setWrapText(true);

        // make it so that the user can only enter in 1 letter in the middle initial text field
        Pattern pattern = Pattern.compile(".{0,1}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>)
                change -> pattern.matcher(change.getControlNewText()).matches() ? change : null);

        miT.setTextFormatter(formatter);

        // Employees sex drop down
        sexC = new ComboBox();
        sexC.getItems().addAll("Male", "Female", "Other");
    }


    // fill in the fields when you are viewing an existing employee
    private void fillFields() {

        //delete the record from the database, then insert it later
        deleteFromDatabase();
        fnT.setText(employee.getFirstName());
        lnT.setText(employee.getLastName());
        miT.setText(employee.getMiddleInitial());
        emailT.setText(employee.getEmail());
        numberT.setText(employee.getNumber());
        address1T.setText(employee.getAddress());
        emergencyT.setText(employee.getEmergencyNumber());
        relationT.setText(employee.getEmergencyRelation());
        collegeT.setText(employee.getCollege());
        positionT.setText(employee.getPosition());
        experienceT.setText(employee.getPastExperience());
        date.setValue(DateFormat.formatDate(employee.getDOB()));

        int index;
        String sexPatient = employee.getSex();
        if (sexPatient.equals("Male"))
            index = 0;
        else if (sexPatient.equals("Female"))
            index = 1;
        else
            index = 2;

        sexC.getSelectionModel().select(index);
        isAdmin.setSelected(employee.getIsAdmin());
    }

    // makes sure that all the fields are filled out so there are no errors
    private boolean allFieldsFilledOut() {
        if (firstName.equals("") || lastName.equals("") || middleInitial.equals("") ||
                email.equals("") || number.equals("") || emergencyNumber.equals("") ||
                address.equals("") || emergencyRelation.equals("") || sex.equals("") ||
                position.equals("") || college.equals("") ||  pastExperience.equals(""))
            return false;
        return true;
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
        pastExperience = experienceT.getText();
        college = collegeT.getText();
        position = positionT.getText();

        try {
            sex = sexC.getSelectionModel().getSelectedItem().toString();
        } catch(NullPointerException error) {
            Alert.display();
        }
        emergencyRelation = relationT.getText();

        //then make sure that they have been entered in, so a nurse doesn't accidentally forget
        //to fill one out
        String sql = "INSERT INTO employee (firstName, lastName, middleInitial, email," +
                " number, DOB, emergencyNumber, address, sex, college, position, pastExperience, emergencyRelation, isAdmin) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (!allFieldsFilledOut()) {
            Alert.display();
            return false;
        } else {
            //insert it into the database!
            //get the connection
            //use prepared statement so that I can use variables as values to insert

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
                pstmt.setString(9, sex);
                pstmt.setString(10, college);
                pstmt.setString(11, position);
                pstmt.setString(12, pastExperience);
                pstmt.setString(13, emergencyRelation);
                pstmt.setString(14, isAdmin.isSelected() ? "true" : "false");

                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e);
            }
        }

        //now enter the employee into the login database, with their email,
        //and their name as the default password, to be changed later
        sql = "INSERT INTO login (email, password, name) VALUES (?,?,?)";
        try {
            String name = firstName + " " + lastName;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, name);
            pstmt.setString(3, name);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in addEmployee line 305: " + e);
        }

        return true;
    }

    public void deleteFromDatabase() {
        String SQL = "DELETE FROM employee WHERE firstName = ? AND lastName = ? AND email = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(SQL);
            pstm.setString(1, employee.getFirstName());
            pstm.setString(2, employee.getLastName());
            pstm.setString(3, employee.getEmail());
            pstm.executeUpdate();

        } catch (SQLException error) {
            System.out.println("SQL ERROR: " + error);
        }
    }
}
