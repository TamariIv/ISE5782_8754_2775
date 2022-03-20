package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    final Point _p0;
    final Vector _normal;

    /**
     * plane constructor
     * @param p0 parameter for p0
     * @param normal parameter for normal
     */
    public Plane(Point p0, Vector normal) {
        _p0 = p0;
        _normal = normal.normalize();
    }

    /**
     * plane constructor
     * @param p1 point 1 to calculate plane
     * @param p2 point 2 to calculate plane
     * @param p3 point 3 to calculate plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        _p0 = p1;
        Vector U = (Vector)p2.subtract(p1);
        Vector V = (Vector)p3.subtract(p1);
        Vector N = U.crossProduct(V);
        _normal = N.normalize();
    }

    /**
     * getter for p0
     * @return p0
     */
    public Point getP0() {
        return _p0;
    }

    /**
     * getter for the normal
     * @return the normal
     */
    public Vector getNormal() {
        return _normal;
    }

    /**
     * implement interface Geometry function
     * @param p the point from which we want the normal
     * @return the perpendicular vector to the point that was received
     */
    @Override
    public Vector getNormal(Point p) {
        return getNormal();
    }
}
