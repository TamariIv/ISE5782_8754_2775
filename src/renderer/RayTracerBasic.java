package renderer;

import geometries.Intersectable;
import lighting.*;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;
import static primitives.Util.*;

/**
 * RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final double EPS = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;


    private int glossinessRaysNum = 36;
    private double distanceGrid = 25;
    private double sizeGrid=4;
    protected  double beamRadius=20d;//the level of the light
    protected boolean softShadows=false;

     /**
     * search for shadow shape
     * @param gp is the point to check if it's unshaded or not
     * @param l is direction from light source to point
     * @param n the normal from the point
     * @param ls the light source
     * @return if the point is unshaded. it means- if there is no shade on it- it should have light.
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource ls, double nv) {
        Vector lightDirection = l.scale(-1); //vector from the point to the light source
        Vector deltaVector=n.scale(nv<0?DELTA:-DELTA);
        Point p=gp.point.add(deltaVector);
        Ray lightRay = new Ray(gp.point, lightDirection,n);
        double lightDistance = ls.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay); //new Ray(lightDistance)

        //if there are no intersections return true (there is no shadow)
        if (intersections == null) {
            return true;
        }

        for (GeoPoint intersection : intersections) {
            //for each intersection if there are points in the intersections list that are closer
            //to the point then light source, return false
            if (intersection.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Constructor
     *
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
        if (scene == null)
            return;
    }


    public void setDistanceGrid(double distanceGrid) {
        this.distanceGrid = distanceGrid;
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
     * An abstract function that get a list of ray and return the color of the avarege points that cross the rays
     * @param rays  that intersect the scene
     * @return Color
     */
    @Override
    public Color traceRay(List<Ray> rays)
    {
        Color finalColor=null;
        Color firstColor=null;
        Color colorTmp=new Color(0,0,0);
        for(var ray:rays)
        {
            List<GeoPoint> intersection = scene.geometries.findGeoIntersections(ray);
            if (intersection == null)
            {
                return scene.background;
            }
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersection);

            colorTmp=calcColor(closestPoint, ray) == null ? scene.background : calcColor(closestPoint, ray);
            if(finalColor==null)
            {
                firstColor=colorTmp;
                finalColor=new Color(0,0,0);
                for (int i = 0; i < 10; i++)
                    finalColor=finalColor.add(colorTmp);
            }

            if(!colorTmp.equals(firstColor))
                finalColor=finalColor.add(colorTmp);

        }
        if(finalColor.equals(firstColor))
            return firstColor;
        int size=rays.size()+10;
        return finalColor.reduce(size);
    }


    /**
     * make object color as point color
     * @param gp is the point we need to find the color of
     * @param ray is a received ray to calculate intersections with it and the scene
     * @return the color of the point received
     */
    public Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }


    /**
     * calculates the color of the point that the ray intersect it
     * (we already get here the closest intersection point)
     * @param intersection is a geo point
     * @param ray is the ray from the viewer
     * @param level of recursion- goes down each time till it gets to 1
     * @param k- factor of reflection and refraction so far
     * @return the color
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }

    /**
     * calc the global effects- reflection and refraction.
     * this func call "calcColor" in recursion to add more and more global effects.
     * @param gp is the point to calculate the global effects of
     * @param level of recursion- goes down each time till it gets to 1
     * @param k- factor of reflection and refraction so far
     * @return the color of the effect
     */
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


    /**
     * calc the global effects- reflection and refraction.
     * this func call "calcColor" in recursion to add more and more global effects.
     * @param ray is the ray from the viewer
     * @param level of recursion- goes down each time till it gets to 1
     * @param kx is kR or kT factor
     * @param kkx is kR or kT factor multiplied by k - factor of reflection and refraction
     * @return the color of the effect
     * new change
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    /**
     * this func returns a new ray- the refracted ray comes from the point because of the light- inRay
     * @param point is the point to calculate refraction with
     * @param v is the normalized ray from the viewer
     * @param n is the vector - normal from the point
     * @return RefractedRay
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * this func returns a new ray- the reflected ray comes from the point because of the light- inRay
     * @param point is the point to calculate reflection with
     * @param v is the normalized ray from the viewer
     * @param n is the normal from the point
     * @return ReflectedRay
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        Vector vn = n.scale(-2 * v.dotProduct(n));
        Vector r = v.add(vn);
        return new Ray(point, r, n);
    }


    /**
     * Calculate the local effect of light sources on a point
     * @param intersection is the point
     * @param ray is the ray from the viewer
     * @return the color of the local effect
     */
    private Color calcLocalEffects(Intersectable.GeoPoint intersection, Ray ray,Double3 k) {
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
                    Double3 ktr = transparency(intersection,lightSource,l,n);
                    if (!ktr.product(k).lowerThan( MIN_CALC_COLOR_K) ) //if (ktr * k > MIN_CALC_COLOR_K)
                    {
                        Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                        color = color.add(calcDiffusive(kd, nl, lightIntensity),
                                calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                    }


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

    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n, double nv) {
        Ray lightRay = new Ray(gp.point, l.scale(-1), n);

        double maxDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(lightRay, maxDistance);

        if (intersections == null) {
            return true;
        }
        for (var item : intersections)
            if (item.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K))
                return false;
        return true;
    }

    /**
     * this function is like unshaded but returns how much shading
     * @param gp the point
     * @param lightSource the light source
     * @param l from light source to point
     * @param n the normal
     * @return the transparency
     */
    private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
        Ray lightRay = new Ray(gp.point, l.scale(-1), n);
        double maxDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(lightRay, maxDistance);

        if (intersections == null) {
            return Double3.ONE;
        }

        Double3 transparency = Double3.ONE;
        for (var geo : intersections) {
            transparency = transparency.product(geo.geometry.getMaterial().kT);
            if (transparency.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        return transparency;
    }

//    private Double3 transparencyBeam(LightSource lightSource, Vector n, GeoPoint geoPoint,int beam) {
//        Double3 tempKtr = new Double3(0d);
//        List<Vector> beamL = lightSource.getBeamL(geoPoint.point, beamRadius,beam);
//
//        for (Vector vl : beamL) {
//            tempKtr = tempKtr.add(transparency( lightSource,n,vl,geoPoint,beam));
//        }
//        tempKtr = tempKtr.reduce(beamL.size());
//
//        return tempKtr;
//    }

    /**
     * find and return the closest point that intersects with the ray
     * @param ray is the ray from the viewer
     * @return the closest intersection to the ray
     */
    public GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections != null)
            return ray.findClosestGeoPoint(intersections);
        return null;
    }

    public Color AverageColor(LinkedList<Ray> rays){
        Color color=Color.BLACK;
        for( Ray ray:rays){
            color=color.add(traceRay(ray));
        }
        return color.reduce(Double.valueOf(rays.size()));
    }


    private List<Ray> constructReflectedRays(GeoPoint geoPoint, Ray ray, double Glossy) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(v.dotProduct(n));
        // r = v - 2*(v * n) * n
        Vector r = v.subtract(n.scale(2d * nv)).normalize();

        return raysGrid( new Ray(geoPoint.point,r,n),1,geoPoint.geometry.getMaterial().getkG(), n);
    }

    private List<Ray> constructRefractedRays(GeoPoint geoPoint, Ray inRay, Vector n) {
        return raysGrid(new Ray(geoPoint.point, inRay.getDir(), n),-1,geoPoint.geometry.getMaterial().getkG(), n);
    }
    List<Ray> raysGrid(Ray ray, int direction, double glossy, Vector n){
        int numOfRowCol = isZero(glossy)? 1: (int)Math.ceil(Math.sqrt(glossinessRaysNum));
        if (numOfRowCol == 1) return List.of(ray);
        Vector Vup ;
        double Ax= Math.abs(ray.getDir().getX()), Ay= Math.abs(ray.getDir().getY()), Az= Math.abs(ray.getDir().getZ());
        if (Ax < Ay)
            Vup= Ax < Az ?  new Vector(0, -ray.getDir().getZ(), ray.getDir().getY()) :
                    new Vector(-ray.getDir().getY(), ray.getDir().getX(), 0);
        else
            Vup= Ay < Az ?  new Vector(ray.getDir().getZ(), 0, -ray.getDir().getX()) :
                    new Vector(-ray.getDir().getY(), ray.getDir().getX(), 0);
        Vector Vright = Vup.crossProduct(ray.getDir()).normalize();
        Point pc=ray.getPoint(distanceGrid);
        double step = glossy/sizeGrid;
        Point pij=pc.add(Vright.scale(numOfRowCol/2*-step)).add(Vup.scale(numOfRowCol/2*-step));
        Vector tempRayVector;
        Point Pij1;

        List<Ray> rays = new ArrayList<>();
        rays.add(ray);
        for (int i = 1; i < numOfRowCol; i++) {
            for (int j = 1; j < numOfRowCol; j++) {
                Pij1=pij.add(Vright.scale(i*step)).add(Vup.scale(j*step));
                tempRayVector =  Pij1.subtract(ray.getP0());
                if(n.dotProduct(tempRayVector) < 0 && direction == 1) //refraction
                    rays.add(new Ray(ray.getP0(), tempRayVector));
                if(n.dotProduct(tempRayVector) > 0 && direction == -1) //reflection
                    rays.add(new Ray(ray.getP0(), tempRayVector));
            }
        }

        return rays;
    }
    /**
     * @param rays List of surrounding rays
     * @return average color
     */
    @Override
    public Color calcColorForSupersampling(List<Ray> rays) {

        Color bkg = scene.background;
        Color color = Color.BLACK;
        for (Ray ray : rays) {
            GeoPoint gp = findClosestIntersection(ray);
            color = color.add(gp == null ? bkg : calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(1d)));
        }
        color = color.add(scene.ambientLight.getIntensity());
        int size = rays.size();
        return (size == 1) ? color : color.reduce(size);
    }


}