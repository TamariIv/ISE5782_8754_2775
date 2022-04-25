package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 *  RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructor
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = _scene.geometries.findIntersections(ray);
        if (intersections != null) {
            Point closestPoint = ray.findClosestPoint(intersections);
            return calcColor(closestPoint);
        }
        //ray did not intersect any geometrical object
        return _scene.background;
    }

    private Color calcColor(Point point) {
        return _scene.ambientLight.getIntensity();
    }
}