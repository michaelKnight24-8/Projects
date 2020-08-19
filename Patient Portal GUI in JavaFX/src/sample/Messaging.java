package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;

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
    private Connection conn;

    public Messaging(Employee employee, Connection conn) {
        this.conn = conn;
        this.employee = employee;
        listView = new ListView<>();
        mainLayout = new BorderPane();
        leftSide = new VBox();
        bottomBtns = new HBox(20);

        newMessage = new Button("New message");
        newMessage.setStyle("-fx-background-color: steelblue");
        newMessage.setOnAction(e -> newMessage());

        refresh = new Button("Refresh");
        refresh.setStyle("-fx-background-color: steelblue");
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

        VBox content = new VBox(20);
        content.setPadding(new Insets(140, 0,0,145));
        String cssLayout = "-fx-border-color: red;\n" +
                "-fx-border-style: dashed;\n";
        content.setStyle(cssLayout);
        content.getChildren().addAll(new Label("Select an item to read"), new Label("  Nothing is selected"));

        bottomBtns.setPadding(new Insets(5,0,5,5));
        bottomBtns.getChildren().addAll(newMessage, refresh);

        leftSide.getChildren().addAll(listView, bottomBtns);

        mainLayout.setCenter(content);
        mainLayout.setLeft(leftSide);
    }

    //displays the pop-up page that allows the user to send a message
    private void newMessage() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create a new message");

        //now make the components
        //container that holds all the subcontainers
        BorderPane mainContainer = new BorderPane();
        VBox messagingContent = new VBox(5);

        //for the "TO" label as well as the input field
        HBox toContainer = new HBox(16.5);
        toContainer.setPadding(new Insets(20, 0, 0, 30));
        TextField recipient = new TextField();
        Label toLabel = new Label("  TO  ");
        toLabel.setMaxHeight(30);
        toLabel.setStyle("-fx-border-color: black;");
        toLabel.setBackground(new Background(new BackgroundFill(Color.rgb(105,105,105),
                CornerRadii.EMPTY, Insets.EMPTY)));
        toContainer.getChildren().addAll(toLabel, recipient);

        //same as above, but for the title
        HBox titleContainer = new HBox(10);
        titleContainer.setPadding(new Insets(0, 0, 0, 30));
        TextField title = new TextField();
        Label titleLabel = new Label(" TITLE ");
        titleLabel.setMaxHeight(30);
        titleLabel.setStyle("-fx-border-color: black;");
        titleLabel.setBackground(new Background(new BackgroundFill(Color.rgb(105,105,105),
                CornerRadii.EMPTY, Insets.EMPTY)));
        titleContainer.getChildren().addAll(titleLabel, title);

        TextArea content = new TextArea();
        content.setMaxHeight(285);
        VBox contentContainer = new VBox();
        contentContainer.getChildren().addAll(content);
        contentContainer.setPadding(new Insets(0, 0, 10, 30));

        Button sendBtn = new Button("SEND");
        sendBtn.setStyle("-fx-background-color: steelblue");

        VBox btn = new VBox();
        btn.getChildren().addAll(sendBtn);
        btn.setPadding(new Insets(0,0,5,30));

        messagingContent.getChildren().addAll(toContainer, titleContainer, contentContainer);
        mainContainer.setCenter(messagingContent);
        ListView<String> names = new ListView<>();
        VBox namesContainer = new VBox();

        names.setMinSize(60, 80);
        namesContainer.setPadding(new Insets(60,5,0,5));
        namesContainer.getChildren().add(names);
        mainContainer.setRight(namesContainer);
        mainContainer.setBottom(btn);

        window.setScene(new Scene(mainContainer, 600,320));
        window.showAndWait();
    }
}
