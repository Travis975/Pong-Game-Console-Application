package game;
import java.awt.Color;
import java.awt.Graphics;

public class Paddle {
    private static final int WIDTH = 10, HEIGHT = 100;
    private int x, y, ySpeed;
    
    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
        this.ySpeed = 5;
    }
    
    public void update(boolean up, boolean down) {
        if (up && y > 0) {
            y -= ySpeed;
        }
        if (down && y < GamePanel.HEIGHT - HEIGHT) {
            y += ySpeed;
        }
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
    // Getters
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return WIDTH;
    }
    
    public int getHeight() {
        return HEIGHT;
    }
    // Setters
	public void setY(int int1) {
		y = int1;
		
	}
}
