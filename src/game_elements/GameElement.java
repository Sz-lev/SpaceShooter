package game_elements;

abstract class GameElement {
    protected int maxCoordinateOfX;
    protected int maxCoordinateOfY;
    protected int xCoordinate;
    protected int yCoordinate;
    protected int speed;
    protected int size;
    
    public GameElement() {}
    
    public GameElement(int x, int y, int speed) {
        maxCoordinateOfX = x;
        maxCoordinateOfY = y;
        this.speed = speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setxCoordinate(int x) {
        xCoordinate = x;
    }
    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setyCoordinate(int y) {
        yCoordinate = y;
    }
    public int getyCoordinate() {
        return yCoordinate;
    }

    public void update() {
        if(xCoordinate > speed && xCoordinate < maxCoordinateOfX-speed)
            xCoordinate += speed;
        if(yCoordinate > speed && yCoordinate < maxCoordinateOfY)
            yCoordinate += speed;
    }
    abstract String getName();

}

