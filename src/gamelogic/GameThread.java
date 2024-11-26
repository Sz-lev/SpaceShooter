package gamelogic;
import gamewindow.GamePanel;

/**
 * A játék szálának osztálya.
 */
public class GameThread extends Thread{

    private GameLogic gamelogic;
    private GamePanel gamepanel;
    private final int FPS = 60;
    private final double drawTime = 1000000000.0/FPS;

    /**
     * Konstruktor
     * @param gp A GamePanel, amin a játék fut.
     */
    public GameThread(GamePanel gp) {
        gamelogic = new GameLogic(gp);
        gamepanel = gp;
        gamepanel.setGameLogic(gamelogic);
    }

    /**
     * Futtatja a játék logikáját és a rajzolásért felelős függvényeket.
     * A rajzolással töltött idő és az FPS alapján kiszámítja, hogy mennyi ideig szüneteljen a következő futásig
     * és addig alvó állapotba kerül. A játék végén meghívja a GamePanelnek a menübe visszatérő függvényét.
     */
    @Override
    public void run() {

        while(!gamelogic.end()) {
            double updateBeginTime = System.nanoTime();

            gamelogic.gameUpdate();
            gamepanel.repaint();

            double timeSpent = System.nanoTime()-updateBeginTime;
            long nextDrawTime = (long) (drawTime-timeSpent)/1000000;
            if(nextDrawTime > 0) {
                try {
                    sleep(nextDrawTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        gamepanel.returnToMenu();
    }
}
