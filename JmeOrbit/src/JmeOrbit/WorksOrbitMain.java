package JmeOrbit;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
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
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Curve;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;
import java.text.DecimalFormat;

public class WorksOrbitMain extends SimpleApplication {

    public static final double SUN_R = 696300.0;
    public static final double SUN_D = 149600000.0;
    public static final double EARTH_R = 6371.0;
    public static final double SC_A = 200.0;
    public static final double SC_R = EARTH_R + SC_A;

    private final Vector3f camVect;
    private Node camNode;

    Node earthSun = new Node("EarthSun");
    Sphere earthMesh;

    private boolean bIncFov;
    private boolean bDecFov;

    private boolean bIncX;
    private boolean bDecX;

    private boolean bIncY;
    private boolean bDecY;

    private boolean bIncZ;
    private boolean bDecZ;

    private boolean bYawRight;
    private boolean bYawLeft;

    private boolean bRollRight;
    private boolean bRollLeft;

    private boolean bCenter;

    private boolean bPitchUp;
    private boolean bPitchDown;

    private String camVectString;
    private BitmapText camVectBitmapText;

    private String camTopString;
    private BitmapText camTopBitmapText;

    private String camBottomString;
    private BitmapText camBottomBitmapText;

    private String camLeftString;
    private BitmapText camLeftBitmapText;

    private String camRightString;
    private BitmapText camRightBitmapText;

    private String camNearString;
    private BitmapText camNearBitmapText;

    private String camFarString;
    private BitmapText camFarBitmapText;

    private String camWidthString;
    private BitmapText camWidthBitmapText;

    private String camHeightString;
    private BitmapText camHeightBitmapText;

    float aspect;
    float fovY;

    private String aspectString;
    private BitmapText aspectBitmapText;

    private String fovYString;
    private BitmapText fovYBitmapText;

    public WorksOrbitMain() {
      
        this.camVect = new Vector3f(0f,
                (float) (-WorksOrbitMain.SC_R),
                0f);

        this.bIncFov = false;
        this.bDecFov = false;
        this.bIncY = false;
        this.bDecY = false;
        this.bYawRight = false;
        this.bYawLeft = false;
        this.bRollRight = false;
        this.bRollLeft = false;
        this.bCenter = false;
        this.bPitchUp = false;
        this.bPitchDown = false;

        this.aspect = (float) 1980.0f / 1200.0f;
        this.fovY = 50f;

    }

    public boolean isbIncX() {
        return bIncX;
    }

    public void setbIncX(boolean bIncX) {
        this.bIncX = bIncX;
    }

    public boolean isbDecX() {
        return bDecX;
    }

    public void setbDecX(boolean bDecX) {
        this.bDecX = bDecX;
    }

    public boolean isbIncY() {
        return bIncY;
    }

    public boolean isbIncZ() {
        return bIncZ;
    }

    public void setbIncZ(boolean bIncZ) {
        this.bIncZ = bIncZ;
    }

    public boolean isbDecZ() {
        return bDecZ;
    }

    public void setbDecZ(boolean bDecZ) {
        this.bDecZ = bDecZ;
    }

    public boolean isbIncFov() {
        return bIncFov;
    }

    public void setbIncFov(boolean bIncFov) {
        this.bIncFov = bIncFov;
    }

    public boolean isbDecFov() {
        return bDecFov;
    }

    public void setbDecFov(boolean bDecFov) {
        this.bDecFov = bDecFov;
    }

    public void setbIncY(boolean bIncY) {
        this.bIncY = bIncY;
    }

    public boolean isbDecY() {
        return bDecY;
    }

    public void setbDecY(boolean bDecY) {
        this.bDecY = bDecY;
    }

    public boolean isbYawRight() {
        return bYawRight;
    }

    public void setbYawRight(boolean bYawRight) {
        this.bYawRight = bYawRight;
    }

    public boolean isbYawLeft() {
        return bYawLeft;
    }

    public void setbYawLeft(boolean bYawLeft) {
        this.bYawLeft = bYawLeft;
    }

