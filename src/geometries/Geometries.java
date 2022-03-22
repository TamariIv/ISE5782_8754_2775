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

//    public void remove(Intersectable... intersectables) {
//        _intersectablesList.removeAll(List.of(intersectables));
//    }

    @Override
    public List<Point> findIntersections(Ray r) {
        List<Point> points = null;
        for (var item : _intersectablesList) {
            List<Point> itemList = item.findIntersections(r);
            if (itemList != null) {
                if (points == null) {
                    points = new LinkedList<>();
                }
                points.addAll(itemList);
            }
        }
        return points;
    }
}
