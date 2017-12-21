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
public class VertexNormals extends VertexCoordinates {

    private double nx;
    private double ny;
    private double nz;

    public VertexNormals() {
        super();
        this.nx = 0.0;
        this.ny = 0.0;
        this.nz = 0.0;
    }

    public VertexNormals(double nx, double ny, double nz, double x, double y, double z) {
        super(x, y, z);
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
    }

    public VertexNormals(VertexNormals vn) {
        super(vn);
        this.nx = vn.getNx();
        this.ny = vn.getNy();
        this.nz = vn.getNz();
    }

    public double getNx() {
        return nx;
    }

    public void setNx(double nx) {
        this.nx = nx;
    }

    public double getNy() {
        return ny;
    }

    public void setNy(double ny) {
        this.ny = ny;
    }

    public double getNz() {
        return nz;
    }

    public void setNz(double nz) {
        this.nz = nz;
    }

    @Override
    public String writeString() {
        String s;
        String ss = super.writeString();
        ss = ss.replaceAll("\n", "");
        s = String.format("%s %.6f %.6f %.6f\n", ss, this.getNx(), this.getNy(), this.getNz());
        return s;
    }

}
