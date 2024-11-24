package gamelogic;

import gamewindow.GameKeyListener;
import gamewindow.GameMouseListener;

import java.awt.*;

public class GamePauseMenu {

    public int WIDTH, HEIGTH;
    public GameKeyListener gameKeyListener;
    public GameMouseListener gameMouseListener;
    public int optionCounter;
    public static Font spacefont = new Font("OCR A Extended", Font.BOLD, 50);
    private Graphics2D grphcs;
    public boolean hover;

    public GamePauseMenu(Dimension screen, GameKeyListener gkl) {
        WIDTH = screen.width;
        HEIGTH = screen.height;
        gameKeyListener = gkl;
        optionCounter = 0;
    }

    public GamePauseMenu(Dimension screen, GameKeyListener gkl, GameMouseListener gml) {
        WIDTH = screen.width;
        HEIGTH = screen.height;
        gameMouseListener = gml;
        gameKeyListener = gkl;
        optionCounter = 0;
        hover = false;
    }

    public int update() {
        if (gameKeyListener.down)
            optionCounter = 2;
        else if (gameKeyListener.up)
            optionCounter = 0;
        else if (gameKeyListener.enter || (hover && gameMouseListener.clicked)) {
            gameKeyListener.pause = false;
            return optionCounter;
        }

        return 1;
    }

    public void draw(Graphics2D g) {
        if (grphcs == null)
            grphcs = g;
        g.setColor(new Color(0.4f, 0.4f, 0.4f, 0.5f));
        g.fillRect(0, 0, WIDTH, HEIGTH);

        g.setFont(spacefont);

        g.setColor(Color.WHITE);


        String resume = "RESUME";
        int xResume = getCenteredText(resume);
        int yResume = HEIGTH / 3;
        Dimension dimRes = getStringDim(resume);
        g.drawString(resume, xResume, HEIGTH / 3);

        String quit = "QUIT";
        int xQuit = getCenteredText(quit);
        int yQuit = HEIGTH * 2 / 3;
        Dimension quitDim = getStringDim(quit);
        g.drawString(quit, xQuit, yQuit);

        if (mouseHover(xResume, yResume, dimRes)) {
            optionCounter = 0;
            hover = true;
        } else if (mouseHover(xQuit, yQuit, quitDim)) {
            optionCounter = 2;
            hover = true;
        } else
            hover = false;

        if (optionCounter == 0) {
            g.drawString("> ", xResume - 40, yResume);
        }
        if (optionCounter == 2) {
            g.drawString("> ", xQuit - 40, yQuit);
        }


    }

    public int getCenteredText(String text) {
        int length = (int) grphcs.getFontMetrics().getStringBounds(text, grphcs).getWidth();
        return (WIDTH - length) / 2;
    }

    public Dimension getStringDim(String string) {
        int width = (int) grphcs.getFontMetrics().getStringBounds(string, grphcs).getWidth();
        int heigth = (int) grphcs.getFontMetrics().getStringBounds(string, grphcs).getHeight();
        return new Dimension(width, heigth);
    }

    public boolean mouseHover(int x, int y, Dimension dim) {
        int xPosMouse = gameMouseListener.xPos;
        int yPosMouse = gameMouseListener.yPos;
        return hover = xPosMouse >= x && xPosMouse <= x + dim.width && yPosMouse <= y + 10 && yPosMouse >= y - dim.height + 10;
    }
}