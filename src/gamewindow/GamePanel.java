package gamewindow;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final int WIDTH = 1080;
    private final int HEIGTH = 720;
    public GamePanel() {
        setSize(WIDTH, HEIGTH);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

}
