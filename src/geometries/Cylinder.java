package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Cylinder extends Tube {

    double height;

    /**
     * ctor of an object of cylinder
     *
     * @param axis   is the axis of the cylinder
     * @param radius is the radius of the cylinder
     * @param height is the height of the cylinder
     */
    public Cylinder(Ray axis, double radius, double height) {
        super(axis, radius);
        this.height= height;
    }

    /**
     * find the height of the cylinder
     *
     * @return height of the cylinder
     */
    public double getHeight() {

        return height;
    }


    /**
     * override toString
     *
     * @return string representing the cylinder
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                '}';
    }


    /**
     * normal vector for the cylinder
     *
     * @param p point on the surface
     * @return normal vector
     * <p>
     * Mathematical principle:
     * if dot product between the axis ray direction vector (v)
     * to a vector from the beginning axis ray to p (t) gives the cylinder height
     * or 0, it means that p is on one of the bases.
     * in other case we set o to p0+vt and the normal is p-o
     */
    @Override
    public Vector getNormal(Point p) {
        Point p0 = axis.getP0();
        Vector v = axis.getDir();

        Vector pSUBp0 = p.subtract(p0);
        double t = v.dotProduct(pSUBp0);
        if (t == 0) {
            return v.scale(-1);
        }
        if(t == height){
            return v;
        }
        Vector u = axis.getDir().scale(t);
        Point o = axis.getP0().add(u);
        return p.subtract(o).normalize();
    }
}

//    @Override
//    public List<Point> findIntersections(Ray r) {
//    }
