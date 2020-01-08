package practice;

public class Knight extends Piece {

    public Knight(Player player)
    {
        super(player);
        name = "Knight";
        symbol = (player.getPlayer() == 1 ? 'n' : 'N');
        special = false;
    }
    @Override
    public void MovePiece(Button [][]board, int sx, int sy, int dx, int dy) {
        if ((dx - sx == 1 || sx - dx == 1) && dy - sy == 2 || dx - sx == 2 && (sy - dy == 1 || dy - sy == 1) ||
                (dx - sx == 1 || sx - dx == 1) && sy - dy == 2 || sx - dx == 2 && (sy - dy == 1 || dy - sy == 1))
        {
            Move(board, sx, sy, dx, dy);
        }
        else
            displayError();
    }
}
