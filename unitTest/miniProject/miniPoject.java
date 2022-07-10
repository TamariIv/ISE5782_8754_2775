package miniProject;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import renderer.ImageWriter;
import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;


public class miniPoject {
    private Scene scene = new Scene("Test scene");

    @Test
    public void finalPic5() {
        Camera camera = new Camera(new Point(0, 70,900), new Vector(0, -0.05, -1), new Vector(0, 1, -0.05))
                .setVPSize(200, 200).setVPDistance(1000);
//        Camera camera = new Camera(new Point(0, 50, 1000), new Vector(0, -100, -1000), new Vector(0, 900, -90))
//                .setVPSize(200, 200).setVPDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add(
                // BASE
                new Polygon(
                        new Point(-60,0,220),
                        new Point(-60,0,280),
                        new Point(60,0,280),
                        new Point(60,0,220))
                        .setEmission(new Color(255, 230, 205)),

                // CUBE
                new Polygon(
                        new Point(20,0,240),
                        new Point(30,0,260),
                        new Point(30,20,260),
                        new Point(20,20,240))
                        .setEmission(new Color(255, 150, 105)),
                new Polygon(
                        new Point(20,0,240),
                        new Point(40,0,230),
                        new Point(40,20,230),
                        new Point(20,20,240))
                        .setEmission(new Color(255, 150, 105)),
                new Polygon(
                        new Point(50,0,250),
                        new Point(40,0,230),
                        new Point(40,20,230),
                        new Point(50,20,250))
                        .setEmission(new Color(255, 150, 105)),
                new Polygon(
                        new Point(50,0,250),
                        new Point(30,0,260),
                        new Point(30,20,260),
                        new Point(50,20,250))
                        .setEmission(new Color(255, 150, 105)),
                new Polygon(
                        new Point(30,20,260),
                        new Point(20,20,240),
                        new Point(40,20,230),
                        new Point(50,20,250))
                        .setEmission(new Color(255, 150, 105)),
                new Sphere(new Point(-25,15,230),15)
                        .setEmission(new Color(255, 150, 105)),
                new Sphere(new Point(-45,10,250),10)
                        .setEmission(new Color(255, 150, 105)),

                // PYRAMID
                new Polygon(
                        new Point(-10,0.1,250),
                        new Point(-30,0.1,260),
                        new Point(-10,0.1,270))
                        .setEmission(new Color(150, 150, 150)),
                new Polygon(
                        new Point(-15,15,260),
                        new Point(-10,0.1,250),
                        new Point(-30,0.1,260))
                        .setEmission(new Color(150, 150, 150)),
                new Polygon(
                        new Point(-15,15,260),
                        new Point(-30,0.1,260),
                        new Point(-10,0.1,270))
                        .setEmission(new Color(150, 150, 150)),
                new Polygon(
                        new Point(-15,15,260),
                        new Point(-10,0.1,250),
                        new Point(-10,0.1,270))
                        .setEmission(new Color(150, 150, 150)),

                // GLOSSY SURFACE
                new Polygon(
                        new Point(20,0,230),
                        new Point(0,0,230),
                        new Point(0,30,230),
                        new Point(20,30,230))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setkR(new Double3(0.8)).setkT(new Double3(0.2))),

                new Sphere(new Point(0,10,250),10)
                        .setEmission(new Color(255, 0, 85))
                        .setMaterial(new Material().setShininess(4).setkT(new Double3(0.7)).setkR(new Double3(0.5)))



                );
        scene.lights.add(new PointLight(new Color(200,150,255), new Point(0,50,250)));
        scene.lights.add(new SpotLight(new Color(100,205,150), new Point(0,50,245),new Vector(0,-1,0))
                .setKl(0.00005).setKq(0.00005));
        scene.lights.add(new DirectionalLight(new Color(200,200,200), new Vector(0,-1,0)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(0, 10, 250), new Vector(0, 1, 0)) //
                        .setKl(0.0004).setKq(0.0000006));
        scene.lights.add(new SpotLight(new Color(0,250,350), new Point(-200, 100, 0), new Vector(1, 1, -2)).setKc(40) //
                .setKl(0.00000005).setKq(0.000000005));
