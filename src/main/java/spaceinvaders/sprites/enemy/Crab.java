package spaceinvaders.sprites.enemy;

import spaceinvaders.GamePanel;
import spaceinvaders.sprites.bullet.Bullet;
import spaceinvaders.sprites.bullet.CrabBullet;

import java.awt.*;

public class Crab extends Enemy {
    private final Image sprite1 = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("crab/crab0.png"));
    private final Image sprite2 = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("crab/crab1.png"));

    public Crab(int startX, int startY) {
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
        return new CrabBullet(startX, startY);
    }

    @Override
    public int getScore() {
        return 20;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(
                x + (GamePanel.SCREEN_WIDTH / 256), y, width - (GamePanel.SCREEN_HEIGHT / 224), height
        );
    }
}
