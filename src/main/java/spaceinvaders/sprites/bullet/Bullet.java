package spaceinvaders.sprites.bullet;

import spaceinvaders.GamePanel;

import java.awt.*;

public class Bullet {
    private final int speed = GamePanel.SCREEN_HEIGHT / GamePanel.TPS;
    private int direction = 1;

    protected int x;
    protected int y;
    protected final int width = (GamePanel.SCREEN_WIDTH / 256);
    protected final int height = 2 * (GamePanel.SCREEN_HEIGHT / 224);
    protected int cycle = 0;

    public boolean isHostile;

    public Bullet(int startX, int startY) {
        x = startX;
        y = startY;
        isHostile = false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public boolean isOffScreen() {
        return y < 0 || y > GamePanel.SCREEN_HEIGHT;
    }

    public void tick() {
        y -= speed * direction;
        cycle++;
        if(cycle >= GamePanel.TPS) cycle = 0;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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
