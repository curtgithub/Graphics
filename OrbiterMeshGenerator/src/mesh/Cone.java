/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mesh;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author owner
 */
public class Cone extends Mesh {

    private int numSteps;

    public Cone() {
    }

    public void generate(int numSteps, double radius, double length) {
        this.numSteps = numSteps;
        // TOD0: numSteps should be even and greater than 4
        /*
         if (this.numQuads % 16 != 0) {
         LOG.warning("numQuads must be divisable by 16.  Setting to 64.");
         this.numQuads = 64;
         }
         */
        this.vertices = new ArrayList<>();
        this.triangles = new ArrayList<>();
        double radSteps = 2 * Math.PI / this.numSteps;
        double rads = 0.0;
        this.vertices.add(new VertexCoordinates(0.0, 0.0, length));
        this.vertices.add(new VertexCoordinates(radius * Math.cos(rads), radius * Math.sin(rads), 0.0));
        rads += radSteps;
        this.vertices.add(new VertexCoordinates(radius * Math.cos(rads), radius * Math.sin(rads), 0.0));
        rads += radSteps;
        int j = 1;
        int k = 2;
        Triangle t = new Triangle(0, j, k);
        this.triangles.add(t);
        j += 1;
        k += 1;

        for (int i = 1; i < this.numSteps; i++) {
            this.vertices.add(new VertexCoordinates(radius * Math.cos(rads), radius * Math.sin(rads), 0.0));
            rads += radSteps;
            t = new Triangle(0, j, k);
            j += 1;
            k += 1;
            this.triangles.add(t);
        }
        k = 1;
        t = new Triangle(0, j, k);
        this.triangles.add(t);
    }

    private static final Logger LOG = Logger.getLogger(Cone.class.getName());

    public static void main(String args[]) throws IOException {
        Path outputFile = Paths.get("output.msh");
        BufferedWriter bw = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8);
        Cone cone = new Cone();
        cone.generate(32, 3.05, 5.0);
        bw.write(cone.writeString());
        bw.flush();
        bw.close();
    }

}
