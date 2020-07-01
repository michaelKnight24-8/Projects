package practice;

import javax.swing.*;

import java.awt.*;

import static practice.Game.F;
import static practice.Game.buttonsPanel;

public abstract class Piece {
    public int possibleSpots;
    public Game game;
    public Player p;
    private ImageIcon image;
    public char symbol;
    protected boolean special;
    //player 1 is white, player 2 is black
    protected boolean isWhite;
    public boolean alive;
    public boolean isAlive() { return alive; }
    public Boolean isWhite() { return isWhite; }
    public ImageIcon getImage()     { return image; }
    public String name;
    public char getSymbol()  { return symbol; }
    public String getName()  { return name;}
    public int x;
    public int y;
    public Piece(Player player)
    {
        possibleSpots = 0;
        game = new Game();
        alive = true;
        p = player;
        isWhite = player.getPlayer() == 1;
    }
    public void CapturePiece(Button [][] board, int dx, int dy)
    {
        Game.ta.setText("Player " + Game.player + " took player " +  (Game.player == 1 ? 2 : 1)
                + "'s \n" + board[dy][dx].getPieceName() + "!");

    }
    public abstract void showPossible(Button [][] board, int sx, int sy);
    public abstract void MovePiece(Button [][]board, int sx, int sy, int dx, int dy);
    public void Move(Button [][] board, int sx, int sy, int dx, int dy)
    {
        if (!board[dy][dx].isPiece())
        {
            board[dy][dx] = board[sy][sx];
            board[sy][sx] = new Button();
            board[sy][sx].addMouseListener(game);
            Game.player = (Game.player == 1 ? 2 : 1); //switch to next player
            Game.clicks = 1;
            Game.ta.setText("Player " + Game.player + "'s turn now\n" + F + "(" + Game.coord[0] + "," + Game.coord[1] + ")");
        }
        else if (board[dy][dx].isPiece() && (board[dy][dx].getPlayer() == Game.player))
        {
            Game.ta.setText("You already have \na piece there");
        }
        else
        {
            if (Character.toUpperCase(board[dy][dx].getPiece().symbol) == ('K'))
            {
                Game.hasWon = true;
                Game.ta.setText("Player " + Game.player + " has \nwon the match!");
            }
            else if (board[dy][dx].isPiece() && (board[dy][dx].getPlayer() != Game.player))
                CapturePiece(board, dx, dy);

            //add the piece to the corresponding list of player pieces captured!
            if (Game.player == 1)
                Game.captureListP1.add(board[dy][dx]);
            else
                Game.captureListP2.add(board[dy][dx]);

            board[dy][dx] = board[sy][sx];
            board[sy][sx] = new Button();
            board[sy][sx].addMouseListener(game);
            game.drawBoard(board, buttonsPanel);

            Game.player = (Game.player == 1 ? 2 : 1);
            Game.clicks = 1;
        }

    }
    public void displayError()
    {
        Game.ta.setText("That is not a legal move " + "\n" + F + "(" + Game.coord[0] + "," + Game.coord[1] + ")");
    }
}
