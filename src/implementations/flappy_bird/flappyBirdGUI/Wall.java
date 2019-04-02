package implementations.flappy_bird.flappyBirdGUI;

/**
 * Created by mcosi on 09/06/2017.
 */
public class Wall extends MovingObject {
    /**
     * Construktor for the {@link StaticObject}
     */
    public static Wall allowedWall = null;

    public Wall(int width, int heigth) throws ClassNotFoundException {
        super("wall.png");
        setSize(width, heigth);
    }

    public void move() {
        Object player = getObjectAt(getX(), getY());
        while (player instanceof Player) {
            ((Player) player).stirb();
            player = getObjectAt(getX(), getY());
        }

        if (this.getX() < 0)
            this.stirb();
        else {
            setLocation(getX() - 7, getY());
            player = getObjectAt(getX(), getY());
            while (player instanceof Player) {
                ((Player) player).stirb();
                player = getObjectAt(getX(), getY());
            }
            if (allowedWall == this)
                PlayGround.abstand = ((PlayGround.WIDTH / 4) - (getX() / 4) + 1) / (PlayGround.WIDTH + 0.0);
        }
    }
}
