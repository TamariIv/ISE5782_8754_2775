/**
 * 
 */
package lighting;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import renderer.ImageWriter;
import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(new Double3(0.3))),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracerBase(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setkT(new Double3(0.5))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 20, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setkR(new Double3(1))),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setkR(new Double3(0.5))));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracerBase(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(new Double3(0.6))));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracerBase(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	@Test
	public void finalPic() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
				.setVPSize(200, 125).setVPDistance(800);
		//scene.setAmbientLight(new AmbientLight(new Color(red), new Double3(0.5)));
//		scene.geometries.add(
//		new Polygon(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150), new Point(-70, 70, -140))
//				.setMaterial(new Material().setKd(0.7).setKs(0.15).setShininess(100)));
		scene.geometries.add(
				new Polygon(new Point(-25, -50, -30),
						new Point(-25, -50, 30),
						new Point(15, -50, 30),
						new Point(15, -50, -30)).
						setEmission(new Color(0, 75, 100))
						.setMaterial(new Material()
								.setkD(new Double3(0.6)).setkS(new Double3(0.4))
								.setShininess(50)));
		scene.geometries.add(new Polygon(new Point(-30, -40, 60),
				new Point(-30, 40, 60),
				new Point(25, 40, 50))
				.setEmission(new Color(10, 80, 120)).setMaterial(new Material().setKd(0.3).setkS(new Double3(0.2))
						.setShininess(70)));

//				new Polygon(new Point(0, 0, 30), new Point(30, 0, 0), new Point(0, 30, 0), new Point(-30, 30, 30))
//						.setMaterial(new Material().setKd(0.7).setKs(0.15).setShininess(100))


		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("targil7pic", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracerBase(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}


//	/*****************************************************************************************************************
//	 *
//	 */
//	private Scene scene2 = new Scene("final image scene")
//			.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));
//
//	private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//			.setVPSize(200, 200)
//			.setVPDistance(1000);
//
//	private static Geometry sphere1 = new Sphere(new Point(0, 0, 0),50)
//			.setEmission(new Color(java.awt.Color.BLACK))
//			.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setkT(new Double3(0.1)).setkR(Double3.ONE));
//	private static Geometry sphere2 = new Sphere(new Point(0, 0, 0),80)
//			.setEmission(new Color(java.awt.Color.BLUE))
//			.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(Double3.ONE).setkR(Double3.ZERO));
//	private static Geometry sphere3 = new Sphere(new Point(-75, -75, 0), 40)
//			.setEmission(new Color(java.awt.Color.YELLOW))
//			.setMaterial(new Material().setkT(Double3.ZERO));
//	private static Geometry sphere4 = new Sphere( new Point(-75, -40, 0),20)
//			.setEmission(new Color(java.awt.Color.GREEN))
//			.setMaterial(new Material().setkT(new Double3(0.5)));
//	private static Geometry sphere5 = new Sphere( new Point(-80, 20, 0),10)
//			.setEmission(new Color(java.awt.Color.YELLOW))
//			.setMaterial(new Material().setkT(new Double3(0.5)));
//	private static Geometry sphere6 = new Sphere( new Point(-100, 50, 0),25)
//			.setEmission(new Color(java.awt.Color.WHITE))
//			.setMaterial(new Material().setkT(Double3.ZERO));
//	private static Geometry sphere7 = new Sphere( new Point(-30, 100, 0),40)
//			.setEmission(new Color(java.awt.Color.CYAN))
//			.setMaterial(new Material().setkT(Double3.ONE));
//
//	private static Geometry triangle1 = new Triangle(
//			new Point(75, 150, -150), new Point(150, 75, -150), new Point(0, 0, 0))
//			.setEmission(new Color(java.awt.Color.GREEN))
//			.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50).setkT(Double3.ONE));
//	private static Geometry triangle2 = new Triangle(
//			new Point(200, 50, -150), new Point(200, 0, -150), new Point(0, 0, 0))
//			.setEmission(new Color(java.awt.Color.GREEN))
//			.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50).setkT(new Double3(0.8)));
//	private static Geometry triangle3 = new Triangle(
//			new Point(200, -20, -150), new Point(200, -200, -150), new Point(0, 0, 0))
//			.setEmission(new Color(java.awt.Color.GREEN))
//			.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50).setkT(new Double3(0.4)));
//	private static Geometry triangle4 = new Triangle(
//			new Point(180, -200, -150), new Point(50, -200, -150), new Point(0, 0, 0))
//			.setEmission(new Color(java.awt.Color.GREEN))
//			.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50).setkT(Double3.ZERO));
//
//
//	/**
//	 * run the test of the final image
//	 */
//	@Test
//	public void TestFinalImage(){
//		//adding geometries to the scene
//		scene.geometries.add(sphere1, sphere2, sphere3, sphere4, sphere5, sphere6, sphere7,
//				triangle1, triangle2, triangle3, triangle4);
//		//adding lights to the scene
//		scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point(0, 0, 0), new Vector(1, 1, 0)));
//		scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point(0, 0, 0), new Vector(1, -1, 0)));
//		scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point(0, 0, 0), new Vector(-1, 1, 0)));
//		scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point(0, 0, 0), new Vector(-1, -1, 0)));
//		//write the image of the scene
//		ImageWriter imageWriter = new ImageWriter("Final Image", 500, 500);
//		//rendering the scene
//		Render render = new Render()
//				.setImageWriter(imageWriter)
//				.setCamera(camera)
//				.setRayTracer(new BasicRayTracer(scene));
//		render.renderImageSuperSampling();
//		render.writeToImage();
//	}
//
//	private static final Point[] pnts = new Point[]{null, //
//			new Point(40.6266, 28.3457, -1.10804), //
//			new Point(40.0714, 30.4443, -1.10804), //
//			new Point(40.7155, 31.1438, -1.10804), //
//			new Point(42.0257, 30.4443, -1.10804), //
//			new Point(43.4692, 28.3457, -1.10804), //
//			new Point(37.5425, 28.3457, 14.5117), //
//			new Point(37.0303, 30.4443, 14.2938), //
//			new Point(37.6244, 31.1438, 14.5466), //
//			new Point(38.8331, 30.4443, 15.0609), //
//			new Point(40.1647, 28.3457, 15.6274)}; //
//	@Test
//	public void teapotAnimation() {
//		scene.lights.add(new PointLight(new Color(500, 500, 500), new Point(100, 0, -100)));
////				.setkQ(0.000001));
//		scene.geometries.add( //
//				new Triangle(pnts[7], pnts[6], pnts[1]).setEmission(new Color(50,70,80)), //
//				new Triangle(pnts[1], pnts[2], pnts[7]).setEmission(new Color(50,70,80)), //
//				new Triangle(pnts[8], pnts[7], pnts[2]).setEmission(new Color(50,70,80)));
//
//		Camera camera = new Camera(
//				new Point(0, 0, -1000),
//				new Vector(0, 0, -1),
//				new Vector(0, 1, 0)) //
//				.setVPDistance(1000).setVPSize(200, 200);
//
//
//		int frames = 10;
//		double angle = 360d / frames;
//		double angleRadians = 2 * Math.PI / frames;
//
//		double radius = camera.getP0().subtract(Point.ZERO).length();
//
//		for (int i = 0; i < frames; i++) {
//			System.out.println("Start frame " + (i + 1));
//
//
//			ImageWriter imageWriter = new ImageWriter("teapotAnimation/teapotFrame" + (i + 1), 800, 800);
//			camera.setImageWriter(imageWriter) //
//				.setRayTracerBase(new RayTracerBasic(scene)) //
//				.renderImage() //
//				.writeToImage();
////			render.setImageWriter(imageWriter);
////			render.renderImage();
////			render.setMultithreading(3);
////			render.writeToImage();
//		}
//	}
//}
}
