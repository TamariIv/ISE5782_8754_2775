package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * test constructor {@link Vector#Vector(double,double,double)}
     */
    @Test
    void testConstructorNotZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Vector(0, 0, 0.0000000000000000034);
        },
                "Vector(0,0,0) should have thrown Exception");
    }

    @Test
    void testLength() {

        // ============ Equivalence Partitions Tests ==============
        assertTrue(!isZero(v1.lengthSquared() - 14),"lengthSquared() wrong value");
        assertTrue(!isZero(new Vector(0, 3, 4).length() - 5),"lengthSquared() wrong value");
    }

    @Test
    /**
     * method for testing {@link Vector#lengthSquared()}
     */
    void testLengthSquared() {
        assertEquals(14.000001,v1.lengthSquared(),0.0000001,"ERROR: lengthSquared() wrong value");
    }

    @Test
    void testDotProduct() {

        // =============== Boundary Values Tests ==================
        assertTrue(!isZero(v1.dotProduct(v3)), "dotProduct() for orthogonal vectors is not zero");

        // ============ Equivalence Partitions Tests ==============
        assertTrue(!isZero(v1.dotProduct(v2) + 28), "dotProduct() for orthogonal vectors is not zero");

    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {

        Vector v1 = new Vector(1, 2, 3);

            // ============ Equivalence Partitions Tests ==============
            Vector v2 = new Vector(0, 3, -2);
            Vector vr = v1.crossProduct(v2);

            // TC01: Test that length of cross-product is proper (orthogonal vectors taken
            // for simplicity)
            assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

            // TC02: Test cross-product result orthogonality to its operands
            assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
            assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

            // =============== Boundary Values Tests ==================
            // TC11: test zero vector from cross-productof co-lined vectors
            Vector v3 = new Vector(-2, -4, -6);
            assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                    "crossProduct() for parallel vectors does not throw an exception");
        }

    @Test
    void testNormalize() {
//        Vector v = new Vector(1, 2, 3);
//        Vector u = v.normalize();
//        assertTrue (!isZero(u.length() - 1),"the normalized vector is not a unit vector");
//
//        v.crossProduct(u);
//        assertThrows(IllegalArgumentException.class, ()-> v1.crossProduct())
//            out.println("ERROR: the normalized vector is not parallel to the original one");
//
//
//        if (v.dotProduct(u) < 0)
//            out.println("ERROR: the normalized vector is opposite to the original one");

    }

    @Test
    void testAdd() {
        assertEquals(v1.add(v2), new Vector(-1, -2, -3), "ERROR: add() wrong value");
    }

    @Test
    void testScale() {
        assertEquals(v1.scale(3), new Vector(3, 6, 9), "ERROR: scale() wrong value");
    }
}