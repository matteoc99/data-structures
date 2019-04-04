package implementations.world.gui;

import data_structures.nn.network_gui.NetworkGUI;
import data_structures.nn.network_gui.NetworkPanel;
import implementations.world.gui.components.DragAndDropListener;
import implementations.world.gui.components.MySlider;
import implementations.world.gui.components.WorldKeyListener;
import implementations.world.logic.Chunk;
import implementations.world.logic.World;
import implementations.world.logic.bot.Bot;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class JWorld extends JFrame {

    /**
     * Describes the speed of the moving map
     */
    public static final int MOVE_SPEED = 40;

    //FPS control
    private int FPS = 60;
    private long timeUntilSleep;
    private int fpsCounter;

    public JChunk[][] map = null;

    /**
     * The container of the World Container
     */
    public Container container;

    /**
     * tells if the Map has finished loading
     */
    private boolean mapLoaded = false;

    /**
     * Contains the World
     */
    public JPanel containerPanel = new JPanel();
    /**
     * Contains the Controls and Settings for the World
     */
    public JPanel controlPanel = new JPanel();

    /**
     * describes the size of the control Panel
     */
    public int controlPanelWidth = 400;

    /**
     * Contains the Population
     */
    public ArrayList<JBot> population = new ArrayList<>();

    /**
     * currently selected Bot
     */
    public JBot selectedBot = null;


    /**
     * Listener used to drag the map
     */
    public  DragAndDropListener listener;


    public NetworkGUI networkGUI;
    public boolean pause = false;

    public String currenDateiname = null;

    JProgressBar progressBar;


    NetworkPanel con = null;


    private World me;

    public JWorld(World me) throws HeadlessException {
        this.me = me;
    }

    public JWorld() {
        this(new World());
        setTitle("World");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);


        // networkGUI = new NetworkGUI();

        listener = new DragAndDropListener(this);

        container = getContentPane();
        container.setLayout(null);
        container.setBackground(Color.gray);

        containerPanel.setBounds(0, 0, me.getWidth() * me.getChunckSize(), me.getHeight()* me.getChunckSize());
        containerPanel.setLayout(null);

        controlPanel = addControls(screenSize.width - controlPanelWidth, 0);

        containerPanel.addMouseListener(listener);
        containerPanel.addMouseMotionListener(listener);

        container.add(controlPanel);
        container.add(containerPanel);

        setVisible(true);
        addKeyListener(new WorldKeyListener(this));

        requestFocus();
    }

    public JPanel addBotStats(JBot b, int x, int y) {
        JPanel ret = new JPanel();

        ret.addMouseListener(listener);
        ret.addMouseMotionListener(listener);
        ret.setBackground(Color.white);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ret.setBounds(x, y, controlPanelWidth, screenSize.height - 500);

        ret.setLayout(null);

        JLabel[] labels = new JLabel[4];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel();
            labels[i].setBounds(20, 60 * i + 20, 300, 50);
            labels[i].setFont(new Font("Times New Roman", 0, 20));
            ret.add(labels[i]);
        }
        labels[0].setText("Health:" + b.getHp());
        labels[1].setText("Age: " + b.getAge());
        labels[2].setText("Generation: " + b.getGeneration());
        labels[3].setText("Color: " + b.getRed() + " " + b.getGreen() + " " + b.getBlue());
        labels[3].setBackground(new Color(b.getRed(), b.getGreen(), b.getBlue()));
        labels[3].setOpaque(true);

        if (con == null || (!con.network.equals(b.getNet()))) {
            con = new NetworkPanel(b.getNet(), "Bot");
            con.setBounds(20, 60 * labels.length + 20, 350, 300);
            con.refresh();
            con.repaint();
            ret.add(con);
        }
        return ret;
    }


    /**
     * add all the Controls components on a JPanel
     */
    public JPanel addControls(int x, int y) {
        JPanel ret = new JPanel();

        ret.addMouseListener(listener);
        ret.addMouseMotionListener(listener);
        ret.setBackground(Color.white);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ret.setBounds(x, y, controlPanelWidth, screenSize.height - 500);

        ret.setLayout(null);

        MySlider fps = new MySlider(JSlider.HORIZONTAL, 0, 300, FPS);
        MySlider food_distrib = new MySlider(JSlider.HORIZONTAL, 0, 80, me.getFoodDistribution());
        MySlider food_regrowth = new MySlider(JSlider.HORIZONTAL, 0, 100, me.getFoodRegrowth());//bis hier min +1 weil
        MySlider populationSlider = new MySlider(JSlider.HORIZONTAL, 0, 500, me.getMinPopSize());

        int xOff = 150;

        JLabel[] labels = new JLabel[4];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel();
            labels[i].setBounds(20, (getHeight() / 12) * i, xOff - 10, getHeight() / 10);
            labels[i].setFont(new Font("Times New Roman", 0, 20));
            ret.add(labels[i]);
        }

        labels[0].setText("FPS");
        labels[1].setText("Food Distrib.");
        labels[2].setText("Food Regrowth");
        labels[3].setText("Population");


        fps.setBounds(xOff, 10, controlPanelWidth - xOff - 20, getHeight() / 10);
        food_distrib.setBounds(xOff, 10 + getHeight() / 12, controlPanelWidth - xOff - 20, getHeight() / 10);
        food_regrowth.setBounds(xOff, 10 + (getHeight() / 12) * 2, controlPanelWidth - xOff - 20, getHeight() / 10);
        populationSlider.setBounds(xOff, 10 + (getHeight() / 12) * 3, controlPanelWidth - xOff - 20, getHeight() / 10);


        fps.setMajorTickSpacing(100);
        food_distrib.setMajorTickSpacing(20);
        food_regrowth.setMajorTickSpacing(10);
        populationSlider.setMajorTickSpacing(50);

        fps.setMinorTickSpacing(20);
        food_distrib.setMinorTickSpacing(5);
        food_regrowth.setMinorTickSpacing(2);
        populationSlider.setMinorTickSpacing(10);
        fps.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                FPS = fps.getValue() + 1;
                requestFocus();
            }
        });
        food_distrib.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                me.setFoodDistribution( food_distrib.getValue() + 1);
                requestFocus();
            }
        });
        food_regrowth.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                me.setFoodDistribution(food_regrowth.getValue() + 1);
                requestFocus();
            }
        });
        populationSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                me.setMinPopSize(populationSlider.getValue());
                requestFocus();
            }
        });

        ret.add(fps);
        ret.add(food_distrib);
        ret.add(food_regrowth);
        ret.add(populationSlider);

        JButton save = new JButton();
        save.setText("SAVE");
        JButton load = new JButton();
        load.setText("LOAD");
        JButton generate = new JButton();
        generate.setText("GENERATE A NEW WORLD");


        save.setBounds(10, 40 + (getHeight() / 12) * 5, 170, 50);
        load.setBounds(220, 40 + (getHeight() / 12) * 5, 170, 50);

        generate.setBounds(10, 50 + (getHeight() / 12) * 4, 380, 50);

        save.setBackground(Color.ORANGE);
        load.setBackground(Color.ORANGE);
        generate.setBackground(Color.ORANGE);


        ret.add(save);
        ret.add(load);
        ret.add(generate);

        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateNewDialog();
            }
        });
        return ret;
    }

    /**
     * creates a dialog for the new world generation
     */
    public void generateNewDialog() {


        JPanel dialog = new JPanel();
        dialog.setLayout(null);
        dialog.addMouseListener(listener);
        dialog.addMouseMotionListener(listener);

        JPanel top = new JPanel();
        top.setBackground(Color.ORANGE);
        top.setLayout(null);
        top.setBounds(0, 0, 480, 60);

        dialog.setBounds(getWidth() / 2 - 240 - 200, getHeight() / 2 - 250, 480, 500);
        dialog.setBackground(Color.WHITE);


        JLabel exit = new JLabel("X");
        exit.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        exit.setBounds(430, 0, 60, 60);
        exit.setHorizontalTextPosition(SwingConstants.CENTER);
        exit.setVerticalTextPosition(SwingConstants.TOP);
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                container.remove(dialog);
            }
        });


        MySlider w_width = new MySlider(JSlider.HORIZONTAL, 0, 400, me.getWidth());
        MySlider w_height = new MySlider(JSlider.HORIZONTAL, 0, 400, me.getHeight());
        MySlider land_amount = new MySlider(JSlider.HORIZONTAL, 0, 100, me.getLandAmount());
        MySlider land_size = new MySlider(JSlider.HORIZONTAL, 0, 100, me.getLandSize());
        MySlider smoothing = new MySlider(JSlider.HORIZONTAL, 0, 10, me.getSmoothingFactor());

        int xOff = 190;

        w_width.setBounds(xOff, 10 + 70, 480 - xOff - 20, 50);
        w_height.setBounds(xOff, 10 + 60 + 70, 480 - xOff - 20, 50);
        land_amount.setBounds(xOff, 10 + 60 * 2 + 70, 480 - xOff - 20, 50);
        land_size.setBounds(xOff, 10 + 60 * 3 + 70, 480 - xOff - 20, 50);
        smoothing.setBounds(xOff, 10 + 60 * 4 + 70, 480 - xOff - 20, 50);

        w_width.setMajorTickSpacing(100);
        w_height.setMajorTickSpacing(100);
        land_amount.setMajorTickSpacing(25);
        land_size.setMajorTickSpacing(25);
        smoothing.setMajorTickSpacing(5);

        w_width.setMinorTickSpacing(20);
        w_height.setMinorTickSpacing(20);
        land_amount.setMinorTickSpacing(5);
        land_size.setMinorTickSpacing(5);
        smoothing.setMinorTickSpacing(1);

        dialog.add(w_width);
        dialog.add(w_height);
        dialog.add(land_amount);
        dialog.add(land_size);
        dialog.add(smoothing);

        JLabel[] labels = new JLabel[5];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel();
            labels[i].setBounds(22, 60 * i + 70, 150, 50);
            labels[i].setFont(new Font("Times New Roman", 0, 20));
            dialog.add(labels[i]);
        }
        labels[0].setText("World Width");
        labels[1].setText("World Height");
        labels[2].setText("Land Amount");
        labels[3].setText("Land Size");
        labels[4].setText("Smoothing");


        container.add(dialog, 0);
        dialog.add(top);
        top.add(exit);

        JButton generate = new JButton();
        generate.setText("GENERATE");
        generate.setBounds(20, 60 * 5 + 90, 440, 50);
        generate.setBackground(Color.ORANGE);

        dialog.add(generate);

        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              /*  container.remove(dialog);

                me.setWidth(w_width.getValue());
                me.setHeight(w_height.getValue());
                me.setLandAmount(land_amount.getValue());
                me.setLandSize(land_size.getValue());
                me.setSmoothingFactor(smoothing.getValue());

                if (population != null) {
                    for (int i = 0; i < population.size(); i++) {
                        JBot b = population.get(i);
                        b.kill();
                        population.remove(b);
                    }
                    population.clear();
                }
                map = null;

                containerPanel.removeAll();
                container.remove(containerPanel);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                containerPanel.setBounds(0, 0, me.getWidth() * me.getChunckSize(), me.getHeight()* me.getChunckSize());
                containerPanel.setLayout(null);

                containerPanel.addMouseListener(listener);
                containerPanel.addMouseMotionListener(listener);
                container.add(containerPanel);
                containerPanel.update(containerPanel.getGraphics());
                container.update(container.getGraphics());

                resizeMap();
                currenDateiname = null;
*/
              throw new RuntimeException("disabled for now");
            }

        });


    }



    /**
     * resizes the World to a given chunk size
     */
    private void resizeMap() {
        // TODO: 03.04.2019
    }


    @Override
    public void paint(Graphics g) {
        //add Bots if necessary
        if (mapLoaded && population.size() < me.getMinPopSize()) {
            JBot b = new JBot(this);
            population.add(b);
            containerPanel.add(b, 0);
            //random Location
            int ranX = (int) (Math.random() * me.getChunckSize()* me.getWidth());
            int ranY = (int) (Math.random() * me.getChunckSize()* me.getHeight());
            b.setLocation(ranX, ranY);

            // TODO: 03.04.2019 spawn nur auf land

            final JBot bf = b;
            bf.addMouseListener(listener);
        }


        //FPS control
        timeUntilSleep = System.currentTimeMillis();
        if (fpsCounter > FPS)
            fpsCounter = 0;
        else
            fpsCounter++;

        //moving things
        if (mapLoaded && !pause) {
            Component[] components = containerPanel.getComponents();
            if (components != null && components.length > 0) {
                for (Component c : components) {
                    if (c instanceof JChunk) {
                        ((JChunk) c).refresh();
                    }
                    if (c instanceof JBot) {
                        ((JBot)c).refresh();
                    }
                }
            }
        }
        super.paint(g);
        //sleep to match FPS
        long passedTime = System.currentTimeMillis() - timeUntilSleep;
        if (passedTime < 1000.0 / FPS) {
            try {
                Thread.sleep((long) (1000.0 / FPS - passedTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        repaint();
    }

    public World getHim() {
        return me;
    }
}
