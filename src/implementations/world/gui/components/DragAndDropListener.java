package implementations.world.gui.components;


import implementations.world.gui.JBot;
import implementations.world.gui.JChunk;
import implementations.world.gui.JWorld;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Matteo Cosi
 * @since 10.07.2017
 */
public class DragAndDropListener extends MouseAdapter {


    int x, y;

    JWorld w;

    public DragAndDropListener(JWorld w) {
        this.w = w;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {

            if (e.getComponent() instanceof JBot) {
                JBot bf = (JBot) e.getComponent();
                if (w.selectedBot != null)
                    w.selectedBot.setSelected(false);
                w.selectedBot = bf;
                bf.setSelected(true);
                int x = w.controlPanel.getX();
                int y = w.controlPanel.getY();
                w.controlPanel.removeAll();
                w.container.remove(w.controlPanel);
                w.controlPanel = null;
                w.controlPanel = w.addBotStats(bf, x, y);
                w.container.add(w.controlPanel, 0);
            } else {
                if (w.selectedBot != null)
                    w.selectedBot.setSelected(false);
                w.selectedBot = null;
                int x = w.controlPanel.getX();
                int y = w.controlPanel.getY();
                w.controlPanel.removeAll();
                w.container.remove(w.controlPanel);
                w.controlPanel = null;
                w.controlPanel = w.addControls(x, y);
                w.container.add(w.controlPanel, 0);
            }
        }
        if (e.getButton() == 3) {
            if (e.getComponent() instanceof JBot) {

                JBot bf = (JBot) e.getComponent();
                bf.kill();
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            x = e.getX();
            y = e.getY();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getComponent() instanceof JBot || e.getComponent() instanceof JChunk) {
            e.translatePoint(w.containerPanel.getLocation().x - x, w.containerPanel.getLocation().y - y);
            w.containerPanel.setLocation(e.getX(), e.getY());
        } else {
            e.translatePoint(e.getComponent().getLocation().x - x, e.getComponent().getLocation().y - y);
            e.getComponent().setLocation(e.getX(), e.getY());
        }
    }
}
