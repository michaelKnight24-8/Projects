package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class Main extends Application {
    public Stage window;
    public Scene homePage;
    public Button addPatient, addSurgery, addEmployee, calBtn, bookAppointment,
            viewAppointments, info, clear, viewChanges, messaging;
    public BorderPane mainLayout;
    public VBox header;
    public HBox buttons;
    public Label searchL;
    public HBox radioButtons, searchContainer, middle;
    public ToggleGroup radioGroup;
    public RadioButton patientBtn, employeeBtn;
    public final int SURGERY = 1;
    public final int APPOINTMENT = 2;
    public final int BOOK_APPOINTMENT = 3;
    public Connection conn;
    public String currentUserName, dbTable;
    private TableView <Person> table;
    private HashMap<String, String> parameters;
    private boolean searchAll;
    //for the accelerators
    private KeyCombination kc, ae, as, aa;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //database connection that will be passed around between
        //view and functions so that I don't keep opening and closing it
        conn = DBUtil.getConnection();

        table = new TableView <>();
        table.setMinSize(392,200);
        table.setMaxSize(392,200);

        window = primaryStage;

        //for when searching with multiple search parameters
        parameters = new HashMap<>();

        //now get the login page ready.
        getLoginPage();
        //if the user doesn't select any search parameters, it searches for all
        //employees, or all patients as specified
        searchAll = true;
        dbTable = "Patient";
    }

    //this is called once the login has been verified as accurate
    public void initHomePage() {

        middle = new HBox(50);
        mainLayout = new BorderPane();
        initLayouts();

        window.setMinWidth(970);
        window.setMinHeight(600);
        window.setTitle("Homepage");

        // only go here if the login was real, or verify
        window.setScene(homePage);
        window.centerOnScreen();
        window.show();
    }


    //function to separate the building of the layout from the scene setting and such
    public void initLayouts() {
        //background color
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(105,105,105),
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);

        radioButtons = new HBox(20);
        searchL = new Label("Search for Patient/Employee");
        searchL.setStyle("-fx-font: 24 Arial;");
        header = new VBox(10);
        header.setPadding(new Insets(20,0,20,30));
        searchContainer = new HBox(10);

        //lets you choose between searching for a patient, or an employee
        radioGroup = new ToggleGroup();

        patientBtn = new RadioButton("Patient");
        patientBtn.setToggleGroup(radioGroup);
        patientBtn.setSelected(true);
        patientBtn.setOnAction(e -> dbTable = "Patient");

        employeeBtn = new RadioButton("Employee");
        employeeBtn.setToggleGroup(radioGroup);
        employeeBtn.setOnAction(e -> dbTable = "Employee");

        radioButtons.getChildren().addAll(patientBtn, employeeBtn);

        initButtons();

        GridPane gp = new GridPane();

        //init text search fields
        PText fNameT = new PText(200);
        PText lNameT = new PText(200);
        PText addressT = new PText(200);
        PText emailT = new PText(150);
        PText numberT = new PText(100);

        //init the search checkboxes
        CheckBox searchByFirstName = new CheckBox("First Name");
        CheckBox searchByLastName = new CheckBox("Last Name");
        CheckBox addressSearch = new CheckBox("Address");
        CheckBox numberSearch = new CheckBox("Number");
        CheckBox emailSearch = new CheckBox("Email");

        //clear all the fields, checkboxes an results when clicked
        clear = new Button("CLEAR");
        clear.setStyle("-fx-background-color: lightskyblue");
        clear.setOnAction(e -> {
            fNameT.setText("");
            lNameT.setText("");
            emailT.setText("");
            numberT.setText("");
            addressT.setText("");
            table.getItems().clear();
            searchByFirstName.setSelected(false);
            searchByLastName.setSelected(false);
            addressSearch.setSelected(false);
            numberSearch.setSelected(false);
            emailSearch.setSelected(false);
        });

        searchByLastName.setPadding(new Insets(0,20,0,30));
        numberSearch.setPadding(new Insets(0,20,0,30));

        //now set up the table to display the query results
        initRefinedTable();

        Button okBtn = new Button("SEARCH");
        okBtn.setStyle("-fx-background-color: lightskyblue");
        okBtn.setMinWidth(100);
        okBtn.setOnAction(e -> {
            parameters.clear();

            initMenuItems();
            //get the values that the user has entered in, as well as the search filters.
            if (searchByFirstName.isSelected())
                addToParameters("firstName", fNameT);
            if (searchByLastName.isSelected())
                addToParameters("lastName", lNameT);
            if (addressSearch.isSelected())
                addToParameters("address", addressT);
            if (numberSearch.isSelected())
                addToParameters("number", numberT);
            if (emailSearch.isSelected())
                addToParameters("email", emailT);

            RadioButton button = (RadioButton) radioGroup.getSelectedToggle();
            dbTable = button.getText();
            table.setItems(getPeople());
        });

        HBox buttonsHolder = new HBox(15);
        buttonsHolder.getChildren().addAll(okBtn, clear);

        //add to the search gridpane
        gp.addRow(0, searchByFirstName, fNameT, searchByLastName, lNameT);
        gp.addRow(1, addressSearch, addressT, numberSearch, numberT);
        gp.addRow(2, emailSearch, emailT);
        gp.addRow(3, new Label(""));
        gp.addRow(4, buttonsHolder);
        gp.setVgap(10);

        middle.setPadding(new Insets(150, 90,0,0));

        header.getChildren().addAll(buttons, searchL, radioButtons, searchContainer, gp, table);

        mainLayout.setRight(middle);
        mainLayout.setLeft(header);
        mainLayout.setBackground(background);

        homePage = new Scene(mainLayout, 900,600);

        //add the shortcuts
        homePage.addMnemonic(new Mnemonic(addPatient, kc));
        homePage.addMnemonic(new Mnemonic(addSurgery, as));
        homePage.addMnemonic(new Mnemonic(bookAppointment, aa));
        //only let them use the add employee shortcut if they are in a leadership position
        if (getEmployee().getIsAdmin())
            homePage.addMnemonic(new Mnemonic(addEmployee, ae));

    }

    //  little function to make adding to the search parameters map easier
    public void addToParameters(String value, TextField context) {
        searchAll = false;
        parameters.put(value, context.getText());
    }

    //set up the buttons
    public void initButtons() {

        buttons = new HBox(10);
        buttons.setPadding(new Insets(0,0,20,0));

        //initialize the accelerator
        kc = new KeyCodeCombination(KeyCode.P, KeyCombination.ALT_ANY);
        ae = new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_ANY);
        as = new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_ANY);
        aa = new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_ANY);

        // the various buttons you see on the main screen that allows the user to
        // carry out various tasks
        viewAppointments = new Button("View Appointments");
        viewAppointments.setOnAction(e -> {
            Calendar calendar = new Calendar(APPOINTMENT, conn);
            CalendarPane.display(calendar.getScene(), APPOINTMENT);
        });
        viewAppointments.setStyle("-fx-background-color: lightskyblue");

        //see your profile information
        info = new Button("View Profile");
        info.setStyle("-fx-background-color: lightskyblue");
        info.setOnAction(e -> {
            //query the database for the information about the user that has signed in via their
            //name
            Employee employee = this.getEmployee();
            employee.display();
        });

        bookAppointment = new Button("Book Appointment");
        bookAppointment.setOnAction(e -> {
            Calendar calendar = new Calendar(BOOK_APPOINTMENT, conn);
            CalendarPane.display(calendar.getScene(), BOOK_APPOINTMENT);
        });
        bookAppointment.setStyle("-fx-background-color: lightskyblue");

        //view calendar
        calBtn = new Button("View Scheduled Surgeries");
        calBtn.setOnAction(e -> {
            Calendar calendar = new Calendar(SURGERY, conn);
            CalendarPane.display(calendar.getScene(), SURGERY);
        });
        calBtn.setStyle("-fx-background-color: lightskyblue");

        //new patient
        addPatient = new Button("Add Patient");
        addPatient.setStyle("-fx-background-color: lightskyblue;");
        addPatient.setOnAction(e -> {
            AddPatient ap = new AddPatient(conn);
            ap.display();
        });

        //new employee
        addEmployee = new Button("Add Employee");
        addEmployee.setOnAction(e -> {
            AddEmployee aE = new AddEmployee(conn);
            aE.display();
        });
        addEmployee.setStyle("-fx-background-color: lightskyblue");

        //new surgery
        addSurgery = new Button("Schedule a surgery");
        addSurgery.setStyle("-fx-background-color: lightskyblue");
        addSurgery.setOnAction(e -> {
            AddSurgery as = new AddSurgery(conn);
            as.display();
        });

        //view the changes that have been made to data pertaining to appointments, employees,
        //and surgeries to make sure that unnecessary changes weren't made, and to see
        //who made those changes
        viewChanges = new Button("View Changes");
        viewChanges.setStyle("-fx-background-color: lightskyblue");
        viewChanges.setOnAction(e -> new ViewChanges().display(conn));

        messaging = new Button();
        messaging.setStyle("-fx-background-image: url('file:///C://Users//mknig//Downloads//mail.png')");
        messaging.setMinSize(30, 25);
        messaging.setOnAction(e -> new Messaging(getEmployee(), conn).display());

        if (getEmployee().getIsAdmin())
            buttons.getChildren().addAll(calBtn, addSurgery, addPatient,
                bookAppointment, viewAppointments, info, addEmployee, messaging);
        else
            buttons.getChildren().addAll(calBtn, addSurgery, addPatient,
                    bookAppointment, viewAppointments, info, messaging);
    }

    private void getLoginPage() {

        //init the labels
        Label welcome = new Label("Welcome");
        welcome.setStyle("-fx-font: 24 Arial");
        welcome.setTextFill(Color.WHITE);
        welcome.setMinWidth(100);
        welcome.setPadding(new Insets(45,0,0,50));

        Label email = new Label("Email:");
        email.setPadding(new Insets(0,0,0,50));
        email.setTextFill(Color.WHITE);

        Label password = new Label("Password:");
        password.setTextFill(Color.WHITE);
        password.setPadding(new Insets(0,0,0,50));

        //now the text input fields
        PText emailT = new PText(200);
        PText passwordT = new PText(200);

        VBox loginHolder = new VBox(30);
        loginHolder.setMinHeight(250);
        loginHolder.setMaxSize(350,250);
        loginHolder.setMinWidth(350);

        BackgroundFill background_fill = new BackgroundFill(Color.rgb(105,105,105),
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);

        Button button = new Button("Login");
        button.setOnAction(e -> {
            if (validateLogin(emailT.getText(), passwordT.getText())) initHomePage();
            else {
                Alert.displayInvalidLogin();
            }
        });

        BorderPane bkd = new BorderPane();
        bkd.setBackground(background);

        GridPane login = new GridPane();
        login.setMaxSize(350,250);
        login.addRow(1, email, emailT);
        login.addRow(2, new Label(""));
        login.addRow(3, password, passwordT);
        login.addRow(4, new Label(""));

        VBox buttons = new VBox();
        buttons.getChildren().add(button);
        buttons.setPadding(new Insets(0,0,0,100));

        login.addRow(5, new Label(""),buttons);
        login.setHgap(20);
        button.setStyle("-fx-background-color: steelblue");

        loginHolder.setStyle("-fx-background-size: 1200 900;\n" +
                "    -fx-background-radius: 18 18 18 18;\n" +
                "    -fx-border-radius: 18 18 18 18;\n" +
                "    -fx-background-color: rgb(53,53,53);");
        loginHolder.getChildren().addAll(welcome, login);

        bkd.setCenter(loginHolder);

        window.setScene(new Scene(bkd));
        window.setMinHeight(500);
        window.setMinWidth(550);
        window.show();
    }

    //find the column in the database associated with that email, and see if the password
    //is correct
    private boolean validateLogin(String email, String password) {

        try {
            PreparedStatement pstm = conn.prepareStatement("SELECT * FROM login WHERE email = ?");
            pstm.setString(1, email);

            ResultSet rs = pstm.executeQuery();
            if (rs != null && rs.getString("password").equals(password)) {
                //use this to query for the details about the person
                //that is currently logged in
                this.currentUserName = rs.getString("name");
                return true;
            }
            else
                return false;

        } catch (SQLException error) {
            System.out.println("SQL ERROR in main.java 298: " + error);
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }

    // gets the information about the user that is currently logged in
    private Employee getEmployee() {
        Employee employee = null;
        try {
            PreparedStatement pstm = conn.prepareStatement("SELECT * FROM employee WHERE firstName = ? " +
                    "AND lastName = ?");
            pstm.setString(1, currentUserName.split(" ")[0]);
            pstm.setString(2, currentUserName.split(" ")[1]);

            ResultSet rs = pstm.executeQuery();

            employee = new Employee(rs.getString("firstName"), rs.getString("lastName"), rs.getString("middleInitial"),
                    rs.getString("email"), rs.getString("number"), rs.getString("DOB"),
                    rs.getString("emergencyNumber"), rs.getString("address"), rs.getString("sex"),
                    rs.getString("college"), rs.getString("position"), rs.getString("pastExperience"),
                    rs.getString("emergencyRelation"), rs.getString("isAdmin").equals("true"));
        } catch (SQLException err) {
            System.out.println("SQL ERROR: " + err);
        }

        return employee;
    }

    //get the people that are associated with the search parameters the the user
    //has chose to search for
    private ObservableList<Person> getPeople() {
        ObservableList<Person> people = FXCollections.observableArrayList();

        try {
            int mapLength = parameters.size();
            int index = 1;

            PreparedStatement pstm;
            //if nothing was selected, show either all the patients or all the employees
            if (!searchAll) {
                //prepare the statement now
                String SQL = "SELECT * FROM " + dbTable.toLowerCase() + " WHERE ";

                // now add the ? marks where needed, as well as the AND when needed
                for (String value : parameters.keySet()) {
                    SQL += (index != mapLength ? value + " = ? AND " : value + " = ?");
                    index++;
                }

                pstm = conn.prepareStatement(SQL);
                int pstmIndex = 1;

                //now set the ? marks with variables
                for (var value : parameters.values())
                    pstm.setString(pstmIndex++, value);

            } else {
                //just get everything from the table that is selected by the user
                String SQL = "SELECT * FROM " + dbTable;
                pstm = conn.prepareStatement(SQL);
            }

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                if (dbTable.equals("Employee")) {
                    people.add(new Employee(rs.getString("firstName"),
                            rs.getString("lastName"), rs.getString("middleInitial"),
                            rs.getString("email"), rs.getString("number"), rs.getString("DOB"),
                            rs.getString("emergencyNumber"), rs.getString("address"), rs.getString("sex"),
                            rs.getString("college"), rs.getString("position"), rs.getString("pastExperience"),
                            rs.getString("emergencyRelation"), rs.getString("isAdmin").equals("true")));
                } else {
                    people.add(new Patient(rs.getString("firstName"),
                            rs.getString("lastName"), rs.getString("middleInitial"),
                            rs.getString("email"), rs.getString("number"), rs.getString("DOB"),
                            rs.getString("emergencyNumber"), rs.getString("address"),rs.getString("height"),
                            rs.getString("weight"), rs.getString("sex"), rs.getString("emergencyRelation")));
                }
            }
        } catch (SQLException error) {
            System.out.println("SQL ERROR: " + error);
        }

        //reset the search all variable
        searchAll = true;

        return people;
    }

    public void initMenuItems() {
        // add functionality for when the user clicks on a record
        ContextMenu menu = new ContextMenu();

        //the menu items we will have
        MenuItem view = new MenuItem("View/Edit");
        MenuItem password = new MenuItem("Change Password");
        MenuItem deleteRequest = new MenuItem("Request deletion");
        MenuItem schedule = new MenuItem("View Schedule");
        MenuItem delete = new MenuItem("Delete");

        // give actions to the menu items
        password.setOnAction(e -> changePassword(table.getSelectionModel().getSelectedItem().getName()));

        schedule.setOnAction(e -> Calendar.showSchedule((Employee)table.getSelectionModel().getSelectedItem(), conn));

        view.setOnAction(e -> {
            if (dbTable.equals("Patient")) {
                new AddPatient(conn,
                        (Patient) table.getSelectionModel().getSelectedItem()).display();
            } else {
                new AddEmployee(conn,
                        (Employee) table.getSelectionModel().getSelectedItem()).display();
            }
        });

        delete.setOnAction(e -> {
            if (dbTable.equals("Patient")) {
                new AddPatient(conn, (Patient) table.getSelectionModel().getSelectedItem(), true);
            } else {
                new AddEmployee(conn, (Employee) table.getSelectionModel().getSelectedItem(), true);
            }
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        });

        //now add the menuitems depending on which table we are referencing
        if (dbTable.equals("Patient")) {
            menu.getItems().addAll(view, new SeparatorMenuItem(),
                    getEmployee().getIsAdmin() ? delete : deleteRequest);
        } else {
            if (getEmployee().getIsAdmin())
                menu.getItems().addAll(view, new SeparatorMenuItem(), password, new SeparatorMenuItem(),
                        schedule, new SeparatorMenuItem(),delete);
            else
                menu.getItems().addAll(schedule, deleteRequest);
        }

        // now add the menu to the table
        table.setOnMouseClicked(e -> {
            if (table.getSelectionModel().getSelectedItem() != null)
                menu.show(table, e.getScreenX(), e.getScreenY());
        });
    }

    //provides the pop up screen that lets the admin change the password of a current user
    //it will then be updated in the database!
    private void changePassword(String eName) {

        Stage window = new Stage();

        //text fields for the inputs
        PText nameT = new PText(150);
        nameT.setText(eName);
        nameT.setEditable(false);
        PText emailT = new PText(150);
        PasswordField password = new PasswordField();
        password.setMaxWidth(150);
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setMaxWidth(150);

        //labels for the boxes
        Label name = new Label("Name:");
        name.setMinWidth(150);

        Label email = new Label("Email:");
        email.setMinWidth(150);

        Label passwordL = new Label("New Password:");
        passwordL.setMinWidth(150);

        Label confirmPasswordL = new Label("Confirm Password:");
        confirmPasswordL.setMinWidth(150);

        //now containers that hold the label and the text fields
        HBox nameContainer = new HBox();
        HBox emailContainer = new HBox();
        HBox passwordContainer = new HBox();
        HBox passwordConfirmContainer = new HBox();

        //add in the labels and the text fields
        nameContainer.getChildren().addAll(name, nameT);
        emailContainer.getChildren().addAll(email, emailT);
        passwordContainer.getChildren().addAll(passwordL, password);
        passwordConfirmContainer.getChildren().addAll(confirmPasswordL, confirmPassword);

        Button confirm = new Button("Confirm");
        confirm.setOnAction(e -> {
            if (nameT.getLength() == 0 || emailT.getLength() == 0 ||
                    password.getLength() == 0 || confirmPassword.getLength() == 0)
                Alert.display();
            else if (!password.getText().equals(confirmPassword.getText()))
                Alert.passwordsMustMatch();
            else  {
                // update the password in the database
                String SQL = "UPDATE login SET password = ? WHERE email = ?";
                try {
                    PreparedStatement pstm = conn.prepareStatement(SQL);
                    pstm.setString(1, password.getText());
                    pstm.setString(2, emailT.getText());
                    pstm.executeUpdate();
                    
                } catch (SQLException err) {
                    System.out.println("Error in line 603 of Main: " + err);
                }
                window.close();
            }
        });

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change Password");

        VBox lay = new VBox();
        lay.getChildren().addAll(nameContainer, emailContainer, passwordContainer,
                passwordConfirmContainer, confirm);

        window.setScene(new Scene(lay,300,150));
        window.showAndWait();
    }


    //init the table that holds the results
    private void initRefinedTable() {

        // init all the columns
        TableColumn <Person, String> nameCol= new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(150);

        TableColumn <Person, String> numberCol= new TableColumn<>("Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        numberCol.setMinWidth(50);

        TableColumn <Person, String> dobCol = new TableColumn<>("DOB");
        dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        dobCol.setMinWidth(50);

        TableColumn<Person, String> sexCol = new TableColumn<>("Sex");
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        sexCol.setMinWidth(50);

        table.getColumns().addAll(nameCol, numberCol, dobCol, sexCol);

    }
}
