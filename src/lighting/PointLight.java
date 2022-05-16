package lighting;

import primitives.*;

public class PointLight extends Light implements LightSource{
//
//
//    private Point position;
//    private double kC = 1;
//    private double kL = 0;
//    private double kQ = 0;
//
//    public PointLight(Color intensity, Point position) {
//        super(intensity);
//        this.position = position;
//    }
//
//
//    public PointLight setKc(double kC) {
//        this.kC = kC;
//        return this;
//    }
//
//    public PointLight setKl(double kL) {
//        this.kL = kL;
//        return this;
//    }
//
//    public PointLight setKq(double kQ) {
//        this.kQ = kQ;
//        return this;
//    }
//
//    @Override
//    public Color getIntensity(Point p) {
//        double distance = position.distance(p);
//        return super.getIntensity().scale(1d / (kC + kL * distance + kQ * distance * distance));
//    }
    private Point position;
    private Double3 kC = Double3.ONE;
    private Double3 kL = Double3.ZERO;
    private Double3 kQ = Double3.ZERO;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }


    public PointLight setKc(double kC) {
        this.kC = new Double3(kC);
        return this;
    }

    public PointLight setKc(Double3 kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(Double3 kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL =  new Double3(kL);
        return this;
    }

    public PointLight setKq(Double3 kQ) {
        this.kQ = kQ;
        return this;
    }
  public PointLight setKq(double kQ) {
        this.kQ =  new Double3(kQ);
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        Double3 factor = (kC.add(kL.scale(distance))).add(kQ.scale(distance*distance));

        return getIntensity().reduce(factor);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
