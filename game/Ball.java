package game;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball {
    private static final int SIZE = 15;
    private int x, y;
    private int xSpeed, ySpeed;
    private Random ballDirection;
    
    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        this.xSpeed = 2;
        this.ySpeed = 2;
        this.ballDirection = new Random();
        setRandomDirection();
    }
    
    public void update() {
        x += xSpeed;
        y += ySpeed;
        
        // Collision with top and bottom walls
        if (y <= 0 || y >= GamePanel.HEIGHT - SIZE) {
            ySpeed = -ySpeed;
        }
    }
    
    public void reverseXDirection() {
        xSpeed = -xSpeed;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, SIZE, SIZE);
    }

    // Getters
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getSize() {
        return SIZE;
    }
    
    public int getXSpeed() {
        return xSpeed;
    }
    
    public int getYSpeed() {
        return ySpeed;
    }
    // Setters
	public void setX(int int1) {
		x = int1;	
	}

	public void setY(int int1) {
		y = int1;
		
	}
    // To make the ball spawn with a randomly set direction at the start of game or after each point
    public void setRandomDirection() {
        xSpeed = ballDirection.nextBoolean() ? 2 : -2;
        ySpeed = ballDirection.nextBoolean() ? 2 : -2;
    }
}
