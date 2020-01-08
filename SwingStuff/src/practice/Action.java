package practice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static practice.Game.*;

public class Action implements ActionListener {
    Game game;

    public Button[][] board;
    public int row;
    public int col;

    public Action(Button[][] board, int col, int row) {
        this.board = board;
        this.row = row;
        this.col = col;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    game = new Game();
        if (e.getSource() == board[row][col]) {
            //if (board[row][col].isPiece())
                // ta.append(board[row][col].getPieceName() + "\n");
                if (game.clicks == 1) {
                    // game.ta.append(Integer.toString(clicks));
                    if (this.board[row][col].isPiece()) {
                        ta.append(Integer.toString(row)/*board[row][col].getPiece().y)*/ + "," + Integer.toString(col)/*board[row][col].getPiece().x)*/ + "\n");
                        game.coord[0] = row;
                        game.coord[1] = col;
                        game.clicks = 2;
                    }
                } else if (game.clicks == 2) {
                    game.ta.append("( " + row + " , " + col + " )");

                    //game.ta.append("Source Piece: " + Integer.toString(board[game.coord[0]][game.coord[1]].getPiece().y) + "," + Integer.toString(board[game.coord[0]][game.coord[1]].getPiece().x) + "\n");
                    //ta.append(Integer.toString(board[row][col].getPiece().y) + "  " + Integer.toString(board[row][col].getPiece().x) + "\n");
                   // board[game.coord[0]][game.coord[1]].movePiece(board, game.coord[1], game.coord[0], col, row);
                    game.ta.append("got here!");
                    game.clicks = 1;
                }

        }
        game.drawBoard(board, buttonsPanel);
        game.startGame();

    }
}





