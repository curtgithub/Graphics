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
public class RungeKuttaVector {

    public static void main(String args[]) {

        Vector3d v1 = new Vector3d(7.07, 7.07, 0);
        Vector3d v1Neg = v1.negate();
        Vector3d v1Norm = v1.normalize();

        System.out.println("v1=" + v1
                + " v1Neg=" + v1Neg
                + " v1Norm=" + v1Norm);

        double altSc = 350;         // km
        double massSc = 1000;         // kg
        double earthRadius = 6371.0;  // km
        double radius = earthRadius + altSc;
        double earthMu = 398600.4415; // km3/sec2

        double coVelMag = ClosedObitalCalcs.calcSpeed(earthMu,
                radius);

        Vector3d cbPosVect = new Vector3d(radius, 0, 0);

        Vector3d obVelVect = new Vector3d(0, coVelMag, 0);

        Vector3d cbPosVectNormNeg = cbPosVect.normalize().negate();

        double MuDr2 = earthMu / cbPosVect.lengthSquared();

        Vector3d obAccVect = cbPosVectNormNeg.mult(MuDr2);

        System.out.println("cbPosVect=" + cbPosVect);
        System.out.println("obVelVect=" + obVelVect);
        System.out.println("obAccVect=" + obAccVect);

        Vector3d cbPosStartVect = cbPosVect;
        Vector3d obVelStartVect = obVelVect;
        Vector3d obAccStartVect = obAccVect;

        double dt = 0.0001;
        double t = 0;
        double period = ClosedObitalCalcs.calcPeriod(earthMu, radius, 0);
        System.out.println("period=" + period);
        for (t = 0; t < period; t += dt) {
            cbPosVectNormNeg = cbPosVect.normalize().negate();
            MuDr2 = earthMu / cbPosVect.lengthSquared();
            obAccVect = cbPosVectNormNeg.mult(MuDr2);

            //
            // Add position change due to acceleration
            //
            Vector3d obVelAccIncVect = obAccVect.mult(dt);
            Vector3d obIncPosVect = obVelAccIncVect.mult(dt);
            cbPosVect = cbPosVect.add(obIncPosVect);

            //
            // Add position change dut to velocy
            //
            obIncPosVect = obVelVect.mult(dt);
            cbPosVect = cbPosVect.add(obIncPosVect);

            //
            // Add velocity change due to velocity and acceleration
            //
            obVelVect = obVelVect.add(obVelAccIncVect);
        }
        System.out.println("t=" + t);
        System.out.println("cbPosVect=" + cbPosVect);
        System.out.println("obVelVect=" + obVelVect);
        System.out.println("obAccVect=" + obAccVect);

        System.out.println("Diff cbPosVect=" + cbPosVect.add(cbPosStartVect.negate()));
        System.out.println("Diff obVelVect=" + obVelVect.add(obVelStartVect.negate()));
        System.out.println("Diff obAccVect=" + obAccVect.add(obAccStartVect.negate()));

    }

// TODO: LookatReference https://www.physicsforums.com/threads/find-orbital-elements-given-orbital-state-vectors.511277/

}
