
package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for {@link Camera} class.
 */
public class CameraIntegrationTest {


    /**
     * Test helper function to count the intersections and compare with expected value
     *
     * @param cam      camera for the test
     * @param geo      3D body to test the integration of the camer with
     * @param expected amount of intersections
     * @author Dan Zilberstein
     */
    private void assertCountIntersections(Camera cam, Intersectable geo, int expected) {
        int count = 0;

        List<Point> allpoints = null;

        cam.setVPSize(3, 3);
        cam.setVPDistance(1);
        int nX = 3;
        int nY = 3;
        //view plane 3X3 (WxH 3X3 & nx,ny =3 => Rx,Ry =1)
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                var intersections = geo.findIntersections(cam.constructRay(nX, nY, j, i));
                if (intersections != null) {
                    if (allpoints == null) {
                        allpoints = new LinkedList<>();
                    }
                    allpoints.addAll(intersections);
                }
                count += intersections == null ? 0 : intersections.size();
            }
        }

        System.out.format("there is %d points:%n", count);
        if (allpoints != null) {
            for (var item : allpoints) {
                System.out.println(item);
            }
        }
        System.out.println();

        assertEquals(expected, count, "Wrong amount of intersections");
    }


    /**
     * Integration tests of Camera Ray construction with Ray-Sphere intersections
     */
    @Test
    public void cameraRaySphereIntegration() {
        Camera cam1 = new Camera(new Point(0,0,0), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3,3).setVPDistance(1);
        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3,3).setVPDistance(1);

        // TC01: Small Sphere 2 points
        assertCountIntersections(cam1, new Sphere( new Point(0, 0, -3),1), 2);

        // TC02: Big Sphere 18 points
        assertCountIntersections(cam2, new Sphere( new Point(0, 0, -2.5),2.5), 18);

        // TC03: Medium Sphere 10 points
        assertCountIntersections(cam2, new Sphere(new Point(0, 0, -2),2), 10);

        // TC04: Inside Sphere 9 points
        assertCountIntersections(cam2, new Sphere( new Point(0, 0, -1),4), 9);

        // TC05: Beyond Sphere 0 points
        assertCountIntersections(cam1, new Sphere( new Point(0, 0, 1),0.5), 0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Plane intersections
     */
    @Test
    public void cameraRayPlaneIntegration() {
        Camera cam = new Camera(new Point(0,0,0), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3,3).setVPDistance(1);

        // TC01: Plane against camera 9 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 0, 1)), 9);

        // TC02: Plane with small angle 9 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 2)), 9);

        // TC03: Plane parallel to lower rays 6 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), 6);

        // TC04: Beyond Plane 0 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), 6);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Triangle intersections
     */
    @Test
    public void cameraRayTriangleIntegration() {
        Camera cam = new Camera(new Point(0,0,0), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(3,3).setVPDistance(1);

        // TC01: Small triangle 1 point
        assertCountIntersections(cam, new Triangle(new Point(0, 1, -2), new Point(-1, -1, -2), new Point(1, -1, -2)), 1);

        // TC02: Medium triangle 2 points
        assertCountIntersections(cam, new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -20, -2)), 2);
    }

}







