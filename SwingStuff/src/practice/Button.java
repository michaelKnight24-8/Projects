package practice;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends JButton {
    private Piece piece;
    public Color backgroundColor;
    public int x;
    public int y;
    private ImageIcon img;
    public ImageIcon getImage() { return img; }
    private ImageClass image = new ImageClass();
    public boolean isPiece;
    public void setImage(ImageIcon img) { setIcon(img); }
    public Piece getPiece() { return piece; }
    public boolean isPiece() { return isPiece; }
    public boolean special() { return piece.special; }
    public int getPlayer()
    {
        if (piece.isWhite())
            return 1;
        else
            return 2;
    }
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }
    public void setPiece(Piece piece) { this.piece = piece; }
    private boolean hasAction;
    public void setAction(boolean action) { hasAction = action; }
    public boolean getHasAction() { return hasAction; }

    public Button()
    {
        isPiece = false;
    }
    public Button(Piece piece)
    {
        setIcon(ImageClass.getImage(piece.getSymbol()));
        this.piece = piece;
        img = ImageClass.getImage(piece.getSymbol());
        isPiece = true;
    }

    public String getPieceName()
    {
        return piece.getName();
    }

}
