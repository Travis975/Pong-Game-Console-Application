package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

@SuppressWarnings("serial")
class GamePanel extends JPanel implements ActionListener, KeyListener {
	// Declare all variables and game pieces 
    public static final int WIDTH = 800, HEIGHT = 600;
    private Ball ball;
    private Paddle leftPaddle, rightPaddle;
    private Timer timer;
    private int playerScore = 0, aiScore = 0;
    private boolean wPressed, sPressed;
    private List<Integer> topScores;
    private JFrame frame;
    private boolean gameEnded = false;
    
    // Variables to introduce Random Number Generation (RNG) to AI paddle movement
    private Random randomNum = new Random();
    private int AIbufferReaction = 0;

    public GamePanel(JFrame frame, List<Integer> topScores) {
        this.frame = frame;
        this.topScores = topScores;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        ball = new Ball(WIDTH / 2, HEIGHT / 2);
        leftPaddle = new Paddle(10, HEIGHT / 2 - 50);
        rightPaddle = new Paddle(WIDTH - 20, HEIGHT / 2 - 50);

        timer = new Timer(10, this);
        timer.start();

        // Create "Quit Game" button
        JButton quitButton = new JButton("Quit Game");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitGame();
            }
        });

        // Add the button to the panel
        this.setLayout(null); // Use absolute positioning for the button
        quitButton.setBounds(10, 10, 120, 30); // Position and size the button
        this.add(quitButton);
        
        this.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ball.draw(g);
        leftPaddle.draw(g);
        rightPaddle.draw(g);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("Player Score: " + playerScore, WIDTH / 2 - 100, 30);
        g.drawString("AI Score: " + aiScore, WIDTH / 2 + 50, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameEnded) {
            ball.update();
            leftPaddle.update(wPressed, sPressed);
            updateAIPaddle();
            checkCollisions();
            repaint();
        }
    }

    private void updateAIPaddle() {
    	// Check the buffer for the AI movement
    	if(AIbufferReaction > 0) {
    		AIbufferReaction--;
    		return;
    	}
    	// Given range for the paddle to adjust to the ball
        int offset = randomNum.nextInt(30) - 15; // Random offset between -15 and 15
        int targetY = ball.getY() + offset;
        
        // Only move when the ball is moving towards the right paddle
        if (ball.getXSpeed() > 0) { 
            if (targetY > rightPaddle.getY() + rightPaddle.getHeight() / 2) {
                rightPaddle.update(false, true);
            } 
            else {
                rightPaddle.update(true, false);
            }
        }
        // The delay in the AI reaction by 2 - 3 frames
        AIbufferReaction = randomNum.nextInt(2) + 2;
    }

    private void checkCollisions() {
        // Ball collision with left paddle
        if (ball.getX() <= leftPaddle.getX() + leftPaddle.getWidth() 
        	&& ball.getY() + ball.getSize() >= leftPaddle.getY() 
        	&& ball.getY() <= leftPaddle.getY() + leftPaddle.getHeight()) {
        	
        	// Send the ball the opposite direction
            ball.reverseXDirection();
        }
        // Ball collision with right paddle
        if (ball.getX() + ball.getSize() >= rightPaddle.getX() 
        	&& ball.getY() + ball.getSize() >= rightPaddle.getY() 
        	&& ball.getY() <= rightPaddle.getY() + rightPaddle.getHeight()) {
        	
        	// Send the ball the opposite direction
            ball.reverseXDirection();
           
        }
        // Ball out of bounds
        if (ball.getX() <= 0) {
            aiScore++;
            resetBall();
        }
        if (ball.getX() >= WIDTH - ball.getSize()) {
            playerScore++;
            resetBall();
        }
    }

    private void resetBall() {
        ball = new Ball(WIDTH / 2, HEIGHT / 2);
    }

    private void endGame() {
        gameEnded = true;
        
        // Store the player's score, sort in desc order then keep the top 3 scores
        topScores.add(playerScore);
        topScores.sort((a, b) -> b - a); 
        if (topScores.size() > 3) {
            topScores.remove(topScores.size() - 1); 
        }

        // Switch to main menu
        SwingUtilities.invokeLater(() -> {
            MenuPanel menuPanel = new MenuPanel(frame, topScores);
            frame.getContentPane().removeAll();
            frame.add(menuPanel);
            frame.revalidate();
            frame.repaint();
        });
    }

    private void quitGame() {
    	System.out.println("Quitting Game!");
        // Stop the timer and switch to the main menu
        timer.stop();
        endGame();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            wPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            sPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            wPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            sPressed = false;
        }
    }
}
