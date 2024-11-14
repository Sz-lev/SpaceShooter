package gamelogic;

import java.awt.*;
import game_elements.*;
public class GameLogic {
    private boolean exit;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGTH;
    public PlayerSpaceShip player;

    public GameLogic() {
        exit = false;
    }
    public void gameRun() {
//        System.out.println("Running");
    }

    public boolean end() {
        return exit;
    }


    public void setScreenDimensions(int width, int heigth) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGTH = heigth;
    }

    public void init() {
        player = new PlayerSpaceShip(SCREEN_WIDTH, SCREEN_HEIGTH, 8);
    }
}
