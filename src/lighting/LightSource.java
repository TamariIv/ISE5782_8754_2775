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

    double getDistance(Point point);
}
