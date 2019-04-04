package implementations.world.logic.bot;

public class Sensor {

    private Bot parent;
    /**
     * 0-100
     */
    private int length;
    /**
     * angle relative to bot
     */
    private int angle;

    public Sensor(Bot parent) {
        this(parent, (int) (Math.random()*100));
    }

    public Sensor(Bot parent, int length) {
        this.parent = parent;
        this.length = length;
    }

    public double getIsLandUnder() {
        return 0;
    }


    public double getFoodUnder() {
    return 0;
    }

    public double getIsEnemyUnder() {
        return 0;
    }

    public void left() {
        
    }

    public void attack() {
    }

    public void right() {
    }
}
