package spaceinvaders;

import javax.swing.*;

public class SpaceInvaders {
    public static GamePanel panel = new GamePanel();

    public static void main(String[] args)  {
        JFrame frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
