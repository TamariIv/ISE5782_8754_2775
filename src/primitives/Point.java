package primitives;

import java.util.Objects;

public class Point {
    final Double3 _xyz;

    /**
     * primary constructor for point
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
        _xyz = new Double3(x,y,z);
    }

    /**
     * xyz getter
     * @return the xyz double3
     */
    public Double3 getXyz() {
        return _xyz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Point)) return false;
        Point other = (Point)o;
        return this._xyz.equals(other._xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_xyz);
    }

    /**
     * override toString
     * @return string representing the point
     */
    @Override
    public String toString() {
        return "Point" +_xyz;
    }

    /**
     * calculate the square of the distance
     * @param other point to calculate the distance to
     * @return the distance
     */
    public double distanceSquared(Point other) {
        return (this._xyz._d1 - other._xyz._d1) * (this._xyz._d1 - other._xyz._d1) +
                (this._xyz._d2 - other._xyz._d2) * (this._xyz._d2 - other._xyz._d2) +
                (this._xyz._d3 - other._xyz._d2) * (this._xyz._d3 - other._xyz._d2);
    }

    /**
     * calculate the distance between two points
     * @param other
     * @return the calculated distance
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * adds a vector to the point
     * @param vector object of type Vector
     * @return a new point
     */
    public Point add(Vector vector) {
        return new Point(_xyz.add(vector._xyz));
    }

    /**
     * receives a Point as a parameter, returns a vector from second point to the point which called the method
     * @param point object of type Point
     * @return vector between 2 points
     */
    public Vector subtract(Point point) { return new Vector(_xyz.subtract(point._xyz)); }
}
