package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Main extends Application {
    public Stage window;
    public Scene homePage;
    public Button back, addPatient, search, addSurgery, addEmployee, refinedSearch,
            calBtn, bookAppointment, viewAppointments, info, clear;
    public BorderPane mainLayout;
    public VBox header, buttons;
    public Label searchL;
    public HBox radioButtons, searchContainer, middle;
    public TextField searchField;
    public ToggleGroup radioGroup;
    public RadioButton patientBtn, employeeBtn;
    public final int SURGERY = 1;
    public final int APPOINTMENT = 2;
    public final int BOOK_APPOINTMENT = 3;
    public Connection conn;
    public String currentUserName;
    private TableView <Person> table;
    private HashMap<String, String> parameters;
    private String dbTable;
    private boolean searchAll;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //database connection that will be passed around between
        //view and functions so that I don't keep opening and closing it
        conn = DBUtil.getConnection();
        table = new TableView <>();
        table.setMinSize(392,200);
        table.setMaxSize(392,200);
        window = primaryStage;
        parameters = new HashMap<>();
        //now get the login page ready.
        getLoginPage();
        searchAll = true;
    }

    //this is called once the login has been verified as accurate
    public void initHomePage() {

        middle = new HBox(50);
        mainLayout = new BorderPane();
        initLayouts();

        window.setMinWidth(900);
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

        employeeBtn = new RadioButton("Employee");
        employeeBtn.setToggleGroup(radioGroup);
        radioButtons.getChildren().addAll(patientBtn, employeeBtn);


        initButtons();

        GridPane gp = new GridPane();
        //init text fields
        PText fNameT = new PText(200);
        PText lNameT = new PText(200);
        PText addressT = new PText(200);
        PText emailT = new PText(150);
        PText numberT = new PText(100);

        //init the checkboxes
        CheckBox searchByFirstName = new CheckBox("First Name");
        CheckBox searchByLastName = new CheckBox("Last Name");
        CheckBox addressSearch = new CheckBox("Address");
        CheckBox numberSearch = new CheckBox("Number");
        CheckBox emailSearch = new CheckBox("Email");

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

        gp.addRow(0, searchByFirstName, fNameT, searchByLastName, lNameT);
        gp.addRow(1, addressSearch, addressT, numberSearch, numberT);
        gp.addRow(2, emailSearch, emailT);
        gp.setVgap(10);

        Button okBtn = new Button("SEARCH");
        okBtn.setStyle("-fx-background-color: lightskyblue");
        okBtn.setMinWidth(100);
        initRefinedTable();
        okBtn.setOnAction(e -> {
            parameters.clear();
            //get the values that the user has entered in, as well as the search filters.
            if (searchByFirstName.isSelected()) {
                searchAll = false;
                parameters.put("firstName", fNameT.getText());
            }
            if (searchByLastName.isSelected()) {
                searchAll = false;
                parameters.put("lastName", lNameT.getText());
            }
            if (addressSearch.isSelected()) {
                searchAll = false;
                parameters.put("address", addressT.getText());
            }
            if (numberSearch.isSelected()) {
                searchAll = false;
                parameters.put("number", numberT.getText());
            }
            if (emailSearch.isSelected()) {
                searchAll = false;
                parameters.put("email", emailT.getText());
            }

            RadioButton button = (RadioButton) radioGroup.getSelectedToggle();
            dbTable = button.getText();
            table.setItems(getPeople());
        });
        HBox buttonsHolder = new HBox(15);
        buttonsHolder.getChildren().addAll(okBtn, clear);
        gp.addRow(3, new Label(""));
        gp.addRow(4, buttonsHolder);

        middle.getChildren().addAll(buttons);
        middle.setPadding(new Insets(150, 90,0,0));
        header.getChildren().addAll(searchL, radioButtons, searchContainer, gp, table);
        mainLayout.setRight(middle);
        mainLayout.setLeft(header);
        mainLayout.setBackground(background);
        homePage = new Scene(mainLayout, 900,600);
    }

    //set up the buttons
    public void initButtons() {

        buttons = new VBox(20);

        //back button
        back = new Button("HOME");
        back.setStyle("-fx-background-color: lightskyblue");
        back.setOnAction(e -> {
            window.setScene(homePage);
            window.setTitle("Home");
            window.setMinHeight(600);
            window.setMaxHeight(600);
            window.setMinWidth(900);
            window.setMaxWidth(900);
            window.show();
        });

        viewAppointments = new Button("View Appointments");
        viewAppointments.setOnAction(e -> {
            Calendar calendar = new Calendar(APPOINTMENT, conn);
            CalendarPane.display(calendar.getScene(), APPOINTMENT);
        });
        viewAppointments.setStyle("-fx-background-color: lightskyblue");

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

        buttons.getChildren().addAll(calBtn, addSurgery, addPatient,
                addEmployee, bookAppointment, viewAppointments, info);
    }

    private void getLoginPage() {

        //init the labels
        Label welcome = new Label("Welcome");
        Label email = new Label("Email:");
        email.setPadding(new Insets(0,0,0,50));
        email.setTextFill(Color.WHITE);
        Label password = new Label("Password:");
        password.setTextFill(Color.WHITE);
        password.setPadding(new Insets(0,0,0,50));
        welcome.setStyle("-fx-font: 24 Arial");
        welcome.setTextFill(Color.WHITE);
        welcome.setMinWidth(100);
        welcome.setPadding(new Insets(45,0,0,50));

        //now the text input fields
        PText emailT = new PText(200);
        PText passwordT = new PText(200);

        VBox loginHolder = new VBox(30);
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(105,105,105),
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);

        loginHolder.setMinHeight(250);
        loginHolder.setMaxSize(350,250);
        loginHolder.setMinWidth(350);


        Button button = new Button("Login");
        button.setOnAction(e -> {
            if (validateLogin(emailT.getText(), passwordT.getText())) initHomePage();
            else {
                Alert.displayInvalidLogin();
            }
        });

        BorderPane bkd = new BorderPane();
        bkd.setBackground(background);

        BorderPane middle = new BorderPane();
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

