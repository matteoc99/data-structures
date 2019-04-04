package implementations.world.logic;

import data_structures.nn.network_gui.NetworkGUI;
import data_structures.nn.network_gui.NetworkPanel;
import implementations.world.logic.bot.Bot;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class World implements Runnable{


    /**
     * describes the width and height of a World
     */
    private  int width = 100;
    private  int height= 75;
    /**
     * from 1-1000 describes the amount of land in the World.
     * 1000 is the Maximum
     */
    private  int landAmount = 30;
    /**
     * The bigger the value the smaller the islands are
     */
    private  int landSize= 6;
    /**
     * Describes the size of the World
     */
    private  int chunckSize= 20;

    /**
     * Describes the distribution of the food
     */
    private  int foodDistribution= 5;


    /**
     * Describes the speed of the Food regrowth
     * smaller value = faster
     */
    private  int foodRegrowth= 10;


    /**
     * Describes how smooth the Islands are
     * 0: smoothing off
     * 1: soft smoothing
     * 1<: strong smoothing
     * better <10
     */
    private int smoothingFactor= 2;

    /**
     * The Map of {@link Chunk}s
     */
    public Chunk[][] map = null;

    /**
     * tells if the Map has finished loading
     */
    private boolean mapLoaded = false;

    /**
     * describes the min Population size, if the value drops below a new {@link Bot} is created
     */
    private int minPopSize = 4;

    /**
     * Contains the Population
     */
    private  ArrayList<Bot> population = new ArrayList<>();

    private NetworkGUI networkGUI;

    private  boolean pause = false;

    private  String currenDateiname = null;

    private boolean kill;


    //FPS control
    private int SPEED = 3;
    private long timeUntilSleep;
    private int fpsCounter;



    public World(String file) {
        //TODO load from file
    }

    public World(int width,int height,int chunkSize) {
//        networkGUI = new NetworkGUI();

        createMap(width,height,chunkSize);
    }

    public World(){
        this(100, 75,25);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLandAmount() {
        return landAmount;
    }

    public void setLandAmount(int landAmount) {
        this.landAmount = landAmount;
    }

    public int getLandSize() {
        return landSize;
    }

    public void setLandSize(int landSize) {
        this.landSize = landSize;
    }

    public int getChunckSize() {
        return chunckSize;
    }

    public void setChunckSize(int chunckSize) {
        this.chunckSize = chunckSize;
    }

    public int getFoodDistribution() {
        return foodDistribution;
    }

    public void setFoodDistribution(int foodDistribution) {
        this.foodDistribution = foodDistribution;
    }

    public int getFoodRegrowth() {
        return foodRegrowth;
    }

    public void setFoodRegrowth(int foodRegrowth) {
        this.foodRegrowth = foodRegrowth;
    }

    public int getSmoothingFactor() {
        return smoothingFactor;
    }

    public void setSmoothingFactor(int smoothingFactor) {
        this.smoothingFactor = smoothingFactor;
    }

    public boolean isMapLoaded() {
        return mapLoaded;
    }

    public void setMapLoaded(boolean mapLoaded) {
        this.mapLoaded = mapLoaded;
    }

    public int getMinPopSize() {
        return minPopSize;
    }

    public void setMinPopSize(int minPopSize) {
        this.minPopSize = minPopSize;
    }

    public ArrayList<Bot> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Bot> population) {
        this.population = population;
    }

    public NetworkGUI getNetworkGUI() {
        return networkGUI;
    }

    public void setNetworkGUI(NetworkGUI networkGUI) {
        this.networkGUI = networkGUI;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public String getCurrenDateiname() {
        return currenDateiname;
    }

    public void setCurrenDateiname(String currenDateiname) {
        this.currenDateiname = currenDateiname;
    }

    public boolean isKill() {
        return kill;
    }

    public void setKill(boolean kill) {
        this.kill = kill;
    }

    /**
     * Method that creates a new Map
     */
    private void createMap(int width,int height,int chunkSize) {
        setWidth(width);
        setHeight(height);
        setChunckSize(chunkSize);

        mapLoaded = false;
        population.clear();
        map = new Chunk[getHeight()][getWidth()];
        generateMap();

        if (getSmoothingFactor()== 1) {
            uniformieze();
        } else {
            for (int i = 0; i < getSmoothingFactor()- 1; i++) {
                uniformieze();
            }
        }
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Chunk c = map[i][j];
                c.update();
            }
        }
        mapLoaded = true;
    }

    /**
     * method used to generate the Map
     */
    private void generateMap() {
        ArrayList<Island> islands = new ArrayList<>();

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Chunk c = new Chunk(this,i, j);
                map[i][j] = c;
                if (getLandAmount()> (Math.random() * 1000)) {
                    islands.add(new Island(getLandSize(), i, j));
                }
            }
        }

        for (Island island : islands) {
            for (int i = 0; i < island.island.length; i++) {
                for (int j = 0; j < island.island[i].length; j++) {
                    if (island.island[i][j] == 1) {
                        int x = i + island.y;
                        int y = j + island.x;
                        if (x < getWidth() && y < getHeight()&& x >= 0 && y >= 0) {
                            Chunk c = map[y][x];
                            c.setType(Chunk.Type.LAND);
                        }
                    }
                }
            }
        }
    }

    /**
     * make uniform
     * if Water chunk has more Land arround than Water, transform to land.
     */
    public void uniformieze() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                //Count water vs land Neighbors
                Chunk c = map[i][j];
                int waterCount = 0;
                int landCount = 0;
                for (Chunk n : c.getNeighbours()) {
                    if (n.getType() == Chunk.Type.LAND)
                        landCount++;
                    else
                        waterCount++;
                }
                //valuate the  water vs land count
                if (c.getType() == Chunk.Type.LAND) {
                    if (getSmoothingFactor() != 1 ? landCount < waterCount : landCount == 0) {
                        c.setType(Chunk.Type.WATER);
                    }
                } else {
                    if (getSmoothingFactor()!= 1 ? landCount > waterCount : waterCount == 0) {
                        c.setType(Chunk.Type.LAND);
                    }
                }
                c.update();
            }
        }
    }


    /**
     * Load a previous World
     */
    public static World loadFrom(String file) {
       return new World(file);
    }

    /**
     * Save the current state of the World
     */
    public void save() {

        File theDir = new File("C:\\EvoBots");
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
                System.out.println("ERROR SAVE");
            }

        }
        //// TODO: 02.04.2019 save to file
    }
    @Override
    public void run() {
        while (!kill){
            compute();
        }
    }

    private void printWorld() {
        for (Chunk[] chunks : map) {
            for (Chunk chunk : chunks) {
                if(chunk.getType()== Chunk.Type.LAND){
                    System.out.print("L");
                }else{
                    System.out.print("W");
                }
            }
            System.out.println();
        }}

    private void compute() {
        timeUntilSleep = System.currentTimeMillis();

        if (population.size() < getMinPopSize())
            population.add(new Bot(this,(int)(Math.random()*getWidth()*getChunckSize()),(int)(Math.random()*getHeight()*getChunckSize())));

        for (Bot bot : population) {
            bot.action();
        }
        for (Chunk[] chunks : map) {
            for (Chunk chunk : chunks) {
                chunk.update();
            }
        }

        long passedTime = System.currentTimeMillis() - timeUntilSleep;
        if (passedTime < 1000.0 / SPEED) {
            try {
                Thread.sleep((long) (1000.0 / SPEED - passedTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void killBot(Bot bot) {
        population.remove(bot);
    }
}
