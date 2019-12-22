using System;

namespace ChessOOP
{
    public partial class Game
    {
        public class Knight : Piece
        {
            public Knight(Player player) : base(player) { name = "n"; PieceType = "Knight"; }
            override
            public void showPossible(Piece[,] board, int sx, int sy)
            {
                var knightShow = new Knight(showPossibleSpots);
                knightShow.name = "o";
                //for showing spots to the left
                if (sx - 2 >= 0)
                {
                    if (sy - 1 >= 0)
                    {
                        if (board[sy - 1, sx - 2] == null)
                            board[sy - 1, sx - 2] = knightShow;
                        else if (board[sy - 1, sx - 2].getPlayer() != Game.player)
                            board[sy - 1, sx - 2].setPlayerNumber(3);
                    }
                    if (sy + 1 >= 0)
                    {
                        if (board[sy + 1, sx - 2] == null)
                            board[sy + 1, sx - 2] = knightShow;
                        else if (board[sy + 1, sx - 2].getPlayer() != Game.player)
                            board[sy + 1, sx - 2].setPlayerNumber(3);
                    }
                        
                }
                if (sx - 1 >= 0) 
                {
                    if (sy - 2 >= 0)
                    {
                        if (board[sy - 2, sx - 1] == null)
                            board[sy - 2, sx - 1] = knightShow;
                        else if (board[sy - 2, sx - 1].getPlayer() != Game.player)
                            board[sy - 2, sx - 1].setPlayerNumber(3);
                    }
                    if (sy + 2 >= 0)
                    {
                        if (board[sy + 2, sx - 1] == null)
                            board[sy + 2, sx - 1] = knightShow;
                        else if (board[sy + 2, sx - 1].getPlayer() != Game.player)
                            board[sy + 2, sx - 1].setPlayerNumber(3);
                    }
                }
                //for showig possible spots to the right
                if (sx + 2 <= 7)
                {
                    if (sy - 1 >= 0)
                    {
                        if (board[sy - 1, sx + 2] == null)
                            board[sy - 1, sx + 2] = knightShow;
                        else if (board[sy - 1, sx + 2].getPlayer() != Game.player)
                            board[sy - 1, sx + 2].setPlayerNumber(3);
                    }
                    if (sy + 1 >= 0)
                    {
                        if (board[sy + 1, sx + 2] == null)
                            board[sy + 1, sx + 2] = knightShow;
                        else if (board[sy + 1, sx + 2].getPlayer() != Game.player)
                            board[sy + 1, sx + 2].setPlayerNumber(3);
                    }
                }
                if (sx + 1 <= 7)
                {
                    if (sy - 2 >= 0)
                    {

                        if (board[sy - 2, sx + 1] == null)
                            board[sy - 2, sx + 1] = knightShow;
                        else if (board[sy - 2, sx + 1].getPlayer() != Game.player)
                            board[sy - 2, sx + 1].setPlayerNumber(3);
                    }
                    if (sy + 2 >= 0) 
                    {
                        if (board[sy + 2, sx + 1] == null)
                            board[sy + 2, sx + 1] = knightShow;
                        else if (board[sy + 2, sx + 1].getPlayer() != Game.player)
                            board[sy + 2, sx + 1].setPlayerNumber(3);
                    }
                }
            }

            override
            public void Move(Piece[,] board, int sx, int sy, int dx, int dy)
            {
                if ((dx - sx == 1 || sx - dx == 1) && dy - sy == 2 || dx - sx == 2 && (sy - dy == 1 || dy - sy == 1) ||
                    (dx - sx == 1 || sx - dx == 1) && sy - dy == 2 || sx - dx == 2 && (sy - dy == 1 || dy - sy == 1))
                {
                    MovePiece(board, sx, sy, dx, dy);
                }
                else
                    DisplayError();
            }
        }
    }
}

