package spaceinvaders.sprites.bullet;

import spaceinvaders.GamePanel;

import java.awt.*;

public class OctopusBullet extends Bullet {
    protected final int width = 3 * (GamePanel.SCREEN_WIDTH / 256);
    protected final int height = 7 * (GamePanel.SCREEN_HEIGHT / 224);

    private final Image sprite1 = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("octopus/octopus_bullet0.png"));
    private final Image sprite2 = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("octopus/octopus_bullet1.png"));
    private final Image sprite3 = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("octopus/octopus_bullet2.png"));
    private final Image sprite4 = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("octopus/octopus_bullet3.png"));

    public OctopusBullet(int startX, int startY) {
        super(startX, startY);

        isHostile = true;
    }

    @Override
    public void draw(Graphics g) {
        Image sprite = switch(cycle / 15) {
            case 0 -> sprite1;
            case 1 -> sprite2;
            case 2 -> sprite3;
            case 3 -> sprite4;
            default -> throw new IllegalStateException("Unexpected value in Bullet::draw: " + cycle / 15);
        };
        g.drawImage(sprite, x, y, width, height, null);
    }
}
