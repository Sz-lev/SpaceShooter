package game_elements.entities;

import java.awt.*;

public abstract class GameEntity {
    public int maxCoordinateOfX;
    public int maxCoordinateOfY;
    protected int xCoordinate;
    protected int yCoordinate;
    protected int speed;
    protected int size_x;
    protected int size_y;
    
    public GameEntity() {
        xCoordinate = 0;
        yCoordinate = 0;
        speed = 0;
    }

    public GameEntity(int speed) {
        xCoordinate = 0;
        yCoordinate = 0;
        this.speed = speed;
    }
    
    public GameEntity(int x, int y, int speed) {
        maxCoordinateOfX = x;
        maxCoordinateOfY = y;
        this.speed = speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    protected void setxCoordinate(int x) {
        xCoordinate = x;
    }
    protected int getxCoordinate() {
        return xCoordinate;
    }

    protected void setyCoordinate(int y) {
        yCoordinate = y;
    }
    protected int getyCoordinate() {
        return yCoordinate;
    }

    protected Dimension getSize() {
        return new Dimension(size_x, size_y);
    }

    protected abstract void update();
    protected abstract void draw(Graphics2D g);
}

