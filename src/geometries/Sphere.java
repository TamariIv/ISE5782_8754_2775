package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry {
    Point p0;
    double radius;

    public Sphere(Point p0, double radius) {
        this.p0 = p0;
        this.radius = radius;
    }

    public Point getP0() {
        return p0;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "p0=" + p0 +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
