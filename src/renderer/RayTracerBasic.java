package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 *  RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * constant for moving shading rays
     */
    private static final double DELTA = 0.1;
    private static final double EPS = 0.1;

    /**
     * Constructor
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * find intersections of the scene geometries with the
     * received ray ad calculate the color of the intersection points
     * @param ray ray that was shot from the camera
     * @return color of the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint, ray);
        }
        //ray did not intersect any geometrical object - return the background color
        return scene.background;
    }


    /**
     * Calculate the color intensity on the point
     * @param point on the geometry
     * @return the color intensity
     */
    private Color calcColor(Intersectable.GeoPoint point, Ray ray) {
//        Color baseColor = scene.ambientLight.getIntensity().add(point.geometry.getEmission());
//        return  baseColor;
//        // add calculated light contribution from all light sources)
//        return baseColor.add(calcLocalEffects(point, ray));;
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(point, ray));
    }

    private Color calcLocalEffects(Intersectable.GeoPoint intersection, Ray ray){

        Vector v = ray.getDir ();
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
                if (unshaded(intersection,lightSource, l, n ,nv)){
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, nl, lightIntensity),
                            calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v,int nShininess, Color lightIntensity) {
        l = l.normalize();
        Vector r = l.subtract(n.scale(2*nl));
        double d = -alignZero(v.dotProduct(r));
        if(d <= 0)
            return Color.BLACK;

        return lightIntensity.scale(ks.scale(Math.pow(d,nShininess)));
//        int specularN = 1;
//        double nl = alignZero(n.dotProduct(l));
//        Vector r=l.subtract(n.scale(nl*2));
//        double vr=Math.pow(v.scale(-1).dotProduct(r),nShininess);
//        return lightIntensity.scale(ks*Math.pow(vr,specularN));
    }

    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        if(nl < 0)
           nl = -nl;
        return lightIntensity.scale(kd).scale(nl);
//        double ln=alignZero(Math.abs(n.dotProduct(l)));
//        return lightIntensity.scale(kd*ln);
    }

    private boolean unshaded(GeoPoint gp ,LightSource lightSource, Vector l, Vector n,double nv) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector;
        if(nv < 0){
            epsVector=n.scale(EPS);
        }
        else {
            epsVector = n.scale(-EPS);
        }
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        double maxDistance = lightSource.getDistance(gp.point);
        if(intersections == null){
            return true;
        }
        for( var geo : intersections){
            double distance = geo.point.distance(gp.point);
            if(distance >= maxDistance){
                intersections.remove(geo);
            }
        }

        return ( intersections.isEmpty());
    }

//        private boolean unshaded(GeoPoint gp, Vector l, Vector n){
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Ray lightRay = new Ray(gp.point, lightDirection);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//        return intersections == null;
//
//    }

}