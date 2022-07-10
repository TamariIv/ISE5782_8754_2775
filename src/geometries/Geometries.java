package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * interface of composite- contains a list of geometries that are intersectable.
 * @author Tamari Ivgi and Hodaya Ashkenazi
 * the idea of this class is to use the design pattern of
 * composite by taking a sum of shapes and calculating in one function
 * the intersections between them without the need to split the actions
 */

public class Geometries extends Intersectable {

    List<Intersectable> _intersectablesList;

    public Geometries() {
        _intersectablesList = new LinkedList<>();
    }

    /**
     * copy-ctor. copies the given array of geometries.
     * @param intersectables
     */
    public Geometries(Intersectable... intersectables) {
        _intersectablesList = new LinkedList<>();
        Collections.addAll(_intersectablesList, intersectables);
    }

    /**
     * Adds new geometries to the list
     * @param intersectables
     */
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {

        List<GeoPoint> intersections = null;

        for (Intersectable geometry : _intersectablesList) {

            // if there are elements in geoIntersections – add them to intersections
            List<GeoPoint> geoIntersections = geometry.findGeoIntersections(ray,maxDistance);

            if (geoIntersections != null) {

                if (intersections == null) {
                    intersections = new LinkedList<>();
                }

                intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }
}
