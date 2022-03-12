package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testDistanceSquared() {

    }

    @Test
    void testDistance() {

    }

    @Test
    /**
     * Test method for {@link Point#add(Vector)}.
     */
    void testAdd() {
        assertTrue((new Vector(-1, -2, -3)).equals(new Point(0, 0, 0)),
                "ERROR: Point + Vector does not work correctly");


    }

    @Test
    void testSubtract() {
    }
}