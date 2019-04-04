package implementations.world.logic;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Matteo Cosi
 * @since 13.06.2017
 */
public class Chunk {

    public enum Type {
        LAND, WATER
    }

    /**
     * Food can go from 0 to 100.
     */
    private int food = 0;

    /**
     * Describes the Type of this Chunk
     */
    private Type type = Type.WATER;

    /**
     * describes the position of this Object in the map
     */
    private int i, j;

    /**
     * Tells weather the chunck should be updated
     */
    private boolean updated = true;

    private Color color = new Color(55, 140, 235);


    //caching
    private ArrayList<Chunk> neighbours = null;

    private World parent;

    /**
     * Construktor for the {@link Chunk}
     */
    Chunk(World parent, int i, int j) {
        this(parent, Type.WATER, i, j);
    }


    /**
     * Construktor for the {@link Chunk}
     */
    Chunk(World parent, Type type, int i, int j) {
        setType(type);
        this.i = i;
        this.j = j;
        this.parent = parent;
    }

    /**
     * sets the Type of a Chunk and automatically changes the Image
     *
     * @param type the type of the {@link Chunk}
     */
    public void setType(Type type) {
        if (type != null)
            this.type = type;
        if (type == Type.LAND)
            food = 5;
    }

    /**
     * returns the Type of this Chunk
     *
     * @return the {@link #type} of this Chunk
     */
    public Type getType() {
        return type;
    }

    /**
     * Setter for the food amount in this {@link Chunk}
     *
     * @param food {@link #food} to set
     */
    public void setFood(int food) {
        this.food = food;
    }

    /**
     * @return the amount of food in this square
     */
    public int getFood() {
        return food;
    }

    /**
     * @return if the Chunk should be updated or not
     */
    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    /**
     * updates only the food without the Grafics
     */
    public void updateFood(int newFood) {
        if (newFood - parent.getFoodDistribution() > food || isUpdated()) {
            int goal = newFood - parent.getFoodDistribution();
            if (type == Type.LAND) {
                food += Math.log10(goal - food) > 1 ? Math.log10(goal - food) : 1;
            }

        }
    }

    /**
     * updates the image in relation to the {@link #food}
     */
    public void update() {
        int newFood = getNewFood();
        if (newFood - parent.getFoodDistribution() > food) {
                setUpdated(true);
                updateFood(newFood);
                updateColor();
                if (type == Type.LAND)
                    System.out.println("update" + i + ":" + j + "-->" + food);
        }
    }

    /**
     * updates the color in reference to th fertility
     */
    private void updateColor() {
        if (type == Type.LAND) {
            color = new Color(97 - food / 2 + food / 4, 55 + food * 2 - food / 2, 26 - food / 4 + food / 8);
        }
    }

    /**
     * Calculates a new Fertility for this Object
     *
     * @return a new Ferility
     */
    public int getNewFood() {
        int ret = 0;
        for (Chunk c : getNeighbours()) {
            if (ret < c.food) {
                ret = c.food;
            }
            if (c.type == Type.WATER) {
                ret = 100;
                break;
            }
        }
        return ret;
    }

    /**
     * Returns an {@link ArrayList} with all the Neighbors
     * +
     * +#+
     * +
     * #...the Chunk
     * + the Neighbors
     *
     * @return returns the neighbours
     */
    public ArrayList<Chunk> getNeighbours() {
        if (neighbours != null)
            return neighbours;
        ArrayList<Chunk> ret = new ArrayList<>();
        try {
            if (i < parent.getHeight() - 1)
                ret.add(parent.map[i + 1][j]);
            if (j < parent.getWidth() - 1)
                ret.add(parent.map[i][j + 1]);
            if (i > 0)
                ret.add(parent.map[i - 1][j]);
            if (j > 0)
                ret.add(parent.map[i][j - 1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        neighbours = ret;
        return neighbours;
    }
}
