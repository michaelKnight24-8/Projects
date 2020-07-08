package sample;

import javafx.scene.control.Button;

public class CButton extends Button {
    private int ID;
    public CButton(int ID) {
        if (ID == 50 || ID == 51) {
            this.setMinHeight(30);
            this.setMinWidth(30);
        } else {
            this.setMinHeight(60);
            this.setMinWidth(60);
        }
        this.setStyle("-fx-background-color: gray;");
        this.ID = ID;
    }

    public int getID() { return this.ID; }
}
