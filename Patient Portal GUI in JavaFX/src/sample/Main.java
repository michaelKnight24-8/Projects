package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Main extends Application {
    public Stage window;
    public Scene homePage;
    public Button back, addPatient, search, addSurgery, addEmployee, refinedSearch, calBtn;
    public BorderPane mainLayout;
    public VBox header, buttons;
    public Label searchL;
    public ListView resultView;
    public HBox radioButtons, searchContainer, middle;
    public TextField searchField;
    public ToggleGroup radioGroup;
    public RadioButton patientBtn, employeeBtn;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //to hold the search results
        resultView = new ListView();
        resultView.setMinWidth(600);
        resultView.setMaxWidth(600);
        resultView.setMinHeight(400);
        resultView.setMaxHeight(400);

        middle = new HBox(50);
        mainLayout = new BorderPane();
        window = primaryStage;
        initLayouts();

        window.setTitle("Homepage");
        window.setScene(homePage);
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

        //view calendar
        calBtn = new Button("View Scheduled Surgeries");
        calBtn.setOnAction(e -> {
            Calendar calendar = new Calendar();
            CalendarPane.display(calendar.getScene());
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
            window.setMaxWidth(800);
            window.setMaxHeight(700);
            window.setMinHeight(600);
            window.setMinWidth(600);
            window.show();
        });
        addEmployee.setStyle("-fx-background-color: lightskyblue");

        //new surgery
        addSurgery = new Button("Schedule a surgery");
        addSurgery.setStyle("-fx-background-color: lightskyblue");

        //search button
        search = new Button("Search");
        searchContainer.getChildren().addAll(searchField, search);

        //refined search
        refinedSearch = new Button("Refined Search");
        refinedSearch.setStyle("-fx-background-color: lightskyblue");

        buttons.getChildren().addAll(refinedSearch, calBtn, addSurgery, addPatient, addEmployee);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
