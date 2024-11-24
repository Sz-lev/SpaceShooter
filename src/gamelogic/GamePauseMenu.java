package gamelogic;

import gamewindow.GameKeyListener;

import java.awt.*;

public class GamePauseMenu {

    public int WIDTH, HEIGTH;
    public GameKeyListener gameKeyListener;
    public int optionCounter;
    public static Font spacefont = new Font("OCR A Extended", Font.BOLD, 50);
    private Graphics2D grphcs;
    public GamePauseMenu(Dimension screen, GameKeyListener gkl) {
        WIDTH = screen.width;
        HEIGTH = screen.height;
        gameKeyListener = gkl;
        optionCounter = 0;
    }

    public int update() {
        if(gameKeyListener.down)
            optionCounter = 2;
        else if(gameKeyListener.up)
            optionCounter = 0;
        else if(gameKeyListener.enter) {
            gameKeyListener.pause = false;
            return optionCounter;
        }

        return 1;
    }

    public void draw(Graphics2D g) {
        if(grphcs == null)
            grphcs = g;
        g.setColor(new Color(0.4f,0.4f,0.4f, 0.5f));
        g.fillRect(0, 0, WIDTH, HEIGTH);

        g.setFont(spacefont);

        g.setColor(Color.WHITE);


        String resume = "RESUME";
        int xResume = getCenteredText(resume);
        g.drawString(resume, xResume, HEIGTH/3);
        if(optionCounter == 0)
            g.drawString("> ", xResume-40, HEIGTH/3);

        String quit = "QUIT";
        int xQuit = getCenteredText(quit);
        g.drawString(quit, xQuit, HEIGTH*2/3);
        if(optionCounter == 2)
            g.drawString("> ", xQuit-40, HEIGTH*2/3);

    }

    public int getCenteredText(String text) {
        int length = (int) grphcs.getFontMetrics().getStringBounds(text, grphcs).getWidth();
        return (WIDTH - length)/2;
    }

}