//package renderer;
//import org.junit.jupiter.api.Test;
//import primitives.*;
//import geometries.*;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Testing integration between creating ray from Camera to find intersection with Geometries
//*/
//
//public class CameraIntegrationTest {
//
//    //Camera object to test intersections of rays that starts from it
//    Camera cam;
//
//    /**
//     * calculate all intersections points of Rays that start at a Camera
//     * and go through the center of the pixels in the view plane with a geometry
//     * @param Nx number of pixels at x axis
//     * @param Ny number of pixels at y axis
//     * @param shape shape from geometries
//     * @return list of intersections points with the shape
//     */
//    private int intersectionPointsNumber(int Nx, int Ny,Intersectable shape, Camera cam){
//
//        int intersectionsNumber=0;
//        //iterate all view plane's pixels and count intersection points
//        for (int i = 0; i < Ny; i++) {
//            for (int j = 0; j < Nx; j++) {
//                List<Point> lst=shape.findIntersections(cam.constructRay(Nx,Ny,j,i));
//                if(lst!=null) {
//                    intersectionsNumber += lst.size();
//                }
//            }
//        }
//        return intersectionsNumber;
//    }
//
//    /**
//     * Test method for
//     * {@link Camera#constructRay}, {@link geometries.Sphere#findIntersections(Ray)}
//     */
//    @Test
//    void sphereIntersections(){
//
//        // TC01: intersection through 1 pixel
//        Sphere sphere = new Sphere(new Point(0,0,-3), 1);
//        cam=new Camera(
//                new Point(0,0,0),
//                new Vector(0,0,-1),
//                new Vector(0,-1,0)).
//                setVPDistance(1).
//                setVPSize(3,3);
//        assertEquals(2,intersectionPointsNumber(3,3,sphere,cam),
//                "Wrong number of intersection points");
//
//        // TC02: intersection through all pixels
//        sphere = new Sphere(new Point(0,0,-2.5), 2.5);
//        cam = new Camera(
//                new Point(0,0,0.5),
//                new Vector(0,0,-1),
//                new Vector(0,-1,0)).
//                setVPDistance(1).
//                setVPSize(3,3);
//        assertEquals(18,intersectionPointsNumber(3,3,sphere,cam),
//                "Wrong number of intersection points");
//
//        // TC03: intersection through 3 pixels in the middle column
//        sphere=new Sphere(new Point(0,0,-2), 2);
//        assertEquals(10,intersectionPointsNumber(3,3,sphere,cam),
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
//        assertEquals(9,intersectionPointsNumber(3,3,sphere,cam),
//                "Wrong number of intersection points");
//
//        // TC05: no intersection points
//        sphere=new Sphere(new Point(0,0,1), 0.5);
//        assertEquals(0,intersectionPointsNumber(3,3,sphere,cam),
//                "Wrong number of intersection points");
//    }
//
//    /**
//     * Test method for
//     * {@link Camera#constructRay}, {@link geometries.Plane#findIntersections(Ray)}
//     */
//    @Test
//    void planeIntersections(){
//
//        // TC01: the plane is parallel to the view plane, intersection through all 9 pixels
//        Plane plane=new Plane(new Point(0,0,-2),new Vector(0,0,-1));
//        cam=new Camera(
//                new Point(0,0,0),
//                new Vector(0,0,-1),
//                new Vector(0,1,0)).
//                setVPDistance(1).
//                setVPSize(3,3);
//        assertEquals(9,intersectionPointsNumber(3,3,plane,cam),
//                "Wrong number of intersection points");
//
//        // TC02: the plane intersects the view plane at the top (ray intersects through 9 pixels)
//        plane=new Plane(
//                new Point(0,1.5,-1),
//                new Point(0,0,-2),
//                new Point(1,0,-2));
//        assertEquals(9,intersectionPointsNumber(3,3,plane,cam),
//                "Wrong number of intersection points");
//
//        // TC03: the plane intersects the view plane between two rows (ray intersects through 6 pixels
//        plane=new Plane(
//                new Point(0,0.5,-1),
//                new Point(0,0,-2),
//                new Point(1,0,-2));
//        assertEquals(6,intersectionPointsNumber(3,3,plane,cam),
//                "Wrong number of intersection points");
//
//    }
//
//    /**
//     * Test method for
//     * {@link Camera#constructRay}, {@link geometries.Triangle#findIntersections(Ray)}
//     */
//    @Test
//    void triangleIntersections(){
//
//        // TC01: intersection through the central pixel only
//        Triangle triangle = new Triangle(
//                new Point(0,1,-2),
//                new Point(-1,-1,-2),
//                new Point(1,-1,-2));
//        cam=new Camera(
//                new Point(0,0,0),
//                new Vector(0,0,-1),
//                new Vector(0,1,0)).
//                setVPDistance(1).
//                setVPSize(3,3);
//
//        assertEquals(1,intersectionPointsNumber(3,3,triangle,cam),
//                "Wrong number of intersection points");
//
//        // TC01: intersection through 2 pixels in the central column
//        triangle=new Triangle(
//                new Point(0,20,-2),
//                new Point(-1,-1,-2),
//                new Point(1,-1,-2));
//
//        assertEquals(2,intersectionPointsNumber(3,3,triangle,cam),
//                "Wrong number of intersection points");
//
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
//import primitives.Ray;
//import primitives.Vector;
//import renderer.Camera;
//
//import java.util.ArrayList;
//import java.util.Collections;
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
//     * Integration tests of a camera and a sphere.
//     */
//    @Test
//    public void testSphereAndCamera() {
//        Sphere[] spheres = new Sphere[]{
//                //TC01: the sphere is in front of the view plane(2).
//                new Sphere(new Point(0, 0, -2.5), 1),
//                //TC02: the view plane is inside the sphere, all rays should intersect twice(18).
//                new Sphere(new Point(0, 0, -2.5), 2.5),
//                //TC03: the view plane cross the sphere (10).
//                new Sphere(new Point(0, 0, -2), 2),
//                //TC04: the camera is inside the sphere,all rays should intersect only once(9).
//                new Sphere(new Point(0, 0, -1), 4),
//                //TC05: the sphere is behind the camera , no ray should intersect(0).
//                new Sphere(new Point(0, 0, 1), 0.5)
//        };
//        int[] expected = new int[]{2, 18, 10, 9, 0};
//
//        testIntersectsAndCamera(spheres, expected);
//    }
//
//    /**
//     * Integration tests of a camera and a plane.
//     */
//    @Test
//    public void testPlaneAndCamera() {
//        Plane[] planes = new Plane[]{
//                //TC01: the plane is parallel with the view plane, all rays should intersect(9).
//                new Plane(new Point(0, 0, -2), new Vector(0, 0, 1)),
//                //TC02: the plane is in front of the view plane and cross, all rays should intersect(9).
//                new Plane(new Point(0, 0, -1.5), new Vector(0, -0.5, 1)),
//                //TC03: the plane is above the view plane's third row (6).
//                new Plane(new Point(0, 0, -3), new Vector(0, -1, 1))
//        };
//        int[] expected = new int[]{9, 9, 6};
//
//        testIntersectsAndCamera(planes, expected);
//    }
//
//    /**
//     * Integration tests of a camera and a triangle.
//     */
//    @Test
//    public void testTriangleAndCamera() {
//        Triangle[] triangles = new Triangle[]{
//                //TC01:only the center ray should intersect(1).
//                new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)),
//                //TC02: only the center ray and the top-middle ray should intersect(2).
//                new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2))
//        };
//
//        int[] expected = new int[]{1, 2};
//
//        testIntersectsAndCamera(triangles, expected);
//    }
//
//    /**
//     * Helper method for testing intersectables and a camera.
//     * @param intersectables Intersectables to check the number of intersections for each one.
//     * @param expectedIntersections All expected intersections for the intersectables (in the same order of intersectables).
//     */
//    private void testIntersectsAndCamera(Intersectable[] intersectables, int[] expectedIntersections) {
//        int nX = 3, nY = 3;
//        Camera cam = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
//                .setVPDistance(1)
//                .setVPSize(3, 3);
//
//        List<List<Point>> intersections = new ArrayList<>(Collections.nCopies(intersectables.length, null));
//
//        for (int i = 0; i < nY; ++i) {
//
//            for (int j = 0; j < nX; ++j) {
//
//                List<Ray> pixelRay  = (List<Ray>) cam.constructRay(nX, nY, j, i);
//
//                // checking every intersect to find intersections with each one.
//                for (Ray r:pixelRay) {
//
//
//                    for (int id = 0; id < intersectables.length; id++) {
//                        List<Point> list = intersectables[id].findIntersections(r);
//
//                        if (list == null) {
//                            continue;
//                        }
//                        if (intersections.get(id) == null) {
//                            intersections.set(id, new ArrayList<>());
//                        }
//
//                        intersections.get(id).addAll(list);
//                    }
//                }
//            }
//
//        }
//
//        // checking each intersectable to assert the number of intersections.
//        for (int id = 0; id < intersectables.length; id++) {
//            int sumOfIntersection = 0;
//
//            if (intersections.get(id) != null) {
//                sumOfIntersection = intersections.get(id).size();
//            }
//            assertEquals(sumOfIntersection, expectedIntersections[id], "Wrong number of intersectables");
//        }
//
//    }
//
//}
