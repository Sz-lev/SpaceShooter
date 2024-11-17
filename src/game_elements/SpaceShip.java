package game_elements;

abstract class SpaceShip extends GameEntity {


    protected double laserRechargeTime;
    protected double lastLaserShoot;
    public SpaceShip() {}

    public SpaceShip(int speed) {
        super(speed);
    }
    public SpaceShip(int x, int y, int speed) {
        super(x, y, speed);
    }

    abstract boolean isRecharging();


}
