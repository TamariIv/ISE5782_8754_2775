package renderer;

import primitives.*;
import primitives.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.isZero;


/**
 * camera producing ray through a view plane
 */
public class Camera {


    /**
     * Camera's forward direction.
     */
    private Vector _vTo;

    /**
     * Camera's upper direction.
     */
    private Vector _vUp;

    /**
     * Camera's right direction
     */
    private Vector _vRight;

    /**
     * Camera's location.
     */
    private Point _p0;

    /**
     * The distance between the camera and the view plane.
     */
    private double _distance;

    /**
     * View plane's width.
     */
    private double _width;

    /**
     * View plane's height.
     */
    private double _height;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int numOfRays;

    private int NumOfRaysSupersampling=0;
    private boolean isSupersampling=false;
    public Camera setSupersampling(boolean isSupersampling) {
        this.isSupersampling = isSupersampling;
        return this;
    }
    /**
     * setter for the num of rays --builder
     * @param numOfRaysSupersampling
     * @return
     */
    public Camera setNumOfRaysSupersampling(int numOfRaysSupersampling) {
        NumOfRaysSupersampling = numOfRaysSupersampling;
        return this;
    }

    /**
     * for the threads
     */
    private int threadsCount = 0;
    private static final int SPARE_THREADS = 3; // Spare threads if trying to use all the cores
    private double debugPrint=0;

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

    /**
     * Returns the distance between the camera and the view plane.
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * Returns the view plane's width.
     */
    public double getWidth() {
        return _width;
    }

    /**
     * Returns the view plane's height.
     */
    public double getHeight() {
        return _height;
    }


    /**
     * Pixel is a helper class. It is used for multi-threading in the renderer and
     * for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     *
     */
    class Pixel {
        private static int maxRows = 0;
        private static int maxCols = 0;
        private static long totalPixels = 0l;

        private static volatile int cRow = 0;
        private static volatile int cCol = -1;
        private static volatile long pixels = 0l;
        private static volatile long last = -1l;
        private static volatile int lastPrinted = -1;

        private static boolean print = false;
        private static long printInterval = 100l;
        private static final String PRINT_FORMAT = "%5.1f%%\r";
        private static Object mutexNext = new Object();
        private static Object mutexPixels = new Object();

        int row;
        int col;

        /**
         * Initialize pixel data for multi-threading
         *
         * @param maxRows  the amount of pixel rows
         * @param maxCols  the amount of pixel columns
         * @param interval print time interval in seconds, 0 if printing is not required
         */
        static void initialize(int maxRows, int maxCols, double interval) {
            Pixel.maxRows = maxRows;
            Pixel.maxCols = maxCols;
            Pixel.totalPixels = (long) maxRows * maxCols;
            cRow = 0;
            cCol = -1;
            pixels = 0;
            printInterval = (int) (interval * 1000);
            print = printInterval != 0;
        }

        /**
         * Function for thread-safe manipulating of main follow up Pixel object - this
         * function is critical section for all the threads, and static data is the
         * shared data of this critical section.<br/>
         * The function provides next available pixel number each call.
         *
         * @return true if next pixel is allocated, false if there are no more pixels
         */
        public boolean nextPixel() {
            synchronized (mutexNext) {
                if (cRow == maxRows)
                    return false;
                ++cCol;
                if (cCol < maxCols) {
                    row = cRow;
                    col = cCol;
                    return true;
                }
                cCol = 0;
                ++cRow;
                if (cRow < maxRows) {
                    row = cRow;
                    col = cCol;
                    return true;
                }
                return false;
            }
        }

        /**
         * Finish pixel processing
         */
        static void pixelDone() {
            synchronized (mutexPixels) {
                ++pixels;
            }
        }

        /**
         * Wait for all pixels to be done and print the progress percentage - must be
         * run from the main thread
         */
        public static void waitToFinish() {
            if (print)
                System.out.printf(PRINT_FORMAT, 0d);

            while (last < totalPixels) {
                printPixel();
                try {
                    Thread.sleep(printInterval);
                } catch (InterruptedException ignore) {
                    if (print)
                        System.out.print("");
                }
            }
            if (print)
                System.out.println("100.0%");
        }

        /**
         * Print pixel progress percentage
         */
        public static void printPixel() {
            long current = pixels;
            if (print && last != current) {
                int percentage = (int) (1000l * current / totalPixels);
                if (lastPrinted != percentage) {
                    last = current;
                    lastPrinted = percentage;
                    System.out.printf(PRINT_FORMAT, percentage / 10d);
                }
            }
        }
    }

