package game;	

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Client extends JPanel implements KeyListener, ActionListener {
    private static final int WIDTH = 810, HEIGHT = 650;
    private Ball ball;
    private Paddle leftPaddle, rightPaddle;
    private Timer timer;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean wPressed, sPressed;
    private int playerScore = 0, aiScore = 0;
    private boolean gameStarted = false;

    public Client(String serverAddress) {
        System.out.println("Initializing Client...");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        ball = new Ball(WIDTH / 2, HEIGHT / 2);
        leftPaddle = new Paddle(10, HEIGHT / 2 - 50);
        rightPaddle = new Paddle(WIDTH - 20, HEIGHT / 2 - 50);
        timer = new Timer(10, this);

        // Show the main menu first
        SwingUtilities.invokeLater(() -> showMenu(serverAddress));
    }

    private void showMenu(String serverAddress) {
        JFrame frame = new JFrame("Pong Client");
        List<Integer> topScores = new ArrayList<>(); // Placeholder for top scores

        MenuPanel menuPanel = new MenuPanel(frame, topScores);
        menuPanel.setStartGameListener(e -> {
            if (!gameStarted) {
                startGame(serverAddress, frame, topScores);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT); // Set size explicitly
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Ensure preferred size is set
        frame.add(menuPanel);
        frame.pack(); // Ensure proper packing
        frame.setVisible(true);
    }


    private void startGame(String serverAddress, JFrame frame, List<Integer> topScores) {
        try {
            socket = new Socket(serverAddress, 4000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server at " + serverAddress + ":4000");
            
            // Switch to the game panel
            GamePanel gamePanel = new GamePanel(frame, topScores);
            frame.getContentPane().removeAll();
            frame.add(gamePanel);
            frame.setSize(WIDTH, HEIGHT);
            frame.revalidate();
            frame.repaint();
            
            timer.start();
            gameStarted = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to server.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameStarted) {
            ball.draw(g);
            leftPaddle.draw(g);
            rightPaddle.draw(g);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString("Player Score: " + playerScore, WIDTH / 2 - 100, 30);
            g.drawString("AI Score: " + aiScore, WIDTH / 2 + 50, 30);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted) {
            try {
                if (wPressed) {
                    out.writeObject("W");
                } else if (sPressed) {
                    out.writeObject("S");
                } else {
                    out.writeObject("");
                }
                out.flush();

                String gameState = (String) in.readObject();
                updateGameState(gameState);

                repaint();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateGameState(String gameState) {
        String[] parts = gameState.split(",");
        ball.setX(Integer.parseInt(parts[0]));
        ball.setY(Integer.parseInt(parts[1]));
        leftPaddle.setY(Integer.parseInt(parts[2]));
        rightPaddle.setY(Integer.parseInt(parts[3]));
        playerScore = Integer.parseInt(parts[4]);
        aiScore = Integer.parseInt(parts[5]);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Client("localhost"));
    }
}
