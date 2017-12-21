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
public class Triangle implements WriteString {
    
    private int i;
    private int j;
    private int k;

    public Triangle() {
        this.i = 0;
        this.j = 0;
        this.k = 0;
    }

    public Triangle(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }
    
    @Override
    public String writeString() {
        String s;
        s = String.format("%d %d %d\n", i, j, k);
        return s;
    }
    
    
    
}