//        scene.lights.add(new SpotLight(new Color(0,250,350), new Vector(1, 0.5, -2), new Point(-200, 50, 0)).setSpecularN(20) //
//                .setkL(0.00000005).setkQ(0.000000005));
//        scene.lights.add(new SpotLight(new Color(0,250,350), new Vector(1, 1, -2), new Point3D(-200, 55, 0)).setSpecularN(10) //
//                .setkL(0.00000005).setkQ(0.000000005));


        ImageWriter imageWriter = new ImageWriter("try4", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void finalPic4() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));


        scene.geometries.add(
                // ROOM

                new Polygon( // floor
                        new Point(-100, -100, 200),
                        new Point(100, -100, 200),
                        new Point(60, -60, 100),
                        new Point(-60, -60, 100))
                        .setEmission(new Color(255, 230, 205))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                new Polygon( // back wall
                        new Point(-60, 60, 100),
                        new Point(60, 60, 100),
                        new Point(60, -60, 100),
                        new Point(-60, -60, 100))
                        .setEmission(new Color(192,192,192))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                new Polygon( // left wall
                        new Point(-60, 60, 100),
                        new Point(-100, 100, 200),
                        new Point(-100, -100, 200),
                        new Point(-60, -60, 100))
                        .setEmission(new Color(244,164,96))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                new Polygon( // right wall
                        new Point(100, 100, 200),
                        new Point(60, 60, 100),
                        new Point(60, -60, 100),
                        new Point(100, -100, 200))
                        .setEmission(new Color(244,164,96))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                new Polygon( // ceiling
                        new Point(-60, 60, 100),
                        new Point(60, 60, 100),
                        new Point(100, 100, 200),
                        new Point(-100, 100, 200))
                        .setEmission(new Color(255, 255, 255))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),

//                // CUPBOARD
//
//                new Polygon( // left
//                new Point(-20, 10, 100),
//                new Point(-30, 15, 110),
//                new Point(-30, -15, 110),
//                new Point(-20, -10, 100))
//                        .setEmission(new Color(205,150,55))
//                        .setMaterial(new Material()
//                                .setKd(0.3).setKs(0.7)),
//                new Polygon( // right
//                        new Point(20, 10, 100),
//                        new Point(30, 15, 110),
//                        new Point(30, -15, 110),
//                        new Point(20, -10, 100))
//                        .setEmission(new Color(205,150,55))
//                        .setMaterial(new Material()
//                                .setKd(0.3).setKs(0.7)),
//                new Polygon( // front
//                        new Point(-30, 15, 110),
//                        new Point(30, 15, 110),
//                        new Point(30, -15, 110),
//                        new Point(-30, -15, 110))
//                        .setEmission(new Color(205,150,55))
//                        .setMaterial(new Material()
//                                .setKd(0.3).setKs(0.7)),
//                new Polygon( // top
//                        new Point(-20, 10, 100),
//                        new Point(20, 10, 100),
//                        new Point(30, 15, 110),
//                        new Point(-30, 15, 110))
//                        .setEmission(new Color(255, 255, 255))
//                        .setMaterial(new Material()
//                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
//                new Polygon( // bottom
//                        new Point(-30, -15, 110),
//                        new Point(30, -15, 110),
//                        new Point(20, -10, 100),
//                        new Point(-20, -10, 100))
//                        .setEmission(new Color(230, 190, 105)),

//               new Cylinder(new Ray(3))
                
                // DESK

                new Polygon( // top
                        new Point(-40, -40, 120),
                        new Point(40, -40, 120),
                        new Point(30, -30, 100),
                        new Point(-30, -30, 100))
                        .setEmission(new Color(230, 190, 105)),
                new Polygon( // bottom
                        new Point(-40, -45, 120),
                        new Point(40, -45, 120),
                        new Point(30, -35, 100),
                        new Point(-30, -35, 100))
                        .setEmission(new Color(230, 190, 105)),
                new Polygon( // left
                        new Point(-30, -60, 100),
                        new Point(-30, -30, 100),
                        new Point(-40, -40, 120),
                        new Point(-40, -70, 120))
                        .setEmission(new Color(230, 190, 105)),
                new Polygon( // right
                        new Point(30, -60, 100),
                        new Point(30, -30, 100),
                        new Point(40, -40, 120),
                        new Point(40, -70, 120))
                        .setEmission(new Color(230, 190, 105)),


                // SHELVES

                                new Polygon( // bottom
                        new Point(-30, -15, 110),
                        new Point(30, -15, 110),
                        new Point(20, -10, 100),
                        new Point(-20, -10, 100))
                        .setEmission(new Color(230, 190, 105))
