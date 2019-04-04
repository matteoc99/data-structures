package implementations.world.logic.bot;

import java.awt.*;
import java.beans.Transient;
import java.util.Objects;

public class Positionable {
    private double x;
    private double y;

    public Positionable(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLocation(double x, double y) {
        this.x=x;
        this.y=y;
    }

    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Positionable that = (Positionable) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Positionable{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
