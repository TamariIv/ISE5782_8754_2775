package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void testCrossProduct() {
    }

    @Test
    void testNormalize() {
    }

    @Test
    void testAdd() {
    }

    @Test
    void testScale() {
    }
}