using System;

namespace ChessOOP
{
    public partial class Game
    {
        public class King : Piece
        {
            public King(Player player) : base(player) { name = "K"; PieceType = "King"; }
            
            override
            public void showPossible(Piece[,] board, int sx, int sy)
            {
                var player3 = new Player(3);
                player3.PlayerColor = ConsoleColor.Green;
                var kingShow = new King(player3);
                kingShow.name = "o";
                //temporarily set the value of the pieces at the spots to the new symbol
                if (sx - 1 >= 0)
                {
                    if (board[sy, sx - 1] == null)
                        board[sy, sx - 1] = kingShow;
                    else if (board[sy, sx - 1].getPlayer() != Game.player)
                        board[sy, sx - 1].setPlayerNumber(3);

                    if (sy - 1 >= 0)
                    {
                        if (board[sy - 1, sx - 1] == null)
                            board[sy - 1, sx - 1] = kingShow;
                        else if (board[sy - 1, sx - 1].getPlayer() != Game.player)
                            board[sy - 1, sx - 1].setPlayerNumber(3);
                    }

                    if (sy + 1 <= 7)
                    {
                        if (board[sy + 1, sx - 1] == null)
                            board[sy + 1, sx - 1] = kingShow;
                        else if (board[sy + 1, sx - 1].getPlayer() != Game.player)
                            board[sy + 1, sx - 1].setPlayerNumber(3);
                    }
                        
                }
                if (sx + 1 <= 7)
                {
                    if (board[sy, sx + 1] == null)
                        board[sy, sx + 1] = kingShow;
                    else if (board[sy, sx + 1].getPlayer() != Game.player)
                        board[sy, sx + 1].setPlayerNumber(3);

                    if (sy - 1 >= 0) 
                    {
                        if (board[sy - 1, sx + 1] == null)
                            board[sy - 1, sx + 1] = kingShow;
                        else if (board[sy - 1, sx + 1].getPlayer() != Game.player)
                            board[sy - 1, sx + 1].setPlayerNumber(3);
                    }
                    if (sy + 1 <= 7)
                    {
                        if (board[sy + 1, sx + 1] == null)
                            board[sy + 1, sx + 1] = kingShow;
                        else if (board[sy + 1, sx + 1].getPlayer() != Game.player)
                            board[sy + 1, sx + 1].setPlayerNumber(3);
                    }
                }
                if (sy - 1 >= 0) 
                {
                    if (board[sy - 1, sx] == null)
                        board[sy - 1, sx] = kingShow;
                    else if (board[sy - 1, sx].getPlayer() != Game.player)
                        board[sy - 1, sx].setPlayerNumber(3);
                }
                if (sy + 1 <= 7) 
                {
                    if (board[sy + 1, sx] == null)
                        board[sy + 1, sx] = kingShow;
                    else if (board[sy + 1, sx].getPlayer() != Game.player)
                        board[sy + 1, sx].setPlayerNumber(3);
                }

                //now reset the values to null since we are done
           

            }

            override
            public void Move(Piece[,] board, int sx, int sy, int dx, int dy)
            {
                if ((dy == sy && (dx - sx > 1 || sx - dx > 1)) || (dx == sx && (sy - dy > 1 || dy - sy > 1)) ||
                  (sx - dx == sy - dy && sx - dx > 1) || (sx - dx == dy - sy && dy - sy > 1) ||
                  (dx - sx == sy - dy && dx - sx > 1) || (dx - sx == dy - sy && dy - sy > 1))
                    DisplayError();
                else
                {
                    MovePiece(board, sx, sy, dx, dy);
                }
            }
        }
    }
}

