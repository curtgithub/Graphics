/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mesh;

/**
 *
 * @author owner
 */
public class VertexUVs extends VertexNormals {

    private double u = 0.0;
    private double v = 0.0;

    public VertexUVs() {
        super();
        this.u = 0.0;
        this.v = 0.0;
    }

    public VertexUVs(double u, double v, double nx, double ny, double nz, double x, double y, double z) {
        super(nx, ny, nz, x, y, z);
        this.u = u;
        this.v = v;
    }

    public VertexUVs(double u, double v, VertexNormals vn) {
        super(vn);
        this.u = u;
        this.v = v;
    }

    public double getU() {
        return u;
    }

    public void setU(double u) {
        this.u = u;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

   @Override
    public String writeString() {
        String s;
        String ss = super.writeString();
        ss = ss.replaceAll("\n", "");
        s = String.format("%s %.6f %.6f\n", ss, this.getU(), this.getV());
        return s;
    }

}
