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
    private double score;
    private int totalSquares;
    private int clickedSquares;
    private Timer gameTimer;

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 450;
    private static final int SQUARE_SIZE = 40;
    private static final int SQUARE_FALLING_SPEED = 15;
    private static final int SQUARE_CREATION_INTERVAL = 1000; // milliseconds
    private static final int GAME_DURATION = 30000; // milliseconds
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

        setVisible(true);

        startGame();
    }

    private List<Square> squares = new ArrayList<>();

    private void startGame() {
        score = 0.0;
        totalSquares = 0;
        clickedSquares = 0;

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
        score = ((double) clickedSquares / totalSquares) * 100.0;
        scoreLabel.setText("Current score: " + String.format("%.0f", score) + "%");
        gamePanel.repaint();
    }

    private void endGame() {
        gameTimer.cancel();
        String message;
        if (score >= REQUIRED_WINNING_SCORE_PERCENTAGE) {
            message = "Congratulations! You won!\nYour result is " + String.format("%.0f", score) + "%";
        } else {
            message = "Game over! You lost!\nYour result is " + String.format("%.0f", score) + "%";
        }

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> System.exit(0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);

        JOptionPane.showOptionDialog(this, message, "Game Over", JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE, null, new Object[]{buttonPanel}, null);
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private class Square {
        private int x;
        private int y;
        private MouseAdapter mouseListener;

        public Square(int x, int y) {
            this.x = x;
            this.y = y;
            this.mouseListener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (contains(e.getX(), e.getY())) {
                        handleClick();
                    }
                }
            };
            gamePanel.addMouseListener(mouseListener);
        }

        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            y += SQUARE_FALLING_SPEED;
            if (y + SQUARE_SIZE >= WINDOW_HEIGHT) {
                squares.remove(this);
                removeMouseListener();
                updateScore();
            }
        }

        public boolean contains(int x, int y) {
            return x >= this.x && x <= this.x + SQUARE_SIZE &&
                    y >= this.y && y <= this.y + SQUARE_SIZE;
        }

        public void handleClick() {
            List<Square> intersectingSquares = new ArrayList<>();
            for (Square square : squares) {
                if (square.contains(x + SQUARE_SIZE / 2, y + SQUARE_SIZE / 2)) {
                    intersectingSquares.add(square);
                }
            }
            if (!intersectingSquares.isEmpty()) {
                Square newestSquare = intersectingSquares.get(intersectingSquares.size() - 1);
                newestSquare.removeMouseListener();
                squares.remove(newestSquare);
                clickedSquares++;
                updateScore();
            }
        }



        private void updateScore() {
            score = ((double) clickedSquares / totalSquares) * 100.0;
            scoreLabel.setText("Current score: " + String.format("%.1f", score) + "%");
            gamePanel.repaint();
        }

        private void removeMouseListener() {
            gamePanel.removeMouseListener(mouseListener);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(FallingSquaresGame::new);
    }
}
