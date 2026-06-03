package spaceinvaders.sprites.enemy;

import spaceinvaders.sprites.bullet.Bullet;
import spaceinvaders.sprites.bullet.OctopusBullet;

import java.awt.*;

public class Octopus extends Enemy {
    private final Image sprite1 = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("octopus/octopus0.png"));
    private final Image sprite2 = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("octopus/octopus1.png"));

    public Octopus(int startX, int startY) {
        super(startX, startY);
    }

    @Override
    protected Bullet createBullet(int startX, int startY) {
        return new OctopusBullet(startX, startY);
    }

    @Override
    public int getScore() {
        return 10;
    }

    @Override
    public void draw(Graphics g) {
        if(isAlive) {
            g.drawImage(cycle ? sprite2 : sprite1, x, y, width, height, null);
        }
    }
}
