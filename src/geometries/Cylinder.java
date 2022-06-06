package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

public class Cylinder extends Tube {

    double height;
    Plane bottomCap;
    Plane topCap;

    /**
     * ctor of an object of cylinder
     *
     * @param axis   is the axis of the cylinder
     * @param radius is the radius of the cylinder
     * @param height is the height of the cylinder
     */
    public Cylinder(Ray axis, double radius, double height) {
        super(axis, radius);
        this.height= height;
    }

    /**
     * find the height of the cylinder
     *
     * @return height of the cylinder
     */
    public double getHeight() {

        return height;
    }


    /**
     * override toString
     *
     * @return string representing the cylinder
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                '}';
    }


    /**
     * normal vector for the cylinder
     *
     * @param p point on the surface
     * @return normal vector
     * <p>
     * Mathematical principle:
     * if dot product between the axis ray direction vector (v)
     * to a vector from the beginning axis ray to p (t) gives the cylinder height
     * or 0, it means that p is on one of the bases.
     * in other case we set o to p0+vt and the normal is p-o
     */
    @Override
    public Vector getNormal(Point p) {
        Point p0 = axis.getP0();
        Vector v = axis.getDir();

        Vector pSUBp0 = p.subtract(p0);
        double t = v.dotProduct(pSUBp0);
        if (t == 0) {
            return v.scale(-1);
        }
        if(t == height){
            return v;
        }
        Vector u = axis.getDir().scale(t);
        Point o = axis.getP0().add(u);
        return p.subtract(o).normalize();
    }


//    @Override
//    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
//        return null;
//    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        Point p0 = axis.getP0();
        Point p1 = axis.getPoint(height);
        List<GeoPoint> result = null;

        // Find the tube's intersections
        List<GeoPoint> tubePoints = super.findGeoIntersections(ray);
        if (tubePoints != null) {
            if (tubePoints.size() == 2) {
                // Checks if the intersection points are on the cylinder
                GeoPoint q0 = tubePoints.get(0);
                GeoPoint q1 = tubePoints.get(1);
                boolean q0Intersects = isBetweenCaps(q0.point);
                boolean q1Intersects = isBetweenCaps(q1.point);

                if (q0Intersects && q1Intersects) {
                    return tubePoints;
                }

                if (q0Intersects) {
                    result = new LinkedList<>();
                    result.add(q0);
                } else if (q1Intersects) {
                    result = new LinkedList<>();
                    result.add(q1);
                }
            }

            if (tubePoints.size() == 1) {
                // Checks if the intersection point is on the cylinder
                GeoPoint q = tubePoints.get(0);
                if (isBetweenCaps(q.point)) {
                    result = new LinkedList<>();
                    result.add(q);
                }
            }
        }

        // Finds the bottom cap's intersections
        List<GeoPoint> cap0Point = bottomCap.findGeoIntersections(ray);
        if (cap0Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap0Point.get(0);
            if (gp.point.distanceSquared(p0) < radius * radius) {
                if (result == null) {
                    result = new LinkedList<>();
                }

                result.add(gp);
                if (result.size() == 2) {
                    return result;
                }
            }
        }

        // Finds the top cap's intersections
        List<GeoPoint> cap1Point = topCap.findGeoIntersections(ray);
        if (cap1Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap1Point.get(0);
            if (gp.point.distanceSquared(p1) < radius * radius) {
                if (result == null) {
                    return List.of(gp);
                }

                result.add(gp);
            }
        }

        return result;
    }

    /**
     * Helper function that checks if a points is between the two caps (not on them, even on the edge)
     * @param p The point that will be checked.
     * @return True if it is between the caps. Otherwise, false.
     */
    private boolean isBetweenCaps(Point p) {
        Vector v0 = axis.getDir();
        Point p0 =  axis.getP0();
        Point p1 =  axis.getPoint(height);

        // Checks against zero vector...
        if (p.equals(p0) || p.equals(p1)) {
            return false;
        }

        return v0.dotProduct(p.subtract(p0)) > 0 &&
                v0.dotProduct(p.subtract(p1)) < 0;
    }
}


