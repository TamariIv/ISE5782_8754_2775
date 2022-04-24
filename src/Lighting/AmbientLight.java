package lighting;

import primitives.*;


/**
 * Ambient Light for all objects in 3D space
 */
public class AmbientLight {

    private final Color intensity;      // intensity of ambient light

    /**
     * Constructor
     * @param Ia light illumination
     * @param Ka light factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {

        intensity = Ia.scale(Ka);
    }

    /**
     * default constructor
     */
    public AmbientLight() {

        intensity = Color.BLACK;
    }

    /**
     * getter for intensity
     * @return intensity
     */
    public Color getIntensity() {

        return intensity;
    }
}