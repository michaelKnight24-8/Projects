package practice;

import javax.swing.*;
import java.awt.*;
/*****************************************************************************************
 * quick little button builder class so that making buttons is alot faster, with less code
 * since most of it is duplicated except for some small changes here and there
 * **************************************************************************************/
public class ButtonBuilder {

    private Color backGroundColor, textColor;
    private int x, y;
    private String text;
    public ButtonBuilder(String text, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.text = text;
        setDefaults();
    }
    private void setDefaults()
    {
        backGroundColor = Color.GRAY;
        textColor = Color.white;
    }

    public ButtonBuilder withBackGroundColor(Color color)
    {
        backGroundColor = color;
        return this;
    }

    public ButtonBuilder withTextColor(Color color)
    {
        textColor = color;
        return this;
    }

    public Button build()
    {
         return new Button(text, x, y, 50, 50, backGroundColor, textColor);
    }
}