    /**
     * Constructs a camera with location, to and up vectors.
     * The right vector is being calculated by the to and up vectors.
     *
     * @param p0  The camera's location.
     * @param vTo The direction to where the camera is looking.
     * @param vUp The direction of the camera's upper direction.
     * @throws IllegalArgumentException When to and up vectors aren't orthogonal.
     */
    public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (!isZero(vTo.dotProduct(vUp))) // if vTo doesn't orthogonal to vUp
            throw new IllegalArgumentException("vUp is not orthogonal to vTo");
        //all the vectors need to be normalized:
        _vTo = vTo.normalize();
        _vUp = vUp.normalize();
        _vRight = (vTo.crossProduct(vUp)).normalize();
        _p0 = p0;
    }

    // chaining methods

    public Camera setP0(Point p0) {
        _p0 = p0;
        return this;
    }

    /**
     * Chaining method for setting the distance between the camera and the view plane.
     *
     * @param distance The new distance between the camera and the view plane.
     * @return The camera itself.
     * @throws IllegalArgumentException When distance illegal.
     */
    public Camera setVPDistance(double distance){
        if (distance <= 0) {
            throw new IllegalArgumentException("Illegal value of distance");
        }
        _distance = distance;
        return this;
    }


    /**
     * Chaining method for setting the view plane's size.
     *
     * @param width  The new view plane's width.
     * @param height The new view plane's height.
     * @return The camera itself.
     */
    public Camera setVPSize(double width, double height) {
        if (width <= 0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
        _width = width;
        if (height <= 0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
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
     * Set debug printing on
     * @return this Camera
     */
    public Camera setDebugPrint(double debugPrint)
    {
        this.debugPrint=debugPrint;
        return this;
    }

    /**
     * The function is responsible for creating the rays from the camera
     * @param Nx int value - resolution of pixel in X
     * @param Ny int value - resolution of pixel in Y
     * @param j int value - index of column
     * @param i int value - index of row
     * @return Ray that created
     * @throws Exception
     */
    public Ray constructRayThroughPixel(int Nx, int Ny, int j, int i) {

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

    /**
     * Set multi-threading <br>
     * if the parameter is 0 - number of cores less 2 is taken
     * @param threads number of threads
     * @return this Camera
     */
    public Camera setMultithreading(int threads)
    {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else //if threads=0
        {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    public Camera renderImage() throws MissingResourceException, IllegalArgumentException {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }
            if (_p0 == null) {
                throw new MissingResourceException("missing resource", Point.class.getName(), "");
            }
            if (_vUp == null || _vRight == null || _vTo == null) {
                throw new MissingResourceException("missing resource", Vector.class.getName(), "");
            }
            if (threadsCount == 0) {
                for (int i = 0; i < imageWriter.getNx(); i++) {
                    for (int j = 0; j < imageWriter.getNy(); j++) {
                        if (numOfRays == 1 || numOfRays == 0) {
                            castRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
                        } else {
                            castRays(imageWriter.getNx(), imageWriter.getNy(), j, i, numOfRays);
                        }
                    }
                }
            } else
                renderImageThreaded();
        } catch (MissingResourceException e) {
            throw new MissingResourceException("No implemented yet", e.getClassName(), e.getKey());
        }
        return this;

    }

//
//                        //rendering the image
//            int nX = imageWriter.getNx();
//            int nY = imageWriter.getNy();
//            for (int i = 0; i < nY; i++) {
//                for (int j = 0; j < nX; j++) {
//                    if(numOfRays == 1 || numOfRays == 0)
//                    {
//                        primitives.Color rayColor = castRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
//                        imageWriter.writePixel(j, i, rayColor);
//                    }
//                    else
//                    {
//                        List<Ray> rays = constructBeamThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i,numOfRays);
//                        primitives.Color rayColor = rayTracer.traceRay(rays);
//                        imageWriter.writePixel(j, i, rayColor);
//                    }
////                    imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
//                }
//            }
//        } catch (MissingResourceException e) {
//            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
//        }
//        return this;


    /**
     * This function renders image's pixel color map from the scene
     * with multi-threading
     */
    private void renderImageThreaded()
    {
        final int nX = imageWriter.getNx();//maxRows
        final int nY = imageWriter.getNy();//maxCols
        Pixel.initialize(nY, nX, debugPrint);
        boolean flag=true;
        if(numOfRays == 1 || numOfRays == 0)//If not used in the improvement of a mini-project1
            flag=false;
        if (flag)
        {
            while (threadsCount-- > 0)
            {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        castRays(nX, nY, pixel.col, pixel.row,numOfRays);
                }).start();
            }
        }
        else
        {
            while (threadsCount-- > 0)
            {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        castRay(nX, nY, pixel.col, pixel.row);
                }).start();
            }
        }
        Pixel.waitToFinish();
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
            return List.of(constructRayThroughPixel(nX, nY, j, i));
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
        sampleRays.add(constructRayThroughPixel(nX, nY, j, i));//add the center  ray to pixel
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

    private void castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRayThroughPixel(nX, nY, j, i);
        Color pixelColor = rayTracer.traceRay(ray);
//        return pixelColor;
        imageWriter.writePixel(j, i, pixelColor);

        //superSampling
        if(isSupersampling==true) {
            List<Ray> rays = constructRaysThroughPixel(nX, nY, j, i, NumOfRaysSupersampling);
            imageWriter.writePixel(j,i, rayTracer.calcColorForSupersampling(rays));
        }
    }


    //SuperSampling
    public List<Ray> constructRaysThroughPixel (int nX, int nY, int j, int i, int raysAmount)
    {
        List<Ray> Rays = new ArrayList<Ray>();
        int numOfRays = (int)Math.floor(Math.sqrt(raysAmount)); //number of rays in each row or column

        if (numOfRays==1) return List.of(constructRayThroughPixel(nX, nY, j, i));

        Point Pc;
        Point point=(_p0.add(_vTo.scale(_distance)));
        if (_distance!=0)
            Pc = new Point(point.getX(),point.getY(),point.getZ()); //reach the view plane
        else
            Pc = new Point(_p0.getX(),_p0.getY(),_p0.getZ());

        //height and width of a single pixel
        double Ry = _height / nY;
        double Rx = _width / nX;
        //center of pixel:
        double yi = (i - nY / 2d)*Ry + Ry / 2d;
        double xj = (j - nX / 2d)*Rx + Rx / 2d;

        Point Pij = Pc;

        if (xj != 0)
        {
            Pij = Pij.add(this._vRight.scale(-xj)); //move Pij to width center
        }
        if (yi != 0)
        {
            Pij = Pij.add(this._vUp.scale(-yi)); //move Pij to height center
        }
        Vector Vij=Pij.subtract(_p0);
        Rays.add(new Ray(_p0,Vij)); //the original vector

        double PRy = Ry / numOfRays; //height distance between each ray
        double PRx = Rx / numOfRays; //width distance between each ray

        Point tmp = Pij; //center

        //creating a grid in the pixel:
        for (int row=0; row<numOfRays; row++) {
            double Pxj = (row - (numOfRays/2d)) * PRx + PRx/2d;//the distance to move on x, and its whole column
            for (int column=0; column<numOfRays; column++) {
                double Pyi = (column - (numOfRays/2d)) * PRy + PRy/2d;
                if (Pxj != 0)
                    Pij = Pij.add(this._vRight.scale(-Pxj));
                if (Pyi != 0)
                    Pij = Pij.add(this._vUp.scale(-Pyi));
                Rays.add(new Ray(_p0, Pij.subtract(_p0)));
                Pij = tmp; //restart
            }
        }
        return Rays;
    }

    /**
     * Cast rays from camera in order to color a pixel
     * @param nX nX resolution on X axis (number of pixels in row)
     * @param nY nY resolution on Y axis (number of pixels in column)
     * @param j pixel's column number (pixel index in row)
     * @param i pixel's row number (pixel index in column)
     * @param numOfRays number of the rays from the camera to the pixel
     */
    private void castRays(int nX,int nY,int j,int i,int numOfRays)
    {
        List<Ray> rays = constructBeamThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i,numOfRays);
        Color color = rayTracer.traceRay(rays);
        imageWriter.writePixel(j, i, color);
    }


    /**
     * A function that creates a grid of lines
     * @param interval int value
     * @param color Color value
     * */
    public Camera printGrid(int interval, primitives.Color color) {
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
        return this;
    }

    /**
     * A function that finally creates the image.
     * This function delegates the function of a class imageWriter
     **/
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
