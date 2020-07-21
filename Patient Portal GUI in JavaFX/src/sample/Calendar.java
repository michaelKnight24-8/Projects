package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;

public class Calendar implements EventHandler<ActionEvent> {

    public int year, month, day, currentYear, currentMonth;
    public int context;
    public Scene scene;
    public GridPane calPane;
    public BorderPane mainLayout;
    public HBox header;
    public VBox vbox;
    public final int SURGERY = 1;
    public final int APPOINTMENT = 2;
    public final int BOOK_APPOINTMENT = 3;
    public Connection conn;


    private CButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10,
            btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20, btn21, btn22,
            btn23, btn24, btn25, btn26, btn27, btn28, btn29, btn30, btn31, btn32, btn33, btn34,
            btn35, btn36, btn37, btn38, btn39, btn40, btn41, btn42, moveLeft, moveRight;
    public ArrayList <CButton> buttons;

    public Scene getScene() { return new Scene(mainLayout,465,550); }

    public Calendar(int context, Connection conn) {
        //set up the scene with gridpane,  positioniong the buttons and the main label for
        //the months
        this.conn = conn;
        this.context = context;
        buttons = new ArrayList<>();
        initButtons();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.year  = localDate.getYear();
        this.currentYear = this.year;
        this.currentMonth = localDate.getMonthValue();
        this.month = this.currentMonth;
        this.day   = localDate.getDayOfMonth();
        this.calPane = new GridPane();
        this.mainLayout = new BorderPane();

        BackgroundFill background_fill = new BackgroundFill(Color.GRAY,
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);
        mainLayout.setBackground(background);

        initButtons();
        header = new HBox();

        vbox = new VBox(40);
        displayCalendar();
        mainLayout.setCenter(calPane);
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private int calcDaysInMonth(int month, int year) {
        // gets the correct number of days for the given month
        //simple array containing the months with 30 days so the if statement isn't super cluttered

        if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        else if (month == 2)
            return (isLeapYear(year) ? 29 : 28);
        else
            return 31;
    }

    private int calcOffset(int month, int year) {
        int daysPassed = 0;
        int startYear = 1753;

        //simple loop to find out the offset needed
        for (int i = startYear; i < year; i++ )
            daysPassed += (isLeapYear(i) ? 366 : 365);

        int days = 0;
        for (int i = 1; i < month; i++)
            days += calcDaysInMonth(i, year);

        return (daysPassed + days) % 7;
    }

    //small function to clear the values in the text views so that each month can
    //be displayed properly
    private void clearTexbtniewText() {
        for (CButton button : buttons) {
            button.setText("");
            button.setStyle("-fx-background-color: gray;");
        }
    }

    public void displayCalendar() {
        //if December was the previous month, increment the year, and set the
        //month to January
        if (month == 13) {
            month = 1;
            year++;
        } else if (month == 0) {
            month = 12;
            year--;
        }

        clearTexbtniewText();
        Button startDay;
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        Label monthDisplay = new Label(months[month - 1] + "   " + year);
        monthDisplay.setMinWidth(400);
        monthDisplay.setStyle("-fx-font: 24 arial;");
        monthDisplay.setAlignment(Pos.CENTER);
        header.getChildren().addAll(moveLeft, monthDisplay, moveRight);

        Label label = new Label("   S        M       T       W       T       F       S");
        label.setStyle("-fx-font: 24 arial;");
        vbox.getChildren().addAll(header, label);
        vbox.setPadding(new Insets(40,0,20,5));
        mainLayout.setTop(vbox);

        int day = calcDaysInMonth(month, year);

        int offset = calcOffset(month, year);
        int texbtniewIndex = 0;

        //switch statement to get where the first day of the month
        //falls in the week
        switch (offset) {
            case 6:
                texbtniewIndex = 0;
                break;
            case 0:
                texbtniewIndex = 1;
                break;
            case 1:
                texbtniewIndex = 2;
                break;
            case 2:
                texbtniewIndex = 3;
                break;
            case 3:
                texbtniewIndex = 4;
                break;
            case 4:
                texbtniewIndex = 5;
                break;
            case 5:
                texbtniewIndex = 6;
                break;
        }
        //now print out the numbers, with the correct spacing
        for (int i = 1; i < day + 1; i++, texbtniewIndex++) {
            String currentDay = Integer.toString(i);
            buttons.get(texbtniewIndex).setText(currentDay);
            buttons.get(texbtniewIndex).setOnAction(this);

            if (i == this.day && month == currentMonth && currentYear == year)
                buttons.get(texbtniewIndex).setStyle("-fx-background-color: rgb(150,150,150);");
            else
                buttons.get(texbtniewIndex).setStyle("-fx-background-color: white; ");
        }
        setUpGridPane();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private void initButtons() {
        //initialize all the texbtniew objects.....get ready....
        btn1 =  new CButton(1); btn2 =  new CButton(2);
        btn3 =  new CButton(3); btn4 =  new CButton(4);
        btn5 =  new CButton(5); btn6 =  new CButton(6);
        btn7 =  new CButton(7); btn8 =  new CButton(8);
        btn9 =  new CButton(9); btn10 = new CButton(10); btn11 =  new CButton(11);
        btn12 = new CButton(11); btn13 =  new CButton(13);
        btn14 = new CButton(13); btn15 =  new CButton(15);
        btn16 = new CButton(15); btn17 =  new CButton(17);
        btn18 = new CButton(18); btn19 =  new CButton(19);
        btn20 = new CButton(20); btn21 =  new CButton(21);
        btn22 = new CButton(22); btn23 =  new CButton(23);
        btn24 = new CButton(24); btn25 =  new CButton(25);
        btn26 = new CButton(26); btn27 =  new CButton(27);
        btn28 = new CButton(28); btn29 =  new CButton(29);
        btn30 = new CButton(30); btn31 =  new CButton(31);
        btn32 = new CButton(32); btn33 =  new CButton(33);
        btn34 = new CButton(34); btn35 =  new CButton(35);
        btn36 = new CButton(36); btn37 =  new CButton(37);
        btn38 = new CButton(38); btn39 =  new CButton(39);
        btn40 = new CButton(40); btn41 =  new CButton(41);
        btn42 = new CButton(42); moveLeft = new CButton(50);
        moveRight = new CButton(51);
        moveLeft.setText("<");
        moveRight.setText(">");
        moveLeft.setOnAction(this);
        moveRight.setOnAction(this);

        //now add them to the array list containing all the buttons
        //lot to do here, but it makes it alot easier to use them later!

        buttons.add(btn1); buttons.add(btn2); buttons.add(btn3); buttons.add(btn4);
        buttons.add(btn5); buttons.add(btn6); buttons.add(btn7); buttons.add(btn8);
        buttons.add(btn9); buttons.add(btn10); buttons.add(btn11); buttons.add(btn12);
        buttons.add(btn13); buttons.add(btn14); buttons.add(btn15); buttons.add(btn16);
        buttons.add(btn17); buttons.add(btn18); buttons.add(btn19); buttons.add(btn20);
        buttons.add(btn21); buttons.add(btn22); buttons.add(btn23); buttons.add(btn24);
        buttons.add(btn25); buttons.add(btn26); buttons.add(btn27); buttons.add(btn28);
        buttons.add(btn29); buttons.add(btn30); buttons.add(btn31); buttons.add(btn32);
        buttons.add(btn33); buttons.add(btn34); buttons.add(btn35); buttons.add(btn36);
        buttons.add(btn37); buttons.add(btn38); buttons.add(btn39); buttons.add(btn40);
        buttons.add(btn41); buttons.add(btn42);
    }

    private void setUpGridPane() {
        calPane.addRow(0,buttons.get(0),buttons.get(1),buttons.get(2),buttons.get(3),buttons.get(4),buttons.get(5),buttons.get(6));
        calPane.addRow(1,buttons.get(7),buttons.get(8), buttons.get(9), buttons.get(10), buttons.get(11), buttons.get(12), buttons.get(13));
        calPane.addRow(2, buttons.get(14), buttons.get(15), buttons.get(16), buttons.get(17), buttons.get(18), buttons.get(19), buttons.get(20));
        calPane.addRow(3, buttons.get(21), buttons.get(22), buttons.get(23), buttons.get(24), buttons.get(25), buttons.get(26), buttons.get(27));
        calPane.addRow(4, buttons.get(28), buttons.get(29), buttons.get(30), buttons.get(31), buttons.get(32), buttons.get(33), buttons.get(34));
        calPane.addRow(5, buttons.get(35), buttons.get(36), buttons.get(37), buttons.get(38), buttons.get(39), buttons.get(40), buttons.get(41));
        calPane.setPadding(new Insets(0,0,0,5));
        calPane.setHgap(5);
        calPane.setVgap(5);
    }

    private void getFromDatabase(int month, String text, int year, int context) {

        if (context == SURGERY) {
            SurgerySchedule surgery = new SurgerySchedule(month + "/" + text + "/" + year, conn);
            SurgeryWindow.display(surgery.getScene());
        } else {
            AppointmentSchedule appointments = new AppointmentSchedule(month + "/" + text + "/" + year);
        }
    }

    @Override
    public void handle(ActionEvent e) {
        if (e.getSource() instanceof CButton) {
            switch (((CButton) e.getSource()).getID()) {
                case 50:
                    this.month--;
                    calPane.getChildren().clear();
                    header.getChildren().clear();
                    vbox.getChildren().clear();
                    displayCalendar();
                    break;
                case 51:
                    this.month++;
                    header.getChildren().clear();
                    calPane.getChildren().clear();
                    vbox.getChildren().clear();
                    displayCalendar();
                    break;
                default:
                    if (context == BOOK_APPOINTMENT) {
                        AddAppointment aa = new AddAppointment(this.month + "/" +
                                ((CButton) e.getSource()).getText() + "/" + this.year);
                    } else if (context == APPOINTMENT) {
                        getFromDatabase(this.month, ((CButton) e.getSource()).getText(), this.year, APPOINTMENT);
                    } else {
                        getFromDatabase(this.month, ((CButton) e.getSource()).getText(), this.year, SURGERY);
                    }
                    break;
            }
        }
    }
}