    public boolean isbRollRight() {
        return bRollRight;
    }

    public void setbRollRight(boolean bRollRight) {
        this.bRollRight = bRollRight;
    }

    public boolean isbRollLeft() {
        return bRollLeft;
    }

    public void setbRollLeft(boolean bRollLeft) {
        this.bRollLeft = bRollLeft;
    }

    public boolean isbCenter() {
        return bCenter;
    }

    public void setbCenter(boolean bCenter) {
        this.bCenter = bCenter;
    }

    public boolean isbPitchUp() {
        return bPitchUp;
    }

    public void setbPitchUp(boolean bPitchUp) {
        this.bPitchUp = bPitchUp;
    }

    public boolean isbPitchDown() {
        return bPitchDown;
    }

    public void setbPitchDown(boolean bPitchDown) {
        this.bPitchDown = bPitchDown;
    }

    public static void main(String[] args) {
        WorksOrbitMain app = new WorksOrbitMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        buildScene();
        registerInputs();

    }

    private void buildScene() {

        this.buildCamGui();
        //
        // Build latitudes
        //
        Geometry lats = buildLatitudesCurve();
        earthSun.attachChild(lats);

        this.earthMesh = new Sphere(64, 64, (float) (1f * WorksOrbitMain.EARTH_R));
        this.earthMesh.setTextureMode(Sphere.TextureMode.Projected); // better quality on spheres
        TangentBinormalGenerator.generate(this.earthMesh);           // for lighting effect
        Geometry sphereGeo = new Geometry("Earth", this.earthMesh);
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
        earthSun.attachChild(sphereGeo);

        //
        // Earth XYZ Axis Arrows
        //
        Node earthAxis = new Node("EarthAxis");

        Arrow xAxisMesh = new Arrow(Vector3f.UNIT_X.mult((float) (2f * WorksOrbitMain.EARTH_R)));
        Geometry xAxisGeo = new Geometry("X-Axis", xAxisMesh);
        Material xAxisMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        xAxisMat.setColor("Color", ColorRGBA.Red);
        xAxisGeo.setMaterial(xAxisMat);
        earthAxis.attachChild(xAxisGeo);

        Arrow yAxisMesh = new Arrow(Vector3f.UNIT_Y.mult((float) (2f * WorksOrbitMain.EARTH_R)));
        Geometry yAxisGeo = new Geometry("Y-Axis", yAxisMesh);
        Material yAxisMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        yAxisMat.setColor("Color", ColorRGBA.Green);
        yAxisGeo.setMaterial(yAxisMat);
        earthAxis.attachChild(yAxisGeo);

        Arrow zAxisMesh = new Arrow(Vector3f.UNIT_Z.mult((float) (2f * WorksOrbitMain.EARTH_R)));
        Geometry zAxisGeo = new Geometry("Z-Axis", zAxisMesh);
        Material zAxisMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        zAxisMat.setColor("Color", ColorRGBA.Blue);
        zAxisGeo.setMaterial(zAxisMat);
        earthAxis.attachChild(zAxisGeo);

        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 1f));
        earthSun.addLight(ambient);
        /*
         * Sun point ligth
         */
        PointLight sun = new PointLight();
        sun.setColor(ColorRGBA.White);
        sun.setRadius((float) ((1000f * WorksOrbitMain.SUN_D))); // NOTE!!!
        sun.setPosition(new Vector3f((float) (WorksOrbitMain.SUN_D), 0, 0));
        earthSun.addLight(sun);

        this.rootNode.attachChild(earthSun);
        this.rootNode.attachChild(earthAxis);

