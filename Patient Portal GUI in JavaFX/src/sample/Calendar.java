package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

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

    final static int LEFT = 1;
    final static int RIGHT = 0;

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
            AppointmentSchedule appointments = new AppointmentSchedule(month + "/" + text + "/" + year, conn);
        }
    }

    // we have the employee, so now query the database based upon the name of the employee
    // look in the appointments, and surgeries table to find if their name is in any of the
    // tables. When the right button is clicked, the day increases, and the database is queried again.
    // Same for the left button, but the day goes back one day. It shows the chosen employees schedule for that day
    public static void showSchedule(Employee employee, Connection conn) {

        Stage cal = new Stage();

        cal.initModality(Modality.APPLICATION_MODAL);
        cal.setTitle("Schedule");

        BorderPane lay = new BorderPane();

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);

        Label date = new Label("12/23/2020");
        date.setMinWidth(150);
        date.setAlignment(Pos.CENTER);
        date.setStyle("-fx-font: 24 Arial;");

        Button left = new Button("<");
        left.setOnAction(e -> date.setText(changeDate(date.getText(),
                LEFT, employee.getName(), employee.getPosition(), conn)));

        Button right = new Button(">");
        right.setOnAction(e -> date.setText(changeDate(date.getText(),
                RIGHT, employee.getName(), employee.getPosition(), conn)));

        header.getChildren().addAll(left, date, right);
        lay.setTop(header);
        cal.setScene(new Scene(lay,500,600));
        cal.showAndWait();
    }

    private static String changeDate(String date, int DIRECTION, String name, String position, Connection conn) {
        //months with 30 days
        //4,6,9,11
        //months with 31 days
        //1,3,5,7,8,10,12
        //split the date into month, day, year to manipulate it
        int month = Integer.parseInt(date.split("/")[0]);
        int day = Integer.parseInt(date.split("/")[1]);
        int year = Integer.parseInt(date.split("/")[2]);
        //first change the day. If 0, change the month, and set the date to be the correct number of days
        //for the given month, and maybe year
        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        if (DIRECTION == LEFT) {
            //date will go down
            day--;
            if (day == 0) {
                if (month == 4 || month == 6 || month == 9 || month == 11)
                    day = 30;
                else if (month == 2)
                    day = isLeapYear ? 29 : 28;
                else
                    day = 31;
                month--;
            }
            if (month == 0) {
                year--;
                day = 31;
                month = 12;
            }
        } else {
            //date will go up
            day++;
            if (day == 31 || day == 32 || day == 29 || day == 30) {
                if (month == 2 && (day == 29 || day == 30)) {
                    if (isLeapYear && day == 30)
                        month++;
                    else if (!isLeapYear && day == 29)
                        month++;
                    day = 1;
                } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
                    month++;
                    day = 1;
                } else if (day == 32){
                    month++;
                    day = 1;
                }
            }
            if (month == 13) {
                month = 1;
                year++;
                day = 1;
            }
        }

        //now pass the new date to the database to have it be displayed on the screen
        String newDate = month + "/" + day + "/" + year;
        getScheduleFromDatabase(newDate, name, position.toLowerCase(), conn);
        return newDate;
    }
    // small utility class that compares two dates, and tells if the first date is older or newer
    // than the second date
    public static boolean dateIsPassed(String date1, String date2) {
        return true;
    }

    private static void getScheduleFromDatabase(String date, String name, String position, Connection conn) {

        // query the name from the appointments, and surgery tables
        if (position.equals("nurse") || position.equals("doctor")) {
            //query the appointments database for data
            String sql = "SELECT * FROM appointments WHERE (nurse = ? OR doctor = ?) AND date = ?";

           try {
               PreparedStatement pstm = conn.prepareStatement(sql);
               pstm.setString(1, name);
               pstm.setString(2, name);
               pstm.setString(3, date);
               ResultSet rs = pstm.executeQuery();

               while (rs.next())
                   System.out.println(rs.getString("date") + " at: " + rs.getString("time"));

           } catch (SQLException error) {
               System.out.println("Error in SQL: " + error);
           }
        }
        if (position.equals("surgeon") || position.equals("anes") ||
                position.equals("RN") || position.equals("scrub")) {
            //query the surgery database for the data

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
                                ((CButton) e.getSource()).getText() + "/" + this.year, conn);
                        aa.display();
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

