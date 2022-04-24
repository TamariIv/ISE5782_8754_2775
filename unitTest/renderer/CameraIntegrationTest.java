
package renderer;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing integration between creating ray from Camera to find intersection with Geometries
*/

public class CameraIntegrationTest {

    //Camera object to test intersections of rays that starts from it
    Camera cam;

    /**
     * calculate all intersections points of Rays that start at a Camera
     * and go through the center of the pixels in the view plane with a geometry
     * @param Nx number of pixels at x axis
     * @param Ny number of pixels at y axis
     * @param shape shape from geometries
     * @return list of intersections points with the shape
     */
    private int intersectionPointsNumber(int Nx, int Ny,Intersectable shape){

        int intersectionsNumber=0;
        //iterate all view plane's pixels and count intersection points
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                List<Point> lst=shape.findIntersections(cam.constructRay(Nx,Ny,j,i));
                if(lst!=null) {
                    intersectionsNumber += lst.size();
                }
            }
        }
        return intersectionsNumber;
    }

    /**
     * Test method for
     * {@link Camera#constructRay}, {@link geometries.Sphere#findIntersections(Ray)}
     */
    @Test
    void sphereIntersections(){

        // TC01: intersection through 1 pixel
        Sphere sphere = new Sphere(new Point(0,0,-3), 1);
        cam=new Camera(
                new Point(0,0,0),
                new Vector(0,0,-1),
                new Vector(0,1,0)).
                setVPDistance(1).
                setVPSize(3,3);
        assertEquals(2,intersectionPointsNumber(3,3,sphere),
                "Wrong number of intersection points");

        // TC02: intersection through all pixels
        sphere = new Sphere(new Point(0,0,-2.5), 2.5);
        cam = new Camera(
                new Point(0,0,0.5),
                new Vector(0,0,-1),
                new Vector(0,1,0)).
                setVPDistance(1).
                setVPSize(3,3);
        assertEquals(18,intersectionPointsNumber(3,3,sphere),
                "Wrong number of intersection points");

        // TC03: intersection through 3 pixels in the middle column
        sphere=new Sphere(new Point(0,0,-2), 2);
        assertEquals(10,intersectionPointsNumber(3,3,sphere),
                "Wrong number of intersection points");

        // TC04: the view plane is in the sphere, intersection through all 9 pixels
        sphere=new Sphere(new Point(0,0,-1), 4);
        cam=new Camera(
                new Point(0,0,0),
                new Vector(0,0,-1),
                new Vector(0,1,0)).
                setVPDistance(1).
                setVPSize(3,3);
        assertEquals(9,intersectionPointsNumber(3,3,sphere),
                "Wrong number of intersection points");

        // TC05: no intersection points
        sphere=new Sphere(new Point(0,0,1), 0.5);
        assertEquals(0,intersectionPointsNumber(3,3,sphere),
                "Wrong number of intersection points");
    }

    /**
     * Test method for
     * {@link Camera#constructRay}, {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    void planeIntersections(){

        // TC01: the plane is parallel to the view plane, intersection through all 9 pixels
        Plane plane=new Plane(new Point(0,0,-2),new Vector(0,0,-1));
        cam=new Camera(
                new Point(0,0,0),
                new Vector(0,0,-1),
                new Vector(0,1,0)).
                setVPDistance(1).
                setVPSize(3,3);
        assertEquals(9,intersectionPointsNumber(3,3,plane),
                "Wrong number of intersection points");

        // TC02: the plane intersects the view plane at the top (ray intersects through 9 pixels)
        plane=new Plane(
                new Point(0,1.5,-1),
                new Point(0,0,-2),
                new Point(1,0,-2));
        assertEquals(9,intersectionPointsNumber(3,3,plane),
                "Wrong number of intersection points");

        // TC03: the plane intersects the view plane between two rows (ray intersects through 6 pixels
        plane=new Plane(
                new Point(0,0.5,-1),
                new Point(0,0,-2),
                new Point(1,0,-2));
        assertEquals(6,intersectionPointsNumber(3,3,plane),
                "Wrong number of intersection points");

    }

    /**
     * Test method for
     * {@link Camera#constructRay}, {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    void triangleIntersections(){

        // TC01: intersection through the central pixel only
        Triangle triangle = new Triangle(
                new Point(0,1,-2),
                new Point(-1,-1,-2),
                new Point(1,-1,-2));
        cam=new Camera(
                new Point(0,0,0),
                new Vector(0,0,-1),
                new Vector(0,1,0)).
                setVPDistance(1).
                setVPSize(3,3);

        assertEquals(1,intersectionPointsNumber(3,3,triangle),
                "Wrong number of intersection points");

        // TC01: intersection through 2 pixels in the central column
        triangle=new Triangle(
                new Point(0,20,-2),
                new Point(-1,-1,-2),
                new Point(1,-1,-2));

        assertEquals(2,intersectionPointsNumber(3,3,triangle),
                "Wrong number of intersection points");

    }

}
























//
//
//package renderer;
//
//import geometries.Intersectable;
//import geometries.Plane;
//import geometries.Sphere;
//import geometries.Triangle;
//import org.junit.jupiter.api.Test;
//import primitives.Point;
//import primitives.Vector;
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
///**
// * Integration tests for {@link Camera} class.
// */
//public class CameraIntegrationTest {
//
//
//    /**
//     * Test helper function to count the intersections and compare with expected value
//     *
//     * @param cam      camera for the test
//     * @param geo      3D body to test the integration of the camer with
//     * @param expected amount of intersections
//     * @author Dan Zilberstein
//     */
//    private void assertCountIntersections(Camera cam, Intersectable geo, int expected) {
//        int count = 0;
//
//        List<Point> allpoints = null;
//
//        cam.setVPSize(3, 3);
//        cam.setVPDistance(1);
//        int nX = 3;
//        int nY = 3;
//        //view plane 3X3 (WxH 3X3 & nx,ny =3 => Rx,Ry =1)
//        for (int i = 0; i < nY; ++i) {
//            for (int j = 0; j < nX; ++j) {
//                var intersections = geo.findIntersections(cam.constructRay(nX, nY, j, i));
//                if (intersections != null) {
//                    if (allpoints == null) {
//                        allpoints = new LinkedList<>();
//                    }
//                    allpoints.addAll(intersections);
//                }
//                count += intersections == null ? 0 : intersections.size();
//            }
//        }
//
//        System.out.format("there is %d points:%n", count);
//        if (allpoints != null) {
//            for (var item : allpoints) {
//                System.out.println(item);
//            }
//        }
//        System.out.println();
//
//        assertEquals(expected, count, "Wrong amount of intersections");
//    }
//
//
//    /**
//     * Integration tests of Camera Ray construction with Ray-Sphere intersections
//     */
//    @Test


