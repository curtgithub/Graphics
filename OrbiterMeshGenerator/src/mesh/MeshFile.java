/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mesh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author owner
 */
public class MeshFile {

    String preGeom;
    private List<VertexCoordinates> vertices;
    private List<Triangle> triangles;
    String postGeom;

    protected MeshFile() {
        preGeom = new String();
        postGeom = new String();
    }

    public MeshFile(List<VertexCoordinates> vertices, List<Triangle> triangles) {
        this.vertices = vertices;
        this.triangles = triangles;
    }

    public String getPreGeom() {
        return preGeom;
    }

    public void setPreGeom(String preGeom) {
        this.preGeom = preGeom;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    public String getPostGeom() {
        return postGeom;
    }

    public void setPostGeom(String postGeom) {
        this.postGeom = postGeom;
    }

    public void write(BufferedWriter bw) throws IOException {

        bw.write(this.preGeom);

        bw.write("GEOM " + vertices.size() + " " + triangles.size() + "\n");

        for (VertexCoordinates v : vertices) {
            bw.write(v.writeString());
        }

        for (Triangle t : triangles) {
            bw.write(t.writeString());
        }

        bw.write(this.postGeom);

        bw.flush();
    }

    public void read(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            LOG.log(Level.INFO, line);
            Scanner scanner = new Scanner(line);
            scanner.useDelimiter(" ");
            if (scanner.hasNext()) {
                //assumes the line has a certain structure
                String name = scanner.next();
                LOG.log(Level.INFO, "name = " + name);
                if (name.equals("GEOM")) {
                    int numVertices = Integer.parseInt(scanner.next());
                    LOG.log(Level.INFO, "numVertices = " + numVertices);
                    vertices = new ArrayList<VertexCoordinates>(numVertices);
                    int numTriangles = Integer.parseInt(scanner.next());
                    LOG.log(Level.INFO, "numTriangles = " + numTriangles);
                    triangles = new ArrayList<Triangle>(numTriangles);
                    if (scanner.hasNext()) {
                        LOG.log(Level.WARNING, "Line has more elements to parse.");
                    }
                    for (int i = 0; i < numVertices; i++) {
                        VertexCoordinates vertex = new VertexCoordinates();
                        line = br.readLine();
                        scanner = new Scanner(line);
                        scanner.useDelimiter(" ");
                        vertex.setX(Double.parseDouble(scanner.next()));
                        vertex.setY(Double.parseDouble(scanner.next()));
                        vertex.setZ(Double.parseDouble(scanner.next()));
                        this.vertices.add(i, vertex);
                        if (scanner.hasNext()) {
                            LOG.log(Level.WARNING, "Line has more elements to parse.");
                        }
                    }
                    for (int i = 0; i < numTriangles; i++) {
                        Triangle triangle = new Triangle();
                        line = br.readLine();
                        scanner = new Scanner(line);
                        scanner.useDelimiter(" ");
                        triangle.setI(Integer.parseInt(scanner.next()));
                        triangle.setJ(Integer.parseInt(scanner.next()));
                        triangle.setK(Integer.parseInt(scanner.next()));
                        this.triangles.add(i, triangle);
                        if (scanner.hasNext()) {
                            LOG.log(Level.WARNING, "Line has more elements to parse.");
                        }
                    }
                } else {
                    if (this.triangles == null) {
                        this.preGeom = this.preGeom.concat(line) + "\n";
                    } else {
                        this.postGeom = this.postGeom.concat(line) + "\n";
                    }
                }
            }
        }
    }
    private static final Logger LOG = Logger.getLogger(MeshFile.class.getName());

    public static void main(String args[]) {
        MeshFile meshFile = new MeshFile();
        Path inputFile = Paths.get("D:\\programs\\orbiter060929\\Meshes\\titan_mid.msh");
        Path outputFile = Paths.get("output.msh");
        try (BufferedReader br = Files.newBufferedReader(inputFile,
                StandardCharsets.UTF_8)) {
            meshFile.read(br);
            try (BufferedWriter bw = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {
                meshFile.write(bw);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
