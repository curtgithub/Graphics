package JmeOrbit;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Curve;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

public class LatitudeTestMain extends SimpleApplication {

    private Node sphereNode = new Node("EarthNode");
    private Node camNode;

    LatitudeTestMain() {

    }

    public static void main(String[] args) {
        LatitudeTestMain app = new LatitudeTestMain();
        app.start();
    }

    public static final int SEGMENTS = 64;
    public static final float RAD_PER_SEGMENTS = FastMath.TWO_PI / SEGMENTS;

    @Override
    public void simpleInitApp() {
        //
        // Add Sphere
        //
        Sphere sphereMesh = new Sphere(64, 64, 1f);
        sphereMesh.setTextureMode(Sphere.TextureMode.Projected); // better quality on spheres
        TangentBinormalGenerator.generate(sphereMesh);           // for lighting effect

        Geometry sphereGeo = new Geometry("Earth", sphereMesh);
        Material sphereMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        assetManager.registerLocator("D:\\data\\jmonkey\\CurtJmeTests\\src\\jpegs\\", FileLocator.class);
        Texture t = assetManager.loadTexture("earthm.jpg");
        sphereMat.setTexture("DiffuseMap", t);
        sphereMat.setTexture("SpecularMap", t);
        sphereMat.setBoolean("UseMaterialColors", true);
        sphereMat.setColor("Ambient", ColorRGBA.White);
        sphereMat.setColor("Diffuse", ColorRGBA.White);
        sphereMat.setColor("Specular", ColorRGBA.White);
        sphereGeo.setMaterial(sphereMat);
        sphereNode.attachChild(sphereGeo);
        this.rootNode.attachChild(sphereNode);
        //
        // Add lattitudew
        //
        Geometry lats = buildLatitudesCurve();
        this.sphereNode.attachChild(lats);

        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 1f));
        sphereNode.addLight(ambient);
        /*
         * Sun point ligth
         */
        PointLight sun = new PointLight();
        sun.setColor(ColorRGBA.White);
        sun.setPosition(new Vector3f(5f, 0, 0));
        sphereNode.addLight(sun);

        this.camNode = new CameraNode("Camera", this.cam);
        this.camNode.setLocalTranslation(new Vector3f(5f, 0f, 0f));
        this.camNode.lookAt(sphereGeo.getLocalTranslation(), Vector3f.UNIT_Z);
        this.rootNode.attachChild(this.camNode);
    }

    private void addSegment(String geoName, Vector3f start, Vector3f end) {

        Line lineMesh = new Line(start, end);
        lineMesh.setLineWidth(2f);
        Geometry lineGeo = new Geometry(geoName, lineMesh);
        lineGeo.rotate(FastMath.HALF_PI, FastMath.HALF_PI, 0);
        start = end;

        Material lineMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        lineMat.setColor("Color", ColorRGBA.Red);
        lineGeo.setMaterial(lineMat);

        this.sphereNode.attachChild(lineGeo);
    }

    private Geometry buildLatitudesCurve() {

        int SEGMENTS = 64;
        float RAD_PER_SEGMENTS = FastMath.TWO_PI / SEGMENTS;

        Vector3f[] controlPoints = new Vector3f[SEGMENTS + 1];
        String latName = "Latitude ";
        for (int i = 0; i < (SEGMENTS + 1); i++) {
            float x = FastMath.cos(i * RAD_PER_SEGMENTS);
            float y = FastMath.sin(i * RAD_PER_SEGMENTS);
            Vector3f controlPoint = new Vector3f(x, y, 0);
            controlPoints[i] = controlPoint;
        }

        Curve latMesh = new Curve(controlPoints, 1);
        Geometry latGeo = new Geometry(latName, latMesh);
        Material latMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        latMat.setColor("Color", ColorRGBA.Orange);
        latGeo.setMaterial(latMat);

        latGeo.scale(1.001f);
        latGeo.rotate(FastMath.HALF_PI, FastMath.HALF_PI, 0f);

        return latGeo;
    }

    @Override
    public void simpleUpdate(float tpf) {
        this.rootNode.getChild("EarthNode").rotate(0, 0, FastMath.DEG_TO_RAD * 0.01f);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
