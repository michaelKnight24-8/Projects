package practice;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener
{
    private boolean continueWithDigits = false;
    private boolean justUsedEquals = false;
    private boolean isPi = false;
    private JLabel textLabel;
    private JPanel jp;
    private Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bPi,
            bC, bD, bDiv, bMult, bAdd, bSub, bEq, bFact, bEx;
    private TextArea ta;
    private static char selected;
    private static String lhs, rhs, result;

    public Calculator()
    {
        super("Calculator");
        setSize(500,500);
        result = lhs = rhs = "";
        selected = ' ';
        textLabel = new JLabel("");

        textLabel.setPreferredSize(new Dimension(500, 250));
        textLabel.setForeground(Color.BLACK);
        textLabel.setFont(new Font("Courier", Font.BOLD, 10));

// sets the text to the upper left corner
        textLabel.setVerticalAlignment(SwingConstants.NORTH);

        textLabel.setBorder(new CompoundBorder( // sets two borders
                BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK), // outer border
                BorderFactory.createEmptyBorder(2, 2, 2, 2))); // inner invisi

        jp = new JPanel();
        jp.setLayout(null);
        textLabel.setBounds(50, 20, 230, 50);
        jp.add(textLabel);

        //add buttons for #'s 0 - 9
        b0 = new ButtonBuilder("0", 50, 80).build();
        b0.addActionListener(this);
        jp.add(b0);

        b1 = new ButtonBuilder("1", 110, 80).build();
        b1.addActionListener(this);
        jp.add(b1);

        b2 = new ButtonBuilder("2", 170, 80).build();
        b2.addActionListener(this);
        jp.add(b2);

        b3 = new ButtonBuilder("3", 50, 140).build();
        b3.addActionListener(this);
        jp.add(b3);

        b4 = new ButtonBuilder("4", 110, 140).build();
        b4.addActionListener(this);
        jp.add(b4);

        b5 = new ButtonBuilder("5", 170, 140).build();
        b5.addActionListener(this);
        jp.add(b5);

        b6 = new ButtonBuilder("6", 50, 200).build();
        b6.addActionListener(this);
        jp.add(b6);

        b7 = new ButtonBuilder("7", 110, 200).build();
        b7.addActionListener(this);
        jp.add(b7);

        b8 = new ButtonBuilder("8", 170, 200).build();
        b8.addActionListener(this);
        jp.add(b8);

        b9 = new ButtonBuilder("9", 50, 260).build();
        b9.addActionListener(this);
        jp.add(b9);

        bC = new ButtonBuilder("C", 110, 260).withBackGroundColor(Color.lightGray)
                                                        .withTextColor(Color.BLACK)
                                                        .build();
        bC.addActionListener(this);
        jp.add(bC);

        bD = new ButtonBuilder(".", 170, 260).withBackGroundColor(Color.lightGray)
                                                        .withTextColor(Color.BLACK)
                                                        .build();
        bD.addActionListener(this);
        jp.add(bD);

        bMult = new ButtonBuilder("x", 230, 80).withBackGroundColor(Color.orange)
                .build();
        bMult.addActionListener(this);
        jp.add(bMult);

        bDiv = new ButtonBuilder("/", 230, 140).withBackGroundColor(Color.orange)
                .build();
        bDiv.addActionListener(this);
        jp.add(bDiv);

        bAdd = new ButtonBuilder("+", 230, 200).withBackGroundColor(Color.orange)
                .build();
        bAdd.addActionListener(this);
        jp.add(bAdd);

        bSub = new ButtonBuilder("-", 230, 260).withBackGroundColor(Color.orange)
                .build();
        bSub.addActionListener(this);
        jp.add(bSub);

        bEq = new ButtonBuilder("=", 230, 320).withBackGroundColor(Color.orange)
                .build();
        bEq.addActionListener(this);
        jp.add(bEq);

        bPi = new ButtonBuilder("π", 50, 320).withBackGroundColor(Color.lightGray)
                .withTextColor(Color.BLACK)
                .build();
        bPi.addActionListener(this);
        jp.add(bPi);

        bFact = new ButtonBuilder("!", 110, 320).withBackGroundColor(Color.lightGray)
                .withTextColor(Color.BLACK)
                .build();
        bFact.addActionListener(this);
        jp.add(bFact);

        bEx = new ButtonBuilder("^", 170, 320).withBackGroundColor(Color.lightGray)
                .withTextColor(Color.BLACK)
                .build();
        bEx.addActionListener(this);
        jp.add(bEx);

        add(jp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        char op = e.getActionCommand().charAt(0);
        if (Character.isDigit(op) || op == '.' || op == 'π') {
            if (selected == ' ') {
                if (justUsedEquals) {
                    textLabel.setText("" + op);
                    justUsedEquals = false;
                }
                else if (op == 'π')
                    textLabel.setText("" + 3.141592653589);
                else
                    textLabel.setText(textLabel.getText() + op);
            }
            else
            {
                if (!continueWithDigits)
                    textLabel.setText("");
                if (op == 'π') {
                    isPi = true;
                    rhs = Double.toString(3.141592653589);
                }
                textLabel.setText(textLabel.getText() + op);
                continueWithDigits = true;
            }
        }
        if (!Character.isDigit(op) && op != '.' && op != 'π' || isPi)
        {
            continueWithDigits = false;
            //put everything pas tthis into another function that can be called!
            if (!isPi) {
                if (selected != ' ') {
                    rhs = textLabel.getText();
                } else {
                    lhs = textLabel.getText();
                }
                textLabel.setText("");
            }
            else
                textLabel.setText(rhs);

            //now carry out the corresponding operation
            switch (op)
            {
                case '+':
                    if (selected != ' ')
                    {
                        lhs = Double.toString(Double.parseDouble(lhs) + Double.parseDouble(rhs));
                        textLabel.setText(lhs);
                    }
                    else {
                         selected = '+';
                    }
                    break;
                case '-':
                    if (selected != ' ')
                    {
                        textLabel.setText(Double.toString(Double.parseDouble(lhs) - Double.parseDouble(rhs)));
                        lhs = Double.toString(Double.parseDouble(lhs) - Double.parseDouble(rhs));
                    }
                    else {
                        selected = '-';
                    }
                    break;
                case '/':
                    if (selected != ' ')
                    {
                        if (Double.parseDouble(rhs ) != 0) {
                            textLabel.setText(Double.toString(Double.parseDouble(lhs) / Double.parseDouble(rhs)));
                            lhs = Double.toString(Double.parseDouble(lhs) / Double.parseDouble(rhs));
                        }
                        else
                        {
                            textLabel.setText("ERROR: You attempted to divide by zero");
                            lhs = rhs = "";
                            selected = ' ';
                        }
                    }
                    else {
                        selected = '/';
                    }
                    break;
                case '!':
                    factorial(Double.parseDouble(lhs));
                    break;
                case '^':
                    textLabel.setText(Double.toString((Double.parseDouble(lhs) * Double.parseDouble(lhs))));
                    justUsedEquals = true;
                    break;
                case '=':
                    switch (selected) {
                        case '+':
                            textLabel.setText(Double.toString(Double.parseDouble(lhs) + Double.parseDouble(rhs)));
                            break;
                        case '-':
                            textLabel.setText(Double.toString(Double.parseDouble(lhs) - Double.parseDouble(rhs)));
                            break;
                        case '/':
                            if (Double.parseDouble(rhs ) != 0)
                                textLabel.setText(Double.toString(Double.parseDouble(lhs) / Double.parseDouble(rhs)));
                            else
                                textLabel.setText("ERROR: You attempted to divide by zero");
                            break;
                    }
                    lhs = rhs = "";
                    selected = ' ';
                    //to make it so that the result on the screen is erased once you start clicking buttons again
                    justUsedEquals = true;
                    break;
                case 'C':
                    textLabel.setText("");
                    lhs = rhs = "";
                    selected = ' ';
                    break;

            }
            //set isPi back to false since we're done
            if (isPi)
                isPi = false;
        }

    }

    //separate function so code looks better
    private void factorial(double lhs)
    {
        double result = 1;
        for (double i = lhs; i > 0; i--)
            result *= i;
        //just display the result right after
        textLabel.setText("" + result);
        justUsedEquals = true;
    }
}
