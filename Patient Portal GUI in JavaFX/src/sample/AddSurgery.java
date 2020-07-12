package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddSurgery {
    public int OR;
    public String surgeon, anes, surgeryType, patient, time, RN, scrub, patientRoom, date;
    public PLabel surgeonL, anesL, surgeryTypeL, patientL, timeL, RNL, scrubL, patientRoomL, ORL, dateL;
    public PText surgeonT, anesT, surgeryTypeT, patientT, timeT, RNT, scrubT, patientRoomT, ORT;
    public DatePicker datePicker;
    public Button scheduledSurgeries, back, saveBtn;
    public Scene addSurgery, homePage;
    public Stage window;
    public GridPane mainLayout;
    public HBox buttons;

    public AddSurgery(Scene homePage, Button back) {
        datePicker = new DatePicker();
        scheduledSurgeries = new Button("View Scheduled Surgeries");
        scheduledSurgeries.setOnAction(e -> {
            Calendar calendar = new Calendar(1);
            CalendarPane.display(calendar.getScene());
        });
        mainLayout = new GridPane();

        this.homePage = homePage;
        this.back = back;
        window = new Stage();
        window.setTitle("Schedule A Surgery");
        saveBtn = new Button("ADD");
        saveBtn.setStyle("-fx-background-color: lightskyblue;");
        saveBtn.setOnAction(e -> saveSurgery());

        buttons = new HBox(10);
        buttons.getChildren().addAll(saveBtn, back);
        initTextFields();
        initLabels();
        initLayout();

        //background color
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(105,105,105),
                CornerRadii.EMPTY, Insets.EMPTY);

        mainLayout.setBackground( new Background(background_fill));
        addSurgery = new Scene(mainLayout, 580,600);
    }

    public Scene getScene() { return addSurgery; }

    private void initTextFields() {
        surgeonT = new PText(200);
        anesT = new PText(200);
        surgeryTypeT = new PText(100);
        patientT = new PText(200);
        timeT = new PText(50);
        RNT = new PText(200);
        scrubT = new PText(200);
        patientRoomT = new PText(50);
        ORT = new PText(50);
    }

    private void initLabels() {
        surgeonL = new PLabel("Surgeon");
        anesL = new PLabel("Anesthesiologist");
        surgeryTypeL = new PLabel("Procedure");
        patientL = new PLabel("Patient Name");
        timeL = new PLabel("Time");
        RNL = new PLabel("Registered Nurse");
        scrubL = new PLabel("Scrub Nurse");
        patientRoomL = new PLabel("Patient Room");
        ORL = new PLabel("O.R.");
        dateL = new PLabel("Date");
    }

    private void initLayout() {
        VBox schedule = new VBox();
        datePicker.setPadding(new Insets(20,0,0,0));
        schedule.getChildren().addAll(datePicker, scheduledSurgeries);
        schedule.setAlignment(Pos.TOP_LEFT);
        mainLayout.addRow(0, patientL);
        mainLayout.addRow(1, patientT);
        mainLayout.addRow(2, surgeryTypeL, dateL, timeL);
        mainLayout.addRow(3, surgeryTypeT, schedule, timeT);
        mainLayout.addRow(4, surgeonL, anesL);
        mainLayout.addRow(5, surgeonT, anesT);
        mainLayout.addRow(6, RNL, scrubL);
        mainLayout.addRow(7, RNT, scrubT);
        mainLayout.addRow(8, ORL, patientRoomL);
        mainLayout.addRow(9, ORT, patientRoomT);
        mainLayout.addRow(11, new Label(""), new Label(""), buttons);
        mainLayout.setHgap(20);
        mainLayout.setVgap(10);
        mainLayout.setPadding(new Insets(20, 0, 0, 30));
    }

    public void saveSurgery() {

    }
}
