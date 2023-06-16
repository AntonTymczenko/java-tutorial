import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FallingSquaresGame extends JFrame {

    private JPanel gamePanel;
    private JLabel scoreLabel;
    private ArrayList<Square> squares;
    private int totalSquares;
    private int clickedSquares;
    private double score;
    private Timer gameTimer;

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 450;
    private static final int GAME_DURATION_SECONDS = 30;
    private static final int SQUARE_SIZE = 40;
    private static final int SQUARE_FALLING_SPEED_PX = 4;
    private static final int SQUARE_CREATION_INTERVAL_FRAMES = 11;
    private static final int FPS = 60;
    private static final double REQUIRED_WINNING_SCORE_PERCENTAGE = 50.0;

    public FallingSquaresGame () {
        setTitle("Falling Squares Game");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                for (Square square: squares) {
                    square.draw(g);
                }
            }
        };
        gamePanel.setBackground(Color.WHITE);
        gamePanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        getContentPane().add(gamePanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Current score: 100%");
        scoreLabel.setBorder(BorderFactory.createTitledBorder("Score"));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(gamePanel, BorderLayout.CENTER);
        add(scoreLabel, BorderLayout.SOUTH);

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        setVisible(true);

        playGame();
    }

    private void playGame () {
        squares = new ArrayList<Square>();
        score = 100.0;
        clickedSquares = 0;
        totalSquares = 0;

        gameTimer = new Timer();

        gameTimer.schedule(new TimerTask() {
            @Override
            public void run () {
                int r = getRandomNumber(1, SQUARE_CREATION_INTERVAL_FRAMES);
                if (r % SQUARE_CREATION_INTERVAL_FRAMES == 0) {
                    createNewSquare();
                }

                updateSquarePositions();
                gamePanel.repaint();
            }
        }, 0, (long)(1000 / FPS));

        Timer endTimer = new Timer();
        endTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                endGame();
            }
        }, GAME_DURATION_SECONDS * 1000);
    }

    private void createNewSquare() {
        int x = getRandomNumber(0, WINDOW_WIDTH - SQUARE_SIZE);
        Square newSquare = new Square(x, -SQUARE_SIZE);
        squares.add(newSquare);
        totalSquares++;
    }

    private void updateSquarePositions () {
        List<Square> squaresToRemove = new ArrayList<>();

        for (Square square : squares) {
            square.update();
            if (square.getY() + SQUARE_SIZE >= WINDOW_HEIGHT) {
                squaresToRemove.add(square);
            }
        }

        if (squaresToRemove.size() > 0) {
            squares.removeAll(squaresToRemove);
            updateScore();
        }
    }

    private void endGame () {
        gameTimer.cancel();

        String message = "Your result is " + String.format("%.0f", score) + "%\n"
          + "Missed squares count: " + (totalSquares - squares.size() - clickedSquares);

        if (score >= REQUIRED_WINNING_SCORE_PERCENTAGE) {
            message = "Congratulations! You won!\n" + message;
        } else {
            message = "Game over! You lost!\n" + message;
        }

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> {
            dispose();
            new FallingSquaresGame();
        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exitGame());

        JOptionPane.showOptionDialog(this, message, "Game Over", JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE, null, new Object[]{exitButton, playAgainButton}, null);
    }

    private void exitGame () {
        gameTimer.purge();
        System.exit(0);
    }

    private int getRandomNumber (int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private class Square {
        private int x;
        private int y;

        public Square (int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw (Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
        }

        public void update () {
            y += SQUARE_FALLING_SPEED_PX;
        }

        public int getY () {
            return y;
        }

        public boolean contains (int x, int y) {
            return x >= this.x && x <= this.x + SQUARE_SIZE &&
                    y >= this.y && y <= this.y + SQUARE_SIZE;
        }
    }

    private void handleClick (int x, int y) {
        Iterator<Square> iterator = squares.iterator();
        while (iterator.hasNext()) {
            Square square = iterator.next();
            if (square.contains(x, y)) {
                iterator.remove();
                clickedSquares++;
                updateScore();
                return;
            }
        }
    }

    private void updateScore () {
        score = ((double)(clickedSquares + squares.size()) / totalSquares) * 100.0;
        String msg = "Current score: " + String.format("%.0f", score) + "%";
        scoreLabel.setText(msg);
        gamePanel.repaint();
    }


    public static void main (String[] args) {
        SwingUtilities.invokeLater(FallingSquaresGame::new);
    }
}
