package renderer;

import primitives.*;
import scene.Scene;

/**
 * abstract class to trace a ray and calculate the color of the point the ray hits
 *
 */
public abstract class RayTracerBase {
    /**
     * scene to be colored
     */
    protected Scene _scene;

    /**
     * RayTracerBase constructor
     * @param scene instance of Scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * determine the color of the color of the point the ray hit
     * @param ray ray from the camera
     * @return color of the point
     */
    public abstract Color traceRay(Ray ray);

}