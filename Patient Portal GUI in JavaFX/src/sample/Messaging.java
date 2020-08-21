package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Messaging {

    // small class that compacts the message data into one small class
    private class Message {
        private String sender, recipient, title, date, content;
        private boolean isRead;
        public Message(String sender, String recipient, String title, String date, String content, String isRead) {
            this.sender = sender;
            this.recipient = recipient;
            this.isRead = isRead.equals("true");
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
    private VBox leftSide, content;
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
        newMessage.setOnAction(e -> newMessage(false, null));

        refresh = new Button("Refresh");
        refresh.setOnAction(e -> getMessages());
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

        //first get all the messages from the database to be displayed in the listView
        getMessages();
        listView.setOnMouseClicked(e -> changeMailContent(listView.getSelectionModel().getSelectedItem()));

        content = new VBox(20);
        String cssLayout = "-fx-border-color: black;";
        content.setStyle(cssLayout);
        displayEmptyMail();

        bottomBtns.setPadding(new Insets(5,0,5,5));
        bottomBtns.getChildren().addAll(newMessage, refresh);

        leftSide.getChildren().addAll(listView, bottomBtns);

        mainLayout.setCenter(content);
        mainLayout.setLeft(leftSide);
    }

    private void displayEmptyMail() {
        // clear it all first, then set it to the empty status it has when a message isn't selected yet
        content.getChildren().clear();
        content.getChildren().addAll(new Label("Select an item to read"), new Label("  Nothing is selected"));
        content.setPadding(new Insets(140, 0,0,145));
    }

    //add the message selected to the right side
    private void changeMailContent(Message message) {

        //first delete the labels saying that there is nothing there
        content.getChildren().clear();
        content.setPadding(new Insets(0));
        //now add the content to the right side, which includes the email conent,
        //as well as an option to delete the email, or to reply to the email,
        //or also forward it to someone else

        Label titleLabel = new Label(message.title);
        Label date = new Label(message.date);
        Label sender = new Label(message.sender);

        VBox header = new VBox();
        header.getChildren().addAll(titleLabel, date, sender);
        header.setPadding(new Insets(10,0,20,30));

        Button delete = new Button("DELETE");
        delete.setOnAction(e -> {
            // first delete the item from the list view, then reset the content to show
            // that nothing is selected, like it did before

            updateReadOrDelete(listView.getSelectionModel().getSelectedItem(), "isDeleted");
            listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            displayEmptyMail();
        });
        Button reply = new Button("REPLY");
        reply.setOnAction(e -> newMessage(true, listView.getSelectionModel().getSelectedItem().sender));
        Button forward = new Button("FORWARD");

        HBox btnsHeader = new HBox();
        btnsHeader.getChildren().addAll(delete, reply, forward);
        btnsHeader.setPadding(new Insets(10,0,0,0));

        HBox headerContainer = new HBox(110);
        headerContainer.getChildren().addAll(header, btnsHeader);
        headerContainer.setPadding(new Insets(20,0,0,0));

        Label messageContent = new Label(message.content);
        messageContent.setMaxWidth(300);
        messageContent.setWrapText(true);

        VBox contentContainer = new VBox();
        contentContainer.getChildren().addAll(messageContent);
        contentContainer.setPadding(new Insets(0,0,0,30));
        contentContainer.setMaxWidth(350);

        content.getChildren().addAll(headerContainer, contentContainer);
    }

    //displays the pop-up page that allows the user to send a message
    // if isReply is true, then we will fill in the 'recipient' portion
    // of the message with the person in whom we are replying to
    private void newMessage(boolean isReply, String replyee) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create a new message");

        //now make the components
        //container that holds all the subcontainers
        BorderPane mainContainer = new BorderPane();
        VBox messagingContent = new VBox(5);

        //for the "TO" label as well as the input field
        TextField recipient = new TextField();
        recipient.textProperty().addListener((observable, oldText, newText) -> {
            System.out.println(newText);
        });

        if (isReply) recipient.setText(replyee);

        Label toLabel = new Label("  TO  ");
        toLabel.setMaxHeight(30);
        toLabel.setStyle("-fx-border-color: black;");
        toLabel.setBackground(new Background(new BackgroundFill(Color.rgb(105,105,105),
                CornerRadii.EMPTY, Insets.EMPTY)));

        HBox toContainer = new HBox(16.5);
        toContainer.setPadding(new Insets(20, 0, 0, 30));
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
        sendBtn.setOnAction(e -> {
            if (title.getText().length() == 0 || recipient.getText().length() == 0 || content.getText().length() == 0)
                Alert.display();
            else
                saveToDatabase(recipient.getText(), title.getText(), content.getText());
        });

        VBox btn = new VBox();
        btn.getChildren().addAll(sendBtn);
        btn.setPadding(new Insets(0,0,5,30));

        messagingContent.getChildren().addAll(toContainer, titleContainer, contentContainer);
        mainContainer.setCenter(messagingContent);

        // this will hold all the names of the employees, which will allow the user to MAKE SURE
        // that they send the email to the correct person, and spell the name correctly
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

    //saves the new message to the database, and gets the current time that it is sent at
    private void saveToDatabase(String recipient, String title, String content) {

        // now get today's date to use for the date of when the message was sent
        String todaysDate = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm").format(LocalDateTime.now());

        try {
            String SQL = "INSERT INTO messages (sender, recipient, date, title, isRead, content, isDeleted) " +
                         " VALUES (?,?,?,?,'false',?,'false')";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, employee.getName());
            ps.setString(2, recipient);
            ps.setString(3, todaysDate);
            ps.setString(4, title);
            ps.setString(5, content);

            //make the insertion into the database
            ps.execute();
        } catch (SQLException error) {
            System.out.println("ERROR at line 239 in messages: " + error);
        }

    }

    //gets all the messages sent to the logged-in user form the database
    private void getMessages() {

        //first clear the list of all messages so that a message isn;t duplicated, then add them to the
        //list view
        listView.getItems().clear();

        //now query the database. Get a message if the recipient matches the name of the current
        //user that is logged in

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM messages WHERE recipient = ?");
            ps.setString(1, employee.getName());
            ResultSet rs = ps.executeQuery();

            // if the user has deleted the message from their system, then don't display it to the user
            // but the message is kept in the database for possible security reasons
            while (rs.next())
                if (rs.getString("isDeleted").equals("false"))
                    listView.getItems().add(new Message(rs.getString("sender"), rs.getString("recipient"),
                            rs.getString("title"), rs.getString("date"), rs.getString("content"),
                            rs.getString("isRead")));

        } catch (SQLException error) {
            System.out.println("SQL ERROR at line 184 in messages: " + error);
        }
    }

    //this changes the value of "isDeleted" to true in the database for the particular message
    private void updateReadOrDelete(Message message, String columnToChange) {

        try {
            // string that we will use to update the isDeleted part
            String SQL = "UPDATE messages SET " + columnToChange + " = 'true'" +
                    " WHERE sender = ? AND recipient = ? AND title = ? AND content = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, message.sender);
            ps.setString(2, message.recipient);
            ps.setString(3, message.title);
            ps.setString(4, message.content);
            ps.executeUpdate();

        } catch (SQLException error) {
            System.out.println("SQL ERROR at line 248 in messages: " + error);
        }
    }
}
