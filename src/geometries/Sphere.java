package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry {
    Point p0;
    double radius;

    /**
     * sphere constructor
     * @param p0 center point parameter
     * @param radius radius if the sphere
     */
    public Sphere(Point p0, double radius) {
        this.p0 = p0;
        this.radius = radius;
    }

    /**
     * getter for p0
     * @return p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter for radius
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * override toString
     * @return string representing the sphere
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "p0=" + p0 +
                ", radius=" + radius +
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
}
