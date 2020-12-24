package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.awt.*;
import java.sql.Connection;


public class Main extends Application {
    public BorderPane mainLayout;
    public int buttonRow = 2;
    public int buttonColumn = 0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        initHomePage();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(mainLayout, 700, 400));
        primaryStage.show();
    }

    public void initHomePage() {
        mainLayout = new BorderPane();
        HBox header = new HBox(60);
        HBox searchContainer = new HBox(20);

        //first set up the header section of the homepage
        // set up the radio buttons
        RadioButton cd = new RadioButton("CD");
        RadioButton movie = new RadioButton("Movie");
        movie.setSelected(true);
        // the radio group
        ToggleGroup radioGroup = new ToggleGroup();
        cd.setToggleGroup(radioGroup);
        movie.setToggleGroup(radioGroup);

        TextField searchText = new TextField();
        Button searchBtn = new Button("SEARCH");
        searchContainer.getChildren().addAll(cd, movie, searchText, searchBtn);
        header.setPadding(new Insets(20,0,0,0));

        Button addMovie = new Button("ADD MOVIE");
        addMovie.setOnAction(e -> {
            new AddItem(DBUtil.getConnection(), "Movie");
        });
        addMovie.setStyle("-fx-background-color: lightskyblue;" + "-fx-font: 10 Arial");
        Button addCD = new Button("ADD CD");
        addCD.setStyle("-fx-background-color: lightskyblue;" + "-fx-font: 10 Arial");
        Button addFolder = new Button("ADD FOLDER");
        addFolder.setStyle("-fx-background-color: lightskyblue;" + "-fx-font: 10 Arial");

        HBox buttons = new HBox(5);
        buttons.setPadding(new Insets(0,0,0,20));
        buttons.getChildren().addAll(addMovie, addCD, addFolder);
        header.getChildren().addAll(buttons, searchContainer);
        mainLayout.setTop(header);
        TableView results = new TableView();
        results.setMinWidth(400);
        results.setMinHeight(300);

        GridPane folderButtons = new GridPane();
        Button aB = new Button("A");
        aB.setOnAction(e -> {
            if (buttonColumn > 2) {
                buttonColumn = 0;
                buttonRow++;
            }
            folderButtons.add(new Button("n"), buttonColumn++, buttonRow);
        });
        folderButtons.addRow(0, aB, new Button("B"), new Button("C"));
        folderButtons.addRow(1, new Button("D"), new Button("E"), new Button("F"));


        ScrollPane test = new ScrollPane();
        test.setContent(folderButtons);
        test.setMaxSize(80, 90);

        mainLayout.setLeft(test);
        mainLayout.setRight(results);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
