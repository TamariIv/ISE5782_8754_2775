package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    /**
     *  direction of the light
     */
    private Vector dir;

    /**
     * initialize directional light with color and direction vector
     * @param intensity color of the light
     * @param dir direction of the light
     */
    public DirectionalLight(Color intensity, Vector dir) {
        super(intensity);
        this.dir = dir.normalize();
    }

    /**
     * get color of the light in a specific point
     * @param p point p
     * @return light intensity in point p
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    /**
     * get light direction
     * @param p
     * @return light direction vector
     */
    @Override
    public Vector getL(Point p) {
        return dir;
    }

    /**
     * distance between the light and point
     * @param point to calculate distance from point to light
     * @return distance
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}

