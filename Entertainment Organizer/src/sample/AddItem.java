package sample;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;

public class AddItem {
    public Connection conn;
    public Item item;
    public String type;

    public AddItem(Connection conn, String type){
        this.type = type;
        this.conn = conn;
        initLayout();
    }

    private void initLayout() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a new " + this.type);
        BorderPane p = new BorderPane();
        p.setTop(new Label("SUP"));
        window.setScene(new Scene(p, 400,300));

        window.showAndWait();
    }

    private void addToDatabase() {

    }

}
