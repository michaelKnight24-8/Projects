package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class SurgerySchedule {

    public TableView<Surgery> table;
    public VBox vbox;
    public String day;
    public SurgerySchedule(String day) {
        table = new TableView<>();
        vbox = new VBox(5);
        this.day = day;
        initTable();
    }

    public Scene getScene() {
        return new Scene(vbox,830,400);
    }

    private ObservableList<Surgery> getSurgeries() {
        ObservableList<Surgery> surgeries = FXCollections.observableArrayList();
        surgeries.add(new Surgery(2, "Michael", "Rob", "General",
                "James", "5:30", "Alisha", "Dr Tores", "105B", "today"));
        surgeries.add(new Surgery(2, "Derek", "Rob", "Appendectomy",
                "Penis", "7:00", "Alisha", "Dr Tores", "110", "Tomorrow"));

        return surgeries;
    }

    private void initTable() {

        //for OR column
        TableColumn<Surgery, Integer> ORCol= new TableColumn<>("O.R.");
        ORCol.setCellValueFactory(new PropertyValueFactory<>("OR"));
        ORCol.setMinWidth(50);

        //surgeon column
        TableColumn<Surgery, String> surgeon= new TableColumn<>("Surgeon");
        surgeon.setCellValueFactory(new PropertyValueFactory<>("surgeon"));
        surgeon.setMinWidth(100);

        //Anesthesiologist column
        TableColumn<Surgery, String> anes = new TableColumn<>("Anes");
        anes.setCellValueFactory(new PropertyValueFactory<>("anes"));
        anes.setMinWidth(100);

        //Anes type column
        TableColumn<Surgery, String> anesType= new TableColumn<>("Procedure");
        anesType.setCellValueFactory(new PropertyValueFactory<>("surgeryType"));
        anesType.setMinWidth(100);

        //patient column
        TableColumn<Surgery, String> patient = new TableColumn<>("Patient");
        patient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        patient.setMinWidth(100);

        //time column
        TableColumn<Surgery, String> time = new TableColumn<>("Time");
        time.setCellValueFactory(new PropertyValueFactory<>("Time"));
        time.setMinWidth(100);

        //RN name column
        TableColumn<Surgery, String> rn = new TableColumn<>("R.N.");
        rn.setCellValueFactory(new PropertyValueFactory<>("RN"));
        rn.setMinWidth(100);

        //Scrub nurse name column
        TableColumn<Surgery, String> scrub = new TableColumn<>("Scrub");
        scrub.setCellValueFactory(new PropertyValueFactory<>("Scrub"));
        scrub.setMinWidth(100);

        //patient room column
        TableColumn<Surgery, String> patientRoom = new TableColumn<>("Patient Rm");
        patientRoom.setCellValueFactory(new PropertyValueFactory<>("patientRoom"));
        patientRoom.setMinWidth(50);

        table.setItems(getSurgeries());
        table.getColumns().addAll(ORCol,surgeon,anes,anesType,
                patient,time,rn,scrub,patientRoom);
        Label label = new Label("SURGERIES FOR " + this.day);
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(830);
        label.setStyle("-fx-font: 24 Arial;");
        vbox.getChildren().addAll(label, table);
    }
}
