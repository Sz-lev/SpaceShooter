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
    private GamePanel gamePanel;

    private int gameState;

    public  GameWindow() {
        initWindow();
    }

    public void initWindow() {
        window = new JFrame();
        window.setTitle("SpaceShooter");
        window.setSize(WIDTH, HEIGTH);
        window.setResizable(true);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setFocusable(true);
        addMenu();
    }


    public void addMenu() {
        if(gamePanel != null) {
            window.remove(gamePanel);
            gamePanel = null;
        }

        menu = new JPanel();
        JButton jb = new JButton("Play");
        jb.addActionListener(new PlayActionListener());
        menu.add(jb);
        window.add(menu);
        menu.setSize(400, 500);
        window.getContentPane().setPreferredSize(new Dimension(400, 500));
        window.pack();
        window.setVisible(true);

    }

    public void addGamePanel() {
        if(menu != null) {
            window.remove(menu);
            menu = null;
        }

        GamePanel gamepanel = new GamePanel(this);
        window.add(gamepanel);
        window.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGTH));
        window.pack();
        window.setVisible(true);
        gamepanel.requestFocusInWindow();
        menu = null;
    }


    class PlayActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addGamePanel();
        }
    }


}


