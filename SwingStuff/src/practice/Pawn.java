package practice;

import javax.swing.*;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static practice.Game.F;

public class Pawn extends Piece {
    public Pawn(Player player)
    {
        super(player);
        name = "Pawn";
        symbol = (player.getPlayer() == 1 ? 'p' : 'P');
    }
    @Override
    public void MovePiece(Button [][] board, int sx, int sy, int dx, int dy) {
        if (board[sy][sx] == board[dy][dx])
        {
            Game.clicks = 1;
            return;
        }
        //check moving forward two spaces after spawn
        if (sy - dy == 2 || dy - sy == 2)
        {
            if (((board[sy][sx].getPlayer() == 2 && sy == 6) || (board[sy][sx].getPlayer() == 1 && sy == 1)) && dx == sx)
            {
                Move(board, sx, sy, dx, dy);
            }
            else
            {
                Game.ta.setText("You can only move you pawn forward 2 spaces if it's your first move"
                        + "\n" + F + "(" + Game.coord[0] + "," + Game.coord[1] + ")");
            }
        }
        else if (sy - dy > 1 || dy - sy > 1 || dx - sx > 1 || sx - dx > 1)
        {
            displayError();
        }
        //up diagonal to kill
        else if (((sy - dy == sx - dx) && sy - dy == 1) || ((dx - sx == sy - dy) && dx - sx == 1))
        {
            if (!board[dy][dx].isPiece())
                displayError();
            else
                Move(board, sx, sy, dx, dy);
        }
        //down diagonal to kill
        else if (((dy - sy == sx - dx) && dy - sy == 1) || ((dx - sx == dy - sy) && dx - sx == 1))
        {
            if (!board[dy][dx].isPiece())
                displayError();
            else
                Move(board, sx, sy, dx, dy);
        }
        //player one moving pawn down the board
        else if (dy - sy == 1 && Game.player == 1)
        {
            if (!board[dy][dx].isPiece())
                Move(board, sx, sy, dx, dy);
            else
                displayError();
        }
        //player 2 moving the pawn up the board
        else if (sy - dy == 1 && Game.player == 2)
        {
            if (!board[dy][dx].isPiece())
                Move(board, sx, sy, dx, dy);
            else
                displayError();
        }
        else
            displayError();
    }
}
