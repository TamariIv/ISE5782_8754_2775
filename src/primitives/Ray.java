package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;

public class Ray {
    final Point p0;
    final Vector dir;

    private static final double DELTA = 0.1;

    /**
     * get the point of the ray
     *
     * @return point
     */
    public Point getP0() {
        return p0;
    }

    /**
     * get the direction of the ray
     *
     * @return direction vector
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Ray ctor
     *
     * @param p0  object of type Point
     * @param dir direction - object of type Vector
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

//        Ray lightRay = new Ray(gp.point, l.scale(-1), n);
    public Ray(Point head, Vector direction, Vector normal) {
        Vector delta = normal.scale(normal.dotProduct(direction) > 0 ? DELTA : -DELTA);
        this.dir=direction.normalize();
//        this.dir = direction.normalize();
//        double ndir = alignZero(normal.dotProduct(this.dir));
//        Vector delta = (ndir>0)? dir.scale(DELTA): dir.scale(-DELTA);
////        if (ndir > 0) {
//            delta = dir.scale(DELTA);
//        } else {
//            delta = dir.scale(-DELTA);
//        }
        this.p0 = head.add(delta);
    }

    public Point getPoint(double delta) {
        if (Util.isZero(delta)) {
            return p0;
        }
        return p0.add(dir.scale(delta));
    }

    /**
     * find the closest Point to Ray origin
     *
     * @param pointsList intersections point List
     * @return closest point
     */
    public Point findClosestPoint(List<Point> pointsList) {
        if (pointsList == null) {
            return null;
        }

        Point result = null;
        double closestDistance = Double.MAX_VALUE;

        for (Point p : pointsList) {
            double temp = p.distance(p0);
            if (temp < closestDistance) {
                closestDistance = temp;
                result = p;
            }
        }

        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray other = (Ray) o;
        return this.dir.equals(other.dir) && this.p0.equals(other.p0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    /**
     * override toString
     *
     * @return string representing the ray
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /**
     * @param intersections
     * @return The closest point to the began of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        double minDistance = Double.MAX_VALUE;
        double d;
        GeoPoint closePoint = null;

        if (intersections == null) {
            return null;
        }

        for (GeoPoint geoP : intersections) {

            d = geoP.point.distance(p0);
            //check if the distance of p is smaller then minDistance
            if (d < minDistance) {
                minDistance = d;
                closePoint = geoP;
            }
        }
        return closePoint;
    }
}
