using System;
using System.Collections.Generic;
using System.Text;

namespace ChessTerminal
{
    public partial class Game
    {
        class PossibleSpaces : Piece
        {
            public PossibleSpaces(Player p) : base(p) { name = "o"; }
            public override void Move(Piece[,] board, int sx, int sy, int dx, int dy)
            {
               
            }

            public override void showPossible(Piece[,] board, int sx, int sy)
            {
                
            }
        }
    }
}
