package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SurgerySchedule {

    public TableView<Surgery> table;
    public VBox vbox;
    public String day;
    public Connection conn;

    public SurgerySchedule(String day, Connection conn) {
        this.conn = conn;
        table = new TableView<>();
        vbox = new VBox(5);
        this.day = day;
        initTable();
    }

    public Scene getScene() {
        return new Scene(vbox,930,400);
    }

    private ObservableList<Surgery> getSurgeries() {
        ObservableList<Surgery> surgeries = FXCollections.observableArrayList();

        try {
            if (conn == null)
                conn = DBUtil.getConnection();
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery("SELECT * FROM surgery");
            if (rs != null) {
                while (rs.next()) {
                    if (rs.getString("date").equals(day)) {
                        surgeries.add(new Surgery(rs.getInt("ORoom"), rs.getString("surgeon"),
                                rs.getString("anes"), rs.getString("surgeryType"), rs.getString("patient"),
                                rs.getString("time"), rs.getString("RN"), rs.getString("scrub"),
                                rs.getString("patientRoom"), rs.getString("date"), rs.getString("results")));
                    }
                }
            }
        } catch (SQLException error){
            System.out.println("SQL ERROR: " + error);
        }

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

        //results!
        TableColumn<Surgery, String> results = new TableColumn<>("Results");
        results.setCellValueFactory(new PropertyValueFactory<>("results"));
        results.setMinWidth(100);

        table.setItems(getSurgeries());
        table.getColumns().addAll(ORCol, surgeon, anes, anesType,
                patient, time, rn, scrub, patientRoom, results);
        Label label = new Label("SURGERIES FOR " + this.day);
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(930);
        label.setStyle("-fx-font: 24 Arial;");

        ContextMenu menu = new ContextMenu();
        MenuItem view = new MenuItem("View/Edit");
        view.setOnAction(e -> {
            Surgery surgery = table.getSelectionModel().getSelectedItem();
            AddSurgery addSurgery = new AddSurgery(table.getSelectionModel().getSelectedItem(), conn);
            addSurgery.display();
        });
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(e -> {
            AddSurgery addSurgery = new AddSurgery(table.getSelectionModel().getSelectedItem(), conn);
            addSurgery.deleteFromDatabase();
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        });
        menu.getItems().addAll(view, new SeparatorMenuItem(), delete);
        table.setOnMouseClicked(e -> {
            if (table.getSelectionModel().getSelectedItem() != null)
                menu.show(table, e.getScreenX(), e.getScreenY());
        });

        vbox.getChildren().addAll(label, table);
    }
}
