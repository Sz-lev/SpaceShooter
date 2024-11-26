package gamelogic;

import gamewindow.GameKeyListener;
import gamewindow.GameMouseListener;

import java.awt.*;

/**
 * A játék PAUSE állapotát vezénylő osztály.
 */
public class GamePauseMenu {

    public int WIDTH, HEIGTH;
    public GameKeyListener gameKeyListener;
    public GameMouseListener gameMouseListener;
    public int optionCounter;
    public static Font spacefont = new Font("OCR A Extended", Font.BOLD, 50);
    private Graphics2D grphcs;
    public boolean hover;

    /**
     * Konstrukor
     * @param screen Az ablak maximális mérete.
     * @param gkl A billentyűzet állapotát figyelő objektum.
     * @param gml Az egér állapotát figyelő objektum.
     */
    public GamePauseMenu(Dimension screen, GameKeyListener gkl, GameMouseListener gml) {
        WIDTH = screen.width;
        HEIGTH = screen.height;
        gameMouseListener = gml;
        gameKeyListener = gkl;
        optionCounter = 0;
        hover = false;
    }

    /**
     * Frissíti a szünet objektumok.
     * Ellenőrzi a billentyűzetes állapotot.
     * @return
     */
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

    /**
     * A szünet állapot megjelenítésért felelős függvény.
     * Az egér változásait is fiygeli.
     * @param g A megjelenítést végző Graphics2D objektum.
     */
    public void draw(Graphics2D g) {
        if (grphcs == null)
            grphcs = g;
        g.setColor(new Color(0.4f, 0.4f, 0.4f, 0.5f));
        g.fillRect(0, 0, WIDTH, HEIGTH);

        g.setFont(spacefont);

        g.setColor(Color.WHITE);


        String resume = "FOLYATATÁS";
        int xResume = getCenteredText(resume);
        int yResume = HEIGTH / 3;
        Dimension dimRes = getStringDim(resume);
        g.drawString(resume, xResume, HEIGTH / 3);

        String quit = "KILÉPÉS";
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

    /**
     * Egy szövegnek visszaadja azt az x értékét amivel vízszintesen középre lesz helyezve
     * @param text A középre helyezendő szöveg.
     * @return Azon x értéke, amivel a szöveg a kijelzőn középen jelenik meg.
     */
    public int getCenteredText(String text) {
        int length = (int) grphcs.getFontMetrics().getStringBounds(text, grphcs).getWidth();
        return (WIDTH - length) / 2;
    }

    /**
     * Egy szövegnek a méretét adja vissza.
     * @param string
     * @return A szöveg mérete.
     */
    public Dimension getStringDim(String string) {
        int width = (int) grphcs.getFontMetrics().getStringBounds(string, grphcs).getWidth();
        int heigth = (int) grphcs.getFontMetrics().getStringBounds(string, grphcs).getHeight();
        return new Dimension(width, heigth);
    }

    /**
     * Egy x, y, és méret értékű téglalapon belűl figyeli, hogy az egér tartózkodik-e.
     * @param x a bal pont x értéke
     * @param y a bal felső y értéke
     * @param dim a jobb alsó pont koordinátája.
     * @return True - Ha az egér az adott koordinátákon belül található, egyébként False.
     */
    public boolean mouseHover(int x, int y, Dimension dim) {
        int xPosMouse = gameMouseListener.xPos;
        int yPosMouse = gameMouseListener.yPos;
        return hover = xPosMouse >= x && xPosMouse <= x + dim.width && yPosMouse <= y + 10 && yPosMouse >= y - dim.height + 10;
    }
}