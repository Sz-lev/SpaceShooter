package game_elements.entities;

import java.awt.*;

/**
 * A játék entitásainak ősosztálya.
 */
public abstract class GameEntity {
    /**
     * Az x értékének maximuma.
     */
    public int maxCoordinateOfX;
    /**
     * Az y értékének maximuma.
     */
    public int maxCoordinateOfY;
    /**
     * Az aktuális x érték.
     */
    protected double xCoordinate;
    /**
     * Az aktuális y érték.
     */
    protected double yCoordinate;
    /**
     * A sebesség változója.
     */
    protected int speed;
    /**
     * Az objektum x-tengelyű mérete, amit a kép mérete határoz meg.
     */
    protected int size_x;
    /**
     * Az objektum y-tengelyű mérete, amit a kép mérete határoz meg.
     */
    protected int size_y;

    /**
     * Konstrukor. Beállítja az x, y és sebesség értékét 0-ra.
     */
    public GameEntity() {
        xCoordinate = 0;
        yCoordinate = 0;
        speed = 0;
    }

    /**
     * A sebesség beállítását végző függvény.
     *
     * @param speed Az új sebesség.
     */
    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Az x értékét állító függvény.
     *
     * @param x Az új x érték.
     */
    protected void setxCoordinate(int x) {
        xCoordinate = x;
    }

    /**
     * Az x értékét visszaadó függvény
     *
     * @return Az x értéke.
     */
    protected int getxCoordinate() {
        return (int) xCoordinate;
    }

    /**
     * Az x értékét állító függvény.
     *
     * @param y Az új y érték.
     */
    protected void setyCoordinate(int y) {
        yCoordinate = y;
    }

    /**
     * Az y értékét visszaadó függvény
     *
     * @return Az y értéke
     */
    protected int getyCoordinate() {
        return (int) yCoordinate;
    }

    /**
     * A méretet visszaadó függvény.
     *
     * @return A méret dimenziója.
     */
    protected Dimension getSize() {
        return new Dimension(size_x, size_y);
    }

    /**
     * Abstract függvény az osztály frissítésére.
     */
    protected abstract void update();

    /**
     * Absztrakt függvény, az osztály megjeenítéséért felel.
     *
     * @param g A megjelenítést végző Graphics2D objektum.
     */
    protected abstract void draw(Graphics2D g);
}

