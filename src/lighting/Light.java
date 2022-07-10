package lighting;

import primitives.Color;

abstract class Light {

    /**
     * color of the ligth
     */
    private Color intensity;

    /**
     * initialize light intensity
     * @param intensity light color
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * get light color
     * @return light color
     */
    public Color getIntensity() {
        return intensity;
    }
}
