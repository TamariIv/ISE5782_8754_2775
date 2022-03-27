package renderer;

import primitives.*;

/**
 * camera producing ray through a view plane
 */
public class Camera {

    private Vector _vTo;      // vector pointing towards the scene
    private Vector _vUp;      // vector pointing upwards
    private Vector _vRight;
    private  Point _p0;             // camera eye

    /**
     *
     * @param p0 origin point in 3D space
     * @param vUp
     * @param vTo
     */
    public Camera(Point p0, Vector vUp, Vector vTo) {
        if(!Util.isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("vTo and vUp should be orthogonal");
        _p0 = p0;

        camera_vTo = vTo.normalize();
        _vUp = vUp.normalize();

        _vRight = _vTo.crossProduct(vUp);
    }

    // chaining methods

    /**
     * set distance between the camera and its view plane
     * @param distance the distnace for the view plane alligator
     * @return instance of camera for chaining
     */
    public Camera setVPDistance(double distance){
        this.

    }
}
