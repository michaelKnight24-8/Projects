using System;

namespace ChessOOP
{
    public partial class Game
    {
        public class Bishop : Piece
        {
            public Bishop(Player player) : base(player) { Name = "b"; PieceType = "Bishop"; }

            public void MoveRightDiagonal(Piece [,] board, int sx, int sy, int dx, int dy)
            {
                if (sy > dy)
                {
                    //for the up diagonal
                    for (int col = sx + 1, row = sy - 1; row >= dy && col <= dx; row--, col++)
                    {
                        if (board[row, col] != null)
                        {
                            if (board[row, col].getPlayer() == Game.player)
                            {
                                MovePiece(board, sx, sy, col - 1, row + 1);
                                return;
                            }
                            else
                            {
                                MovePiece(board, sx, sy, col, row);
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
                        if (board[row, col] != null)
                        {
                            if (board[row, col].getPlayer() == Game.player)
                            {
                                MovePiece(board, sx, sy, col - 1, row - 1);
                                return;
                            }
                            else
                            {
                                MovePiece(board, sx, sy, col, row);
                                return;
                            }
                        }
                    }
                }
                MovePiece(board, sx, sy, dx, dy);
            }
            public void MoveLeftDiagonal(Piece [,] board, int sx, int sy, int dx, int dy)
            {
                //for the down diagonal
                if (dy > sy)
                {
                    for (int row = sy + 1, col = sx - 1; col >= dx && row <= dy; row++, col--)
                    {
                        if (board[row, col] != null)
                        {
                            if (board[row, col].getPlayer() == Game.player)
                            {
                                MovePiece(board, sx, sy, col + 1, row - 1);
                                return;
                            }
                            else
                            {
                                MovePiece(board, sx, sy, col, row);
                                return;
                            }

                        }
                    }
                }//for the up diagonal
                else
                {
                    for (int row = sy - 1, col = sx - 1; col >= dx && row >= dy; row--, col--)
                    {
                        if (board[row, col] != null)
                        {
                            if (board[row, col].getPlayer() == Game.player)
                            {
                                MovePiece(board, sx, sy, col + 1, row + 1);
                                return;
                            }
                            else
                            {
                                MovePiece(board, sx, sy, col, row);
                                return;
                            }

                        }
                    }
                }
                MovePiece(board, sx, sy, dx, dy);
            }
            override
            public void showPossible(Piece[,] board, int sx, int sy)
            {
                showPossibleBishop(board, sx, sy);
            }
            protected void showPossibleBishop(Piece [,] board, int sx, int sy)
            {
              
                var bishopShow = new Bishop(showPossibleSpots);
                bishopShow.Name = "o";

                //for the left up diagonal
                for (int row = sx - 1, col = sy - 1; row >= 0 && col >= 0; row--, col--)
                {
                    if (board[col, row] == null)
                        board[col, row] = bishopShow;
                    else
                    {
                        if (board[col, row].getPlayer() != board[sy, sx].getPlayer())
                            board[col, row].setPlayerNumber(3);
                        break;
                    }
                }
                //for the right up diagonal
                for (int row = sx + 1, col = sy - 1; row <= 7 && col >= 0; row++, col--)
                {
                    if (board[col, row] == null)
                        board[col, row] = bishopShow;
                    else
                    {
                        if (board[col, row].getPlayer() != board[sy, sx].getPlayer())
                            board[col, row].setPlayerNumber(3);
                        break;
                    }
                }
                //for the right down diagonal
                for (int row = sx + 1, col = sy + 1; row <= 7 && col <= 7; row++, col++)
                {
                    if (board[col, row] == null)
                        board[col, row] = bishopShow;
                    else
                    {
                        if (board[col, row].getPlayer() != board[sy, sx].getPlayer())
                            board[col, row].setPlayerNumber(3);
                        break;
                    }

                }//for the left down diagonal
                for (int row = sx - 1, col = sy + 1; row >= 0 && col <= 7; row--, col++)
                {
                    if (board[col, row] == null)
                        board[col, row] = bishopShow;
                    else
                    {
                        if (board[col, row].getPlayer() != board[sy, sx].getPlayer())
                            board[col, row].setPlayerNumber(3);
                        break;
                    }
                }
            }
            override
            public void Move(Piece [,] board, int sx, int sy, int dx, int dy)
            {
                if ((dx > sx && (dy < sy || dy > sy)) && (dy - sy == dx - sx || sy - dy == dx - sx))
                    MoveRightDiagonal(board, sx, sy, dx, dy);
                else if ((sx > dx && (sy > dy || sy < dy)) && (sx - dx == dy - sy || sx - dx == sy - dy))
                    MoveLeftDiagonal(board, sx, sy, dx, dy);
                else
                    DisplayError();
            }
        }
    }
}

