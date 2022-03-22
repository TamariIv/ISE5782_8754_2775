package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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
        Vector N = p.subtract(p0);
        return N.normalize();
    }

    @Override
    public List<Point> findIntersections(Ray r) {

        Point P0 = r.getP0();
        Vector v = r.getDir();

        if (P0.equals(p0)) {
            return List.of(p0.add(v.scale(radius)));
        }

        Vector U = p0.subtract(P0);

        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));

        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

////////////לבדוק מכאן/////////////////////////////
        if (t1 > 0 && t2 > 0) {
//            Point3D P1 = P0.add(v.scale(t1));
//            Point3D P2 = P0.add(v.scale(t2));
            Point P1 =r.getPoint(t1);
            Point P2 =r.getPoint(t2);
            return List.of(P1, P2);
        }
        if (t1 > 0) {
//            Point3D P1 = P0.add(v.scale(t1));
            Point P1 = r.getPoint(t1);
            return List.of(P1);
        }
        if (t2 > 0) {
//            Point3D P2 = P0.add(v.scale(t2));
            Point P2 = r.getPoint(t2);
            return List.of(P2);
        }
        return null;
    }
}
