package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Geometries} class.
 */
class GeometriesTest {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {
        Geometries geometries = new Geometries(
                new Sphere(new Point(1, 0, 0), 1),
                new Polygon(
                        new Point( 1, 0, 0),
                        new Point(0,  1, 0),
                        new Point(-1, 0, 0),
                        new Point(0, -1, 0)
                ),
                new Triangle(new Point(-4,0,0), new Point(0, 0, 5), new Point(0, -5, 0)),
                new Plane (new Point(0, 0, 1), new Point(1, 0, 0), new Point(4, 0, 2))
        );
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        //TC01: A few geometries intersects
        result=geometries.findIntersections(new Ray(new Point(-1,-1,-1),new Vector(2,2,2)));
        assertEquals(3, result.size(), "A few geometries intersects");

        // =============== Boundary Values Tests ==================
        //TC02: All geometries intersects
        result=geometries.findIntersections(new Ray(new Point(-4, -3, 0), new Vector(6,3,0.5)));
        assertEquals(4,result.size(),"All geometries intersects");

        //TC03: Only 1 geometry intersect
        result=geometries.findIntersections(new Ray(new Point(0.2,0.2,0.2),new Vector(1,1,1)));
        assertEquals(1,result.size(),"Only 1 geometry intersect");

        //TC04: No geometries intersects
        assertNull(geometries.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))), "No geometries intersects");

        //TC05: Empty list of geometries
        assertNull(new Geometries().findIntersections(new Ray(new Point(1,2,3), new Vector(2,2,2))), "Empty list of geometries");

    }
}