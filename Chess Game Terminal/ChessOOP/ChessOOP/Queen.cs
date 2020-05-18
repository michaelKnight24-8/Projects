using System;

namespace ChessOOP
{
    public partial class Game
    {
        /*************************************************************************
         * This will inherit from the rook class, which inherits from the bishop 
         * class, which inherits from the piece class, which is the super class 
         * for all the pieces. With this approach, queen can use the code written
         * in rook, and bishop, because the queen can move the same as the bishop
         * and the rook
         * ***********************************************************************/
        public class Queen : Rook 
        {
            public Queen(Player player) : base(player) { Name = "Q"; PieceType = "Queen"; }
            override
            public void showPossible(Piece[,] board, int sx, int sy)
            {
                //reuse the code already written for rook and bishop class
                showPossibleRook(board, sx, sy);
                showPossibleBishop(board, sx, sy);
            }
            
            override
            public void Move(Piece[,] board, int sx, int sy, int dx, int dy)
            {
                //REUSE THE CODE ALREADY WRITTEN FOR THE ROOK AND BISHOP
                //for ability to move diagonally
                if ((dx > sx && (dy < sy || dy > sy)) && (dy - sy == dx - sx || sy - dy == dx - sx))
                    MoveRightDiagonal(board, sx, sy, dx, dy);
                else if ((sx > dx && (sy > dy || sy < dy)) && (sx - dx == dy - sy || sx - dx == sy - dy))
                    MoveLeftDiagonal(board, sx, sy, dx, dy);
                //for ability to move horizontally and vertically
                else if (sy == dy)
                    MoveHorizontal(board, sy, sx, dx, dy);
                else if (sx == dx)
                    MoveVertical(board, sx, sy, dx, dy);
                else
                    DisplayError();
            }
        }
    }
}

