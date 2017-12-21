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
public class ZStepZRotation {
    
    private double zStep = 1.0;
    private int numQuads = 64;

    public ZStepZRotation() {
    }

    public double getzStep() {
        return zStep;
    }

    public void setzStep(double zStep) {
        this.zStep = zStep;
    }

    public int getNumQuads() {
        return numQuads;
    }

    public void setNumQuads(int numQuads) {
        this.numQuads = numQuads;
    }
    
    public void generateMesh() {
        
    }
    
    
}
