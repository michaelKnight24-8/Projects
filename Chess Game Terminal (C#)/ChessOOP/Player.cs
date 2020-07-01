using System;
using System.Collections.Generic;

namespace ChessOOP
{
    public partial class Game
    {
        public class Player
        {
            //list of possible colors for a player
          
            public int PlayerNumber { get; set; }

            /************************************************************
             * TempPlayerNum is used to keep track of the player who is
             * using the piece, because if the piece is turned green
             * by the show possible moves function, it will need to
             * be reset back to the color it originally was
             * **********************************************************/
            public int TempPlayerNum;
            public Player(int number)
            {
                PlayerNumber = TempPlayerNum = number;
            }
            public ConsoleColor PlayerColor { get; set; }
        }
    }
}

