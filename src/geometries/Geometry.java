package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable {



    protected Color emission = Color.BLACK;

    /**
     * get emission
     * @return emission
     */
    public Color getEmission() {
        return emission;
    }
    /**
     * set emission
     * @param emission set emission to param
     */
    public void setEmission(Color emission) {
        this.emission = emission;
    }

    /**
     * return the normal vector from the shape
     * @param p point to get the normal from {@link Point}
     * @return normal vector {@link Vector}
     */
    public abstract Vector getNormal(Point p);
}
