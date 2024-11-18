package game_elements.entities.spaceships;

import game_elements.entities.GameEntity;

import java.awt.*;
import java.awt.geom.Ellipse2D;

abstract class SpaceShip extends GameEntity {

    protected double laserRechargeTime;
    protected double lastLaserShoot;
    protected int laserSpeed;

    public SpaceShip() {}

    public SpaceShip(int speed) {
        super(speed);
    }
    public SpaceShip(int x, int y, int speed) {
        super(x, y, speed);
    }

    abstract boolean isRecharging();

    public Ellipse2D.Double getSpaceShipBounds() {
        return new Ellipse2D.Double(xCoordinate, yCoordinate, size_x, size_y);
    }

    @Override
    protected int getxCoordinate() {
        return super.getxCoordinate();
    }

    @Override
    protected int getyCoordinate() {
        return super.getyCoordinate();
    }

    @Override
    protected Dimension getSize() {
        return super.getSize();
    }
}
