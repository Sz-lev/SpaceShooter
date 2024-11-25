package gamelogic;
import gamewindow.GamePanel;
public class GameThread extends Thread{

    private GameLogic gamelogic;
    private GamePanel gamepanel;
    private final int FPS = 60;

    public GameThread(GamePanel gp) {
        gamelogic = new GameLogic(gp);
        gamepanel = gp;
        gamepanel.setGameLogic(gamelogic);
    }
    @Override
    public void run() {

        double drawTime = 1000000000.0/FPS;

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
