package practice;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    private Color baseColor;
    public Color getBaseColor()    { return baseColor; }
    public Button(String text, int x, int y, int width, int height, Color backGroundColor, Color textColor)
    {
        super(text);
        setBounds(x, y, width, height);
        setBackground(backGroundColor);
        setForeground(textColor);
        baseColor = backGroundColor;
    }
}
