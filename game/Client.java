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
    private JFrame frame;
    private List<Integer> topScores;

    public Client(String serverAddress) {
        this.topScores = new ArrayList<>();
        this.frame = new JFrame("Pong Client");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        showMenu(serverAddress);
    }

    private void showMenu(String serverAddress) {
        // Initialize the frame and menu panel
        MenuPanel menuPanel = new MenuPanel(frame, topScores);
        menuPanel.setStartGameListener(e -> startGame(serverAddress, frame, topScores));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.add(menuPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void startGame(String serverAddress, JFrame frame, List<Integer> topScores) {
        if (gameStarted) {
            // Clean up existing game resources if any
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            socket = new Socket(serverAddress, 4000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server at " + serverAddress + ":4000");

            // Initialize a new GamePanel
            GamePanel gamePanel = new GamePanel(frame, topScores);
            frame.getContentPane().removeAll();
            frame.add(gamePanel);
            frame.setSize(WIDTH, HEIGHT);
            frame.revalidate();
            frame.repaint();

            timer = new Timer(10, this);
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
