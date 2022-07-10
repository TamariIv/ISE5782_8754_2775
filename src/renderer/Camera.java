package renderer;

import primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.isZero;


/**
 * camera producing ray through a view plane
 */
public class Camera {


    private Vector _vTo;            // vector pointing towards the scene
    private Vector _vUp;            // vector pointing upwards
    private Vector _vRight;
    private Point _p0;             // camera eye

    private double _distance;       // camera distance from view plane
    private double _width;          // view plane width
    private double _height;         // view plane height
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int numOfRays;

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public Point getP0() {
        return _p0;
    }

    public double getDistance() {
        return _distance;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }


    /**
     * @param p0  origin point in 3D space
     * @param vUp
     * @param vTo
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("vTo and vUp should be orthogonal");
        _p0 = p0;

        _vTo = vTo.normalize();
        _vUp = vUp.normalize();

        _vRight = _vTo.crossProduct(vUp);
    }

    // chaining methods

    public Camera setP0(Point p0) {
        _p0 = p0;
        return this;
    }

    /**
     * set distance between the camera and its view plane
     *
     * @param distance the distnace for the view plane alligator
     * @return instance of camera for chaining
     */
    public Camera setVPDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Illegal value of distance");
        }
        _distance = distance;
        return this;
    }

    /**
     * set view plane size
     *
     * @param width  physical width
     * @param height physical height
     * @return
     */
    public Camera setVPSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracerBase(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * setter for number of rays
     * @param numOfRays
     * @return this class-a builder pattern
     */
    public Camera setNumOfRays(int numOfRays)
    {
        if(numOfRays <= 0)
            this.numOfRays=1;
        else
            this.numOfRays = numOfRays;
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

        // Image center ---> Pc = p0 + distance * vTo
        Point Pc = _p0.add(_vTo.scale(_distance));

        // Ratio (pixel height & width)
        double Ry = (double) _height / Ny;
        double Rx = (double) _width / Nx;

        Point Pij = Pc;
        double yI = -(i - (Ny - 1) / 2d) * Ry;
        double xJ = (j - (Nx - 1) / 2d) * Rx;

        // movement to middle of pixel ij
        if (!isZero(xJ)) {
            Pij = Pij.add(_vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            Pij = Pij.add(_vUp.scale(yI));
        }
        //return ray from camera to view plane ij coordinates
        return new Ray(_p0, Pij.subtract(_p0));
    }

    public Camera renderImage() throws MissingResourceException, IllegalArgumentException {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }
            if (_p0 == null){
                throw new MissingResourceException("missing resource", Point.class.getName(), "");
            }
            if (_vUp == null || _vRight == null || _vTo == null){
                throw new MissingResourceException("missing resource", Vector.class.getName(), "");
            }

            //rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    if(numOfRays == 1 || numOfRays == 0)
                    {
                        primitives.Color rayColor = castRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
                        imageWriter.writePixel(j, i, rayColor);
                    }
                    else
                    {
                        List<Ray> rays = constructBeamThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i,numOfRays);
                        primitives.Color rayColor = rayTracer.traceRay(rays);
                        imageWriter.writePixel(j, i, rayColor);
                    }
//                    imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }

    /**
     *
     * @param nX the amount of horizontal pixels
     * @param nY the amount of vertical pixels
     * @param j the row of a pixel
     * @param i the column of a pixel
     * @param raysAmount
     * @return list of rays
     */
    public List<Ray> constructBeamThroughPixel (int nX, int nY, int j, int i, int raysAmount)
    {
        int numOfRays = (int)Math.floor(Math.sqrt(raysAmount)); //num of rays in each row or column
        if (numOfRays==1)
            return List.of(constructRay(nX, nY, j, i));
        //Ratio (pixel width & height)
        double Ry= _height/nY;
        double Rx=_width/nX;
        double Yi=(i-(nY-1)/2d)*Ry;
        double Xj=(j-(nX-1)/2d)*Rx;
        double PRy = Ry / numOfRays; //height distance between each ray
        double PRx = Rx / numOfRays; //width distance between each ray
        //The distance between the view plane and the camera cannot be 0
        if (isZero(_distance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        List<Ray> sampleRays = new ArrayList<>();

        //for each pixel in the pixels grid
        for (int i1 = 0; i1 < numOfRays; ++i1)
        {
            for (int j1 = 0; j1< numOfRays; ++j1)
            {
                sampleRays.add(constructRaysThroughPixel(PRy,PRx,Yi, Xj, i1, j1));//add the ray
            }
        }
        sampleRays.add(constructRay(nX, nY, j, i));//add the center  ray to pixel
        return sampleRays;
    }

    /**
     * the function treats each pixel like a little screen of its own and divide it to smaller "pixels".
     * Through each one we construct a ray. This function is similar to ConstructRayThroughPixel.
     * @param Ry height of each grid block we divided the pixel into
     * @param Rx width of each grid block we divided the pixel into
     * @param yi distance of original pixel from (0,0) on Y axis
     * @param xj distance of original pixel from (0,0) on X axis
     * @param j j coordinate of small "pixel"
     * @param i i coordinate of small "pixel"
     * @return beam of rays through pixel
     */
    private Ray constructRaysThroughPixel(double Ry,double Rx, double yi, double xj, int j, int i)
    {
        Point Pc = _p0.add(_vTo.scale(_distance)); //the center of the screen point

        double ySampleI =  (i *Ry + Ry/2d); //The pixel starting point on the y axis
        double xSampleJ=   (j *Rx + Rx/2d); //The pixel starting point on the x axis

        Point Pij = Pc; //The point at the pixel through which a beam is fired
        //Moving the point through which a beam is fired on the x axis
        if (!isZero(xSampleJ + xj))
        {
            Pij = Pij.add(_vRight.scale(xSampleJ + xj));
        }
        //Moving the point through which a beam is fired on the y axis
        if (!isZero(ySampleI + yi))
        {
            Pij = Pij.add(_vUp.scale(-ySampleI -yi ));
        }
        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0,Vij);//create the ray throw the point we calculate here
    }

    private primitives.Color castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRay(nX, nY, j, i);
        primitives.Color pixelColor = rayTracer.traceRay(ray);
        return pixelColor;
    }

    public void printGrid(int interval, primitives.Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        imageWriter.writeToImage();
    }

    // CAMERA ROTATION BONUS

    /**
     * Rotates the camera around the axes with the given angles
     *
     * @param v vector of angles
     * @return the current camera
     */
    public Camera rotate(Vector v) {
        return rotate(v.getX(), v.getY(), v.getZ());
    }


    /**
     * Rotates the camera around the axes with the given angles
     *
     * @param x angles to rotate around the x axis
     * @param y angles to rotate around the y axis
     * @param z angles to rotate around the z axis
     * @return the current camera
     */
    public Camera rotate(double x, double y, double z) {
        _vTo.rotateX(x).rotateY(y).rotateZ(z);
        _vUp.rotateX(x).rotateY(y).rotateZ(z);
        _vRight = _vTo.crossProduct(_vUp);

        return this;
    }
}