//                new Polygon( // bottom
//                        new Point(-30, -15, 110),
//                        new Point(30, -15, 110),
//                        new Point(20, -10, 100),
//                        new Point(-20, -10, 100))
//                        .setEmission(new Color(230, 190, 105))

//                new Polygon(
//                        new Point(40, -45, 120),
//                        new Point(6 -45, 120),
//                        new Point(36,-70, 120),
//                        new Point(40,-70, 120))
//                        .setEmission(new Color(230, 190, 105)),
//                new Polygon(
//                        new Point(-40, -45, 120),
//                        new Point(-36, -45, 120),
//                        new Point(-36,-70,120),
//                        new Point(-40,-70,120))
//                        .setEmission(new Color(230, 190, 105))



                );

//        scene.lights.add(
//                new SpotLight(new Color(255,255,255), new Point(0, 80, 150), new Vector(0,-1,0)).setKl(0.05).setKq(0.01));
//        scene.lights.add(new SpotLight(new Color(400,400,400), new Point(30,-40,170),new Vector(0,0,-1)).setKl(0.01).setKq(0.001));

        for (int i=0; i<6; i++)
        {

        }

        ImageWriter imageWriter = new ImageWriter("minip2", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void finalPic3() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))) ;
scene.geometries.add(
        new Polygon(
                new Point(-100, -100, 200),
                new Point(-30, 20, -20),
                new Point(70, 20, -20),
                new Point(100, -100, 200))
                .setEmission(new Color	(50,205,50))
                .setMaterial(new Material()
                        .setKd(0.3).setKs(0.8).setShininess(90).setkT(new Double3(0.7))),

        new Sphere(new Point(0, 10, 60), 15)
                .setEmission(new Color(128,0,0))
                     .setMaterial(new Material()
                             .setKd(0.5).setKs(0.5).setShininess(10))

);
        scene.lights.add(new SpotLight(new Color(400, 400, 400), new Point(-10, -10, 100), new Vector(0, -2, -2)).setKl(0.01).setKq(0.001));
                ImageWriter imageWriter = new ImageWriter("minip", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }



    @Test
    public void finalPic0() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))) ;
