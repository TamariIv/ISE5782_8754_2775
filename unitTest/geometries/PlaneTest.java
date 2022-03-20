package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    Plane _plane = new Plane(new Point(0,0,1), new Point(1,0,0),new Point(0,1,0));

    @Test
    /**
     * Test method for {@link geometries.Plane#Plane()}.
     */
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(0, 0, 1), new Point(0, 0, 3), new Point(0, 0, 9)), //
                "The points are on the same line");
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(0, 0, 1), new Point(0, 0, 1), new Point(1, 0, 1)), //
                "The two points are the same");
    }

    @Test
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        double sqrt3 = Math.sqrt(1d/3);
        assertEquals(new Vector(sqrt3,sqrt3,sqrt3),_plane.getNormal(new Point(0,0,1)),"Incorrect normal to plane");

    }


}