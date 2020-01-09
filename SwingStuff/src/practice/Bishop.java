package practice;

import java.awt.*;

public class Bishop extends Piece {
    public Bishop(Player player) {
        super(player);
        name = "Bishop";
        symbol = (player.getPlayer() == 1 ? 'b' : 'B');
        special = false;
    }

    @Override
    public void showPossible(Button[][] board, int sx, int sy) {
        showPossibleBishop(board, sx, sy);
    }
    public void showPossibleBishop(Button [][]board, int sx, int sy) {
        //for the left up diagonal
        for (int row = sx - 1, col = sy - 1; row >= 0 && col >= 0; row--, col--)
        {
            if (!board[col][row].isPiece())
            board[col][row].setBackground(Color.GREEN);
            else
            {
                if (board[col][row].getPlayer() != board[sy][sx].getPlayer())
                board[col][row].setBackground(Color.GREEN);
                break;
            }
        }
        //for the right up diagonal
        for (int row = sx + 1, col = sy - 1; row <= 7 && col >= 0; row++, col--)
        {
            if (!board[col][row].isPiece())
            board[col][row].setBackground(Color.GREEN);
            else
            {
                if (board[col][row].getPlayer() != board[sy][sx].getPlayer())
                board[col][row].setBackground(Color.GREEN);
                break;
            }
        }
        //for the right down diagonal
        for (int row = sx + 1, col = sy + 1; row <= 7 && col <= 7; row++, col++)
        {
            if (!board[col][row].isPiece())
            board[col][row].setBackground(Color.GREEN);
            else
            {
                if (board[col][row].getPlayer() != board[sy][sx].getPlayer())
                board[col][row].setBackground(Color.GREEN);
                break;
            }

        }//for the left down diagonal
        for (int row = sx - 1, col = sy + 1; row >= 0 && col <= 7; row--, col++)
        {
            if (!board[col][row].isPiece())
            board[col][row].setBackground(Color.GREEN);
            else
            {
                if (board[col][row].getPlayer() != board[sy][sx].getPlayer())
                board[col][row].setBackground(Color.GREEN);
                break;
            }
        }
    }
    @Override
    public void MovePiece(Button [][]board, int sx, int sy, int dx, int dy) {
        if ((dx > sx && (dy < sy || dy > sy)) && (dy - sy == dx - sx || sy - dy == dx - sx))
            MoveRightDiagonal(board, sx, sy, dx, dy);
        else if ((sx > dx && (sy > dy || sy < dy)) && (sx - dx == dy - sy || sx - dx == sy - dy))
            MoveLeftDiagonal(board, sx, sy, dx, dy);
        else
            displayError();
    }
        public void MoveRightDiagonal(Button [][] board, int sx, int sy, int dx, int dy)
        {
            if (sy > dy)
            {
                //for the up diagonal
                for (int col = sx + 1, row = sy - 1; row >= dy && col <= dx; row--, col++)
                {
                    if (board[row][col].isPiece())
                    {
                        if (board[row][col].getPlayer() == Game.player)
                        {
                            Move(board, sx, sy, col - 1, row + 1);
                            return;
                        }
                        else
                        {
                            Move(board, sx, sy, col, row);
                            return;
                        }

                    }
                }
            }
            //for the down diagonal
            else
            {
                for (int col = sx + 1, row = sy + 1; row <= dy && col <= dx; row++, col++)
                {
                    if (board[row][col].isPiece())
                    {
                        if (board[row][col].getPlayer() == Game.player)
                        {
                            Move(board, sx, sy, col - 1, row - 1);
                            return;
                        }
                        else
                        {
                            Move(board, sx, sy, col, row);
                            return;
                        }
                    }
                }
            }
            Move(board, sx, sy, dx, dy);
        }

        public void MoveLeftDiagonal(Button [][] board, int sx, int sy, int dx, int dy)
        {
            //for the down diagonal
            if (dy > sy)
            {
                for (int row = sy + 1, col = sx - 1; col >= dx && row <= dy; row++, col--)
                {
                    if (board[row][col].isPiece())
                    {
                        if (board[row][col].getPlayer() == Game.player)
                        {
                            Move(board, sx, sy, col + 1, row - 1);
                            return;
                        }
                        else
                        {
                            Move(board, sx, sy, col, row);
                            return;
                        }

                    }
                }
            }//for the up diagonal
            else
            {
                for (int row = sy - 1, col = sx - 1; col >= dx && row >= dy; row--, col--)
                {
                    if (board[row][col].isPiece())
                    {
                        if (board[row][col].getPlayer() == Game.player)
                        {
                            Move(board, sx, sy, col + 1, row + 1);
                            return;
                        }
                        else
                        {
                            Move(board, sx, sy, col, row);
                            return;
                        }

                    }
                }
            }
            Move(board, sx, sy, dx, dy);
        }
    }

