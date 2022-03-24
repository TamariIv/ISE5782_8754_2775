package geometries;
import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube implements Geometry {
    Ray axis;
    double radius;

    /**
     * tube constructor
     * @param axis parameter for axis
     * @param radius parameter for radius
     */
    public Tube(Ray axis, double radius) {
        this.axis = axis;
        this.radius = radius;
    }

    /**
     * axis getter
     * @return the axis
     */
    public Ray getAxis() {
        return axis;
    }

    /**
     * radius getter
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * override toString
     * @return string representing the tube
     */
    @Override
    public String toString() {
        return "Tube{" +
                "axis=" + axis +
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
        Vector v = p.subtract(axis.getP0());
        double t = alignZero(axis.getDir().dotProduct(v));
        Point o = axis.getP0().add(axis.getDir().scale(t));
        Vector N = p.subtract(o);
        return N.normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
