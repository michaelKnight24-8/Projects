package practice;

public class Queen extends Rook{

    public Queen(Player player)
    {
        super(player);
        name = "Queen";
        symbol = (player.getPlayer() == 1 ? 'q' : 'Q');
        special = false;
    }
    @Override
    public void showPossible(Button [][] board, int sx, int sy)
    {
        showPossibleRook(board, sx, sy);
        showPossibleBishop(board, sx, sy);
    }
    @Override
    public void MovePiece(Button [][]board, int sx, int sy, int dx, int dy) {
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
            displayError();

    }
}
