/*
 * http://stackoverflow.com/questions/19960368/how-to-make-sense-of-javafx-triangle-mesh
 * http://jperedadnr.blogspot.com/2015/01/creating-and-texturing-javafx-3d-shapes.html
 */
package testharness;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.fxyz.shapes.primitives.SpheroidMesh;

/**
 *
 * @author owner
 */
// drag the mouse over the rectangle to rotate it.
public class EarthMap extends Application {

    private static final double MAP_WIDTH = 8192 / 2d;
    private static final double MAP_HEIGHT = 4092 / 2d;

    private static final String DIFFUSE_MAP
            = "http://planetmaker.wthr.us/img/earth_gebco8_texture_8192x4096.jpg";
    private static final String NORMAL_MAP
            = "http://planetmaker.wthr.us/img/earth_normalmap_flat_8192x4096.jpg";
    private static final String SPECULAR_MAP
            = "http://planetmaker.wthr.us/img/earth_specularmap_flat_8192x4096.jpg";

    double anchorX, anchorY, anchorAngle;

    public static void main(String[] args) {
        launch(args);
    }

    private Group buildScene() {

//        final MeshView theShape = new MeshView(
//                new SegmentedTorusMesh()
//                new EarthMap.Shape3DRectangle(200, 200)
//        );
//        final MeshView theShape = new SegmentedTorusMesh();
//        final SurfacePlotMesh theShape = new SurfacePlotMesh();
//        theShape.setRangeX(200);
//        theShape.setRangeY(200);
        final SpheroidMesh theShape = new SpheroidMesh(128);

        PhongMaterial earthMaterial = new PhongMaterial();

        earthMaterial.setDiffuseMap(
                new Image(
                        DIFFUSE_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        earthMaterial.setBumpMap(
                new Image(
                        NORMAL_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        earthMaterial.setSpecularMap(
                new Image(
                        SPECULAR_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );

        theShape.setMaterial(
                earthMaterial
        );

        //       theShape.setMaterial(new PhongMaterial(Color.DARKGREEN));
        theShape.setRotationAxis(Rotate.Y_AXIS);
//        theShape.setTranslateX(250);
//        theShape.setTranslateY(250);

//        theShape.setScaleX(10);
//        theShape.setScaleY(10);
//        theShape.setScaleZ(10);
// try commenting this line out to see what it's effect is . . .
//        theShape.setCullFace(CullFace.NONE);
        return new Group(theShape);
    }

    private RotateTransition rotateAroundYAxis(Node node) {
        RotateTransition rotate = new RotateTransition(
                Duration.seconds(10),
                node
        );
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(360);
        rotate.setToAngle(0);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(RotateTransition.INDEFINITE);

        return rotate;
    }

    @Override
    public void start(Stage stage) {

        final Group group = buildScene();

        Scene scene = new Scene(
                new StackPane(group),
                500, 500,
                true,
                SceneAntialiasing.BALANCED
        );

        scene.setFill(Color.WHITE);

        scene.setCamera(new PerspectiveCamera());

        stage.setScene(scene);
        stage.show();

        stage.setFullScreen(false);

        rotateAroundYAxis(group).play();
    }

    public class Shape3DRectangle extends TriangleMesh {

        public Shape3DRectangle(float Width, float Height) {
            float[] points = {
                -Width / 2, -Height / 2, 0, // idx p0
                Width / 2, -Height / 2, 0, // idx p1
                -Width / 2, Height / 2, 0, // idx p2
                Width / 2, Height / 2, 0 // idx p3
            };
            float[] texCoords = {
                0, 0, // idx t0
                1, 0, // idx t1
                0, 1, // idx t2
                1, 1 // idx t3
            };

// if you use the co-ordinates as defined in the above comment, it will be all messed up
            int[] faces = {
                0, 0, 1, 1, 2, 2,
                3, 3, 2, 2, 1, 1
            };

// try defining faces in a counter-clockwise order to see what the difference is.
//            int[] faces = {
//                    2, 2, 1, 1, 0, 0,
//                    2, 2, 3, 3, 1, 1
//            };
// try defining faces in a clockwise order to see what the difference is.
//            int[] faces = {
//                2, 3, 0, 2, 1, 0,
//                2, 3, 1, 0, 3, 1
//            };
            this.getPoints().setAll(points);
            this.getTexCoords().setAll(texCoords);
            //this.getTexCoords().addAll(0,0);
            this.getFaces().setAll(faces);
        }
    }
}
