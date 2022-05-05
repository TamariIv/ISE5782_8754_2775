package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {

    List<Intersectable> _intersectablesList;

    public Geometries() {
        _intersectablesList = new LinkedList<>();
    }

    public Geometries(Intersectable... intersectables) {
        _intersectablesList = new LinkedList<>();
        Collections.addAll(_intersectablesList, intersectables);
    }

    public void add(Intersectable... intersectables) {
        Collections.addAll(_intersectablesList, intersectables);
    }


//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        List<Point> result = null;
//        for (Intersectable item : _intersectablesList) {
//            //get intersections points of a particular item from _intersectables
//            List<Point> itemPoints = item.findIntersections(ray);
//            if (itemPoints != null) {
//                //first time initialize result to new LinkedList
//                if (result == null) {
//                    result = new LinkedList<>();
//                }
//                //add all item points to the resulting list
//                result.addAll(itemPoints);
//            }
//        }
//        return result;
//    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        List<GeoPoint> intersections = null;

        for (Intersectable geometry : _intersectablesList) {

            // if there are elements in geoIntersections – add them to intersections
            List<GeoPoint> geoIntersections = geometry.findGeoIntersections(ray);

            if (geoIntersections != null) {

                if (intersections == null) {
                    intersections = new LinkedList<>();
                }

                intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }

}
