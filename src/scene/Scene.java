package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import primitives.Color;

import static java.awt.Color.black;

public class Scene {

    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries = null;

    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientlight) {
        this.ambientLight = ambientlight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

}