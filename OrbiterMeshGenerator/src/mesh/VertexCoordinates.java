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
public class VertexCoordinates implements WriteString {
    
    private double x;
    private double y;
    private double z;

    public VertexCoordinates() {        
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public VertexCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public VertexCoordinates(VertexCoordinates v) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    @Override
    public String writeString() {
        String s;
        s = String.format("%.6f %.6f %.6f\n", x, y, z);
        return s;
    }
    
    
    
}
