package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry {
    Ray axis;
    double radius;

    public Tube(Ray axis, double radius) {
        this.axis = axis;
        this.radius = radius;
    }

    public Ray getAxis() {
        return axis;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axis=" + axis +
                ", radius=" + radius +
                '}';
    }
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
