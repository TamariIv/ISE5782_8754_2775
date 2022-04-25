package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane implements Geometry {
    final Point _p0;
    final Vector _normal;

    /**
     * plane constructor
     * @param p0     parameter for p0
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
        Vector U = (Vector) p2.subtract(p1);
        Vector V = (Vector) p3.subtract(p1);
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

    @Override
    public List<Point> findIntersections(Ray ray) {
//        Point p0 = r.getP0();
//        Vector v = r.getDir();
//        Vector n = _normal;
//
//        // denominator
//        double nv = n.dotProduct(v);
//        if (isZero(nv)) {
//            return null;
//        }
//
//        Vector p0_q = p0.subtract(_p0);
//        double t = alignZero(n.dotProduct(p0_q)/nv);
//        // if t < 0 the ray is in the opposite direction
//        // if t == 0 the ray is
//        if(t>0){
//            Point p = p0.add(v.scale(t));
//            return List.of(p);
//        }
//
//        return null;

        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        Vector n = _normal;

        if(_p0.equals(P0)){
            return  null;
        }

        Vector P0_Q0 = _p0.subtract(P0);

        //numerator
        double nP0Q0  = alignZero(n.dotProduct(P0_Q0));

        //
        if (isZero(nP0Q0 )){
            return null;
        }

        //denominator
        double nv = alignZero(n.dotProduct(v));

        // ray is lying in the plane axis
        if(isZero(nv)){
            return null;
        }

        double  t = alignZero(nP0Q0  / nv);

        if (t <=0){
            return  null;
        }

        Point point = ray.getPoint(t);

        return List.of(point);
    }
}
