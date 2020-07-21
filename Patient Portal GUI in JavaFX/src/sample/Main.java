package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Main extends Application {
    public Stage window;
    public Scene homePage;
    public Button back, addPatient, search, addSurgery, addEmployee, refinedSearch,
            calBtn, bookAppointment, viewAppointments, info;
    public BorderPane mainLayout;
    public VBox header, buttons;
    public Label searchL;
    public ListView resultView;
    public HBox radioButtons, searchContainer, middle;
    public TextField searchField;
    public ToggleGroup radioGroup;
    public RadioButton patientBtn, employeeBtn;
    public final int SURGERY = 1;
    public final int APPOINTMENT = 2;
    public final int BOOK_APPOINTMENT = 3;
    public Connection conn;
    public String currentUserName;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //database connection that will be passed around between
        //view and functions so that I don't keep opening and closing it
        conn = DBUtil.getConnection();

        window = primaryStage;
        //now get the login page ready.
        getLoginPage();
    }

    //this is called once the login has been verified as accurate
    public void initHomePage() {
        //to hold the search results
        resultView = new ListView();
        resultView.setMinWidth(600);
        resultView.setMaxWidth(600);
        resultView.setMinHeight(400);
        resultView.setMaxHeight(400);

        middle = new HBox(50);
        mainLayout = new BorderPane();
        initLayouts();

        window.setMinWidth(900);
        window.setMinHeight(600);
        window.setTitle("Homepage");

        //only go here if the login was real, or verifie
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

        //for search input field
        searchField = new TextField();
        searchField.setMinHeight(20);
        searchField.setMinWidth(300);
        searchField.setMaxWidth(300);

        initButtons();
        middle.getChildren().addAll(resultView, buttons);
        header.getChildren().addAll(searchL, radioButtons, searchContainer, middle);
        mainLayout.setTop(header);
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
            AddPatient ap = new AddPatient(homePage, back);
            window.setScene(ap.getScene());
            window.setTitle("Add A Patient");
            window.setMaxWidth(800);
            window.setMaxHeight(700);
            window.setMinHeight(600);
            window.setMinWidth(600);
            window.show();
        });

        //new employee
        addEmployee = new Button("Add Employee");
        addEmployee.setOnAction(e -> {
            AddEmployee aE = new AddEmployee(homePage, back);
            window.setScene(aE.getScene());
            window.setTitle("Add An Employee");
            window.setMaxWidth(700);
            window.setMaxHeight(700);
            window.setMinHeight(700);
            window.setMinWidth(700);
            window.show();
        });
        addEmployee.setStyle("-fx-background-color: lightskyblue");

        //new surgery
        addSurgery = new Button("Schedule a surgery");
        addSurgery.setStyle("-fx-background-color: lightskyblue");
        addSurgery.setOnAction(e -> {
            AddSurgery as = new AddSurgery(conn);
            as.display();
        });

        //search button
        search = new Button("Search");
        searchContainer.getChildren().addAll(searchField, search);

        //refined search
        refinedSearch = new Button("Refined Search");
        refinedSearch.setStyle("-fx-background-color: lightskyblue");

        buttons.getChildren().addAll(refinedSearch, calBtn, addSurgery, addPatient,
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
       // loginHolder.setBackground(new Background(new BackgroundFill(Color.rgb(53,53,53),
                //CornerRadii.EMPTY, Insets.EMPTY)));
        loginHolder.getChildren().addAll(welcome, login);
        //loginHolder.setPadding(new Insets(150,0,0,70));
        bkd.setCenter(loginHolder);


        window.setScene(new Scene(bkd));
        window.setMinHeight(500);
        window.setMinWidth(400);
        window.show();
    }

    //find the column in the database associated with taht email, and see if the password
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
        return false;
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
}
