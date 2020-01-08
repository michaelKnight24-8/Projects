package practice;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageFilter;

public class ImageClass {
    public static ImageIcon getImage(char piece)
    {
        switch(piece) {
            case 'P':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\pawnB.png");
            case 'p':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\pawn.png");
            case 'N':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\knightB.png");
            case 'n':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\knight.png");
            case 'r':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\rook.png");
            case 'R':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\rookB.png");
            case 'K':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\kingB.png");
            case 'k':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\king.png");
            case 'q':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\queen.png");
            case 'Q':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\queenB.png");
            case 'b':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\bishop.png");
            case 'B':
                return new ImageIcon("C:\\Users\\mknig\\Downloads\\bishopB.png");

        }
        return null;
    }
}
