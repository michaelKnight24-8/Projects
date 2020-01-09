package practice;

public class King extends Piece {

        public King(Player player)
        {
            super(player);
            name = "King";
            symbol = (player.getPlayer() == 1 ? 'k' : 'K');
            special = false;
        }

    @Override
    public void showPossible(Button [][] board, int sx, int sy) {}
        @Override
        public void MovePiece(Button [][]board, int sx, int sy, int dx, int dy){
            if ((dy == sy && (dx - sx > 1 || sx - dx > 1)) || (dx == sx && (sy - dy > 1 || dy - sy > 1)) ||
                    (sx - dx == sy - dy && sx - dx > 1) || (sx - dx == dy - sy && dy - sy > 1) ||
                    (dx - sx == sy - dy && dx - sx > 1) || (dx - sx == dy - sy && dy - sy > 1) || dy - sy > 1 || sy - dy > 1
                    || dx - sx > 1 || sx - dx > 1)
                displayError();
            else
            {
                Move(board, sx, sy, dx, dy);
            }
        }

}
