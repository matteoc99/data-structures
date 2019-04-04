package implementations.world.gui.components;

import implementations.world.gui.JWorld;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static implementations.world.gui.JWorld.MOVE_SPEED;


/**
 * @author Matteo Cosi
 * @since 05.09.2017
 */
public class WorldKeyListener extends KeyAdapter {

    JWorld world;

    public WorldKeyListener(JWorld world) {
        this.world = world;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                world.generateNewDialog();
                break;

            case KeyEvent.VK_UP:
                world.containerPanel.setLocation(world.containerPanel.getX(), world.containerPanel.getY() - MOVE_SPEED);
                break;
            case KeyEvent.VK_DOWN:
                world.containerPanel.setLocation(world.containerPanel.getX(), world.containerPanel.getY() + MOVE_SPEED);
                break;
            case KeyEvent.VK_LEFT:
                world.containerPanel.setLocation(world.containerPanel.getX() - MOVE_SPEED, world.containerPanel.getY());
                break;
            case KeyEvent.VK_RIGHT:
                world.containerPanel.setLocation(world.containerPanel.getX() + MOVE_SPEED, world.containerPanel.getY());
                break;
            case KeyEvent.VK_H:
            case KeyEvent.VK_CONTROL:
                Point p = MouseInfo.getPointerInfo().getLocation();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                screenSize.width -= world.controlPanelWidth;
                int xOff = (int) (p.x - screenSize.getWidth() / 2);
                int yOff = (int) (p.y - screenSize.getHeight() / 2);
                world.containerPanel.setLocation(world.containerPanel.getX() + xOff / 8, world.containerPanel.getY() + yOff / 8);
                break;
            case KeyEvent.VK_L:
                world.containerPanel.setLocation(0, 0);
                break;
            case KeyEvent.VK_P:
                world.pause = !world.pause;
                break;

            //player steuerung for test
            case KeyEvent.VK_W:
                world.population.get(0).setYDir(-2);

                break;
            case KeyEvent.VK_S:
                world.population.get(0).setYDir(2);

                break;
            case KeyEvent.VK_A:
                world.population.get(0).setXDir(-2);

                break;
            case KeyEvent.VK_D:
                world.population.get(0).setXDir(2);
                break;
            case KeyEvent.VK_E:
                world.population.get(0).eat();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                world.population.get(0).setYDir(0);

                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                world.population.get(0).setXDir(0);

                break;

        }
    }
}