//        this.cam.setFrustumPerspective(this.fovY, this.aspect, 0.0000001f,
//                (float) (OrbitMain.SUN_D));
        //
        // TODO: Note range of Frustrum near to far
        //
        this.cam.setFrustumPerspective(this.fovY, this.aspect, 1f,
                (float) (WorksOrbitMain.SUN_D * 10f));
        this.camNode = new CameraNode("Camera", this.cam);
        this.camNode.setLocalTranslation(this.camVect);
        this.camNode.rotate(0f, 0f, -FastMath.HALF_PI);
        this.camNode.lookAt(sphereGeo.getLocalTranslation(), Vector3f.UNIT_Z);
        this.rootNode.attachChild(this.camNode);

    }

    private void buildCamGui() {

        float lineHeight = 0f;

        this.camVectBitmapText = new BitmapText(guiFont, false);
        camVectBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        camVectBitmapText.setColor(ColorRGBA.Magenta);
        this.camVectString = "camVect = (########0.0000000, ########0.0000000, ########0.0000000)";
        this.camVectBitmapText.setText(this.camVectString);
        camVectBitmapText.setBox(
                new Rectangle(settings.getWidth() - camVectBitmapText.getLineWidth(),
                        settings.getHeight(),
                        camVectBitmapText.getLineWidth(),
                        camVectBitmapText.getLineHeight()));
        camVectBitmapText.setAlignment(BitmapFont.Align.Right);
        camVectBitmapText.setLocalTranslation(0, 0, 0);
        guiNode.attachChild(camVectBitmapText);

        this.camTopBitmapText = new BitmapText(guiFont, false);
        camTopBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        camTopBitmapText.setColor(ColorRGBA.Orange);
        this.camTopString = "Top = ########0.0000000";
        camTopBitmapText.setText(this.camTopString);
        lineHeight -= camVectBitmapText.getLineHeight();

        camTopBitmapText.setBox(new Rectangle(settings.getWidth() - camTopBitmapText.getLineWidth(),
                settings.getHeight(),
                camTopBitmapText.getLineWidth(), camTopBitmapText.getLineHeight()));
        camTopBitmapText.setAlignment(BitmapFont.Align.Right);
        camTopBitmapText.setLocalTranslation(0, lineHeight, 0);
        guiNode.attachChild(camTopBitmapText);

        this.camBottomBitmapText = new BitmapText(guiFont, false);
        camBottomBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        camBottomBitmapText.setColor(ColorRGBA.Orange);
        this.camBottomString = "Bottom = ########0.0000000";
        camBottomBitmapText.setText(this.camBottomString);
        lineHeight -= camTopBitmapText.getLineHeight();

        camBottomBitmapText.setBox(new Rectangle(settings.getWidth() - camBottomBitmapText.getLineWidth(),
                settings.getHeight(),
                camBottomBitmapText.getLineWidth(), camBottomBitmapText.getLineHeight()));
        camBottomBitmapText.setAlignment(BitmapFont.Align.Right);
        camBottomBitmapText.setLocalTranslation(0, lineHeight, 0);
        guiNode.attachChild(camBottomBitmapText);

        this.camLeftBitmapText = new BitmapText(guiFont, false);
        camLeftBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        camLeftBitmapText.setColor(ColorRGBA.Orange);
        this.camLeftString = "Left = ########0.0000000";
        camLeftBitmapText.setText(this.camLeftString);
        lineHeight -= camBottomBitmapText.getLineHeight();

        camLeftBitmapText.setBox(new Rectangle(settings.getWidth() - camLeftBitmapText.getLineWidth(),
                settings.getHeight(),
                camLeftBitmapText.getLineWidth(), camLeftBitmapText.getLineHeight()));
        camLeftBitmapText.setAlignment(BitmapFont.Align.Right);
        camLeftBitmapText.setLocalTranslation(0, lineHeight, 0);
        guiNode.attachChild(camLeftBitmapText);

        this.camRightBitmapText = new BitmapText(guiFont, false);
        camRightBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        camRightBitmapText.setColor(ColorRGBA.Orange);
        this.camRightString = "Right = ########0.0000000";
        camRightBitmapText.setText(this.camRightString);
        lineHeight -= camLeftBitmapText.getLineHeight();

        camRightBitmapText.setBox(new Rectangle(settings.getWidth() - camRightBitmapText.getLineWidth(),
                settings.getHeight(),
                camRightBitmapText.getLineWidth(), camRightBitmapText.getLineHeight()));
        camRightBitmapText.setAlignment(BitmapFont.Align.Right);
        camRightBitmapText.setLocalTranslation(0, lineHeight, 0);
        guiNode.attachChild(camRightBitmapText);

        this.camNearBitmapText = new BitmapText(guiFont, false);
        camNearBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        camNearBitmapText.setColor(ColorRGBA.Orange);
        this.camNearString = "Near = ########0.0000000";
        camNearBitmapText.setText(this.camNearString);
        lineHeight -= camRightBitmapText.getLineHeight();

        camNearBitmapText.setBox(new Rectangle(settings.getWidth() - camNearBitmapText.getLineWidth(),
                settings.getHeight(),
                camNearBitmapText.getLineWidth(), camNearBitmapText.getLineHeight()));
        camNearBitmapText.setAlignment(BitmapFont.Align.Right);
        camNearBitmapText.setLocalTranslation(0, lineHeight, 0);
        guiNode.attachChild(camNearBitmapText);

        this.camFarBitmapText = new BitmapText(guiFont, false);
        camFarBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        camFarBitmapText.setColor(ColorRGBA.Orange);
        this.camFarString = "Far = ########0.0000000";
        camFarBitmapText.setText(this.camFarString);
        lineHeight -= camNearBitmapText.getLineHeight();

        camFarBitmapText.setBox(new Rectangle(settings.getWidth() - camFarBitmapText.getLineWidth(),
                settings.getHeight(),
                camFarBitmapText.getLineWidth(), camFarBitmapText.getLineHeight()));
        camFarBitmapText.setAlignment(BitmapFont.Align.Right);
        camFarBitmapText.setLocalTranslation(0, lineHeight, 0);
        guiNode.attachChild(camFarBitmapText);

        this.camWidthBitmapText = new BitmapText(guiFont, false);
        camWidthBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        camWidthBitmapText.setColor(ColorRGBA.Orange);
        this.camWidthString = "Width = ########0.0000000";
        camWidthBitmapText.setText(this.camWidthString);
        lineHeight -= camFarBitmapText.getLineHeight();

        camWidthBitmapText.setBox(new Rectangle(settings.getWidth() - camWidthBitmapText.getLineWidth(),
                settings.getHeight(),
                camWidthBitmapText.getLineWidth(), camWidthBitmapText.getLineHeight()));
        camWidthBitmapText.setAlignment(BitmapFont.Align.Right);
        camWidthBitmapText.setLocalTranslation(0, lineHeight, 0);
        guiNode.attachChild(camWidthBitmapText);

        this.camHeightBitmapText = new BitmapText(guiFont, false);
        camHeightBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        camHeightBitmapText.setColor(ColorRGBA.Orange);
        this.camHeightString = "Height = ########0.0000000";
        camHeightBitmapText.setText(this.camHeightString);
        lineHeight -= camWidthBitmapText.getLineHeight();

        camHeightBitmapText.setBox(new Rectangle(settings.getWidth() - camHeightBitmapText.getLineWidth(),
                settings.getHeight(),
                camHeightBitmapText.getLineWidth(), camHeightBitmapText.getLineHeight()));
        camHeightBitmapText.setAlignment(BitmapFont.Align.Right);
        camHeightBitmapText.setLocalTranslation(0, lineHeight, 0);
        guiNode.attachChild(camHeightBitmapText);

        this.aspectBitmapText = new BitmapText(guiFont, false);
        aspectBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        aspectBitmapText.setColor(ColorRGBA.Orange);
        this.aspectString = "Aspect = ########0.0000000";
        aspectBitmapText.setText(this.aspectString);
        lineHeight -= camHeightBitmapText.getLineHeight();

        aspectBitmapText.setBox(new Rectangle(settings.getWidth() - aspectBitmapText.getLineWidth(),
                settings.getHeight(),
                aspectBitmapText.getLineWidth(), aspectBitmapText.getLineHeight()));
        aspectBitmapText.setAlignment(BitmapFont.Align.Right);
        aspectBitmapText.setLocalTranslation(0, lineHeight, 0);
        guiNode.attachChild(aspectBitmapText);

        this.fovYBitmapText = new BitmapText(guiFont, false);
        fovYBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        fovYBitmapText.setColor(ColorRGBA.Orange);
        this.fovYString = "FOVY = ########0.0000000";
        fovYBitmapText.setText(this.fovYString);
        lineHeight -= aspectBitmapText.getLineHeight();

        fovYBitmapText.setBox(new Rectangle(settings.getWidth() - fovYBitmapText.getLineWidth(),
                settings.getHeight(),
                fovYBitmapText.getLineWidth(), fovYBitmapText.getLineHeight()));
        fovYBitmapText.setAlignment(BitmapFont.Align.Right);
        fovYBitmapText.setLocalTranslation(0, lineHeight, 0);
        guiNode.attachChild(fovYBitmapText);

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
        latMesh.setLineWidth(5f);
        Geometry latGeo = new Geometry(latName, latMesh);
        Material latMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        latMat.setColor("Color", ColorRGBA.Orange);
        latGeo.setMaterial(latMat);

        latGeo.scale((float) (WorksOrbitMain.EARTH_R * 1.001f));
        latGeo.rotate(FastMath.HALF_PI, FastMath.HALF_PI, 0f);

        return latGeo;
    }

    private void registerInputs() {

        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_MEMORY);
        this.flyCam.setEnabled(false);

        inputManager.addMapping("IncFov", new KeyTrigger(KeyInput.KEY_MINUS));
        inputManager.addMapping("DecFov", new KeyTrigger(KeyInput.KEY_EQUALS));

        inputManager.addMapping("IncX", new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("DecX", new KeyTrigger(KeyInput.KEY_Z));

        inputManager.addMapping("IncY", new KeyTrigger(KeyInput.KEY_Y));
        inputManager.addMapping("DecY", new KeyTrigger(KeyInput.KEY_T));

        inputManager.addMapping("IncZ", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("DecZ", new KeyTrigger(KeyInput.KEY_A));

        inputManager.addMapping("YawRight", new KeyTrigger(KeyInput.KEY_END),
                new KeyTrigger(KeyInput.KEY_NUMPAD1));
        inputManager.addMapping("YawLeft", new KeyTrigger(KeyInput.KEY_PGDN),
                new KeyTrigger(KeyInput.KEY_NUMPAD3));

        inputManager.addMapping("RollRight", new KeyTrigger(KeyInput.KEY_RIGHT),
                new KeyTrigger(KeyInput.KEY_NUMPAD6));
        inputManager.addMapping("RollLeft", new KeyTrigger(KeyInput.KEY_LEFT),
                new KeyTrigger(KeyInput.KEY_NUMPAD4));

        inputManager.addMapping("Center", new KeyTrigger(KeyInput.KEY_SPACE),
                new KeyTrigger(KeyInput.KEY_NUMPAD5));

        inputManager.addMapping("PitchUp", new KeyTrigger(KeyInput.KEY_UP),
                new KeyTrigger(KeyInput.KEY_NUMPAD8));
        inputManager.addMapping("PitchDown", new KeyTrigger(KeyInput.KEY_DOWN),
                new KeyTrigger(KeyInput.KEY_NUMPAD2));

        ActionListener rotationListener;
        rotationListener = (String name, boolean keyPressed, float tpf) -> {
            if (name.equals("IncFov")) {
                setbIncFov(keyPressed);
            }
            if (name.equals("DecFov")) {
                setbDecFov(keyPressed);
            }

            if (name.equals("IncX")) {
                setbIncX(keyPressed);
            }
            if (name.equals("DecX")) {
                setbDecX(keyPressed);
            }

            if (name.equals("IncY")) {
                setbIncY(keyPressed);
            }
            if (name.equals("DecY")) {
                setbDecY(keyPressed);
            }

            if (name.equals("IncZ")) {
                setbIncZ(keyPressed);
            }
            if (name.equals("DecZ")) {
                setbDecZ(keyPressed);
            }

            if (name.equals("YawRight")) {
                setbYawRight(keyPressed);
            }
            if (name.equals("YawLeft")) {
                setbYawLeft(keyPressed);
            }

            if (name.equals("RollRight")) {
                setbRollRight(keyPressed);
            }
            if (name.equals("RollLeft")) {
                setbRollLeft(keyPressed);
            }

            if (name.equals("Center")) {
                setbCenter(keyPressed);
            }

            if (name.equals("PitchUp")) {
                setbPitchUp(keyPressed);
            }
            if (name.equals("PitchDown")) {
                setbPitchDown(keyPressed);
            }
        };

        this.inputManager.addListener(rotationListener, "IncFov", "DecFov",
                "IncX", "DecX",
                "IncY", "DecY",
                "IncZ", "DecZ",
                "YawRight", "YawLeft",
                "RollRight", "RollLeft",
                "Center",
                "PitchUp", "PitchDown");

    }

    @Override
    public void simpleUpdate(float tpf) {

        this.updateCamGui();
        //
        // Rotate earth
        //
        this.rootNode.getChild("EarthSun").rotate(0, 0, FastMath.DEG_TO_RAD * 0.01f);
        Vector3f earthSunGlt
                = this.rootNode.getChild("EarthSun").getLocalTranslation();
        //
        // IncDecrement Camera 
        //
        if (this.bIncFov) {
            this.fovY = this.fovY + 0.01f;
            this.cam.setFrustumPerspective(this.fovY, this.aspect,
                    this.cam.getFrustumNear(), this.cam.getFrustumFar());
            this.rootNode.getChild("Camera").setLocalTranslation(camVect);

        }
        if (this.bDecFov) {
            this.fovY = this.fovY - 0.01f;
            this.cam.setFrustumPerspective(this.fovY, this.aspect,
                    this.cam.getFrustumNear(), this.cam.getFrustumFar());
            this.rootNode.getChild("Camera").setLocalTranslation(camVect);

        }

        float incf = (float) (0.00125f * WorksOrbitMain.EARTH_R);
        if (this.bIncX) {
            this.camVect.x = this.camVect.x + incf;
            this.rootNode.getChild("Camera").setLocalTranslation(camVect);
        }
        if (this.bDecX) {
            this.camVect.x = this.camVect.x - incf;
            this.rootNode.getChild("Camera").setLocalTranslation(camVect);
        }

        if (this.bIncY) {
            this.camVect.y = this.camVect.y + incf;
            this.rootNode.getChild("Camera").setLocalTranslation(camVect);
        }
        if (this.bDecY) {
            this.camVect.y = this.camVect.y - incf;
            this.rootNode.getChild("Camera").setLocalTranslation(camVect);
        }

        if (this.bIncZ) {
            this.camVect.z = this.camVect.z + incf;
            this.rootNode.getChild("Camera").setLocalTranslation(camVect);
        }
        if (this.bDecZ) {
            this.camVect.z = this.camVect.z - incf;
            this.rootNode.getChild("Camera").setLocalTranslation(camVect);
        }

        if (this.bYawRight) {
            this.camNode.rotate(0, FastMath.TWO_PI / 10000, 0);
        }
        if (this.bYawLeft) {
            this.camNode.rotate(0, -FastMath.TWO_PI / 10000, 0);
        }

        if (this.bRollRight) {
            System.out.println("this.camNode.getLocalRotation()=" + this.camNode.getLocalRotation());
            float[] angles = {0f, 0f, 0f};
            angles = this.camNode.getLocalRotation().toAngles(angles);
            System.out.println("z=" + angles[2] * FastMath.RAD_TO_DEG);

            this.camNode.rotate(0, 0, FastMath.TWO_PI / 10000);
        }
        if (this.bRollLeft) {
            System.out.println("this.camNode.getLocalRotation()=" + this.camNode.getLocalRotation());
            float[] angles = {0f, 0f, 0f};
            angles = this.camNode.getLocalRotation().toAngles(angles);
            System.out.println("z=" + angles[2] * FastMath.RAD_TO_DEG);

            this.camNode.rotate(0, 0, -FastMath.TWO_PI / 10000);
        }

        if (this.bCenter) {
            this.rootNode.getChild("Camera").lookAt(earthSunGlt, Vector3f.UNIT_Z);
            Vector3f camUpVec = this.cam.getUp();
            this.setbCenter(false);
        }

        if (this.bPitchUp) {
            this.camNode.rotate(FastMath.TWO_PI / 10000, 0, 0);
        }
        if (this.bPitchDown) {
            this.camNode.rotate(-FastMath.TWO_PI / 10000, 0, 0);

        }

        float zs = this.earthMesh.getZSamples();
        float radius = this.earthMesh.getRadius();
        float camdis = this.camVect.length();
        float alt = camdis - radius;

        if ((alt < 500) && (zs < 128)) {
            this.earthMesh.updateGeometry(128, 128, radius);
            System.out.println("alt=" + alt);
        }
        if ((alt > 500) && (zs > 64)) {
            this.earthMesh.updateGeometry(64, 64, radius);
            System.out.println("alt=" + alt);
        }
        if ((alt < 5000) && (zs < 64)) {
            this.earthMesh.updateGeometry(64, 64, radius);
            System.out.println("alt=" + alt);
        }
        if ((alt > 5000) && (zs > 32)) {
            this.earthMesh.updateGeometry(32, 32, radius);
            System.out.println("alt=" + alt);
        }
        if ((alt < 10000) && (zs < 32)) {
            this.earthMesh.updateGeometry(32, 32, radius);
            System.out.println("alt=" + alt);
        }
        if ((alt > 10000) && (zs > 16)) {
            this.earthMesh.updateGeometry(16, 16, radius);
            System.out.println("alt=" + alt);
        }
        if ((alt < 100000) && (zs < 16)) {
            this.earthMesh.updateGeometry(16, 16, radius);
            System.out.println("alt=" + alt);
        }
        if ((alt > 100000) && (zs > 8)) {
            this.earthMesh.updateGeometry(8, 8, radius);
            System.out.println("alt=" + alt);
        }

    }

    private void updateCamGui() {

        DecimalFormat floatFormatter = new DecimalFormat("########0.0000000");
        this.camVectString = "camVect = (";
        this.camVectString = this.camVectString
                + floatFormatter.format(this.camVect.x)
                + ", "
                + floatFormatter.format(this.camVect.y)
                + ", "
                + floatFormatter.format(this.camVect.z)
                + ")";
        this.camVectBitmapText.setText(this.camVectString);

        this.camTopString = "Top = "
                + floatFormatter.format(this.cam.getFrustumTop());
        camTopBitmapText.setText(this.camTopString);

        this.camBottomString = "Bottom = "
                + floatFormatter.format(this.cam.getFrustumBottom());
        camBottomBitmapText.setText(this.camBottomString);

        this.camLeftString = "Left = "
                + floatFormatter.format(this.cam.getFrustumLeft());
        camLeftBitmapText.setText(this.camLeftString);

        this.camRightString = "Right = "
                + floatFormatter.format(this.cam.getFrustumRight());
        camRightBitmapText.setText(this.camRightString);

        this.camNearString = "Near = "
                + floatFormatter.format(this.cam.getFrustumNear());
        camNearBitmapText.setText(this.camNearString);

        this.camFarString = "Far = "
                + floatFormatter.format(this.cam.getFrustumFar());
        camFarBitmapText.setText(this.camFarString);

        this.camWidthString = "Width = "
                + floatFormatter.format(this.cam.getWidth());
        camWidthBitmapText.setText(this.camWidthString);

        this.camHeightString = "Height = "
                + floatFormatter.format(this.cam.getHeight());
        camHeightBitmapText.setText(this.camHeightString);

        this.aspectString = "Aspect = "
                + floatFormatter.format(this.aspect);
        aspectBitmapText.setText(this.aspectString);

        this.fovYString = "FOVY = "
                + floatFormatter.format(this.fovY);
        fovYBitmapText.setText(this.fovYString);

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
