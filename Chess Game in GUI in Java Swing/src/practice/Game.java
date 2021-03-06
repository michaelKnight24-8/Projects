package practice;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.ByteOrder;
import java.sql.Array;
import java.util.*;
import java.util.List;

import static javax.swing.BorderFactory.createTitledBorder;

public class Game implements MouseListener {
    public static List<Button> captureListP1;
    public static List<Button> captureListP2;
    public static int clicks = 1;
    public static final String F = "Last Clicked: ";
    public static int player = 1;
    public JLabel titleLabel;
    public JLabel rightPiecesPic;
    public JLabel leftPiecesPic;
    public static boolean hasWon = false;
    public static JFrame jf = new JFrame("Chess");
    public static JPanel content = new JPanel();
    public static JPanel buttonsPanel = new JPanel();
    public static boolean firstTime = true;
    public static int coord[] = new int[2];
    public static JTextArea ta = new JTextArea(5,25);
    public static Button[][] board =
            {
                    { new Button(new Rook(new Player(1))), new Button(new Bishop(new Player(1))), new Button(new Knight(new Player(1))), new Button(new Queen(new Player(1))),
                            new Button(new King(new Player(1))), new Button(new Knight(new Player(1))), new Button(new Bishop(new Player(1))), new Button(new Rook(new Player(1)))},
                    { new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1))),
                            new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1))), new Button(new Pawn(new Player(1)))},
                    { new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button()},
                    { new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button()},
                    { new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button()},
                    { new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button()},
                    { new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2))),
                            new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2))), new Button(new Pawn(new Player(2)))},
                    { new Button(new Rook(new Player(2))), new Button(new Bishop(new Player(2))), new Button(new Knight(new Player(2))), new Button(new King(new Player(2))),
                            new Button(new Queen(new Player(2))), new Button(new Knight(new Player(2))), new Button(new Bishop(new Player(2))), new Button(new Rook(new Player(2)))}
            };

    public void startGame() {
        captureListP1 = new LinkedList<>();
        captureListP2 = new LinkedList<>();
        titleLabel = new JLabel("Chess");
        rightPiecesPic = new JLabel();
        leftPiecesPic = new JLabel();
        if (firstTime)
            init();
        drawBoard(board, buttonsPanel);
    }


    public void init() {

        coord[0] = coord[1] = 0;
        content.setLayout(null);
        content.setBorder(new EmptyBorder(130, 300, 150, 150));
        jf.setSize(1100, 1010);
        firstTime = false;

        buttonsPanel.setBackground(Color.getHSBColor(210,131,51));
        ta.setBackground(Color.BLACK);
        ta.setForeground(Color.GREEN);
        ta.setBorder(BorderFactory.createTitledBorder("Feed"));
        TitledBorder titledBorder = (TitledBorder)ta.getBorder();
        titledBorder.setTitleColor(Color.green);
        buttonsPanel.setLayout(new GridLayout(8, 8, 3, 3));
        var l = new JLabel("   SCOREBOARD");
        l.setForeground(Color.BLACK);
        l.setFont(new Font("Courier New", Font.BOLD, 20));
        //ta.setBounds(100,360,100,100);
        var errorPane = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        var feed = new JLabel("  FEED");
        feed.setForeground(Color.BLACK);

        feed.setFont(new Font("Courier New", Font.BOLD, 20));
        errorPane.setBounds(935, 180, 150, 50);
        errorPane.setBorder(BorderFactory.createSoftBevelBorder(2,Color.WHITE,Color.BLACK));
        feed.setBounds(963, 130, 150, 60);


        content.setLayout(new BorderLayout());
  //      jf.add(label);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                board[i][j].addMouseListener(this);
            }

        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 60));
        titleLabel.setBounds(520, 80, 400, 50);
        rightPiecesPic.setBounds(710, 80, 400, 50);
        leftPiecesPic.setBounds(420, 80, 400, 50);
        leftPiecesPic.setIcon(new ImageIcon("C:\\Users\\mknig\\Documents\\titleWhite.jpg"));
        rightPiecesPic.setIcon(new ImageIcon("C:\\Users\\mknig\\Documents\\titleBlack.jpg"));
        var ta2 = new TextArea();
        var ta3 = new TextArea();
        ta2.setText("\t          P L A Y E R     1");
        ta3.setText("\t          P L A Y E R     2");

        ta2.setBackground(Color.getHSBColor(210,131,51));
        ta3.setBackground(Color.getHSBColor(210,131,51));
        //ta2.setBounds(200, 133, 150, 153);
        JScrollPane scorePanePlayer1 =  new JScrollPane(ta2, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                                      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane scorePanePlayer2 =  new JScrollPane(ta3, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //label.setIcon(image);
        //label1.setIcon(image1);
        scorePanePlayer1.setBounds(50, 180, 250, 20);
        scorePanePlayer2.setBounds(50, 400, 250, 20);
        l.setBounds(80, 130, 160, 50);
        //add everything to the JPanel
        content.add(errorPane);
        content.add(l);
        content.add(feed);
        content.add(titleLabel);
        content.add(leftPiecesPic);
        content.add(rightPiecesPic);
        content.add(scorePanePlayer2);
        content.add(scorePanePlayer1);
        content.add(buttonsPanel, BorderLayout.CENTER);
        jf.setContentPane(content);
        //jf.add(ta, BorderLayout.EAST);
        //jf.add(ta2, BorderLayout.EAST);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        content.setBackground(Color.GRAY);
    }

    public void drawBoard(Button[][] board, JPanel buttonsPanel) {

        buttonsPanel.removeAll();
        int alternate = 1;
        for (var i = 0; i < 8; i++) {
            for (var j = 0; j < 8; j++) {
                buttonsPanel.add(board[i][j]);
                board[i][j].x = j;
                board[i][j].y = i;
                if (board[i][j].getBackground() != Color.GREEN) {
                    if ((j + alternate) % 2 == 0) {
                        board[i][j].setBackground(new Color(229, 187, 129));
                        board[i][j].backgroundColor = new Color(229, 187, 129);
                    } else {
                        board[i][j].setBackground(new Color(108, 71, 58));
                        board[i][j].backgroundColor = new Color(108, 71, 58);
                    }

                }
            }
            alternate = (alternate == 1 ? 0 : 1);
        }
        buttonsPanel.repaint();
        buttonsPanel.revalidate();

        int p1X = 50;
        int p1Y = 200;
        //for player 1's captured pieces
        for (int i = 0; i < captureListP1.size(); i++) {
            var label = new JLabel();
            label.setIcon(captureListP1.get(i).getImage());
            label.setBounds(p1X, p1Y, 38, 50);
            content.add(label);
            p1X += 38;
            if (i % 5 == 0 && i != 0)
            {
                p1X = 50;
                p1Y += 40;
            }
        }
        int p2X = 50;
        int p2Y = 420;
        //for player 2's captured pieces
        for (int i = 0; i < captureListP2.size(); i++) {
            var label = new JLabel();
            label.setIcon(captureListP2.get(i).getImage());
            label.setBounds(p2X, p2Y, 38, 50);
            content.add(label);
            p2X += 38;
            if (i % 5 == 0 && i != 0)
            {
                p2X = 50;
                p2Y += 40;
            }
        }
        content.repaint();
        content.revalidate();
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
                    board[row][col].getPiece().showPossible(board, coord[1], coord[0]);
                }
            }
            else
                ta.setText("You can't move the other player's piece\n" + F + "(" + coord[0] + "," + coord[1] + ")");
        } else if (clicks == 2) {
            if (board[row][col] == board[coord[0]][coord[1]])
                clicks = 1;
            else
                board[coord[0]][coord[1]].getPiece().MovePiece(board, coord[1], coord[0], col, row);
                //resets the background colors to gray instead of green for the show possible
            for (var buttonRow : board)
                for (var buttonCol : buttonRow) {
                    if (!buttonCol.isPiece())
                        buttonCol.setIcon(null);
                    else
                        buttonCol.setIcon(buttonCol.getImage());

                    buttonCol.setBackground(buttonCol.backgroundColor);
                }
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
        if (button.getBackground() != Color.GREEN)
            button.setBackground(Color.getHSBColor(23,45,67));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        var button = (Button) e.getSource();
        if (button.getBackground() != Color.GREEN)
        {
            button.setBackground(button.backgroundColor);
        }

    }
}
