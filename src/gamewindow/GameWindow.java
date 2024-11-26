package gamewindow;

import playerdata.LeaderBoard;
import playerdata.PlayerData;
import playerdata.Profiles;
import playerdata.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A játék ablakának osztálya
 */
public class GameWindow {

    public final int WIDTH = 1080;
    public final int HEIGTH = 720;
    public JFrame window;
    public GameMenu menu;
    public GamePanel gamePanel;
    public PlayerData player;

    private int gameState;

    /**
     * Konstrukor ami meghívja az inícializáló függvényt.
     */
    public  GameWindow() {
        initWindow();
    }

    /**
     * Létrehozza az ablakot és beállítja a kezdeti paramétereit.
     * Az ablak létrehozása után beolvassa a játék progilokat és ranglistát,
     * majd meghívja a menüt létrehozó függvényt.
     */
    public void initWindow() {
        window = new JFrame();
        window.setTitle("SpaceShooter");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setFocusable(true);
        Profiles.readJSONFile("./data/profiles.json");
        LeaderBoard.readLeaderboardFile("./data/leaderboard.txt");
        addMenu();
    }

    /**
     * A Menü paneljét létrehozó függvény.
     * Ha volt játékpanel létrehozva, akkor megszűnteti azt.
     * Beállítja a menüpanelhez az ablak paramétereit.
     */
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

    /**
     * A játék paneljét létrehozó függvény.
     * Ha volt menüpanel létrehozva, akkor megszűnteti azt.
     * Beállítja a jatékpanelhez az ablak paramétereit.
     */
    public void addGamePanel() {
        if(menu != null) {
            window.remove(menu);
            menu = null;
        }
        gamePanel = new GamePanel(this);
        window.add(gamePanel);
        window.setSize(WIDTH, HEIGTH);
        window.setLocationRelativeTo(null);
        window.getContentPane().setPreferredSize(gamePanel.getScreenDimension());
        window.pack();
        window.setVisible(true);
        gamePanel.requestFocusInWindow();
    }
}


