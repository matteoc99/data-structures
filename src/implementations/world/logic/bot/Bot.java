package implementations.world.logic.bot;

import data_structures.nn.network.Network;
import implementations.world.logic.World;
import utils.Converter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Bot extends Positionable {

    private int age;
    private ArrayList<Sensor> sensors;
    /**
     * combination of these waste more energy
     * 1-100
     */
    private int speed;
    private int strength;
    /**
     * more weight more energy can be stored
     * 10-100
     */
    private int weight;
    /**
     * strength of shell. 0 for no shell
     */
    private int shell;
    private int sensorCount = 0;

    private int health;
    private int maxHealth;
    private int goodHealth;

    /**
     * heritage
     */
    private int red;
    private int green;
    private int blue;

    private int generation;

    private World parent;

    /**
     * [0]->isLandUnderBody [0 oder 1]
     * [1]->hp [-1-1] -1 wenn wenig hp
     * [3]->food under body [0-1]
     * [4]->enemy under body [0-1]
     * [5]->food under sensor1 [0-1]
     * [6]->isLandUnderSensor1 [0 oder 1]
     * [7]->isEnemyUnderSensor1[0 oder 1]
     * ...
     * Outputs:
     * [0]-walk
     * [1]->make love
     * [2]->eat
     * [3]->dx
     * [4]->dy
     * [5]->attackmsensor1
     * [6]->move right sensor1
     * [7]->move left sensor1
     * * ...
     */
    private Network neuralNetwork;
    private boolean isDead = false;
    private double dx;
    private double dy;

    public Bot(World parent, double x, double y) {
        super(x, y);
        this.sensors = new ArrayList<>();
        this.parent = parent;
        randInit();
    }

    private void randInit() {
        speed = (int) (Math.random() * 100) + 1;
        strength = (int) (Math.random() * 98) + 2;
        weight = (int) (Math.random() * 90) + 10;
        shell = (int) (Math.random() * 2);

        int rand = (int) (Math.random() * 10000) + 10;
        if (rand > 7000) {
            sensorCount++;
        }
        if (rand > 9000) {
            sensorCount++;
        }
        if (rand > 9800) {
            sensorCount++;
        }
        if (rand > 10000) {
            sensorCount++;
        }

        createSensor();
        maxHealth = weight * 3000;
        health = goodHealth = maxHealth / 2;

        red = (int) (Math.random() * 255);
        green = (int) (Math.random() * 255);
        blue = (int) (Math.random() * 255);

        generation = 0;

        neuralNetwork = Network.createDFF(4 + 3 * sensorCount, 3 + 3 * sensorCount, (int) (Math.random() * 2), (int) (Math.random() * 15 + 8));
    }

    public Bot(String dna1, String dna2) {
        super(0, 0);
        createFromDna(dna1, dna2);
    }

    private void createFromDna(String dna1, String dna2) {
        // TODO: 02.04.2019
    }

    public String getDna() {
        return "";
    }

    private void createSensor() {
        for (int i = 0; i < sensorCount; i++) {
            Sensor s = new Sensor(this);
            sensors.add(s);
        }
    }


    public void action() {
        if (isDead)
            return;

        calcNewHealth();

        ArrayList<java.lang.Double> data = new ArrayList<>();
        data.add(getIsLandUnderBody());
        data.add(getHp());
        data.add(getFoodUnderBody());
        data.add(getIsEnemyUnderBody());

        for (int i = 0; i < sensorCount; i++) {
            Sensor sensor = sensors.get(i);
            data.add(sensor.getFoodUnder());
            data.add(sensor.getIsLandUnder());
            data.add(sensor.getIsEnemyUnder());
        }

        double[] ret = neuralNetwork.processData(Converter.toDoubleArray(data));

        takeAction(ret);
    }

    private void calcNewHealth() {
        int minimizer=1;
        if (getIsLandUnderBody() == 0) {
            minimizer=3;
        }
        health-=minimizer*((speed*weight*strength)/1000);
    }

    private void takeAction(double[] ret) {
        double move = ret[0];
        double love = ret[1];
        double eat = ret[2];
        if (move > 0)
            move(move);
        if (love > 0)
            love();
        if (eat > 0)
            eat();
        this.dx = ret[3];
        this.dy = ret[4];
        for (int i = 0; i < sensorCount; i++) {
            Sensor sensor = sensors.get(i);
            double attack = ret[5 + i * 3];
            double left = ret[5 + i * 3 + 1];
            double right = ret[5 + i * 3 + 1];
            if (attack > 0)
                sensor.attack();
            if (left > 0)
                sensor.left();
            if (right > 0)
                sensor.right();
        }
    }

    public void eat() {
        // TODO: 03.04.2019
    }

    private void love() {
        // TODO: 03.04.2019
    }

    private void move(double move) {
        translate((dx * move * speed) / (weight > strength ? weight - strength : 1), (dy * move * speed) / (weight > strength ? weight - strength : 1));
    }

    public void kill() {
        parent.killBot(this);
        isDead = true;
    }

    private double getIsLandUnderBody() {
        return 0;
    }

    private double getFoodUnderBody() {
        return 0;
    }

    private double getHp() {
        return 0;
    }

    private double getIsEnemyUnderBody() {
        return 0;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getShell() {
        return shell;
    }

    public void setShell(int shell) {
        this.shell = shell;
    }

    public int getSensorCount() {
        return sensorCount;
    }

    public void setSensorCount(int sensorCount) {
        this.sensorCount = sensorCount;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public Network getNeuralNetwork() {
        return neuralNetwork;
    }

    public World getParent() {
        return parent;
    }

    public void setParent(World parent) {
        this.parent = parent;
    }

}
