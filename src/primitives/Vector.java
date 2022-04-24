package primitives;

public class Vector extends Point{


    /**
     * Vector ctor which receives three coordinates
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(getXyz().equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector (0,0,0) is not allowed");
    }

    /**
     * Vector ctor which receives an object of type Double3
     * @param xyz an object of type Double3
     */
    public Vector(Double3 xyz){
        super(xyz);
        if (_xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector (0,0,0) is not allowed");
        }
    }

    /**
     * the squared value of the vector’s length
     * @return the squared length
     */
    public double lengthSquared() {
        return (_xyz.d1 * _xyz.d1)  +
                (_xyz.d2 * _xyz.d2) +
                (_xyz.d3 * _xyz.d3);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * algebraic dot product
     * @param other object of type vector
     * @return the result
     */
    public double dotProduct(Vector other) {
        return (this._xyz.d1 * other._xyz.d1) +
                (this._xyz.d2 * other._xyz.d2) +
                (this._xyz.d3 * other._xyz.d3);
    }

    /**
     * algebraic cross product
     * @param other object of type vector
     * @return the result
     */
    public Vector crossProduct(Vector other) {
        double ax = _xyz.d1;
        double ay = _xyz.d2;
        double az = _xyz.d3;

        double bx = other._xyz.d1;
        double by = other._xyz.d2;
        double bz = other._xyz.d3;

        double cx = ay*bz - az*by;
        double cy = az*bx - ax*bz;
        double cz = ax*by - ay*bx;

        return new Vector(cx, cy, cz);
    }

    /**
     * normalize the vector
     * @return new vector which is the original vector, normalized
     */
    public Vector normalize() {
        double length = length();
        return new Vector(_xyz.reduce(length));
    }

    /**
     * add two vectors
     * @param v vector to add
     * @return the new vector
     */
    public Vector add(Vector v) {
        return new Vector(this._xyz.add(v._xyz));
    }

    /**
     * multiplication vector in scalar
     * @param d scale
     * @return the new vector
     */
    public Vector scale(double d) {
        return new Vector(this._xyz.scale(d));
    }
}
