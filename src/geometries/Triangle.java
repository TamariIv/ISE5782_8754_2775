package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Triangle extends Polygon {

    /**
     * triangle constructor
     *
     * @param p1 vertex 1 of the triangle
     * @param p2 vertex 2 of the triangle
     * @param p3 vertex 3 of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

//    @Override
//    public List<Point> findIntersections(Ray r) {
//        List<Point> points = plane.findIntersections(r);
//        if (points == null)
//            return null;
//
//        Point p0 = r.getP0();
//        Vector v = r.getDir();
//
//        Point p1 = vertices.get(0);
//        Point p2 = vertices.get(1);
//        Point p3 = vertices.get(2);
//
//        Vector v1 = p1.subtract(p0); // p0 -> p1
//        Vector v2= p2.subtract(p0);  // p0 -> p2
//        Vector v3= p3.subtract(p0);  // p0 -> p3
//
//        Vector n1 = v1.crossProduct(v2);
//        Vector n2 = v2.crossProduct(v3);
//        Vector n3 = v3.crossProduct(v1);
//
//        double s1 = v.dotProduct(n1);
//        double s2 = v.dotProduct(n2);
//        double s3 = v.dotProduct(n3);
//
//        if((s1>0 && s2>0 && s3>0) ||(s1<0 && s2<0 && s3<0))
//            return points;
//
//        return null;
//    }

//    @Override
//    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
//        return super.findGeoIntersections(ray);
//    }
}
