/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JmeOrbit;

import com.jme3.scene.plugins.blender.math.Vector3d;


/**
 *
 * @author owner
 */
public class OrbitalStateVectors {

    private Vector3d obR; // Orbiting Body Position Vector units KM
    private Vector3d obV; // Orbiting Body Velocity Vector units KM/S
    private double obM;   // Orbiting Body Mass units KG
    private double cbM;   // Central Body Mass units KG

    protected OrbitalStateVectors() {
    }

    public OrbitalStateVectors(Vector3d obR, Vector3d obV, double obM, double cbM) {
        this.obR = obR;
        this.obV = obV;
        this.obM = obM;
        this.cbM = cbM;
    }

    public OrbitalStateVectors(OrbitalStateVectors osv) {

        this.obR = osv.getObR();
        this.obV = osv.getObV();
        this.obM = osv.getObM();
        this.cbM = osv.getCbM();
    }

    public Vector3d getObR() {
        return obR;
    }

    public void setObR(Vector3d obR) {
        this.obR = obR;
    }

    public Vector3d getObV() {
        return obV;
    }

    public void setObV(Vector3d obV) {
        this.obV = obV;
    }

    public double getObM() {
        return obM;
    }

    public void setObM(double obM) {
        this.obM = obM;
    }

    public double getCbM() {
        return cbM;
    }

    public void setCbM(double cbM) {
        this.cbM = cbM;
    }

    public OrbitalStateVectors calcDt(double dT) {
        OrbitalStateVectors osv = new OrbitalStateVectors(this);

        double a = this.getObR().x; // HERE

        return osv;
    }



}
