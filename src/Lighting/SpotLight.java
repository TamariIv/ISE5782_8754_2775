package Lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector dir;


    public SpotLight(Color intensity, Point position, Vector dir) {
        super(intensity, position);
        this.dir = dir.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double factor = Math.max(0, dir.dotProduct(getL(p)));
        return super.getIntensity(p).scale((factor));
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
