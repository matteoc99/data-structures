package Test;

import implementations.world.logic.World;

import javax.swing.plaf.TableHeaderUI;

public class WorldTest {
    public static void main(String[] args) {
        Thread thread = new Thread(new World(20,10,20));
        thread.start();
    }
}
