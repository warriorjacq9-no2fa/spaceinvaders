package spaceinvaders.sprites.enemy;

import spaceinvaders.GamePanel;
import spaceinvaders.SpaceInvaders;
import spaceinvaders.sprites.bullet.Bullet;

import java.awt.*;

public abstract class Enemy {
    private static final int MARGIN = 16 * (GamePanel.SCREEN_WIDTH / 256);
    //private int sCounter = 0;
    private int xDelta = 2 * (GamePanel.SCREEN_WIDTH / 256);
    private final int yDelta = 8 * (GamePanel.SCREEN_HEIGHT / 224);
    private int xCounter = 0;
    private int fireTimeout = (int)(Math.random() * (16 * GamePanel.TPS));

    protected static final int width = 12 * (GamePanel.SCREEN_WIDTH / 256);
    protected static final int height = 8 * (GamePanel.SCREEN_HEIGHT / 224);

    protected int x;
    protected int y;

    protected boolean isAlive = true;
    protected boolean cycle = false;

    public Enemy(int startX, int startY) {
        x = startX;
        y = startY;
    }

    public void draw(Graphics g) {
        if(isAlive) {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    // Returns true if the group needs to reverse
    public boolean tick() {
        xCounter++;
        if(xCounter > GamePanel.TPS / 2) {
            x += xDelta;
            /*if(sCounter > 4) {
                sCounter = 0;
                if(xDelta > 0) xDelta += (GamePanel.SCREEN_WIDTH / 256);
                else xDelta -= (GamePanel.SCREEN_WIDTH / 256);
            }*/
            cycle = !cycle;
            xCounter = 0;
            //sCounter++;
        }

        if(isAlive) fireTimeout--;
        if(fireTimeout <= 0) {
            Bullet b = createBullet(x + width / 2, y);
            b.setDirection(-1);
            SpaceInvaders.panel.addBullet(b);
            fireTimeout = (int)(Math.random() * (16 * GamePanel.TPS)) + 4 * GamePanel.TPS;
        }
        if(isAlive)
            return x < MARGIN || x + width > GamePanel.SCREEN_WIDTH - MARGIN;
        else return false;
    }

    public void reverse() {
        y += yDelta;
        xDelta *= -1;
        x += xDelta;
    }

    protected abstract Bullet createBullet(int startX, int startY);

    public abstract int getScore();

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean getAlive() {
        return isAlive;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
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
