/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IMOE001
 */
public class GameOfLife implements ActionListener {

    int k;
    int i;
    int BoardSize = 20;
    int j;

    JFrame frame = new JFrame();

    JPanel title_panel = new JPanel();
    JPanel start_panel = new JPanel();
    JPanel button_panel = new JPanel();

    JLabel textfield = new JLabel();
    JLabel startfield = new JLabel();

    JButton startButton = new JButton("One Generation");
    JButton loopButton = new JButton("Start");
    JButton resetButton = new JButton("reset");
    JButton[][] buttons = new JButton[BoardSize][BoardSize];

    int[][] gameArray = new int[BoardSize][BoardSize];
    int[][] gameArray2 = new int[BoardSize][BoardSize];

    boolean stop = true;
    boolean loop = false;

    public GameOfLife() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("MV Boli", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Game Of Life");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        startButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        startButton.addActionListener(this);

        loopButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        loopButton.addActionListener(this);

        resetButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        resetButton.addActionListener(this);

        button_panel.setLayout(new GridLayout(BoardSize, BoardSize));
        button_panel.setBackground(new Color(150, 150, 150));

        start_panel.setLayout(new GridLayout(1, 3));
        start_panel.setBackground(new Color(150, 150, 150));

        for (i = 0; i < BoardSize; i++) {
            for (j = 0; j < BoardSize; j++) {
                buttons[i][j] = new JButton();
                button_panel.add(buttons[i][j]);
                buttons[i][j].setFont(new Font("MV Boli", Font.BOLD, 120));
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(this);

                gameArray[i][j] = 0;
            }
        }

        title_panel.add(textfield);
        start_panel.add(startButton);
        start_panel.add(loopButton);
        start_panel.add(resetButton);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);
        frame.add(start_panel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (i = 0; i < BoardSize; i++) {
            for (j = 0; j < BoardSize; j++) {
                if (e.getSource() == buttons[i][j]) {
                    gameArray[i][j] = 1; //src
                    play();
                }
            }
        }
        if (startButton == e.getSource()) { // when pressing start the board wont update until a box is checked
            copy(gameArray); //copy first array to a second array
            stop = false;
            oneStep(gameArray2);
            play();
        }
        if (loopButton == e.getSource()) {
            k = 0;
            while (k <= 15) {
                loop();
                k++;
            }
        }

        if (resetButton == e.getSource()) {
            reset();
        }
    }

    public void copy(int a[][]) {
        for (i = 0; i < BoardSize; i++) {
            for (j = 0; j < BoardSize; j++) {
                gameArray2[i][j] = a[i][j]; //copy
            }
        }
    }

    public void display(int a[][]) {
        for (i = 0; i < BoardSize; i++) {
            for (j = 0; j < BoardSize; j++) {
                gameArray[i][j] = a[i][j];
            }
        }
    }

    public void play() {
        for (i = 0; i < BoardSize; i++) {
            for (j = 0; j < BoardSize; j++) {
                if (gameArray[i][j] == 1) {
                    buttons[i][j].setBackground(Color.yellow);
                } else {
                    buttons[i][j].setBackground(Color.GRAY);
                }
            }
        }
    }

    public void loop() {
        try {
            Thread.sleep(100);
            copy(gameArray); //copy first array to a second array
            stop = false;
            play();
            oneStep(gameArray2);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void reset() {
        for (i = 0; i < BoardSize; i++) {
            for (j = 0; j < BoardSize; j++) {
                gameArray[i][j] = 0;
            }
        }
        copy(gameArray);
        play();
    }

    public void oneStep(int b[][]) {
        if (stop == false) {
            int count;
            for (i = 0; i < BoardSize; i++) {
                for (j = 0; j < BoardSize; j++) {
                    count = 0;
                    if (gameArray[i][j] == 1) {
                        count = 0;
                        if (i > 0) {
                            if (gameArray[i - 1][j] == 1) {
                                count++;
                            }
                        }
                        if (i < BoardSize - 1) {
                            if (gameArray[i + 1][j] == 1) {
                                count++;
                            }
                        }
                        if (i > 0 && j > 0) {
                            if (gameArray[i - 1][j - 1] == 1) {
                                count++;
                            }
                        }
                        if (i < BoardSize - 1 && j < BoardSize - 1) {
                            if (gameArray[i + 1][j + 1] == 1) {
                                count++;
                            }
                        }
                        if (i > 0 && j < BoardSize - 1) {
                            if (gameArray[i - 1][j + 1] == 1) {
                                count++;
                            }
                        }
                        if (j > 0) {
                            if (gameArray[i][j - 1] == 1) {
                                count++;
                            }
                        }
                        if (j < BoardSize - 1) {
                            if (gameArray[i][j + 1] == 1) {
                                count++;
                            }
                        }
                        if (i < BoardSize - 1 && j > 0) {
                            if (gameArray[i + 1][j - 1] == 1) {
                                count++;
                            }
                        }
                    } else if (gameArray[i][j] == 0) {
                        if (i > 0) {
                            if (gameArray[i - 1][j] == 1) {
                                count++;
                            }
                        }
                        if (i < BoardSize - 1) {
                            if (gameArray[i + 1][j] == 1) {
                                count++;
                            }
                        }
                        if (i > 0 && j > 0) {
                            if (gameArray[i - 1][j - 1] == 1) {
                                count++;
                            }
                        }
                        if (i < BoardSize - 1 && j < BoardSize - 1) {
                            if (gameArray[i + 1][j + 1] == 1) {
                                count++;
                            }
                        }
                        if (i > 0 && j < BoardSize - 1) {
                            if (gameArray[i - 1][j + 1] == 1) {
                                count++;
                            }
                        }
                        if (j > 0) {
                            if (gameArray[i][j - 1] == 1) {
                                count++;
                            }
                        }
                        if (j < BoardSize - 1) {
                            if (gameArray[i][j + 1] == 1) {
                                count++;
                            }
                        }
                        if (i < BoardSize - 1 && j > 0) {
                            if (gameArray[i + 1][j - 1] == 1) {
                                count++;
                            }
                        }
                    }
                    if (count == 3) {
                        b[i][j] = 1; // revive
                    } else if (count > 3 || count < 2) {
                        b[i][j] = 0; // kill
                    }
                }
            }
            display(b);
            stop = true;
        }
    }
}
