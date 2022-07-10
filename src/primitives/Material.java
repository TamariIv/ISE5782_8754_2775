package primitives;

public class Material {

    // Field represents the density attenuation factor
    public Double3 kD = new Double3(0);
    public Double3 kS = new Double3(0);

    // Field represents the transparency attenuation factor
    public Double3 kT = new Double3(0.0);

    // Field represents the reflectivity attenuation factor
    public Double3 kR = new Double3(0.0);

    // Field represents the Glossy attenuation factor
    public double kG = 1;

    // Field represents the shininess of the material
    public int nShininess = 0;


    /**
     * Builder patterns setter for field kD
     * @param kD parameter for kD
     * @return Material object
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Builder patterns setter for field kD
     * @param kD parameter for kD constructor
     * @return Material object
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Builder patterns setter for field kS
     * @param kS parameter for kS
     * @return Material object
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Builder patterns setter for field kS
     * @param ks parameter for kS constructor
     * @return Material object
     */
    public Material setKs(double ks) {
        this.kS = new Double3(ks);
        return this;
    }


    /**
     * Builder patterns setter for field kT
     * @param kT parameter for kT constructor
     * @return Material object
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Builder patterns setter for field kR
     * @param kR parameter for kT constructor
     * @return Material object
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }


    /**
     * Builder patterns setter for field nShininess
     * @param nShininess parameter for nShininess
     * @return Material object
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


    /**
     * Chaining method for setting the glossiness of the material.
     * @param kG the glossiness to set, value in range [0,1]
     * @return the current material
     */
    public Material setkG(double kG) {
        this.kG = Math.pow(kG, 0.5);
        return this;
    }

    public double getkG() {
        return kG;
    }
}
