package renderer;

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
     * determine the color of the color of the point the ray hit
     * @param ray ray from the camera
     * @return color of the point
     */
    public abstract primitives.Color traceRay(Ray ray);
    public abstract primitives.Color traceRay(List<Ray> rays);


}