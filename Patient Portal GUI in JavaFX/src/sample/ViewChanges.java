package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// little class to init the view changes layout, as well as
// make the database queries, and display the information
public class ViewChanges {

    private Scene scene;
    private ListView<Changes> list;
    private Connection conn;

    // small utility class to hold the changes that have been made to the tables in question
    private class Changes {
        private String name, thingsChanged, date, tableName;

        public Changes(String employeeName, String thingsChanged, String date, String tableName) {
            name = employeeName;
            this.thingsChanged = thingsChanged;
            this.date = date;
            this.tableName = tableName;
        }

        public String toString() {
            String vowels = "aeiou";
            return name + " changed " + (vowels.indexOf(tableName.charAt(0)) == -1 ? "a " : "an ")
            + tableName + " value on " + date; }
    }

    public void display(Connection conn) {
        this.conn = conn;
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        Button appointments = new Button("APPOINTMENTS");
        Button surgeries = new Button("SURGERIES");
        Button patients = new Button("PATIENTS");
        Button employees = new Button("EMPLOYEES");

        appointments.setOnAction(e -> getFromDatabase("appointment"));
        surgeries.setOnAction(e -> getFromDatabase("surgery"));
        patients.setOnAction(e -> getFromDatabase("patient"));
        employees.setOnAction(e -> getFromDatabase("employee"));

        appointments.setStyle("-fx-background-color: lightskyblue");
        surgeries.setStyle("-fx-background-color: lightskyblue");
        patients.setStyle("-fx-background-color: lightskyblue");
        employees.setStyle("-fx-background-color: lightskyblue");

        appointments.setMinSize(120,120);
        patients.setMinSize(120,120);
        surgeries.setMinSize(120,120);
        employees.setMinSize(120,120);

        GridPane layout = new GridPane();
        layout.addRow(0, appointments, surgeries);
        layout.addRow(1, patients, employees);
        layout.setPadding(new Insets(50,0,0,50));
        layout.setHgap(10);
        layout.setVgap(10);

        layout.setBackground(new Background(new BackgroundFill(Color.rgb(105,105,105),
                CornerRadii.EMPTY, Insets.EMPTY)));

        window.setScene(new Scene(layout, 350, 320));
        window.showAndWait();
    }

    private void getFromDatabase(String tableName) {

        list = new ListView<>();
        list.setMinSize(300,200);

        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM changes WHERE tableChanged = ?");
            pstmt.setString(1, tableName);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                //add to the list of changes
                list.getItems().add(new Changes(rs.getString("firstName") + " " + rs.getString("lastName"),
                        rs.getString("changesMade"), rs.getString("date"), tableName));
            }

        } catch(SQLException err) {
            System.out.println("SQL ERROR -> View changes line 69: " + err);
        }
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(list, 450, 200);
        window.setScene(scene);
        window.showAndWait();
    }
}
