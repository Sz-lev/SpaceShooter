package gamewindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow {

    public final int WIDTH = 1080;
    public final int HEIGTH = 720;
    public JFrame window;
    public GameMenu menu;
    public GamePanel gamePanel;

    private int gameState;

    public  GameWindow() {
        initWindow();
    }

    public void initWindow() {
        window = new JFrame();
        window.setTitle("SpaceShooter");
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setFocusable(true);
        addMenu();
    }


    public void addMenu() {
        if(gamePanel != null) {
            window.remove(gamePanel);
            gamePanel = null;
        }
        menu = new GameMenu(this);
        window.setSize(400, 500);
        window.setLocationRelativeTo(null);
        window.getContentPane().setPreferredSize(menu.getMenuDimension());
        window.add(menu, BorderLayout.CENTER);
        window.pack();
        window.setVisible(true);
    }

    public void addGamePanel() {
        if(menu != null) {
            window.remove(menu);
            menu = null;
        }
        gamePanel = new GamePanel(this);
        window.add(gamePanel);
        window.setSize(WIDTH, HEIGTH);
        window.setLocationRelativeTo(null);
        window.getContentPane().setPreferredSize(gamePanel.getPanelDimension());
        window.pack();
        window.setVisible(true);
        gamePanel.requestFocusInWindow();
    }


    class PlayActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addGamePanel();
        }
    }


}


