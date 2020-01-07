package practice;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    public Button(String text, int x, int y, int width, int height, Color backGroundColor, Color textColor)
    {
        super(text);
        setBounds(x, y, width, height);
        setBackground(backGroundColor);
        setForeground(textColor);
    }
}
