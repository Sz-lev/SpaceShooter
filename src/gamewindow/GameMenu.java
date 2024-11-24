package gamewindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JPanel {

    public final int WIDTH = 400;
    public final int HEIGTH = 500;
    public GameWindow gameWindow;

    public GameMenu(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.setLayout(new GridLayout(5, 1, 40, 20 ));



        addButtons();
//        setSize(WIDTH, HEIGTH);

    }

    public Dimension getMenuDimension() {
        return new Dimension(WIDTH, HEIGTH);
    }

    public void addButtons() {
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new PlayActionListener());
        playButton.setBackground(Color.CYAN);

        playButton.setAlignmentX(CENTER_ALIGNMENT);
        add(playButton);

        JButton profilButton = new JButton("Profilok");
        profilButton.setBackground(Color.CYAN);

        profilButton.setAlignmentX(CENTER_ALIGNMENT);
        add(profilButton);

        JButton ranglistaButton = new JButton("Ranglista");
        ranglistaButton.setBackground(Color.CYAN);

        ranglistaButton.setAlignmentX(CENTER_ALIGNMENT);
        add(ranglistaButton);

        JButton exitButton = new JButton("Kilépés");
        exitButton.setBackground(Color.CYAN);

        exitButton.setAlignmentX(CENTER_ALIGNMENT);
        add(exitButton);
    }

    class PlayActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gameWindow.addGamePanel();
        }
    }
}
