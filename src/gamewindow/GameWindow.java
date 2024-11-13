package gamewindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow {

    private final int WIDTH = 1080;
    private final int HEIGTH = 720;
    private JFrame window;
    private JPanel menu;

    public  GameWindow() {
        initWindow();
    }
    public void initWindow() {
        window = new JFrame();
        window.setTitle("SpaceShooter");
        window.setSize(WIDTH, HEIGTH);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);


        menu = new JPanel();
        JButton jb = new JButton("Play");
        jb.addActionListener(new PlayActionListener());
        menu.add(jb);
        window.add(menu, BorderLayout.NORTH);
        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel, BorderLayout.CENTER);
        window.setVisible(true);
    }
    class PlayActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            window.remove(menu);
            GamePanel gamepanel = new GamePanel();
            window.add(gamepanel);
        }
    }
}


