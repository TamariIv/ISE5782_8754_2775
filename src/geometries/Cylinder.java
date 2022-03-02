package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder implements Geometry {

    double height;

    public Cylinder(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                '}';
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
