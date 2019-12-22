using System;

namespace ChessOOP
{
    public partial class Game
    {
        public class Rook : Bishop
        {
            public Rook(Player player) : base(player) { name = "r"; PieceType = "Rook"; }
            public void MoveHorizontal(Piece [,] board, int sy, int sx, int dx, int dy)
            {
                //for if you want to move right
                if (dx > sx)
                {
                    for (int row = sx + 1; row <= dx; row++)
                    {
                        if (board[dy, row] != null)
                        {
                            if (board[dy, row].getPlayer() == Game.player)
                            {
                                MovePiece(board, sx, sy, row - 1, dy);
                                return;
                            }
                            else
                            {
                                MovePiece(board, sx, sy, row, dy);
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
                        if (board[dy, row] != null)
                        {
                            if (board[dy, row].getPlayer() == Game.player)
                            {
                                MovePiece(board, sx, sy, row + 1, dy);
                                return;
                            }
                            else
                            {
                                MovePiece(board, sx, sy, row, dy);
                                return;
                            }
                        }
                    }
                }
                //we moved to a legal spot unoccupied by your own piece
                MovePiece(board, sx, sy, dx, dy);
            }
            public void MoveVertical(Piece [,] board, int sx, int sy, int dx, int dy)
            {
                if (sy > dy)
                {
                    for (int col = sy - 1; col >= dy; col--)
                    {
                        if (board[col, sx] != null)
                        {
                            if (board[col, sx].getPlayer() == Game.player)
                            {
                                MovePiece(board, sx, sy, dx, col + 1);
                                return;
                            }
                            else
                            {
                                MovePiece(board, sx, sy, dx, col);
                                return;
                            }
                        }
                    }
                    MovePiece(board, sx, sy, dx, dy);
                }
                else
                {
                    for (int col = sy + 1; col <= dy; col++)
                    {
                        if (board[col, dx] != null)
                        {
                            if (board[col, dx].getPlayer() == Game.player)
                            {
                                MovePiece(board, sx, sy, dx, col - 1);
                                return;
                            }
                            else
                            {
                                MovePiece(board, sx, sy, dx, col);
                                return;
                            }
                        }
                    }
                    MovePiece(board, sx, sy, dx, dy);
                }
            }
            override
            public void showPossible(Piece[,] board, int sx, int sy)
            {
                showPossibleRook(board, sx, sy);
            }
            protected void showPossibleRook(Piece[,] board, int sx, int sy)
            {
                var rookShow = new Rook(showPossibleSpots);
                rookShow.name = "o";
                //for showing posisble spots horizontally
                for (int row = sx - 1; row >= 0; row--)
                {
                    if (board[sy, row] == null)
                        board[sy, row] = rookShow;
                    else
                    {
                        if (board[sy, row].getPlayer() != board[sy, sx].getPlayer())
                            board[sy, row].setPlayerNumber(3);
                        break;
                    }
            
                }
                for (int row = sx + 1; row < 8; row++)
                {
                    if (board[sy, row] == null)
                        board[sy, row] = rookShow;
                    else
                    {
                        if (board[sy, row].getPlayer() != board[sy, sx].getPlayer())
                            board[sy, row].setPlayerNumber(3);
                        break;
                    }
                }

                //for showing possible spots vertically
                for (int col = sy + 1; col < 8; col++)
                {
                    if (board[col, sx] == null)
                        board[col, sx] = rookShow;
                    else
                    {
                        if (board[col, sx].getPlayer() != board[sy, sx].getPlayer())
                            board[col, sx].setPlayerNumber(3);
                        break;
                    }
                }

                for (int col = sy - 1; col >= 0; col--)
                {
                    if (board[col, sx] == null)
                        board[col, sx] = rookShow;
                    else
                    {
                        if (board[col, sx].getPlayer() != board[sy, sx].getPlayer())
                            board[col, sx].setPlayerNumber(3);
                        break;
                    }
                }
            }

            override
            public void Move(Piece[,] board, int sx, int sy, int dx, int dy)
            {
                if (sy == dy)
                    MoveHorizontal(board, sy, sx, dx, dy);
                else if (sx == dx)
                    MoveVertical(board, sx, sy, dx, dy);
                else
                    DisplayError();
            }
        }
    }
}

