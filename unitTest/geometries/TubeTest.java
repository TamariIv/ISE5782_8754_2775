package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube tube = new Tube(new Ray(new Point(0, 0, 1), new Vector(0, -1, 0)), 1.0);

        Vector normal = tube.getNormal(new Point(0, 0.5, 2)).normalize();

        double dotProduct = normal.dotProduct(tube.getAxis().getDir());
        assertEquals(0d, dotProduct, "normal is not orthogonal to the tube");

        boolean firstnormal = new Vector(0, 0, 1).equals(normal);
        boolean secondtnormal = new Vector(0, 0, -1).equals(normal);

        assertTrue(firstnormal || secondtnormal, "Bad normal to tube");

        assertEquals(new Vector(0, 0, 1), normal, "Bad normal to tube");


        Vector p_p0 = new Point(1,0,1).subtract(tube.getAxis().getP0());
        assertTrue(Util.isZero(tube.getAxis().getDir().dotProduct(p_p0)));
    }
}