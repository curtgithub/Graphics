/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JmeOrbit;

import com.jme3.math.FastMath;
import com.jme3.scene.plugins.blender.math.Vector3d;

/**
 *
 * @author owner
 */
public class ClosedObitalCalcs {

    public static Vector3d calcHVect(Vector3d posVect, Vector3d velVect) {
        Vector3d hVect;
        hVect = posVect.cross(velVect);
        return hVect;
    }

    public static Vector3d calcEccVect(Vector3d posVect, Vector3d velVect,
            Vector3d hVect, double mu) {
        Vector3d eccVect = velVect.cross(hVect).divide(mu);
        eccVect = eccVect.subtract(posVect.divide(posVect.length()));
        return eccVect;
    }

    public static double calcEccFactor(double ecc) {
        double ep1 = 1 + ecc;
        double em1 = 1 - ecc;
        double eccFactor = Math.sqrt(ep1 / em1);
        return eccFactor;
    }

    public static double calcEccentricAnomaly(double trueAnomaly,
            double eccFactor) {
        double eccentricAnomaly = Math.tan(0.5 * trueAnomaly);
        eccentricAnomaly /= eccFactor;
        eccentricAnomaly = 2.0 * Math.atan(eccentricAnomaly);
        if (eccentricAnomaly < 0) {
            eccentricAnomaly = FastMath.TWO_PI + eccentricAnomaly;

        }

        return eccentricAnomaly;
    }

    public static double calcMeanAnomaly(double eccentricAnomaly,
            double ecc) {
        double meanAnomaly = eccentricAnomaly
                - ecc * Math.sin(eccentricAnomaly);
        return meanAnomaly;
    }

    public static double calcSpeed(double mu, double radius) {
        double speed;
        speed = Math.sqrt(mu / radius);
        return speed;
    }

    public static double calcSma(double radiusPeriApsis, double ecc) {
        double sma = (1 + ecc) / (1 - ecc);
        sma *= radiusPeriApsis; // NOTE: sma is know the redius at apoapsis
        sma += radiusPeriApsis;
        sma *= 0.5;
        return sma;
    }

    public static double calcPeriod(double mu, double sma, double ecc) {
        double period;
        period = FastMath.TWO_PI * Math.sqrt((sma * sma * sma) / mu);
        return period;
    }

    public static void main(String args[]) {
        double earthMu = 398600.4415; // Earth mu km3/sec2
        double earthRadius = 6371.0; // Earth raidus km

        System.out.println("Altitude\tSpeed\tPeriod");
        for (double alt = 100; alt < 500000; alt += 100) {
            double speed = ClosedObitalCalcs.calcSpeed(earthMu, earthRadius + alt);
            double period = ClosedObitalCalcs.calcPeriod(earthMu,
                    earthRadius + alt, 0);
            System.out.println(alt + "\t" + speed + "\t" + period);
        }

        double g = earthMu / (earthRadius * earthRadius);
        System.out.println("g = " + g);

        double periApsisRadius = earthRadius + 200;
        double speed = ClosedObitalCalcs.calcSpeed(earthMu, periApsisRadius);
        speed *= 1.1;
        Vector3d posVect = new Vector3d(periApsisRadius, 0, 0);
        Vector3d velVect = new Vector3d(0, speed, 0);
        Vector3d hVect = ClosedObitalCalcs.calcHVect(posVect, velVect);
        Vector3d eccVect = ClosedObitalCalcs.calcEccVect(posVect, velVect,
                hVect, earthMu);

        System.out.println("posVect=" + posVect);
        System.out.println("velVect=" + velVect);
        System.out.println("hVect=" + hVect);
        System.out.println("eccVect=" + eccVect);

        double ecc = eccVect.length();
        System.out.println("ecc=" + ecc);

        double sma = ClosedObitalCalcs.calcSma(periApsisRadius, ecc);
        double period = ClosedObitalCalcs.calcPeriod(earthMu, sma, ecc);
        
        System.out.println("period="+period);

        double eccFactor = ClosedObitalCalcs.calcEccFactor(ecc);
        System.out.println("eccFactor=" + eccFactor);

        double trueAnomaly = 0;
        double eccentricAnomaly
                = ClosedObitalCalcs.calcEccentricAnomaly(trueAnomaly, eccFactor);
        System.out.println("eccentricAnomaly=" + eccentricAnomaly);

        double meanAnomaly = calcMeanAnomaly(eccentricAnomaly, ecc);
        double time =  period * meanAnomaly / FastMath.TWO_PI;

        double incTrueAnomaly = FastMath.TWO_PI / 100;
        System.out.println("trueAnomaly\teccentricAnomaly\tmeanAnomaly\ttime");
        for (trueAnomaly = 0;
                trueAnomaly < (FastMath.TWO_PI + incTrueAnomaly);
                trueAnomaly += incTrueAnomaly) {

            eccentricAnomaly
                    = ClosedObitalCalcs.calcEccentricAnomaly(trueAnomaly, eccFactor);
            meanAnomaly = calcMeanAnomaly(eccentricAnomaly, ecc);
            time = time =  period * meanAnomaly / FastMath.TWO_PI;

            System.out.println(trueAnomaly + "\t" + eccentricAnomaly
                    + "\t" + meanAnomaly + "\t" + time);

        }

    }
}
