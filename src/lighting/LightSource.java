package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public interface LightSource {

    public Color getIntensity(Point p);

    /**
     * vector from light source to geometry
     * @param p
     * @return
     */
    public Vector getL(Point p);

    /**
     * distance from light source to point
     * @param point
     * @return
     */
    double getDistance(Point point);
}
