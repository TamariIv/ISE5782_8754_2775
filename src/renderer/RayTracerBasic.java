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
                .add(point.geometry.getEmission())
                .add(calcLocalEffects(point, ray));
    }

    private Color calcLocalEffects(Intersectable.GeoPoint intersection, Ray ray){

        Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        int nShininess = intersection.geometry.getMaterial().nShininess;
        double kd = intersection.geometry.getMaterial().nShininess, ks = intersection.geometry.getMaterial().nShininess;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        int specularN = 1;
        double nl = alignZero(n.dotProduct(l));
        Vector r=l.subtract(n.scale(nl*2));
        double vr=Math.pow(v.scale(-1).dotProduct(r),nShininess);
        return lightIntensity.scale(ks*Math.pow(vr,specularN));
    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln=alignZero(Math.abs(n.dotProduct(l)));
        return lightIntensity.scale(kd*ln);
    }

}