//        // TC01: intersection through 1 pixel
//        Sphere sphere = new Sphere(new Point(0,0,-3), 1);
//        cam=new Camera(
//                new Point(0,0,0),
//                new Vector(0,0,-1),
//                new Vector(0,1,0)).
//                setVPDistance(1).
//                setVPSize(3,3);
//        assertEquals(2,intersectionPointsNumber(3,3,sphere),
//                "Wrong number of intersection points");
//
//        // TC02: intersection through all pixels
//        sphere = new Sphere(new Point(0,0,-2.5), 2.5);
//        cam = new Camera(
//                new Point(0,0,0.5),
//                new Vector(0,0,-1),
//                new Vector(0,1,0)).
//                setVPDistance(1).
//                setVPSize(3,3);
//        assertEquals(18,intersectionPointsNumber(3,3,sphere),
//                "Wrong number of intersection points");
//
//        // TC03: intersection through 3 pixels in the middle column
//        sphere=new Sphere(new Point(0,0,-2), 2);
//        assertEquals(10,intersectionPointsNumber(3,3,sphere),
//                "Wrong number of intersection points");
//
//        // TC04: the view plane is in the sphere, intersection through all 9 pixels
//        sphere=new Sphere(new Point(0,0,-1), 4);
//        cam=new Camera(
//                new Point(0,0,0),
//                new Vector(0,0,-1),
//                new Vector(0,1,0)).
//                setVPDistance(1).
//                setVPSize(3,3);
//        assertEquals(9,intersectionPointsNumber(3,3,sphere),
//                "Wrong number of intersection points");
//
//        // TC05: no intersection points
//        sphere=new Sphere(new Point(0,0,1), 0.5);
//        assertEquals(0,intersectionPointsNumber(3,3,sphere),
//                "Wrong number of intersection points");
//    }
//


//}