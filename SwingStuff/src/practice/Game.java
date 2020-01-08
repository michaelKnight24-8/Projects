package practice;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.ByteOrder;
import java.util.Random;

public class Game implements MouseListener {
    public static int clicks = 1;
    public static final String F = "Last Clicked: ";
    public static int player = 1;
    public static boolean hasWon = false;
    public static JFrame jf = new JFrame("Chess");
    public static JPanel content = new JPanel();
    public static JPanel buttonsPanel = new JPanel();
    public static boolean firstTime = true;
    public static int coord[] = new int[2];
    public static TextArea ta = new TextArea(10,25);
    public static Button[][] board =
            {
                    { new Button(new Rook(new Player(1))), new Button(new Bishop(new Player(1))), new Button(new Knight(new Player(1))), new Button(new Queen(new Player(1))),
                            new Button(new King(new Player(1))), new Button(new Knight(new Player(1))), new Button(new Bishop(new Player(1))), new Button(new Rook(new Player(1)))},
                    { new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1))),
                            new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1)))},
                    { new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button()},
                    { new Button(), new Button(new Bishop(new Player(1))), new Button(), new Button(), new Button(), new Button(), new Button(), new Button()},
                    { new Button(), new Button(), new Button(), new Button(), new Button(new Bishop(new Player(2))), new Button(), new Button(), new Button()},
                    { new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button()},
                    { new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2))),
                            new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2)))},
                    { new Button(new Rook(new Player(2))), new Button(new Bishop(new Player(2))), new Button(new Knight(new Player(2))), new Button(new King(new Player(2))),
                            new Button(new Queen(new Player(2))), new Button(new Knight(new Player(2))), new Button(new Bishop(new Player(2))), new Button(new Rook(new Player(2)))}
            };

    public void startGame() {

        if (firstTime)
            init();
        drawBoard(board, buttonsPanel);
    }


    public void init() {

        coord[0] = coord[1] = 0;
        JLabel background;
        JLabel background1;
        ImageIcon img = new ImageIcon("C:\\Users\\mknig\\Downloads\\chessB.jpg");
        background = new JLabel("where is this",img,JLabel.CENTER);
        background.setBounds(0,0, 860, 1200);
        background1 = new JLabel("where is this",img,JLabel.CENTER);
        background1.setBounds(1130,0, 200, 1200);
        JLabel background2 = new JLabel("where is this",img,JLabel.CENTER);
        JLabel background3 = new JLabel("where is this",img,JLabel.CENTER);
        background2.setBounds(860,400, 400, 400);
        background3.setBounds(860,-70, 400, 200);
        //content.setLayout(null);
        ta.setBackground(Color.GRAY);
        //content.add(background1);
        //content.add(background);
        //content.add(background2);
        //content.add(background3);
        content.setBorder(new EmptyBorder(130, 400, 150, 150));
        jf.setSize(1100, 1010);
        firstTime = false;

        buttonsPanel.setBackground(Color.DARK_GRAY);

        buttonsPanel.setLayout(new GridLayout(8, 8, 3, 3));

        content.setLayout(new BorderLayout());

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                board[i][j].addMouseListener(this);
            }

        var ta2 = new TextArea(10,25);
        ta2.setBackground(Color.GRAY);
        content.add(buttonsPanel, BorderLayout.CENTER);
        jf.setContentPane(content);
        jf.add(ta, BorderLayout.EAST);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        content.setBackground(Color.BLACK);
    }

    public void drawBoard(Button[][] board, JPanel buttonsPanel) {
        buttonsPanel.removeAll();
        for (var i = 0; i < 8; i++)
            for (var j = 0; j < 8; j++) {
                buttonsPanel.add(board[i][j]);
                board[i][j].x = j;
                board[i][j].y = i;
            }
        buttonsPanel.repaint();
        buttonsPanel.revalidate();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        var button = (Button) e.getSource();
        int row = button.y;
        int col = button.x;

        if (clicks == 1) {

            if (board[row][col].getPlayer() == player) {
                if (board[row][col].isPiece()) {
                    coord[0] = row;
                    coord[1] = col;
                    clicks = 2;
                }
            }
            else
                ta.setText("You can't move the other player's piece\n" + F + "(" + coord[0] + "," + coord[1] + ")");
        } else if (clicks == 2) {
            board[coord[0]][coord[1]].getPiece().MovePiece(board, coord[1], coord[0], col, row);
        }

        drawBoard(board, buttonsPanel);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        var button = (Button) e.getSource();
        button.setBackground(Color.GREEN);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        var button = (Button) e.getSource();
        button.setBackground(Color.GRAY);
    }
}
