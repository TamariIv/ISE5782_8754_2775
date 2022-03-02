package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    final Point _p0;
    final Vector _normal;

    public Plane(Point p0, Vector normal) {
        _p0 = p0;
        _normal = normal;
    }

    public Plane(Point p1, Point p2, Point p3) {

    }

    public Point getP0() {
        return _p0;
    }

    public Vector getNormal() {
        return _normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }
}
