package renderer;

import geometries.Intersectable;
import lighting.*;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.*;

/**
 * RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
 */
public class RayTracerBasic extends RayTracerBase {

//    private static final double DELTA = 0.1;
    private static final double EPS = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Constructor
     *
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    /**
     * find intersections of the scene geometries with the
     * received ray ad calculate the color of the intersection points
     *
     * @param ray ray that was shot from the camera
     * @return color of the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint != null)
            return calcColor(closestPoint, ray);

        //ray did not intersect any geometrical object - return the background color
        return scene.background;
    }


    /**
     * Calculate the color intensity on the point
     *
     * @param gp on the geometry
     * @return the color intensity
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }


    private Color calcColor(GeoPoint geopoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geopoint, ray);
        return 1 == level ? color : color.add(calcGlobalEffects(geopoint, ray.getDir(), level, k));
    }


    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
//        double kkr = k * material.kR;
        Double3 kkr = material.kR.product(k);
//        if (kkr > MIN_CALC_COLOR_K)
        if (kkr.higherThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr));
//        double kkt = k * material.kT;
        Double3 kkt = material.kT.product(k);
//        if (kkt > MIN_CALC_COLOR_K)
        if (kkt.higherThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }


    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
//        return new Ray(point, ray.getDir());
        return new Ray(point, v, n);
    }

    //    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
//        return new Ray(point, v);
//    }
//    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
//        Vector vn = n.scale(-2 *  v.dotProduct(n));
//        Vector r = v.add(vn);
//        return new Ray(point, r);
//    }
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
//        v = v.normalize();
//        double vn = v.dotProduct(n);
//        if (isZero(vn)) {
//            return null;
//        }
//        Vector r = v.add(n.scale(-2d * vn));
//        return new Ray(point, r, n);

        Vector vn = n.scale(-2 * v.dotProduct(n));
        Vector r = v.add(vn);
        return new Ray(point, r, n);
    }


    private Color calcLocalEffects(Intersectable.GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        Color color = intersection.geometry.getEmission();

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        int nShininess = intersection.geometry.getMaterial().nShininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
//                if (unshaded(intersection, lightSource, l, n, nv)) {

                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, nl, lightIntensity),
                            calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
//                }
            }
        }
        return color;
    }


    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {
        l = l.normalize();
        Vector r = l.subtract(n.scale(2 * nl));
        double d = -alignZero(v.dotProduct(r));
        if (d <= 0)
            return Color.BLACK;

        return lightIntensity.scale(ks.scale(Math.pow(d, nShininess)));
    }

    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        if (nl < 0)
            nl = -nl;
        return lightIntensity.scale(kd).scale(nl);
    }

//    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
//        Point point = (Point) geopoint.point.add(delta);
//        Ray lightRay = new Ray(point, lightDirection);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//        if (intersections == null) return true;
//        double lightDistance = light.getDistance(geopoint.point);
//        for (GeoPoint gp : intersections) {
//            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0)
//                return false;
//        }
//        return true;
//    }

    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n, double nv) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
//        Point point = gp.point.add(delta);
        Ray lightRay = new Ray(gp.point, l.scale(-1), n);

        double maxDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, maxDistance);

        if (intersections == null) {
            return true;
        }
//        return false;

        for (var item : intersections)
            if (item.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K))
                return false;
        return true;
    }

    private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
        Ray lightRay = new Ray(gp.point, l.scale(-1), n);
        double maxDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, maxDistance);

        if (intersections == null) {
            return Double3.ONE;
        }

        Double3 transperancy = Double3.ONE;
        for (var geo : intersections) {
            transperancy = transperancy.product(geo.geometry.getMaterial().kT);
            if (transperancy.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        return transperancy;
    }


//    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Ray lightRay = new Ray(gp.point, lightDirection, n);
//
//        double lightDistance = ls.getDistance(gp.point);
//        var intersections = scene.geometries.findGeoIntersections(lightRay, lightDistance);
//        if (intersections == null)
//            return Double3.ONE;
//
//        Double3 tr = Double3.ONE;
//        for (var intersection : intersections) {
//            tr = tr.product(intersection.geometry.getMaterial().kT);
//            if (tr.lowerThan(MIN_CALC_COLOR_K))
//                return Double3.ZERO;
//        }
//
//        return tr;
//    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections != null)
            return ray.findClosestGeoPoint(intersections);
        return null;
    }

}