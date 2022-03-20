package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry extends Intersectable {
    /**
     * return the normal vector from the shape
     * @param p point to get the normal from {@link Point}
     * @return normal vector {@link Vector}
     */
    public Vector getNormal(Point p);
}