//        try {
//            PreparedStatement pstm = conn.prepareStatement("SELECT * FROM login WHERE email = ?");
//            pstm.setString(1, email);
//            ResultSet rs = pstm.executeQuery();
//            if (rs != null && rs.getString("password").equals(password)) {
//                //use this to query for the details about the person
//                //that is currently logged in
//                this.currentUserName = rs.getString("name");
//                return true;
//            }
//            else
//                return false;
//
//        } catch (SQLException error) {
//            System.out.println("SQL ERROR in main.java 298: " + error);
//        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Employee getEmployee() {
        Employee employee = null;
        try {
            PreparedStatement pstm = conn.prepareStatement("SELECT * FROM employee WHERE name = ?");
            pstm.setString(1, currentUserName);
            ResultSet rs = pstm.executeQuery();
            String [] names = rs.getString("name").split(" ");
            employee = new Employee(names[0], names[1], rs.getString("middleInitial"),
                    rs.getString("email"), rs.getString("number"), rs.getString("DOB"),
                    rs.getString("emergencyNumber"), rs.getString("address"), rs.getString("sex"),
                    rs.getString("college"), rs.getString("position"), rs.getString("pastExperience"),
                    rs.getString("emergencyRelation"));
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

                for (String value : parameters.keySet()) {
                    SQL += (index != mapLength ? value + " = ? AND " : value + " = ?");
                    index++;
                }
                System.out.println(SQL);

                pstm = conn.prepareStatement(SQL);

                int pstmIndex = 1;
                //now set the ? marks with variables
                for (var value : parameters.values())
                    pstm.setString(pstmIndex++, value);
            } else {
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
                            rs.getString("emergencyRelation")));
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
        searchAll = true;
        return people;
    }
    private void initRefinedTable() {

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

        ContextMenu menu = new ContextMenu();
        MenuItem view = new MenuItem("View/Edit");
        view.setOnAction(e -> {
            if (dbTable.equals("Patient")) {
                Patient patient = (Patient) table.getSelectionModel().getSelectedItem();
                AddPatient addPatient = new AddPatient(conn,
                        (Patient) table.getSelectionModel().getSelectedItem());
                addPatient.display();
            } else {
                Employee employee = (Employee) table.getSelectionModel().getSelectedItem();
                AddEmployee addEmployee = new AddEmployee(conn,
                        (Employee) table.getSelectionModel().getSelectedItem());
                addEmployee.display();
            }
        });
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(e -> {
            if (dbTable.equals("Patient")) {
                new AddPatient(conn, (Patient) table.getSelectionModel().getSelectedItem(), true);
            } else {
                new AddEmployee(conn, (Employee) table.getSelectionModel().getSelectedItem(), true);
            }
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        });
        menu.getItems().addAll(view, new SeparatorMenuItem(), delete);
        table.setOnMouseClicked(e -> {
            if (table.getSelectionModel().getSelectedItem() != null)
                menu.show(table, e.getScreenX(), e.getScreenY());
        });
    }
}
