package gamewindow;

import playerdata.LeaderBoard;
import playerdata.PlayerData;
import playerdata.Profiles;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class GameMenu extends JPanel {
    public final int WIDTH = 400;
    public final int HEIGTH = 500;
    public GameWindow gameWindow;
    public JLabel playerLabel;
    public LeaderBoard ranglista;
    public GridBagConstraints constraints;
    public ArrayList<PlayerData> playerList;
    public JFrame profilFrame;
    public DefaultListModel playerModel;
    public JList listAblak;
    public JTextField textField;
    public static final Color bgColor = new Color(0f, 0f, 0.08f);

    public GameMenu(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        constraints = new GridBagConstraints();
        ranglista = new LeaderBoard();
        gamemenuInit();
    }

    public void gamemenuInit() {
        this.setLayout(new GridBagLayout());
        setSize(WIDTH, HEIGTH);
        setBackground(bgColor);
        addButtons();

//        playerProfiles = new Profiles();
//        Profiles.readJSONFile("./data/profiles.json");
        playerList = Profiles.getPlayerList();
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

        if(gameWindow.player == null)
            playerLabel = new JLabel("Játékos: ");
        else
            playerLabel = new JLabel("Játékos: "+gameWindow.player);
        playerLabel.setFont(new Font("OCR A Extended", Font.BOLD, 14));
        playerLabel.setForeground(Color.WHITE);
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(playerLabel, constraints);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new PlayActionListener());
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(playButton, constraints);

        JButton profilButton = new JButton("Profilok");
        profilButton.addActionListener(new ProfilActionListener());
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(profilButton, constraints);

        JButton ranglistaButton = new JButton("Ranglista");
        ranglistaButton.addActionListener(new RanglistaActionListener());
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(ranglistaButton, constraints);

        JButton kituntetesButton = new JButton("Kitüntetések");
        kituntetesButton.addActionListener(new AchievementsActionListener());
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(kituntetesButton, constraints);

        JButton exitButton = new JButton("Kilépés");
        exitButton.addActionListener(new ExitActionListener());
        constraints.gridx = 1;
        constraints.gridy = row;
        add(exitButton, constraints);
    }

    public void addRanglistaTable() {
        gameWindow.window.setVisible(false);

        JFrame rangFrame = new JFrame();
        rangFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        rangFrame.setMinimumSize(new Dimension(400, 250));
        rangFrame.setTitle("Ranglista");


        JTable rangTable = new JTable(ranglista);
        rangTable.setFillsViewportHeight(true);
        rangTable.setBackground(bgColor);
        rangTable.setForeground(Color.WHITE);
        rangTable.setFont(new Font("Arial", Font.BOLD, 14));
        rangTable.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        rangTable.setDefaultRenderer(String.class, centerRenderer);
        rangTable.setDefaultRenderer(Integer.class, centerRenderer);
        rangTable.setDefaultRenderer(Double.class, centerRenderer);
        JScrollPane scroll = new JScrollPane(rangTable);
        rangFrame.add(scroll, BorderLayout.CENTER);

        JButton backButton = new JButton("Vissza");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.window.setVisible(true);
                rangFrame.dispose();
            }
        });
        rangFrame.add(backButton, BorderLayout.SOUTH);

        rangFrame.pack();
        rangFrame.setLocationRelativeTo(null);
        rangFrame.setVisible(true);
    }

    public void profileScreen() {
        gameWindow.window.setVisible(false);

        profilFrame = new JFrame();
        profilFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        profilFrame.setTitle("Profilok");

        playerModel = new DefaultListModel();
        playerModel.addAll(playerList);

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel newProfileLabel = new JLabel("Új profil:");
        topPanel.add(newProfileLabel);

        textField = new JTextField(20);
        textField.addActionListener(new addPlayer());
        topPanel.add(textField);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JButton proceedButton = new JButton("Kiválaszt");
        proceedButton.addActionListener(new proceedAction());

        JButton deleteButton = new JButton("Törlés");
        deleteButton.addActionListener(new deleteAction());

        JButton backButton = new JButton("Vissza");
        backButton.addActionListener(new backAction());

        buttonsPanel.add(proceedButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(backButton);

        listAblak = new JList<>(playerModel);
        listAblak.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listAblak.setFont(new Font("OCR A Extended", Font.BOLD, 15));
        listAblak.setForeground(Color.WHITE);
        listAblak.setBackground(bgColor);
        JScrollPane scrollPane = new JScrollPane(listAblak);

        profilFrame.add(scrollPane, BorderLayout.CENTER);
        profilFrame.add(topPanel, BorderLayout.NORTH);
        profilFrame.add(buttonsPanel, BorderLayout.SOUTH);
        profilFrame.pack();
        profilFrame.setLocationRelativeTo(null);
        profilFrame.setResizable(false);
        profilFrame.setVisible(true);
    }

    public void addAchievmentsScreen() {
        gameWindow.window.setVisible(false);

        JFrame achScreen = new JFrame();
        achScreen.setTitle("Kitüntetések");
        achScreen.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel cimSor = new JPanel(new FlowLayout());


        JLabel jatekos = new JLabel(gameWindow.player.name+" kitüntetései");

        jatekos.setFont(new Font("Arial", Font.BOLD, 14));
        cimSor.add(jatekos);
        achScreen.add(cimSor, BorderLayout.NORTH);

        PlayerDataTable pdt = new PlayerDataTable(gameWindow.player);
        JTable achTable = new JTable(pdt);
        achTable.setFillsViewportHeight(true);
        achTable.setBackground(bgColor);
        achTable.setForeground(Color.WHITE);
        achTable.setFont(new Font("Arial", Font.BOLD, 14));
        achTable.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        achTable.setDefaultRenderer(String.class, centerRenderer);
        achTable.setDefaultRenderer(Integer.class, centerRenderer);
        achTable.setDefaultRenderer(Double.class, centerRenderer);
        JScrollPane scroll = new JScrollPane(achTable);
        achScreen.add(scroll, BorderLayout.CENTER);

        JButton backButton = new JButton("Vissza");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                achScreen.dispose();
                gameWindow.window.setVisible(true);
            }
        });

        achScreen.add(backButton, BorderLayout.SOUTH);

        achScreen.pack();
        achScreen.setLocationRelativeTo(null);
        achScreen.setResizable(false);
        achScreen.setVisible(true);


    }

    class PlayActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(gameWindow.player == null) {
                JOptionPane.showMessageDialog(gameWindow.window, "Először hozz létre, vagy válassz profilt!",
                        "Nincs profil kiválasztva!", JOptionPane.ERROR_MESSAGE);
            } else {
                gameWindow.addGamePanel();
            }
        }
    }

    class ProfilActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            profileScreen();
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
            if(gameWindow.player == null) {
                JOptionPane.showMessageDialog(gameWindow.window, "Először hozz létre, vagy válassz profilt!",
                        "Nincs profil kiválasztva!", JOptionPane.ERROR_MESSAGE);
            } else {
                addAchievmentsScreen();
            }
        }
    }

    class ExitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Profiles.writeJSONFile("./data/profiles.json");
            LeaderBoard.writeLeaderboardToFile("./data/leaderboard.txt");
            gameWindow.window.dispose();
        }
    }

    class addPlayer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = textField.getText();
            if(text.equals("")) {
                JOptionPane.showMessageDialog(profilFrame, "Az új profilnak nem lehet üres a neve!",
                        "Hiba!", JOptionPane.ERROR_MESSAGE);
            } else if(text.contains(",")) {
                JOptionPane.showMessageDialog(profilFrame, "A profil nem tartalmazhat vesszőt!",
                        "Hiba!", JOptionPane.ERROR_MESSAGE);
            } else {
                playerModel.addElement(new PlayerData(textField.getText()));
                textField.setText("");
            }
        }
    }

    class proceedAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(listAblak.getSelectedValue() != null) {
                playerList.clear();
                for(int i = 0; i < playerModel.getSize(); i++) {
                    PlayerData player = (PlayerData) playerModel.get(i);
                    playerList.add(player);
                }
                PlayerData selectedPlayer = (PlayerData) listAblak.getSelectedValue();
                gameWindow.player = selectedPlayer;
                playerLabel.setText("Játékos: "+selectedPlayer.name);
                gameWindow.window.setVisible(true);
                profilFrame.dispose();
            }
        }
    }

    class deleteAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(listAblak.getSelectedValue() != null) {
                PlayerData deletePlayer = (PlayerData) listAblak.getSelectedValue();
                playerModel.removeElement(deletePlayer);
                if(gameWindow.player == deletePlayer){
                    gameWindow.player = null;
                    playerLabel.setText("Játékos: ");
                }
            }
        }
    }

    class backAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            playerList.clear();
            for(int i = 0; i < playerModel.getSize(); i++) {
                PlayerData player = (PlayerData) playerModel.get(i);
                playerList.add(player);
            }
            gameWindow.window.setVisible(true);
            profilFrame.dispose();
        }
    }
}
