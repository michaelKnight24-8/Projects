package practice;

import javax.swing.*;

import static practice.Game.F;

public abstract class Piece {
    Game game = new Game();
    Player p;
    ImageIcon image;
    public char symbol;
    protected boolean special;
    //player 1 is white, player 2 is black
    protected boolean isWhite;
    public boolean alive;
    public boolean isAlive() { return alive; }
    public Boolean isWhite() { return isWhite; }
    ImageIcon getImage()     { return image; }
    public String name;
    public char getSymbol()  { return symbol; }
    public String getName()  { return name;}
    public int x;
    public int y;
    public Piece(Player player)
    {
        alive = true;
        p = player;
        isWhite = player.getPlayer() == 1;
    }
    public void CapturePiece(Button [][] board, int dx, int dy)
    {
        Game.ta.setText("\nPlayer " + Game.player + " took player " +  (Game.player == 1 ? 2 : 1)
                + "'s " + board[dy][dx].getPieceName() + "!");
    }
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
            Game.ta.setText("player " + Game.player + "'s turn now\n" + F + "(" + Game.coord[0] + "," + Game.coord[1] + ")");
        }
        else if (board[dy][dx].isPiece() && (board[dy][dx].getPlayer() == Game.player))
        {
            Game.ta.setText("\nYou already have a piece there");
        }
        else
        {
            if (Character.toUpperCase(board[dy][dx].getPiece().symbol) == ('K'))
            {
                Game.hasWon = true;
                Game.ta.setText("Player has won the match!" + Game.player);
            }
            else if (board[dy][dx].isPiece() && (board[dy][dx].getPlayer() != Game.player))
                CapturePiece(board, dx, dy);

            board[dy][dx] = board[sy][sx];
            board[sy][sx] = new Button();
            board[sy][sx].addMouseListener(game);
            Game.player = (Game.player == 1 ? 2 : 1);
            Game.clicks = 1;
        }

    }
    public void displayError()
    {
        Game.ta.setText("That is not a legal move " + "\n" + F + "(" + Game.coord[0] + "," + Game.coord[1] + ")");
    }
}
