package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.awt.*;


public class Main extends Application {
    public BorderPane mainLayout;
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

        Button addMovie = new Button("ADD MOVIE");
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

    }

    public static void main(String[] args) {
        launch(args);
    }
}
