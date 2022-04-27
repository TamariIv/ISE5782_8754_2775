package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable {



    protected Color emission = Color.BLACK;
    private Material material =new Material();

    /**
     * get emission
     * @return emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * get material
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * set emission
     * @param emission set emission to param
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * return the normal vector from the shape
     * @param p point to get the normal from {@link Point}
     * @return normal vector {@link Vector}
     */
    public abstract Vector getNormal(Point p);
}
