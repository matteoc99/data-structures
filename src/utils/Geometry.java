package utils;

import java.awt.*;

public class Geometry {
    public static double getDistance(Point from, Point to) {
        return Math.sqrt(Math.pow(to.x-from.x,2)+ Math.pow(to.y-from.y,2));
    }
}
