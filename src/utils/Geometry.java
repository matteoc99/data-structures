package utils;

import java.awt.*;

public class Geometry {
    public static double getDistance(Point from, Point to) {
        return Math.sqrt(Math.pow(to.getX()-from.getX(),2)+ Math.pow(to.getY()-from.getY(),2));
    }
}
