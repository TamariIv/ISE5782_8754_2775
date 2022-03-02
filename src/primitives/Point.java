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

    @Override
    public String toString() {
        return "Point" +_xyz;
    }

    public double distanceSquared(Point other) {
        return (this._xyz._d1 - other._xyz._d1) * (this._xyz._d1 - other._xyz._d1) +
                (this._xyz._d2 - other._xyz._d2) * (this._xyz._d2 - other._xyz._d2) +
                (this._xyz._d3 - other._xyz._d2) * (this._xyz._d3 - other._xyz._d2);
    }

    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    public Point add(Vector vector) {
        return new Point(_xyz.add(vector._xyz));
    }

    public Vector subtract(Point point) { return new Vector(point._xyz.subtract(_xyz)); }
}
