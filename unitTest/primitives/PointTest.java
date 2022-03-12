package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    Point p1 = new Point(1, 2, 3);
    Point p2 = new Point(-2, -4, -6);
    @Test
    void testDistanceSquared() {
        assertEquals(p1.distanceSquared(p2),126.0,"ERROR: DistanceSquared() wrong value");
    }

    @Test
    void testDistance() {
        assertEquals(p1.distance(p2),sqrt(126),"ERROR: Distance() wrong value");
    }

    @Test
    /**
     * Test method for {@link Point#add(Vector)}.
     */
    void testAdd() {
        assertEquals(p1.add(new Vector(-1, -2, -3)),new Point(0, 0, 0),"ERROR: Point + Vector does not work correctly");

        }

    @Test
    void testSubtract() {
        assertEquals(new Point(2, 3, 4).subtract(p1),new Vector(1, 1, 1),"ERROR: Point - Point does not work correctly");
    }
}