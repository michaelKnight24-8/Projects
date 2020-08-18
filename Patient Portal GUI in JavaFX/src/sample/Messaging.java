package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Messaging {

    // small class that compacts the message data into one small class
    private class Message {
        private String sender, recipient, title, date, content;
        private boolean isRead;
        public Message(String sender, String recipient, String title, String date, String content, boolean isRead) {
            this.sender = sender;
            this.recipient = recipient;
            this.isRead = isRead;
            this.date = date;
            this.title = title;
            this.content = content;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private Employee employee;
    private ListView<Message> listView;
    private BorderPane mainLayout;
    private VBox leftSide;
    private HBox bottomBtns;
    private Button newMessage, refresh;

    public Messaging(Employee employee) {
        this.employee = employee;
        listView = new ListView<>();
        mainLayout = new BorderPane();
        leftSide = new VBox();
        bottomBtns = new HBox(20);
        newMessage = new Button("NEW");
        refresh = new Button("REFRESH");
    }

    //this will display the window to the screen
    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Messaging");

        initLayouts();

        window.setScene(new Scene(mainLayout, 600,300));
        window.showAndWait();
    }

    //init the list view, and the placeholder for the messages the user clicks on
    private void initLayouts() {
        listView.setMaxSize(200, 280);
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));
        listView.getItems().add(new Message("sdf", "sdfd", "Delete this big boi", "09/10/2020", "sdf", true));

        bottomBtns.getChildren().addAll(newMessage, refresh);
        leftSide.getChildren().addAll(listView, bottomBtns);
        mainLayout.setLeft(leftSide);
    }
}
