package practice;

import java.awt.*;

public class Knight extends Piece {

    public Knight(Player player)
    {
        super(player);
        name = "Knight";
        symbol = (player.getPlayer() == 1 ? 'n' : 'N');
        special = false;
    }
    @Override
    public void showPossible(Button [][] board, int sx, int sy) {

        if (sx - 2 >= 0) {

            if (sy - 1 >= 0) {
                if (!board[sy - 1][sx - 2].isPiece())
                    board[sy - 1][sx - 2].setBackground(Color.GREEN);
                else if (board[sy - 1][sx - 2].getPlayer() != Game.player)
                    board[sy - 1][sx - 2].setBackground(Color.GREEN);
            }
            if (sy + 1 <= 7) {
                if (!board[sy + 1][sx - 2].isPiece())
                    board[sy + 1][sx - 2].setBackground(Color.GREEN);
                else if (board[sy + 1][sx - 2].getPlayer() != Game.player)
                    board[sy + 1][sx - 2].setBackground(Color.GREEN);
            }
        }
        if (sx - 1 >= 0)
        {
            if (sy - 2 >= 0) {
                if (!board[sy - 2][sx - 1].isPiece())
                    board[sy - 2][sx - 1].setBackground(Color.GREEN);
                else if (board[sy - 2][sx - 1].getPlayer() != Game.player)
                    board[sy - 2][sx - 1].setBackground(Color.GREEN);
            }
                if (sy + 2 <= 7) {
                    if (!board[sy + 2][sx - 1].isPiece())
                        board[sy + 2][sx - 1].setBackground(Color.GREEN);
                    else if (board[sy + 2][sx - 1].getPlayer() != Game.player)
                        board[sy + 2][sx - 1].setBackground(Color.GREEN);
                }
            }
            //for showig possible spots to the right
            if (sx + 2 <= 7) {
                if (sy - 1 >= 0) {
                    if (!board[sy - 1][sx + 2].isPiece())
                        board[sy - 1][sx + 2].setBackground(Color.GREEN);
                    else if (board[sy - 1][sx + 2].getPlayer() != Game.player)
                        board[sy - 1][sx + 2].setBackground(Color.GREEN);
                }
                if (sy + 1 <= 7) {
                    if (!board[sy + 1][sx + 2].isPiece())
                        board[sy + 1][sx + 2].setBackground(Color.GREEN);
                    else if (board[sy + 1][sx + 2].getPlayer() != Game.player)
                        board[sy + 1][sx + 2].setBackground(Color.GREEN);
                }
            }
            if (sx + 1 <= 7) {
                if (sy - 2 >= 0) {
                    if (!board[sy - 2][sx + 1].isPiece())
                        board[sy - 2][sx + 1].setBackground(Color.GREEN);
                    else if (board[sy - 2][sx + 1].getPlayer() != Game.player)
                        board[sy - 2][sx + 1].setBackground(Color.GREEN);
                }
                if (sy + 2 <= 7) {

                    if (!board[sy + 2][sx + 1].isPiece())
                        board[sy + 2][sx + 1].setBackground(Color.GREEN);
                    else if (board[sy + 2][sx + 1].getPlayer() != Game.player)
                        board[sy + 2][sx + 1].setBackground(Color.GREEN);
                }
            }
        }

    @Override
    public void MovePiece(Button [][]board, int sx, int sy, int dx, int dy) {
        if ((dx - sx == 1 || sx - dx == 1) && dy - sy == 2 || dx - sx == 2 && (sy - dy == 1 || dy - sy == 1) ||
                (dx - sx == 1 || sx - dx == 1) && sy - dy == 2 || sx - dx == 2 && (sy - dy == 1 || dy - sy == 1))
        {
            Move(board, sx, sy, dx, dy);
        }
        else
            displayError();
    }
}
