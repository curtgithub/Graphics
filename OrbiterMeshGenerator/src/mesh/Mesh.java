/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mesh;

import java.util.List;

public class Mesh implements WriteString {

    List<VertexCoordinates> vertices;
    List<Triangle> triangles;

    public Mesh() {
        this.vertices = null;
        this.vertices = null;
    }

    public Mesh(List<VertexCoordinates> vertices, List<Triangle> triangles) {
        this.vertices = vertices;
        this.triangles = triangles;
    }

    @Override
    public String writeString() {
        String s = new String();
        s += "GEOM " + this.vertices.size() + " " + this.triangles.size() + "\n";
        for (VertexCoordinates v : this.vertices) {
            s += v.writeString();
        }
        for (Triangle t : this.triangles) {
            s += t.writeString();
        }
        return s;
    }

}
