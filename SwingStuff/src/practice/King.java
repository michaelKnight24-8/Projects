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
        public void MovePiece(Button [][]board, int sx, int sy, int dx, int dy){
            Game.ta.append("King moved");
        }

}
