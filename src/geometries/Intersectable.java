package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * common interface for all graphic object that intersect with a ray {@link Ray}
 */
public abstract class Intersectable {
    /**
     * find all intersection points from the array
     *
     * @param ray ray pointing towards graphic object
     * @return immutable list of intersection points {@link Point}
     */
    public final List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null? null: geoList.stream().map(gp -> gp.point).toList();
    }

    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructor for initialize point and geometry
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

    }

    public List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
