package primitives;

import java.util.Objects;

public class Point {

    public static final Point ZERO =  new Point(0,0,0);
//    final Double3 _xyz;
    Double3 _xyz;

    /**
     * primary constructor for point
     *
     * @param xyz double3 value for _xyz
     */
    public Point(Double3 xyz) {
        _xyz = xyz;
    }

    /**
     * @param x coordinate x axis
     * @param y coordinate y axis
     * @param z coordinate z axis
     */
    public Point(double x, double y, double z) {
        _xyz = new Double3(x, y, z);
    }

    /**
     * xyz getter
     *
     * @return the xyz double3
     */
    public Double3 getXyz() {
        return _xyz;
    }

    public double getX() {
        return _xyz.d1;
    }

    public double getY() {
        return _xyz.d2;
    }

    public double getZ() {
        return _xyz.d3;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Point)) return false;
        Point other = (Point) o;
        return this._xyz.equals(other._xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_xyz);
    }

    /**
     * override toString
     *
     * @return string representing the point
     */
    @Override
    public String toString() {
        return "Point" + _xyz;
    }

    /**
     * calculate the square of the distance
     *
     * @param other point to calculate the distance to
     * @return the distance
     */
    public double distanceSquared(Point other) {
        return (this._xyz.d1 - other._xyz.d1) * (this._xyz.d1 - other._xyz.d1) +
                (this._xyz.d2 - other._xyz.d2) * (this._xyz.d2 - other._xyz.d2) +
                (this._xyz.d3 - other._xyz.d3) * (this._xyz.d3 - other._xyz.d3);
    }

    /**
     * calculate the distance between two points
     *
     * @param other
     * @return the calculated distance between two points
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * adds a vector to the point
     *
     * @param vector object of type Vector
     * @return a new point
     */
    public Point add(Vector vector) {
        return new Point(_xyz.add(vector._xyz));
    }

    /**
     * receives a Point as a parameter, returns a vector from second point to the point which called the method
     *
     * @param point object of type Point
     * @return vector between 2 points
     */
    public Vector subtract(Point point) {
        return new Vector(_xyz.subtract(point._xyz));
    }
}
