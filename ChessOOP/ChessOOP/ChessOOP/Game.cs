using System;
using System.Collections.Generic;
using System.Text;

namespace ChessOOP
{
    partial class Game
    {
        public static Player player1 = new Player(1);
        public static Player player2 = new Player(2);
        public List<string> colorList = new List<string>() { "RED", "BLUE", "DARK BLUE",
                                                          "YELLOW", "CYAN", "WHITE", "GRAY"};
        public static bool hasWon = false;
        public static int player = 1;

        Piece[,] board = new Piece[8, 8]
        {
                {new Rook(new Player(1)),  new Bishop(new Player(1)), new Knight(new Player(1)), new King(new Player(1)),
                 new Queen(new Player(1)), new Knight(new Player(1)), new Bishop(new Player(1)), new Rook(new Player(1))},
                {new Pawn(new Player(1)),  new Pawn(new Player(1)),   new Pawn(new Player(1)),   new Pawn(new Player(1)),
                 new Pawn(new Player(1)),  new Pawn(new Player(1)),   new Pawn(new Player(1)),   new Pawn(new Player(1))},
                {null, null, null, null, null, null, null, null },
                {null, new King(new Player(2)), null, null, new King(new Player(1)), null, null, null },
                {null, null, null, null, null, null, null, null },
                {null, null, null, null, null, null, null, null },
                {new Pawn(new Player(2)),  new Pawn(new Player(2)),   new Pawn(new Player(2)), new Pawn(new Player(2)),
                 new Pawn(new Player(2)),  new Pawn(new Player(2)),   new Pawn(new Player(2)),   new Pawn(new Player(2))},
                {new Rook(new Player(2)),  new Bishop(new Player(2)), new Knight(new Player(2)), new Queen(new Player(2)),
                 new King(new Player(2)),  new Knight(new Player(2)), new Bishop(new Player(2)), new Rook(new Player(2)) }
        };

        public void startGame()
        {
            bool specialCase = false;
            string p1Color = null;
            string p2Color = null;

            while (true)
            {
                Console.WriteLine("Possible colors are: Red, Blue, Dark blue, Yellow, Cyan, White and Gray");
                Console.Write("Player 1, please choose a color \n > ");
                p1Color = Console.ReadLine();
                Console.Write("Player 2, please choose a color \n > ");
                p2Color = Console.ReadLine();
                if (!colorList.Contains(p1Color.ToUpper()) || !colorList.Contains(p2Color.ToUpper()))
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine("Please choose a color from the list given");
                    Console.ForegroundColor = ConsoleColor.White;
                }
                else
                    break;
            }

            /*************************
             * the main game loop
             * ***********************/
            while (!hasWon)
            {
                DrawBoard(board, p1Color, p2Color);
                Console.Write("(Hint: type 'D' to display options, or add an 's' at the end to see the possible moves) \nPlayer {0}, which piece are you going to move? \n> ", player);
                string source = Console.ReadLine();
                if (source.Length == 1 && !source.ToUpper().Equals("D"))
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine("Please enter a correct command");
                    Console.ForegroundColor = ConsoleColor.White;
                    continue;
                }
                else
                {
                    DisplayOptions();
                    continue;
                }
                if (source.Length == 3 && Char.ToUpper(source[2]) == 'S')
                {
                    specialCase = true;
                    int sourceX = Char.ToUpper(source[0]) - 65;
                    int sourceY = source[1] - 49;
                    if (board[sourceY, sourceX] == null)
                    {
                        Console.ForegroundColor = ConsoleColor.Red;
                        Console.WriteLine("Please choose a spot that is already occupied by a piece");
                        Console.ForegroundColor = ConsoleColor.White;
                        continue;
                    }
                    Console.WriteLine("These are all the legal moves for the {0} at this spot",
                        board[sourceY, sourceX].PieceType);
                    board[sourceY, sourceX].showPossible(board, sourceX, sourceY);
                    DrawBoard(board, p1Color, p2Color);
                    Piece.clearPossibleSpotsShown(board);
                }
                Console.Write("Where do you want to move it? \n> ");
                string destination = Console.ReadLine();

                if ((source.Length != 2 || destination.Length != 2 || !isValid(source) || !isValid(destination)) && !specialCase)
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine("Please choose a valid coordinate");
                    Console.ForegroundColor = ConsoleColor.White;
                }
                else
                {//convert coordinates to array notation
                    int sourceX = Char.ToUpper(source[0]) - 65;
                    int sourceY = source[1] - 49;
                    int destinationX = Char.ToUpper(destination[0]) - 65;
                    int destinationY = destination[1] - 49;
                    if (board[sourceY, sourceX] == null)
                    {
                        Console.ForegroundColor = ConsoleColor.Red;
                        Console.WriteLine("You did not select a piece");
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    //make sure you aren't trying to access a piece that isn't yours
                    else if (board[sourceY, sourceX].getPlayer() != player)
                    {
                        Console.ForegroundColor = ConsoleColor.Red;
                        Console.WriteLine("You can't move the other players piece");
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else //all good, now call the move function associated with the piece you're on
                        board[sourceY, sourceX].Move(board, sourceX, sourceY, destinationX, destinationY);
                }
            }
        }
        //Logic for letting the player choose the color
        public ConsoleColor getColor(string player)
        {
            switch (player.ToUpper())
            {
                case "DARK BLUE":
                    return ConsoleColor.DarkBlue;
                case "RED":
                    return ConsoleColor.Red;
                case "BLUE":
                    return ConsoleColor.Blue;
                case "YELLOW":
                    return ConsoleColor.Yellow;
                case "CYAN":
                    return ConsoleColor.Cyan;
                case "GRAY":
                    return ConsoleColor.Gray;
                case "WHITE":
                    return ConsoleColor.White;
            }
            return ConsoleColor.Black;
        }

        /**********************************************************************
         * CHecks whether or not the coordinates entered are valid, so the 
         * program doesn't crash
         * ********************************************************************/
        public bool isValid(string source)
        {
            if (!Char.IsLetter(source[0]) || Char.IsLetter(source[1]))
                return false;
            else if (Char.ToUpper(source[0]) > 72 || Char.ToUpper(source[0]) < 65)
                return false;
            else if (source[1] > 56 || source[1] < 49)
                return false;
            else
                return true;
        }
       
        public void DisplayOptions()
        {

        }
      
        public void DrawBoard(Piece[,] board, string p1Color, string p2Color)
        {
            Console.WriteLine("\n           A     B     C     D     E     F     G     H  ");
            Console.WriteLine("        +-----+-----+-----+-----+-----+-----+-----+-----+");
            for (int row = 0; row < 8; row++)
            {
                Console.Write("     {0}  :  ", row + 1);
                for (int col = 0; col < 8; col++)
                {
                    if (board[row, col] != null)
                        Console.ForegroundColor = (board[row, col].getPlayer() == 1 ? getColor(p1Color) :
                                                   board[row, col].getPlayer() == 3 ? ConsoleColor.Green : getColor(p2Color));

                    Console.Write((board[row, col] == null ? " " : 
                        (board[row, col].getIsAlive() ? board[row, col].getName() : " ")));
                    Console.ForegroundColor = ConsoleColor.White;
                    Console.Write("  :  ");
                }

                Console.WriteLine("\n        +-----+-----+-----+-----+-----+-----+-----+-----+");
            }
        }
    }
}
