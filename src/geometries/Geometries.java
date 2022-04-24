package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

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


    @Override
    public List<Point> findIntersections(Ray r) {
        List<Point> result = null;
        for (Intersectable item : _intersectablesList) {
            //get intersections points of a particular item from _intersectables
            List<Point> itempoints = item.findIntersections(r);
            if (itempoints != null) {
                //first time initialize result to new LinkedList
                if (result == null) {
                    result = new LinkedList<>();
                }
                //add all item points to the resulting list
                result.addAll(itempoints);
            }
        }
        return result;
    }
}
