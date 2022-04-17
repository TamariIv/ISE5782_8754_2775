package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {



    @Test
    void testFindIntersections() {
        Triangle tr = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        Ray ray;
        List<Point> result;
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the triangle
        ray = new Ray(new Point(-1, -1, -2), new Vector(1, 1, 2));
       result = tr.findIntersections(ray);
       assertEquals(1,result.size(),"Ray doesn't intersect the triangle");

        //TC02:Ray outside against vertex
        ray = new Ray(new Point(-2,-2,-2),new Vector(1,1,2));
        assertNull(tr.findIntersections(ray), "Ray isn't outside against vertex");

        //TC03: Ray outside against edge
        ray = new Ray(new Point(-1, -2, -2), new Vector(1, 1, 2));
        assertNull(tr.findIntersections(ray), "Ray isn't outside against edge");

        //TC04:Ray inside the triangle
        ray = new Ray(new Point(0.5, 0.5, 0.2), new Vector(0.5, 0.5, 1.8d));
        assertNull(tr.findIntersections(ray),"Ray  isn't inside the triangle");
        // =============== Boundary Values Tests ==================
        // TC11: In vertex
        ray = new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0));
        assertEquals(List.of(new Point(0, 1, 0)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(tr.findIntersections(ray), "Bad intersection");

        // TC12: On edge
        ray = new Ray(new Point(-1, -1, 0), new Vector(1, 1, 0));
        assertEquals(List.of(new Point(0.5, 0.5, 0)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(tr.findIntersections(ray), "Bad intersection");

        // TC13: On edge continuation
        ray = new Ray(new Point(-2, 0, 0), new Vector(1, 1, 0));
        assertEquals(List.of(new Point(-0.5, 1.5, 0)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(tr.findIntersections(ray), "Bad intersection");
    }
}