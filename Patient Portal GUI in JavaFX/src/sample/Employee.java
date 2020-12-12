package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Employee extends Person {

    public String position, college, pastExperience, password;
    public boolean isAdmin;
    //small class that can be used for displaying the personal information.
    //I need to have the font-size a specific size, as well as other changes. it's faster
    //to do it here, rather than for every single label
    public class DLabel extends Label {

        public DLabel(String text) {
            super(text);
            setWrapText(true);
            setMinWidth(200);
        }
    }

    public Employee(String firstName, String lastName, String middleInitial, String email, String number,
                   String DOB, String emergencyNumber, String address, String sex, String college, String position,
                    String pastExperience, String emergencyRelation, boolean isAdmin) {
        super(firstName,lastName,middleInitial,email,number,DOB, emergencyNumber,address,sex,emergencyRelation);
        this.isAdmin = isAdmin;
        this.pastExperience = pastExperience;
        this.college = college;
        this.position = position;
        //default password, which will be changed later
        this.password = firstName + " " + lastName;
    }

    public void setPassword(String password) { this.password = password; }

    public String getPosition() { return position; }

    public String getCollege() { return college; }

    public String getPastExperience() { return pastExperience; }

    public boolean getIsAdmin() { return isAdmin; }

    public String getPassword() { return password; }

    @Override
    public String toString() {
        return "Employee{" +
                "position='" + position + '\'' +
                ", college='" + college + '\'' +
                ", pastExperience='" + pastExperience + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleInitial='" + middleInitial + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", DOB='" + DOB + '\'' +
                ", emergencyNumber='" + emergencyNumber + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", emergencyRelation='" + emergencyRelation + '\'' +
                '}';
    }

    public void display() {

        //the header for each block of information
        DLabel nameT, emailT, numberT, DOBT, addressT, sexT, emergencyNumberT, emergencyRelationT;

        //labels that contain the actual text
        Label nameL, emailL, numberL, DOBL, addressL, sexL, emergencyNumberL, emergencyRelationL;

        //containers that hold the header and the text labels
        VBox nameHolder = new VBox();
        VBox emailHolder = new VBox();
        VBox numberHolder = new VBox();
        VBox DOBHolder = new VBox();
        VBox addressHolder = new VBox();
        VBox sexHolder = new VBox();
        VBox emNumHolder = new VBox();
        VBox relationHolder = new VBox();

        //init the labels
        nameL = new Label("Name");
        emailL = new Label("Email");
        numberL = new Label("Number");
        DOBL = new Label("DOB");
        addressL = new Label("Address");
        sexL = new Label("Sex");
        emergencyNumberL = new Label("Emergency Number");
        emergencyRelationL = new Label("Emergency Relation");

        //style the headers
        nameL.setStyle("-fx-font: 24 Arial;");
        emailL.setStyle("-fx-font: 24 Arial;");
        numberL.setStyle("-fx-font: 24 Arial;");
        DOBL.setStyle("-fx-font: 24 Arial;");
        addressL.setStyle("-fx-font: 24 Arial;");
        sexL.setStyle("-fx-font: 24 Arial;");
        emergencyNumberL.setStyle("-fx-font: 24 Arial;");
        emergencyRelationL.setStyle("-fx-font: 24 Arial;");

        //now init the labels that will actually display the information
        nameT = new DLabel(firstName + " " + lastName);
        emailT = new DLabel(email);
        numberT = new DLabel(formatNumber(number));
        DOBT = new DLabel(DOB);
        addressT = new DLabel(address);
        sexT = new DLabel(sex);
        emergencyNumberT = new DLabel(formatNumber(emergencyNumber));
        emergencyRelationT = new DLabel(emergencyRelation);

        //now combine the individual elements into their respective holders
        nameHolder.getChildren().addAll(nameL, nameT);
        emailHolder.getChildren().addAll(emailL, emailT);
        numberHolder.getChildren().addAll(numberL, numberT);
        DOBHolder.getChildren().addAll(DOBL, DOBT);
        addressHolder.getChildren().addAll(addressL, addressT);
        sexHolder.getChildren().addAll(sexL, sexT);
        emNumHolder.getChildren().addAll(emergencyNumberL, emergencyNumberT);
        relationHolder.getChildren().addAll(emergencyRelationL, emergencyRelationT);

        //style them a little bit with padding
        nameT.setPadding(new Insets(0,0,0,30));
        emailT.setPadding(new Insets(0,0,0,30));
        numberT.setPadding(new Insets(0,0,0,30));
        DOBT.setPadding(new Insets(0,0,0,30));
        addressT.setPadding(new Insets(0,0,0,30));
        sexT.setPadding(new Insets(0,0,0,30));
        emergencyNumberT.setPadding(new Insets(0,0,0,30));
        emergencyRelationT.setPadding(new Insets(0,0,0,30));

        // add all the containers to the main vbox layout
        VBox mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(nameHolder, emailHolder, numberHolder, DOBHolder, addressHolder,
                sexHolder, emNumHolder, relationHolder);
        mainLayout.setPadding(new Insets(40,0,0,30));

        Scene scene = new Scene(mainLayout);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.setTitle("Your Personal Information");
        window.setMaxWidth(600);
        window.setMaxHeight(600);
        window.setMinHeight(600);
        window.setMinWidth(600);
        window.showAndWait();
    }

    //formats the number in this format: (xxx) xxx-xxxx
    public static String formatNumber(String number) {
        String formattedNumber = "(";
        for (int i = 0; i < number.length(); i++) {
            if (i == 3)
                formattedNumber += ") ";
            if (i == 6)
                formattedNumber += "-";
            formattedNumber += number.charAt(i);
        }
        return formattedNumber;
    }
}
