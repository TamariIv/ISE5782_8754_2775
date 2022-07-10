package renderer;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * abstract class to trace a ray and calculate the color of the point the ray hits
 *
 */
public abstract class RayTracerBase {
    /**
     * scene to be colored
     */
    protected Scene scene;

    /**
     * RayTracerBase constructor
     * @param scene instance of Scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * An abstract function that get a ray and return the color of the point that cross the ray
     * @param ray ray that intersect the scene
     * @return Color
     */
    public abstract Color traceRay(Ray ray);

    /**
     * find the closest Geo point intersection to the ray
     * @param ray is the ray from the viewer
     * @return the closest intersection to the ray
     */
    public abstract GeoPoint findClosestIntersection(Ray ray);

    /**
     * make object color as point color
     * @param geoPoint is the point we need to find the color of
     * @param ray is a received ray to calculate intersections with it and the scene
     * @return the color of the point received
     */
    public abstract Color calcColor(GeoPoint geoPoint, Ray ray);

    /**
     * An abstract function that get a list of ray and return the color of the avarege points that cross the rays
     * @param rays  that intersect the scene
     * @return Color
     */
    public abstract Color traceRay(List<Ray> rays);

    public abstract Color calcColorForSupersampling(List<Ray> rays);

}