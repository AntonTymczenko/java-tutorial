import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FallingSquaresGame extends JFrame {

    private JPanel gamePanel;
    private JLabel scoreLabel;
    private int score;
    private int totalSquares;
    private int clickedSquares;
    private int requiredWinningScore;
    private Timer gameTimer;

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 450;
    private static final int SQUARE_SIZE = 40;
    private static final int SQUARE_FALLING_SPEED = 15;
    private static final int SQUARE_CREATION_INTERVAL = 1000; // milliseconds
    private static final int GAME_DURATION = 60000; // milliseconds
    private static final double REQUIRED_WINNING_SCORE_PERCENTAGE = 50.0;

    public FallingSquaresGame() {
        super("Falling Squares Game");
        setLayout(new BorderLayout());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                for (Square square : squares) {
                    square.draw(g);
                }
            }
        };

        scoreLabel = new JLabel("Current score: 0%");
        scoreLabel.setBorder(BorderFactory.createTitledBorder("Score"));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(gamePanel, BorderLayout.CENTER);
        add(scoreLabel, BorderLayout.SOUTH);

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        setVisible(true);

        startGame();
    }

    private List<Square> squares = new ArrayList<>();

    private void startGame() {
        score = 0;
        totalSquares = 0;
        clickedSquares = 0;
        requiredWinningScore = (int) (REQUIRED_WINNING_SCORE_PERCENTAGE / 100 * totalSquares);

        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                createSquare();
            }
        }, 0, SQUARE_CREATION_INTERVAL);

        Timer endTimer = new Timer();
        endTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                endGame();
            }
        }, GAME_DURATION);
    }

    private void createSquare() {
        int x = getRandomNumber(0, WINDOW_WIDTH - SQUARE_SIZE);
        Square square = new Square(x, 0);
        squares.add(square);
        totalSquares++;
        requiredWinningScore = (int) (REQUIRED_WINNING_SCORE_PERCENTAGE / 100 * totalSquares);
        score = (int) ((double) clickedSquares / totalSquares * 100);
        scoreLabel.setText("Current score: " + score + "%");
        gamePanel.repaint();
    }

    private void handleClick(int x, int y) {
        boolean squareClicked = false;
        for (Square square : squares) {
            if (square.contains(x, y)) {
                squareClicked = true;
                clickedSquares++;
                score = (int) ((double) clickedSquares / totalSquares * 100);
                scoreLabel.setText("Current score: " + score + "%");
                squares.remove(square);
                break;
            }
        }
        if (!squareClicked) {
            score = (int) ((double) clickedSquares / totalSquares * 100);
            scoreLabel.setText("Current score: " + score + "%");
        }
        gamePanel.repaint();
    }

    private void endGame() {
        gameTimer.cancel();
        double result = (double) clickedSquares / totalSquares * 100;
        String message;
        if (score >= requiredWinningScore) {
            message = "Congratulations! You won!\nYour result is " + result + "%";
        } else {
            message = "Game over! You lost!\nYour result is " + result + "%";
        }
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private class Square {
        private int x;
        private int y;

        public Square(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            y += SQUARE_FALLING_SPEED;
            if (y + SQUARE_SIZE >= WINDOW_HEIGHT) {
                squares.remove(this);
                score = (int) ((double) clickedSquares / totalSquares * 100);
                scoreLabel.setText("Current score: " + score + "%");
            }
        }

        public boolean contains(int x, int y) {
            return x >= this.x && x <= this.x + SQUARE_SIZE &&
                    y >= this.y && y <= this.y + SQUARE_SIZE;
        }
    }

    public static void main(String[] args) {
        new FallingSquaresGame();
    }
}
