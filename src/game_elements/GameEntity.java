package game_elements;

abstract class GameEntity {
    protected int maxCoordinateOfX;
    protected int maxCoordinateOfY;
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

    public void update() {}
}

