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
public class Cylinder extends Mesh {

    private int numSteps;

    public Cylinder() {
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
        int iTriangles = 0;
        for (int i = 0; i < this.numSteps; i++) {
            double x0 = radius * Math.cos(rads);
            double y0 = radius * Math.sin(rads);
            double x1 = radius * Math.cos(rads + radSteps);
            double y1 = radius * Math.sin(rads + radSteps);
            VertexCoordinates v0 = new VertexCoordinates(x0, y0, 0.0);
            VertexCoordinates v1 = new VertexCoordinates(x1, y1, 0.0);
            VertexCoordinates v2 = new VertexCoordinates(x0, y0, length);
            this.vertices.add(v0);
            this.vertices.add(v1);
            this.vertices.add(v2);
            Triangle t = new Triangle(iTriangles, iTriangles + 1, iTriangles + 2);
            this.triangles.add(t);
            VertexCoordinates v3 = new VertexCoordinates(x1, y1, length);
            VertexCoordinates v4 = new VertexCoordinates(x0, y0, length);
            VertexCoordinates v5 = new VertexCoordinates(x1, y1, 0.0);
            this.vertices.add(v3);
            this.vertices.add(v4);
            this.vertices.add(v5);
            t = new Triangle(iTriangles + 3, iTriangles + 4, iTriangles + 5);
            this.triangles.add(t);
            iTriangles += 6;
            rads += radSteps;
        }

    }

    private static final Logger LOG = Logger.getLogger(Cylinder.class.getName());

    public static void main(String args[]) throws IOException {
        Path outputFile = Paths.get("output.msh");
        BufferedWriter bw = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8);
        Cylinder cylinder = new Cylinder();
        cylinder.generate(32, 3.05, 20.0);
        bw.write(cylinder.writeString());
        bw.flush();
        bw.close();
    }

}
