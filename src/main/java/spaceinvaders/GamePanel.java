package spaceinvaders;

import spaceinvaders.sprites.bullet.Bullet;
import spaceinvaders.sprites.enemy.Crab;
import spaceinvaders.sprites.enemy.Enemy;
import spaceinvaders.sprites.enemy.Octopus;
import spaceinvaders.sprites.enemy.Squid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    public static final int SCREEN_WIDTH = 823;
    public static final int SCREEN_HEIGHT = 720;
    public static final int TPS = 60;

    private final int MAX_BULLETS = 3;

    private Timer timer;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean isShooting = false;

    private Player player;
    private ArrayList<Bullet> bullets;
    private Enemy[][] enemies;

    private int score = 0;
    private int lives = 3;
    private boolean win = false;
    private int speedupCounter = 0;
    private int speedup = 0;

    private static final Font font = new Font(Font.MONOSPACED, Font.BOLD, 12 * (SCREEN_HEIGHT / 224));

    public GamePanel() {
        player = new Player(SCREEN_WIDTH/2, SCREEN_HEIGHT - Player.height / 2);
        bullets = new ArrayList<>();
        enemies = new Enemy[5][11];

        int spacingX = Enemy.getWidth() + SCREEN_WIDTH / 32;
        int spacingY = Enemy.getHeight() + SCREEN_HEIGHT / 28;
        int offsetX = SCREEN_WIDTH / 9;
        int offsetY = Enemy.getHeight();

        for(int i = 0; i < enemies.length; i++) {
            for(int j = 0; j < enemies[0].length; j++) {
                int xPos = offsetX + j * spacingX;
                int yPos = offsetY + i * spacingY;
                if(i == 1 || i == 2)
                    enemies[i][j] = new Crab(xPos, yPos);
                else if(i == 3 || i == 4)
                    enemies[i][j] = new Octopus(xPos, yPos);
                else
                    enemies[i][j] = new Squid(xPos, yPos);
                System.out.printf("Added enemy at %d, %d\n", xPos, yPos);
            }
        }

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT) movingLeft = true;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) movingRight = true;
                if(e.getKeyCode() == KeyEvent.VK_SPACE) isShooting = true;
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT) movingLeft = false;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) movingRight = false;
                if(e.getKeyCode() == KeyEvent.VK_SPACE) isShooting = false;
            }
        });

        timer = new Timer(1000/TPS, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(win) {
            g.setColor(Color.WHITE);
            g.setFont(font);
            FontMetrics m = g.getFontMetrics(font);
            g.drawString("WIN!", SCREEN_WIDTH / 2 - (m.charWidth('A') * 2), SCREEN_HEIGHT / 2);
            String score_str = "%d".formatted(score);
            g.drawString(score_str, SCREEN_WIDTH / 2 - (m.charsWidth(score_str.toCharArray(), 0, score_str.length()) / 2), SCREEN_HEIGHT / 2 + m.getHeight());
        } else if(lives <= 0) {
            g.setColor(Color.MAGENTA);
            g.setFont(font);
            FontMetrics m = g.getFontMetrics(font);
            g.drawString("LOSE", SCREEN_WIDTH / 2 - (m.charWidth('A') * 2), SCREEN_HEIGHT / 2);
            String score_str = "%d".formatted(score);
            g.drawString(score_str, SCREEN_WIDTH / 2 - (m.charsWidth(score_str.toCharArray(), 0, score_str.length()) / 2), SCREEN_HEIGHT / 2 + m.getHeight());
        }
        player.draw(g);
        for(Bullet b: bullets) {
            b.draw(g);
        }
        for(Enemy[] row: enemies) {
            for(Enemy e: row) {
                if(e == null) continue;
                e.draw(g);
                //Rectangle r = e.getBounds();
                //g.setColor(Color.GREEN);
                //g.drawRect(r.x, r.y, r.width, r.height);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    boolean reverse = false;

    private void update() {
        if(reverse) {
            for(Enemy[] row: enemies) {
                for(Enemy e: row) {
                    if(e == null) continue;
                    e.reverse();
                }
            }
            reverse = false;
        }
        if(lives <= 0 || win) return;
        if(movingLeft) player.moveLeft();
        if(movingRight) player.moveRight();
        if(isShooting) {
            int playerBullets = 0;
            for(Bullet b: bullets) {
                if(!b.isHostile) playerBullets++;
            }
            if(playerBullets < MAX_BULLETS) {
                Bullet bullet = new Bullet(player.getX() + player.getWidth() / 2, player.getY());
                bullets.add(bullet);
            }
            isShooting = false;
        }

        win = true;
        for(Enemy[] row: enemies) {
            for(Enemy e: row) {
                if(e == null) continue;
                if(e.getAlive()) win = false;
                if(e.getBounds().y + e.getBounds().width > SCREEN_HEIGHT) lives = 0;
                if(e.tick() && !reverse) {
                    reverse = true;
                }
            }
        }

        for(int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.tick();
            if(b.isOffScreen()) bullets.remove(i);

            if(b.isHostile) {
                if(player.getBounds().contains(b.getBounds())) {
                    lives--;
                    bullets.remove(i);
                }
            } else {
                for (Enemy[] row : enemies) {
                    for (Enemy e : row) {
                        if (e == null) continue;
                        if (e.getAlive() && e.getBounds().contains(b.getBounds())) {
                            e.setAlive(false);
                            bullets.remove(i);
                            score += e.getScore();
                            break;
                        }
                    }
                }
            }
        }
        speedupCounter++;
        if(speedupCounter >= TPS * 2) {
            speedup++;
            timer.stop();
            timer = new Timer(1000/(TPS + speedup), this);
            timer.start();
            speedupCounter = 0;
        }
    }

    public void addBullet(Bullet b) {
        bullets.add(b);
    }
}
