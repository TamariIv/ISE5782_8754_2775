package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Cylinder implements Geometry {

    double height;

    /**
     * cylinder constructor
     * @param height parameter for height
     */
    public Cylinder(double height) {
        this.height = height;
    }

    /** getter for height
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * override toString
     * @return string representing the cylinder
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                '}';
    }

    /**
     * implement interface Geometry function
     * @param p the point from which we want the normal
     * @return the perpendicular vector to the point that was received
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    @Override
    public List<Point> findIntersections(Ray r) {
        return null;
    }
}
