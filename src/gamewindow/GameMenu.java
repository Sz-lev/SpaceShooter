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

/**
 * A játék menüjének osztálya.
 */
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

    /**
     * Konstruktor.
     * Menti a játék ablakát. Létrehozza a ranglista objektumok. Meghívja az inícializáló függvényt.
     * @param gameWindow Az ablak objektum, amin a menü megtalálható.
     */
    public GameMenu(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        constraints = new GridBagConstraints();
        ranglista = new LeaderBoard();
        gamemenuInit();
    }

    /**
     * A Menü értékeinek inícializásáért felel. Beállítja a felületet, a méretet és meghívja a gombokat hozzáadó függvényt.
     */
    public void gamemenuInit() {
        this.setLayout(new GridBagLayout());
        setSize(WIDTH, HEIGTH);
        setBackground(bgColor);
        addButtons();
        playerList = Profiles.getPlayerList();
    }

    /**
     * Visszaadja az ablak méretének dimenzióját.
     * @return Az ablak mérete.
     */
    public Dimension getMenuDimension() {
        return new Dimension(WIDTH, HEIGTH);
    }

    /**
     * A menü gombjainak hozzáadását végző függvény.
     */
    public void addButtons() {

        // Az elemek közötti margin beállítását végzi
        constraints.insets = new Insets(10, 20, 10, 20);

        // Cím
        int row = 0;
        JLabel label = new JLabel("SpaceShooter", SwingConstants.CENTER);
        label.setFont(new Font("OCR A Extended", Font.BOLD, 30));
        label.setForeground(Color.WHITE);
        // Annak értéke, hogy kitöltse a vízszintes teret amíg lehet.
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        // padding értéke
        constraints.ipady = 10;
        constraints.ipadx = 40;
        // A cella meghatározása
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(label, constraints);

        // A játékos nevének kiiratása, ha már ki lett választva
        if(gameWindow.player == null)
            playerLabel = new JLabel("Játékos: ");
        else
            playerLabel = new JLabel("Játékos: "+gameWindow.player);
        playerLabel.setFont(new Font("OCR A Extended", Font.BOLD, 14));
        playerLabel.setForeground(Color.WHITE);
        constraints.gridx = 1;
        constraints.gridy = row++;
        add(playerLabel, constraints);

        JButton playButton = new JButton("Játék");
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

    /**
     * A ranglista menüpont megjelenítéséért felelős függvény.
     * Megjeleníti a ranglista ablakát és táblázatát, és elrejti a menü ablakát.
     */
    public void addRanglistaTable() {
        gameWindow.window.setVisible(false);

        JFrame rangFrame = new JFrame();
        // Kilépés gomb itt nem működik, a vissza gombbal lehet visszalépni.
        rangFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        rangFrame.setMinimumSize(new Dimension(400, 250));
        rangFrame.setTitle("Ranglista");
        rangFrame.setResizable(false);

        // a rangtábla létrehozása
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
            /**
             * A vissza gomb megnyomásakor a menü ablaka ismét megjelenik, míg a ranglista ablak bezáródik.
             * @param e the event to be processed
             */
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

    /**
     * A profil ablak megjelenítéséért felelős függvény.
     * Megjeleníti a profil ablak minden elemét.
     */
    public void profileScreen() {
        gameWindow.window.setVisible(false);

        profilFrame = new JFrame();
        profilFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        profilFrame.setTitle("Profilok");
        profilFrame.setResizable(false);

        // A listamodel létrehozása
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

    /**
     * A kitüntetések ablak megjelenítéséért felelős függvény.
     * Megjeleníti a kitüntetések ablak minden elemét.
     */
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

        // A kitüntetések táblázat létrehozásáért felelős függvény
        PlayerDataTable pdt = new PlayerDataTable(gameWindow.player);
        JTable achTable = new JTable(pdt);
        achTable.setFillsViewportHeight(true);
        achTable.setBackground(bgColor);
        achTable.setForeground(Color.WHITE);
        achTable.setFont(new Font("Arial", Font.BOLD, 14));
        achTable.setRowHeight(30);
        // A középre helyezésért felelős függvény
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        achTable.setDefaultRenderer(String.class, centerRenderer);
        achTable.setDefaultRenderer(Integer.class, centerRenderer);
        achTable.setDefaultRenderer(Double.class, centerRenderer);
        JScrollPane scroll = new JScrollPane(achTable);
        achScreen.add(scroll, BorderLayout.CENTER);

        JButton backButton = new JButton("Vissza");
        backButton.addActionListener(new ActionListener() {
            /**
             * A menü ablakát megjeleníti a kitüntetések ablakát bezárja.
             * @param e the event to be processed
             */
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

    /**
     * A play gomb ActionListenere
     */
    class PlayActionListener implements ActionListener {

        /**
         * Ha nincs játékos kiválasztva, akkor egy hibaüzenetet dob.
         * Egyébként meghívja a játékablak függvényét.
         * @param e the event to be processed
         */
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

    /**
     * A profil gomb ActionListenere
     */
    class ProfilActionListener implements ActionListener {

        /**
         * Meghívja a profilscreen függvényt, amivel létrejön a profilablak.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            profileScreen();
        }
    }

    /**
     * A ranglista gomb ActionListenere
     */
    class RanglistaActionListener implements ActionListener {

        /**
         * Meghívja ranglista ablak létrehozásáért felelős függvényt.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            addRanglistaTable();
        }
    }
    /**
     * A kitüntetések gomb ActionListenere
     */
    class AchievementsActionListener implements ActionListener {
        /**
         * Ha nincs játékos kiválasztva, akkor egy hibaüzenetet dob.
         * Meghívja kitüntetések ablak létrehozásáért felelős függvényt.
         * @param e the event to be processed
         */
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

    /**
     * A kilépés gomb ActionListenere
     */
    class ExitActionListener implements ActionListener {

        /**
         * Meghívja a Profil osztály statikus json író függvényét, amivel menti a profilokat.
         * Meghívja a LeaderBoard osztály statikus txt író függvényét, amivel menti a ranglistát.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Profiles.writeJSONFile("./data/profiles.json");
            LeaderBoard.writeLeaderboardToFile("./data/leaderboard.txt");
            gameWindow.window.dispose();
        }
    }

    /**
     * A játkos hozzáadása JTextfield ActionListenere
     */
    class addPlayer implements ActionListener {
        /**
         * Ha üres az új név mező vagy egy vesszőt tartalmaz, akkor hibaüzenetet dob.
         * Egyébként menti az új profilt és üresre állítja a beviteli mezőt.
         * @param e the event to be processed
         */
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

    /**
     * A kiválaszt gomb ActionListenere
     */
    class proceedAction implements ActionListener{
        /**
         * Frissíti a játékos profilok listáját a modelben találhatóéval.
         * A választott játékost menti a játék ablakába.
         * Frissíti a megjelenítendő játékos nevet.
         * Bezárja a profilok ablakát és megjeleníti a menü ablakát.
         * @param e the event to be processed
         */
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

    /**
     * A törlés gomb ActionListenere
     */
    class deleteAction implements ActionListener{
        /**
         * Ha van kiválasztott játékos, akkor törli a modelből.
         * Ha a kiválasztott játékos került törlésre, akkor törli a megjelenítendő játékos nevét.
         * @param e the event to be processed
         */
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

    /**
     * A vissza gomb ActionListenere
     */
    class backAction implements ActionListener{
        /**
         * Menti a modelből a profilokat a játékos profil listába.
         * Bezárja a profilok ablakát és megnyitja a menü ablakát.
         * @param e the event to be processed
         */
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
