package game;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 4000;
    private static Ball ball;
    private static Paddle leftPaddle;
    private static Paddle rightPaddle;
    private static int playerScore = 0;
    private static int aiScore = 0;

    public static void main(String[] args) {
        System.out.println("Game server started...");
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Initialize game objects
            ball = new Ball(400, 300);
            leftPaddle = new Paddle(10, 250);
            rightPaddle = new Paddle(780, 250);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                while (true) {
                    String input = (String) in.readObject();
                    handleClientInput(input);
                    String gameState = getGameState();
                    out.writeObject(gameState);
                    out.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleClientInput(String input) {
            if (input.equals("W")) {
                leftPaddle.update(true, false);
            } else if (input.equals("S")) {
                leftPaddle.update(false, true);
            }
            ball.update();
            checkCollisions();
        }

        private void checkCollisions() {
            // Ball collision with paddles
            if (ball.getX() <= leftPaddle.getX() + leftPaddle.getWidth() &&
                ball.getY() >= leftPaddle.getY() &&
                ball.getY() <= leftPaddle.getY() + leftPaddle.getHeight()) {
                ball.reverseXDirection();
            }

            if (ball.getX() >= rightPaddle.getX() - rightPaddle.getWidth() &&
                ball.getY() >= rightPaddle.getY() &&
                ball.getY() <= rightPaddle.getY() + rightPaddle.getHeight()) {
                ball.reverseXDirection();
            }

            // Ball collision with walls
            if (ball.getX() <= 0) {
                aiScore++;
                resetBall();
            } else if (ball.getX() >= 800 - ball.getSize()) {
                playerScore++;
                resetBall();
            }
        }

        private void resetBall() {
            ball.setX(400);
            ball.setY(300);
        }

        private String getGameState() {
            return ball.getX() + "," + ball.getY() + "," +
                   leftPaddle.getY() + "," + rightPaddle.getY() + "," +
                   playerScore + "," + aiScore;
        }
    }
}
