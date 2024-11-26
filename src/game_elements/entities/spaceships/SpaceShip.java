package game_elements.entities.spaceships;

import game_elements.entities.GameEntity;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Az űrhajók absztrakt ősosztálya.
 */
abstract class SpaceShip extends GameEntity {

    protected double laserRechargeTime;
    protected double lastLaserShoot;
    protected int laserSpeed;

    abstract boolean isRecharging();

    /**
     * Az űrhajó határait megadó függvény.
     *
     * @return Egy ellipszis formát határoz meg, ami részben azonos az űrhajó határával.
     */
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
