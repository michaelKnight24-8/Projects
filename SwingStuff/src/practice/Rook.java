package practice;

import java.awt.*;

public class Rook extends Bishop {

    public Rook(Player player)
    {
        super(player);
        name = "Rook";
        symbol = (player.getPlayer() == 1 ? 'r' : 'R');
        special = false;
    }
    @Override
    public void showPossible(Button [][] board, int sx, int sy) {
        showPossibleRook(board, sx, sy);
    }
    public void showPossibleRook(Button [][] board, int sx, int sy){
        //for showing posisble spots horizontally
        for (int row = sx - 1; row >= 0; row--)
        {
            if (!board[sy][row].isPiece())
            board[sy][row].setBackground(Color.GREEN);
            else
            {
                if (board[sy][row].getPlayer() != board[sy][sx].getPlayer())
                board[sy][row].setBackground(Color.GREEN);
                break;
            }

        }
        for (int row = sx + 1; row < 8; row++)
        {
            if (!board[sy][row].isPiece())
            board[sy][row].setBackground(Color.GREEN);
            else
            {
                if (board[sy][row].getPlayer() != board[sy][sx].getPlayer())
                board[sy][row].setBackground(Color.GREEN);
                break;
            }
        }

        //for showing possible spots vertically
        for (int col = sy + 1; col < 8; col++)
        {
            if (!board[col][sx].isPiece())
            board[col][sx].setBackground(Color.GREEN);
            else
            {
                if (board[col][sx].getPlayer() != board[sy][sx].getPlayer())
                board[col][sx].setBackground(Color.GREEN);
                break;
            }
        }

        for (int col = sy - 1; col >= 0; col--)
        {
            if (!board[col][sx].isPiece())
            board[col][sx].setBackground(Color.GREEN);
                    else
            {
                if (board[col][sx].getPlayer() != board[sy][sx].getPlayer())
                board[col][sx].setBackground(Color.GREEN);
                break;
            }
        }
    }

    public void MoveHorizontal(Button [][] board, int sy, int sx, int dx, int dy)
    {
        //for if you want to move right
        if (dx > sx)
        {
            for (int row = sx + 1; row <= dx; row++)
            {
                if (board[dy][row].isPiece())
                {
                    if (board[dy][row].getPlayer() == Game.player)
                    {
                        Move(board, sx, sy, row - 1, dy);
                        return;
                    }
                            else
                    {
                        Move(board, sx, sy, row, dy);
                        return;
                    }
                }
            }
        }
        //for moving left
        else
        {
            for (int row = sx - 1; row >= dx; row--)
            {
                if (board[dy][row].isPiece())
                {
                    if (board[dy][row].getPlayer() == Game.player)
                    {
                        Move(board, sx, sy, row + 1, dy);
                        return;
                    }
                    else
                    {
                        Move(board, sx, sy, row, dy);
                        return;
                    }
                }
            }
        }
        //we moved to a legal spot unoccupied by your own piece
        Move(board, sx, sy, dx, dy);
    }
    public void MoveVertical(Button [][] board, int sx, int sy, int dx, int dy)
    {
        if (sy > dy)
        {
            for (int col = sy - 1; col >= dy; col--)
            {
                if (board[col][sx].isPiece())
                {
                    if (board[col][sx].getPlayer() == Game.player)
                    {
                        Move(board, sx, sy, dx, col + 1);
                        return;
                    }
                            else
                    {
                        Move(board, sx, sy, dx, col);
                        return;
                    }
                }
            }
            Move(board, sx, sy, dx, dy);
        }
        else
        {
            for (int col = sy + 1; col <= dy; col++)
            {
                if (board[col][dx].isPiece())
                {
                    if (board[col][dx].getPlayer() == Game.player)
                    {
                        Move(board, sx, sy, dx, col - 1);
                        return;
                    }
                            else
                    {
                        Move(board, sx, sy, dx, col);
                        return;
                    }
                }
            }
            Move(board, sx, sy, dx, dy);
        }
    }
    @Override
    public void MovePiece(Button [][]board, int sx, int sy, int dx, int dy) {
        if (sy == dy)
            MoveHorizontal(board, sy, sx, dx, dy);
        else if (sx == dx)
            MoveVertical(board, sx, sy, dx, dy);
        else
            displayError();
    }
}
