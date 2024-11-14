package game_elements;

public class PlayerSpaceShip extends SpaceShip{

    private int size;
    public PlayerSpaceShip(int x, int y, int speed) {
        super(x, y, speed);
        size = 50;
        xCoordinate = maxCoordinateOfX/2 -size;
        yCoordinate = maxCoordinateOfY/2 -size;
    }

    public void moveX(boolean rigth) {
        if(rigth && xCoordinate < maxCoordinateOfX-size-speed)
            xCoordinate += speed;
        else if (!rigth && xCoordinate > speed)
            xCoordinate -= speed;

    }
    public void moveY(boolean down) {
        System.out.println(yCoordinate);
        System.out.println(maxCoordinateOfY);
        if(down && yCoordinate < maxCoordinateOfY-size-speed)
            yCoordinate += speed;
        else if(!down && yCoordinate > speed)
            yCoordinate -= speed;
    }

}
