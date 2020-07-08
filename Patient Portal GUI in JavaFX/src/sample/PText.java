package sample;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PText extends TextField {
    PText(double width) {
        this.setMaxWidth(width);
    }
}
