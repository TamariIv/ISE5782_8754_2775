package lighting;

import primitives.*;


/**
 * Ambient Light for all objects in 3D space
 */
public class AmbientLight extends Light {

    /**
     * Constructor
     * @param Ia light illumination
     * @param Ka light factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * default constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}