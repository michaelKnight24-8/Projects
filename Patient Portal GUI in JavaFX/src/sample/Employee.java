package sample;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Employee extends Person {

    //small class that can be used for displaying the personal information.
    //I need to have the font-size a specific size, as well as other changes. it's faster
    //to do it here, rather than for every single label
    public class DLabel extends Label {
        public DLabel(String text) {
            super(text);
            setStyle("-fx-font: 24 Arial;");
            setWrapText(true);
            setMinWidth(200);
        }
    }
    public String position, college, pastExperience, password;
    public Employee(String firstName, String lastName, String middleInitial, String email, String number,
                   String DOB, String emergencyNumber, String address, String sex, String college, String position,
                    String pastExperience, String emergencyRelation) {
        super(firstName,lastName,middleInitial,email,number,DOB, emergencyNumber,address,sex,emergencyRelation);

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
        DLabel nameT, emailT, numberT, DOBT, addressT, sexT, emergencyNumberT, emergencyRelationT;
        Label nameL, emailL, numberL, DOBL, addressL, sexL, emergencyNumberL, emergencyRelationL;

        //init the labels
        nameL = new Label("Name");
        emailL = new Label("Email");
        numberL = new Label("Number");
        DOBL = new Label("DOB");
        addressL = new Label("Address");
        sexL = new Label("Sex");
        emergencyNumberL = new Label("Emergency Number");
        emergencyRelationL = new Label("Emergency Relation");

        //now init the labels that will actually display the information
        nameT = new DLabel(firstName + " " + lastName);
        emailT = new DLabel(email);
        numberT = new DLabel(formatNumber(number));
        DOBT = new DLabel(DOB);
        addressT = new DLabel(address);
        sexT = new DLabel(sex);
        emergencyNumberT = new DLabel(formatNumber(emergencyNumber));
        emergencyRelationT = new DLabel(emergencyRelation);

        GridPane mainLayout = new GridPane();

        mainLayout.addRow(0, nameL, emailL, numberL);
        mainLayout.addRow(1, nameT, emailT, numberT);
        mainLayout.addRow(2, DOBL, addressL, sexL);
        mainLayout.addRow(3, DOBT, addressT, sexT);
        mainLayout.addRow(4, emergencyNumberL, emergencyRelationL);
        mainLayout.addRow(5, emergencyNumberT, emergencyRelationT);
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
    private String formatNumber(String number) {
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
