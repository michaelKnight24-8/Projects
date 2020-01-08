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
    public void MovePiece(Button [][]board, int sx, int sy, int dx, int dy) {

    }
}
