package spaceinvaders.sprites.enemy;

import spaceinvaders.GamePanel;
import spaceinvaders.sprites.bullet.Bullet;
import spaceinvaders.sprites.bullet.SquidBullet;

import java.awt.*;

public class Squid extends Enemy {
    private final Image sprite1 = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("squid/squid0.png"));
    private final Image sprite2 = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("squid/squid1.png"));

    public Squid(int startX, int startY) {
        super(startX, startY);
    }

    @Override
    public void draw(Graphics g) {
        if(isAlive) {
            g.drawImage(cycle ? sprite2 : sprite1, x, y, width, height, null);
        }
    }

    @Override
    protected Bullet createBullet(int startX, int startY) {
        return new SquidBullet(startX, startY);
    }

    @Override
    public int getScore() {
        return 30;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(
                x + 2 * (GamePanel.SCREEN_WIDTH / 256), y,
                width - 4 * (GamePanel.SCREEN_HEIGHT / 224), height
        );
    }
}
