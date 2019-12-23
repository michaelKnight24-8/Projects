using System;

namespace ChessOOP
{
    public partial class Game
    {
        public abstract class Piece
        {
           public Player showPossibleSpots;
            public Piece(Player player)
            {
                showPossibleSpots = new Player(3);
                showPossibleSpots.PlayerColor = ConsoleColor.Green;
                this.player = player;
                isAlive = true;
            }
            public abstract void showPossible(Piece[,] board, int sx, int sy);
            public abstract void Move(Piece[,] board, int sx, int sy, int dx, int dy);
            protected bool isAlive;
            public bool getIsAlive()                       { return isAlive; }
            public string name;
            public void setPlayerNumber(int num)           { player.PlayerNumber = num; }
            public void setPlayerColor(ConsoleColor color) { player.PlayerColor = color; }
            public ConsoleColor getPlayerColor()           { return player.PlayerColor; }
            public string getName()                        { return name; }
            public int getPlayer()                         { return player.PlayerNumber; }
            public Player player   { get; set; }
            public int Player      { get; set; }
            public string PieceType     { get; set; }

            public int getTempPlayerNumber()               { return player.TempPlayerNum; }

            /**********************************************************************************
             * The possible spots need to be cleared, and if any piece was a possible spot for 
             * the other player, it will need to be reset to the original color for the player
             ***********************************************************************************/
            public static void clearPossibleSpotsShown(Piece[,] board)
            {
                for (int row = 0; row < 8; row++)
                    for (int col = 0; col < 8; col++)
                    {
                        if (board[row, col] != null && board[row, col].getName().Equals("o"))
                            board[row, col] = null;
                        else if (board[row, col] != null && board[row, col].getPlayer() == 3)
                            board[row, col].setPlayerNumber(board[row, col].getTempPlayerNumber());
                    }
            }
            public void DisplayError()
            {
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine("\nThat is not a legal spot, please try again.");
                Console.ForegroundColor = ConsoleColor.White;
            }
            
            public void CapturePiece(Piece [,] board, int dx, int dy)
            {
                Console.WriteLine("\nPlayer {0} took player {1}'s " + board[dy, dx].PieceType +
                         "!", Game.player, (Game.player == 1 ? 2 : 1));

                //now add it to the capture peices list for the player
                if (Game.player == 1)
                    Game.player1.capturedPieces.Add(board[dy, dx].PieceType);
                else
                    Game.player2.capturedPieces.Add(board[dy, dx].PieceType);

            }
        
             /*******************************************************************************
             * Used by all the different pieces to actually move the piece to the right spot
             * once you know that it is a legal spot
             * *****************************************************************************/
            public void MovePiece(Piece [,] board, int sx, int sy, int dx, int dy)
            {
                if (board[dy, dx] == null)
                {
                    board[dy, dx] = board[sy, sx];
                    board[sy, sx] = null;
                    Game.player = (Game.player == 1 ? 2 : 1); //switch to next player
                }
                else if (board[dy, dx] != null && (board[dy, dx].getPlayer() == Game.player))
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine("\nYou already have a piece there");
                    Console.ForegroundColor = ConsoleColor.White;
                }
                else
                {
                    Console.ForegroundColor = ConsoleColor.Green;
                    if (board[dy, dx].getName().Equals("K"))
                    {
                        Game.hasWon = true;
                        Console.WriteLine("Player {0} has won the match!", Game.player);
                    }
                    else if (board[dy, dx] != null && (board[dy, dx].getPlayer() != Game.player))
                        CapturePiece(board, dx, dy);

                    Console.ForegroundColor = ConsoleColor.White;
                    board[dy, dx] = board[sy, sx];
                    board[sy, sx] = null;
                    Game.player = (Game.player == 1 ? 2 : 1); 
                }
            }
        }
    }
}

