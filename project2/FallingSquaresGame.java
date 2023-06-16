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

public class FallingSquaresGame extends JFrame {

    private JPanel gamePanel;
    private JLabel scoreLabel;
    private List<Square> squares;
    private int totalSquares;
    private int clickedSquares;
    private double score;

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 450;
    private static final int SQUARE_SIZE = 40;
    private static final int SQUARE_FALLING_SPEED = 15;
    private static final int SQUARE_CREATION_INTERVAL = 1000; // FIXME
    private static final int GAME_DURATION_SECONDS = 30;
    private static final double REQUIRED_WINNING_SCORE_PERCENTAGE = 50.0;

    public FallingSquaresGame() {
        setTitle("Falling Squares Game");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Square square : squares) {
                    square.draw(g);
                }
            }
        };
        gamePanel.setBackground(Color.WHITE);
        gamePanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        getContentPane().add(gamePanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Current score: 0%");
        scoreLabel.setBorder(BorderFactory.createTitledBorder("Score"));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(scoreLabel, BorderLayout.SOUTH);

        squares = new ArrayList<>();
        score = 0.0;
        clickedSquares = 0;
        totalSquares = 0;

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        setVisible(true);

        playGame();
    }

    private void playGame() {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (GAME_DURATION_SECONDS * 1000);

        while (System.currentTimeMillis() < endTime) {
            createNewSquare();
            gamePanel.repaint();

            try {
                Thread.sleep(SQUARE_FALLING_SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        endGame();
    }

    private void createNewSquare() {
        int x = getRandomNumber(0, WINDOW_WIDTH - SQUARE_SIZE);
        Square newSquare = new Square(x, -SQUARE_SIZE);
        squares.add(newSquare);
        totalSquares++;
        updateScore();
    }

    private void endGame() {
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
            }
        }

        public boolean contains(int x, int y) {
            return x >= this.x && x <= this.x + SQUARE_SIZE &&
                    y >= this.y && y <= this.y + SQUARE_SIZE;
        }
    }

    private void handleClick(int x, int y) {
        Iterator<Square> iterator = squares.iterator();
        while (iterator.hasNext()) {
            Square square = iterator.next();
            if (square.contains(x, y)) {
                iterator.remove();
                clickedSquares++;
                updateScore();
                break;
            }
        }
    }

    private void updateScore() {
        score = ((double) clickedSquares / totalSquares) * 100.0;
        scoreLabel.setText("Current score: " + String.format("%.0f", score) + "%");
        gamePanel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(FallingSquaresGame::new);
    }
}
