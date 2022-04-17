package renderer;

import primitives.*;

/**
 * camera producing ray through a view plane
 */
public class Camera {

    private Vector _vTo;            // vector pointing towards the scene
    private Vector _vUp;            // vector pointing upwards
    private Vector _vRight;
    private  Point _p0;             // camera eye
    
    private double _distance;       // camera distance from view plane
    private double _width;          // view plane width
    private double _height;         // view plane height

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

        _vTo = vTo.normalize();
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
        _distance = distance
        return this;
    }

    /**
     * set view plane size
     * @param width physical width
     * @param height physical height
     * @return
     */
    public Camera setVPSize(double width, double height){
        _width = width;
        _height = height;
        return this;
    }

    public Ray constructRay(int Nx, int Ny, int j, int i) {
    }
}