//        scene.background= new Color	(135,206,235);

        Point h=new Point(60,-50,30);
        Point g=new Point(45,-30,0);
        Point a=new Point(30,-50,30);
        Point b=new Point(40,0,15);

        scene.geometries.add(
                                new Polygon( // gray floor
                                        new Point(-100, -100, 200),
                                        new Point(100, -100, 200),
                                        new Point(60, -60, 100),
                                        new Point(-60, -60, 100))
                                        .setEmission(new Color	(0,0,0))
                                        .setMaterial(new Material()
                                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
//                                new Polygon( // yellow back wall
//                                        new Point(-60, 60, 100),
//                                        new Point(60, 60, 100),
//                                        new Point(60, -60, 100),
//                                        new Point(-60, -60, 100))
//                                        .setEmission(new Color(0,0,0))
//                                        .setMaterial(new Material()
//                                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                                new Polygon( // pink left wall
                                        new Point(-60, 60, 100),
                                        new Point(-100, 100, 200),
                                        new Point(-100, -100, 200),
                                        new Point(-60, -60, 100))
                                        .setEmission(new Color(0,0,0))
                                        .setMaterial(new Material()
                                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                                new Polygon( // pink right wall
                                        new Point(100, 100, 200),
                                        new Point(60, 60, 100),
                                        new Point(60, -60, 100),
                                        new Point(100, -100, 200))
                                        .setEmission(new Color(0,0,0))
                                        .setMaterial(new Material()
                                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                                new Polygon( // blue ceiling
                                        new Point(-60, 60, 100),
                                        new Point(60, 60, 100),
                                        new Point(100, 100, 200),
                                        new Point(-100, 100, 200))
                                        .setEmission(new Color(0,0,0))
                                        .setMaterial(new Material()
                                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),

                                new Sphere(new Point(50, -70, 0), 22).
                                        setEmission(new Color(105,105,105))
                                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                                new Sphere(new Point(55, -70, -5), 5)
                                        .setEmission(new Color(105,105,105))
                                        .setMaterial(new Material().setkR(new Double3(0.9))),
               new Sphere(new Point(20, -70, 60), 15)
                     .setEmission(new Color(200,105,80))
//                     .setMaterial(new Material()
//                             .setKd(0.5).setKs(0.5).setShininess(10))
        );

        scene.lights.add(new SpotLight(new Color(800, 500, 250), new Point(30, 10, -100), new Vector(-2, -2, -2)).setKl(0.001).setKq(0.0001));
        ImageWriter imageWriter = new ImageWriter("finalPic", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

















    @Test
    public void tryStuff() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
        scene.geometries.add(
                new Polygon(
                        new Point(-60, -20, 100),
                        new Point(30, -20, 100),
                        new Point(60, -40, 100),
                        new Point(-30, -40, 100))
                        .setEmission(new Color(255, 255, 0))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80))
                , new Sphere(new Point(0, -40, 100), 5)
                        .setEmission(new Color(0, 255, 0))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5)))

        );

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("try", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void tryStuff2() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
        scene.geometries.add(
                new Polygon( // green floor
                        new Point(-100, -100, 200),
                        new Point(100, -100, 200),
                        new Point(60, -60, 100),
                        new Point(-60, -60, 100))
                        .setEmission(new Color(0, 255, 0))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                new Polygon( // yellow back wall
                        new Point(-60, 60, 100),
                        new Point(60, 60, 100),
                        new Point(60, -60, 100),
                        new Point(-60, -60, 100))
                        .setEmission(new Color(255, 255, 0))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                new Polygon( // pink left wall
                        new Point(-60, 60, 100),
                        new Point(-100, 100, 200),
                        new Point(-100, -100, 200),
                        new Point(-60, -60, 100))
                        .setEmission(new Color(255, 0, 255))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                new Polygon( // pink right wall
                        new Point(100, 100, 200),
                        new Point(60, 60, 100),
                        new Point(60, -60, 100),
                        new Point(100, -100, 200))
                        .setEmission(new Color(255, 0, 255))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                new Polygon( // blue ceiling
                        new Point(-60, 60, 100),
                        new Point(60, 60, 100),
                        new Point(100, 100, 200),
                        new Point(-100, 100, 200))
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                new Sphere(new Point(0, -50, 160), 20)
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5))),
                new Sphere(new Point(20, -40, 120), 15)
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80).setkT(new Double3(0.5)))
        );


        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(255, 250, 220), new Point(0, 60, 180), new Vector(0, -1, 0)));

        ImageWriter imageWriter = new ImageWriter("try2", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }


    @Test
    public void tryStuff3() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(300, 300).setVPDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add(
                // ROOM

                new Polygon(
                        new Point(0,-120,0),
                        new Point(80,-120,80),
                        new Point(80,0,80),
                        new Point(0,0,0))
                        .setEmission(new Color(255, 255, 255)),
                new Polygon(
                        new Point(0,-120,0),
                        new Point(-80,-120,80),
                        new Point(-80,0,80),
                        new Point(0,0,0))
                        .setEmission(new Color(255, 0, 255)),
                new Polygon(
                        new Point(0,-120,0),
                        new Point(80,-120,80),
                        new Point(0,-120,160),
                        new Point(-80,-120,80))
                        .setEmission(new Color(0,255,255)),

                // DESK
                new Polygon(
                        new Point(0-1,-120,0),
                        new Point(20-1,-120,20),
                        new Point(20-1,-85,20),
                        new Point(0-1,-85,0))
                        .setEmission(new Color(255,255,0)),
                new Polygon(
                        new Point(-30-1,-120,30),
                        new Point(-10-1,-120,50),
                        new Point(-10-1,-85,50),
                        new Point(-30-1,-85,30))
                        .setEmission(new Color(255,0,0)),
                new Polygon(
                        new Point(20-1,-85,20),
                        new Point(0-1,-85,0),
                        new Point(-30-1,-85,30),
                        new Point(-10-1,-85,50))
                        .setEmission(new Color(255,0,0)),
                new Polygon(
                        new Point(20-1,-87,20),
                        new Point(0-1,-87,0),
                        new Point(-30-1,-87,30),
                        new Point(-10-1,-87,50))
                        .setEmission(new Color(255,0,0)),
                new Polygon(
                        new Point(20-1,-87,20),
                        new Point(-10-1,-87,50),
                        new Point(-10-1,-85,50),
                        new Point(20-1,-85,20))
                        .setEmission(new Color(255,0,0))




        );


        ImageWriter imageWriter = new ImageWriter("try3", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}

