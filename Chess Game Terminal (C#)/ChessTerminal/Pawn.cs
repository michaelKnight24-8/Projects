using System;

namespace ChessTerminal
{
    public partial class Game
    {
        public class Pawn : Piece
        {
            public Pawn(Player player) : base(player) { name = "p"; PieceType = "Pawn"; }
            override
            public void showPossible(Piece[,] board, int sx, int sy)
            {
                var player3 = new Player(3);
                player3.PlayerColor = ConsoleColor.Green;
                var pawnShow = new Pawn(player3);
                pawnShow.name = "o";
                if (board[sy, sx].getPlayer() == 1)
                {
                    if (sy == 1)
                    {
                        if (board[sy + 1, sx] == null)
                        {
                            board[sy + 1, sx] = pawnShow;
                            if (board[sy + 2, sx] == null)
                                board[sy + 2, sx] = pawnShow;
                        }
                       
                    }
                    else if (sy + 1 <= 7 && board[sy + 1, sx] == null)
                        board[sy + 1, sx] = pawnShow;
                    if (sy + 1 <= 7)
                    {
                        if (sx + 1 <= 7 && board[sy + 1, sx + 1] != null && board[sy + 1, sx + 1].getPlayer() != Game.player)
                           board[sy + 1, sx + 1].setPlayerNumber(3);
                        if (sx - 1 >= 0 && board[sy + 1, sx - 1] != null && board[sy + 1, sx - 1].getPlayer() != Game.player)
                           board[sy + 1, sx - 1].setPlayerNumber(3);
                    }
                }
                else
                {
                    if (sy == 6)
                    {
                        if (board[sy - 1, sx] == null)
                        {
                            board[sy - 1, sx] = pawnShow;
                            if (board[sy - 2, sx] == null)
                                board[sy - 2, sx] = pawnShow;
                        }
        
                    }
                    else if (sy - 1 >= 0 && board[sy - 1, sx] == null)
                        board[sy - 1, sx] = pawnShow;
                    if (sy - 1 >= 0)
                    {
                        if (sy - 1 >= 0 && board[sy - 1, sx + 1] != null && board[sy - 1, sx + 1].getPlayer() != Game.player)
                            board[sy - 1, sx + 1].setPlayerNumber(3);
                        if (sy - 1 >= 0 && board[sy - 1, sx - 1] != null && board[sy - 1, sx - 1].getPlayer() != Game.player)
                            board[sy - 1, sx - 1].setPlayerNumber(3);
                    }
                }
            }
            override
            public void Move(Piece[,] board, int sx, int sy, int dx, int dy)
            {
                //check moving forward two spaces after spawn
                if (sy - dy == 2 || dy - sy == 2)
                {
                    if ((board[sy, sx].getPlayer() == 2 && sy == 6) || (board[sy, sx].getPlayer() == 1 && sy == 1))
                    {
                        MovePiece(board, sx, sy, dx, dy);
                    }
                    else
                    {
                        Console.ForegroundColor = ConsoleColor.Red;
                        Console.WriteLine("You can only move you pawn forward 2 spaces if it's your first move");
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                }
                //up diagonal to kill
                else if (((sy - dy == sx - dx) && sy - dy == 1) || ((dx - sx == sy - dy) && dx - sx == 1))
                {
                    if (board[dy, dx] == null)
                        DisplayError();
                    else
                        MovePiece(board, sx, sy, dx, dy);
                }
                //down diagonal to kill
                else if (((dy - sy == sx - dx) && dy - sy == 1) || ((dx - sx == dy - sy) && dx - sx == 1))
                {
                    if (board[dy, dx] == null)
                        DisplayError();
                    else
                        MovePiece(board, sx, sy, dx, dy);
                }
                //player one moving pawn down the board
                else if (dy - sy == 1 && Game.player == 1)
                {
                    if (board[dy, dx] == null)
                        MovePiece(board, sx, sy, dx, dy);
                    else
                        DisplayError();
                }
                //player 2 moving the pawn up the board
                else if (sy - dy == 1 && Game.player == 2)
                {
                    if (board[dy, dx] == null)
                        MovePiece(board, sx, sy, dx, dy);
                    else
                        DisplayError();
                }
                else
                    DisplayError();
            }
        }
    }
}

