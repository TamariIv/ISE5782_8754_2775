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
    public void finalPic2() {
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

//
//                new Triangle(a,g,h)
//                        .setEmission(new Color(0, 75, 66))
//                        .setMaterial(new Material()
//                                .setkD(new Double3(0.6)).setkS(new Double3(0.4)).setkT(new Double3(0.6))
//                                .setShininess(80)),
//                new Triangle(a,b,h)
//                        .setEmission(new Color(0, 75, 66))
//                        .setMaterial(new Material()
//                                .setkD(new Double3(0.6)).setkS(new Double3(0.4)).setkT(new Double3(0.6))
//                                .setShininess(80)),
//                new Triangle(a,b,g)
//                        .setEmission(new Color(0, 75, 66))
//                        .setMaterial(new Material()
//                                .setkD(new Double3(0.6)).setkS(new Double3(0.4)).setkT(new Double3(0.6))
//                                .setShininess(80)),
//                new Triangle(g,b,h)
//                        .setEmission(new Color(0, 75, 66))
//                        .setMaterial(new Material()
//                                .setkD(new Double3(0.6)).setkS(new Double3(0.4)).setkT(new Double3(0.6))
//                                .setShininess(80))
        );


















//                                          new Polygon(new Point(-25,-50,-30),
//                                                         new Point(-25,-50,30),
//                                                         new Point(15,-50,30),
//                                                         new Point(15,-50,-30))
//                                                         .setEmission(new Color(0,75,100))
//                                                         .setMaterial(new Material()
//                                                                 .setkD(new Double3(0.6)).setkS(new Double3(0.4))
//                                                                 .setShininess(50)),
//                                                 new Polygon(new Point(-25,-25,-30),
//                                                         new Point(-25,-25,30),
//                                                         new Point(15,-25,30),
//                                                         new Point(15,-25,-30))
//                                                         .setEmission(new Color(0,75,100))
//                                                         .setMaterial(new Material()
//                                                                 .setkD(new Double3(0.6)).setkS(new Double3(0.4))
//                                                                 .setShininess(50))
//
//
//

                

//
//                        new Polygon(
//                                new Point(130, -60, -60), //
//                                new Point(-100, -60, 60),
//                                new Point(-100, -100, 60),  //
//                                new Point(130, -100, -60))  //
////                new Polygon(
////
////                        new Point(-120, -120, 500),
////                        new Point(-80, -120, 500),
////                        new Point(120, 60, 100),
////                        new Point(160, 60, 100))
//                        .setEmission(new Color(105,105,105))
//                        .setMaterial(new Material())

//
//        scene.lights.add(
//                new SpotLight(
//                        new Color(400, 400, 400),
//                        new Vector(-0.5, -1, -0.5),
//                        new Vector(-1, 0, 1))
//                        .setKl(0.004)
//                        .setKq(0.000006));
//        scene.lights.add(new SpotLight(new Color(255,228,181), new Point(80, 50, 0), new Vector(0, -1, 0)) //
//                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(800, 500, 250), new Point(30, 10, -100), new Vector(-2, -2, -2)).setKl(0.001).setKq(0.0001));
        ImageWriter imageWriter = new ImageWriter("finalPic", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }














    @Test
    public void finalPic() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add(
                new Polygon(
                        new Point(-95, -80, -60),
                        new Point(-100, -100, 60),
                        new Point(100, -100, 60),
                        new Point(95, -80, -60))
                        .setEmission(new Color(0, 150, 200))
                        .setMaterial(new Material()
                                .setkD(new Double3(0.6)).setkS(new Double3(0.4))
                                .setShininess(50)),
                new Polygon(
                        new Point(-95, -80, -60),
                        new Point(-70, 60, 200),
                        new Point(70, 60, 200),
                        new Point(95, -80, -60))
                        .setEmission(new Color(0, 255, 255))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80))
                , new Polygon(
                        new Point(-95, 120, -60),
                        new Point(-70, 60, 200),
                        new Point(70, 60, 200),
                        new Point(95, 120, -60))
                        .setEmission(new Color(255, 255, 0))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80))


                , new Sphere(new Point(-95, -80, -60), 5)
                        .setEmission(new Color(0, 255, 0))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80))
                , new Sphere(new Point(-70, 60, 200), 5)
                        .setEmission(new Color(255, 0, 255))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80))
                , new Sphere(new Point(70, 60, 200), 5)
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80))
                , new Sphere(new Point(95, -80, -60), 5)
                        .setEmission(new Color(255, 0, 0))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80))
                , new Sphere(new Point(0, 0, 0), 5)
                        .setEmission(new Color(255, 0, 0))
                        .setMaterial(new Material()
                                .setKd(0.3).setKs(0.8).setShininess(80))


        );

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("targil7pic", 600, 600);
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
}
