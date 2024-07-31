package game;
import java.awt.Color;
import java.awt.Graphics;

public class Ball {
    private static final int SIZE = 15;
    private int x, y;
    private int xSpeed, ySpeed;
    
    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        this.xSpeed = 2;
        this.ySpeed = 2;
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
}
