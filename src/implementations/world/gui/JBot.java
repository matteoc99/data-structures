package implementations.world.gui;

import data_structures.nn.network.Network;
import implementations.world.logic.World;
import implementations.world.logic.bot.Bot;

import javax.swing.*;

public class JBot extends JComponent {
    private boolean selected;
    private Bot me;

    JWorld parent;

    public JBot(JWorld parent) {
        this.parent = parent;
        me = new Bot(parent.getHim());
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void kill() {
        me.kill();
    }

    public void eat() {
        me.eat();
    }

    public void setYDir(int i) {
        me.setYDir(i);
    }

    public void setXDir(int i) {
        me.setXDir(i);

    }

    public int getHp() {
        return me.getHealth();
    }

    public int getAge() {
        return me.getAge();
    }

    public int getGeneration() {
    return me.getGeneration();
    }

    public int getRed() {
        return me.getRed();
    }

    public int getGreen() {
        return me.getGreen();
    }

    public int getBlue() {
        return me.getBlue();
    }

    public Network getNet() {
    return me.getNeuralNetwork();
    }

    public void refresh() {
    }
}
