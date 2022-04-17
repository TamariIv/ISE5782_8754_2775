package renderer;

import primitives.*;

import static primitives.Util.isZero;

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
        if(!isZero(vUp.dotProduct(vTo)))
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
        _distance = distance;
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

    /**
     * Constructing a ray through a pixel
     *
     * @param Nx
     * @param Ny
     * @param j
     * @param i
     * @return ray from the camera to Pixel[i,j]
     */
    public Ray constructRay(int Nx, int Ny, int j, int i) {
        //Image center
        Point Pc = _p0.add(_vTo.scale(_distance));

        //Ratio (pixel width & height)
        double Ry = _height/Ny;
        double Rx = _width/Nx;

        //Pixel[i,j] center
        Point Pij= Pc;

        //delta values for going to Pixel[i,j] from Pc

        double yI = -(i- (Ny-1)/2d)*Ry;
        double xJ = -(j- (Nx-1)/2d)*Rx;

        if(!isZero(xJ)) {
            Pij = Pij.add(_vRight.scale(xJ));
        }
        if(!isZero(yI)){
            Pij = Pij.add(_vUp.scale(yI));
        }
        return  new Ray(_p0,_p0.subtract(Pij));
    }
}
