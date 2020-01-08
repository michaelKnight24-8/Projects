package practice;

public class Rook extends Bishop {

    public Rook(Player player)
    {
        super(player);
        name = "Rook";
        symbol = (player.getPlayer() == 1 ? 'r' : 'R');
        special = false;
    }

    public void MoveHorizontal(Button [][] board, int sy, int sx, int dx, int dy)
    {
        //for if you want to move right
        if (dx > sx)
        {
            for (int row = sx + 1; row <= dx; row++)
            {
                if (board[dy][row].isPiece())
                {
                    if (board[dy][row].getPlayer() == Game.player)
                    {
                        Move(board, sx, sy, row - 1, dy);
                        return;
                    }
                            else
                    {
                        Move(board, sx, sy, row, dy);
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
                if (board[dy][row].isPiece())
                {
                    if (board[dy][row].getPlayer() == Game.player)
                    {
                        Move(board, sx, sy, row + 1, dy);
                        return;
                    }
                    else
                    {
                        Move(board, sx, sy, row, dy);
                        return;
                    }
                }
            }
        }
        //we moved to a legal spot unoccupied by your own piece
        Move(board, sx, sy, dx, dy);
    }
    public void MoveVertical(Button [][] board, int sx, int sy, int dx, int dy)
    {
        if (sy > dy)
        {
            for (int col = sy - 1; col >= dy; col--)
            {
                if (board[col][sx].isPiece())
                {
                    if (board[col][sx].getPlayer() == Game.player)
                    {
                        Move(board, sx, sy, dx, col + 1);
                        return;
                    }
                            else
                    {
                        Move(board, sx, sy, dx, col);
                        return;
                    }
                }
            }
            Move(board, sx, sy, dx, dy);
        }
        else
        {
            for (int col = sy + 1; col <= dy; col++)
            {
                if (board[col][dx].isPiece())
                {
                    if (board[col][dx].getPlayer() == Game.player)
                    {
                        Move(board, sx, sy, dx, col - 1);
                        return;
                    }
                            else
                    {
                        Move(board, sx, sy, dx, col);
                        return;
                    }
                }
            }
            Move(board, sx, sy, dx, dy);
        }
    }
    @Override
    public void MovePiece(Button [][]board, int sx, int sy, int dx, int dy) {
        if (sy == dy)
            MoveHorizontal(board, sy, sx, dx, dy);
        else if (sx == dx)
            MoveVertical(board, sx, sy, dx, dy);
        else
            displayError();
    }
}
