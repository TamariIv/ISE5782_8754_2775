package primitives;

public class Vector extends Point{

    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    public Vector(Double3 xyz){
        super(xyz);
        if (_xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Vector (0,0,0) is not allowed");
        }
    }

    public double lengthSquared() {
        return (_xyz._d1 * _xyz._d1)  +
                (_xyz._d2 * _xyz._d2) +
                (_xyz._d3 * _xyz._d3);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double dotProduct(Vector other) {
        return (this._xyz._d1 * other._xyz._d1) +
                (this._xyz._d2 * other._xyz._d2) +
                (this._xyz._d3 * other._xyz._d3);
    }


    public Vector crossProduct(Vector other) {
        double ax = _xyz._d1;
        double ay = _xyz._d2;
        double az = _xyz._d3;
        double bx = other._xyz._d1;
        double by = other._xyz._d2;
        double bz = other._xyz._d3;
        double cx = ay*bz - az*by;
        double cy = az*bx - ax*bz;
        double cz = ax*by - ay*bx;
        return new Vector(cx, cy, cz);
    }

    public Vector normalize() {
        double length = length();
        return new Vector(_xyz.reduce(length));
    }
}
