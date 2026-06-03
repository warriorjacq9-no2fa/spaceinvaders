package spaceinvaders;

import java.awt.*;

public class Player {
    private int x;
    private final int y;
    public static final int width = GamePanel.SCREEN_WIDTH / 32;
    public static final int height = GamePanel.SCREEN_HEIGHT / 28;
    private final int speed = GamePanel.SCREEN_WIDTH / 128;

    public Player(int startX, int startY) {
        x = startX;
        y = startY;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public void moveLeft() {
        if(x > 0) x -= speed;
    }

    public void moveRight() {
        if(x + width < GamePanel.SCREEN_WIDTH) x += speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
