package gamewindow;

import playerdata.LeaderBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JPanel {

    enum MenuState {
        MENU,
        PROFILOK

    }
    public final int WIDTH = 400;
    public final int HEIGTH = 500;
    public GameWindow gameWindow;
    public LeaderBoard ranglista;
    public GridBagConstraints constraints;


    public GameMenu(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        constraints = new GridBagConstraints();
        ranglista = new LeaderBoard();
        gamemenuInit();
    }

    public void gamemenuInit() {
        this.setLayout(new GridBagLayout());
        setSize(WIDTH, HEIGTH);
        setBackground(new Color(0f, 0f, 0.08f));
        addButtons();
    }

    public Dimension getMenuDimension() {
        return new Dimension(WIDTH, HEIGTH);
    }

    public void addButtons() {

        constraints.insets = new Insets(10, 20, 10, 20);

        int row = 0;
        JLabel label = new JLabel("SpaceShooter", SwingConstants.CENTER);
        label.setFont(new Font("OCR A Extended", Font.BOLD, 30));
        label.setForeground(Color.WHITE);
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.ipady = 10;
        constraints.ipadx = 40;
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(label, constraints);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new PlayActionListener());
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(playButton, constraints);

        JButton profilButton = new JButton("Profilok");
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(profilButton, constraints);

        JButton ranglistaButton = new JButton("Ranglista");
        ranglistaButton.addActionListener(new RanglistaActionListener());
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(ranglistaButton, constraints);

        JButton kituntetesButton = new JButton("Kitüntetések");
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(kituntetesButton, constraints);

        JButton exitButton = new JButton("Kilépés");
        constraints.gridx = 1;
        constraints.gridy = row;
        add(exitButton, constraints);
    }

    public void addRanglistaTable() {
        JFrame rangFrame = new JFrame();
        rangFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        rangFrame.setMinimumSize(new Dimension(400, 250));
        rangFrame.setTitle("Ranglista");
        rangFrame.setLocationRelativeTo(null);

        JTable rangTable = new JTable(ranglista);
        rangTable.setFillsViewportHeight(true);
        rangFrame.add(rangTable, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(rangTable);

        rangFrame.add(scroll, BorderLayout.CENTER);

        rangFrame.setVisible(true);
    }

    class PlayActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gameWindow.addGamePanel();
        }
    }

    class ProfilActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class RanglistaActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addRanglistaTable();
        }
    }

    class AchievementsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class ExitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
