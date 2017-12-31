/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JmeOrbit;

/**
 *
 * @author owner
 */
public class RungeKutta {

    public class State {

        private double pos;
        private double vel;
        private double acc;
        private double dt;

        protected State() {

        }

        public State(double pos, double vel, double acc, double dt) {
            this.pos = pos;
            this.vel = vel;
            this.acc = acc;
            this.dt = dt;
        }

        public State(State orig) {
            this.pos = orig.getPos();
            this.vel = orig.getVel();
            this.acc = orig.getAcc();
            this.dt = orig.getDt();
        }

        public double getPos() {
            return pos;
        }

        public void setPos(double pos) {
            this.pos = pos;
        }

        public double getVel() {
            return vel;
        }

        public void setVel(double vel) {
            this.vel = vel;
        }

        public double getAcc() {
            return acc;
        }

        public void setAcc(double acc) {
            this.acc = acc;
        }

        public double getDt() {
            return dt;
        }

        public void setDt(double dt) {
            this.dt = dt;
        }

        @Override
        public String toString() {
            return "State{" + "pos=" + pos + ", vel=" + vel + ", acc=" + acc + ", dt=" + dt + '}';
        }

    }

    private State state;

    protected RungeKutta() {

    }

    public RungeKutta(double pos, double vel, double acc, double dt) {
        this.state = new State(pos, vel, acc, dt);
    }

    public RungeKutta(State state) {
        this.state = new State(state);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "RungeKutta{" + "state=" + state + '}';
    }

    //
    // http://doswa.com/2009/01/02/fourth-order-runge-kutta-numerical-integration.html
    //
    //
    // For know acceleration is only caused by gravity
    // Therefore v and dt are not used
    //
    public void rk4() {

        double pos = this.state.getPos();
        double vel = this.state.getVel();
        double acc = this.state.getAcc();
        double dt = this.state.getDt();

        double pos1 = pos;
        double vel1 = vel;
        double acc1 = acc;

        double pos2 = pos + 0.5 * vel1 * dt;
        double vel2 = vel + 0.5 * acc1 * dt;
        double acc2 = acc / 2.0;

        double pos3 = pos + 0.5 * vel2 * dt;
        double vel3 = vel + 0.5 * acc2 * dt;
        double acc3 = acc / 2.0;

        double pos4 = pos + vel3 * dt;
        double vel4 = vel + acc3 * dt;
        double acc4 = acc;

        double posDt = pos + (dt / 6.0) * (vel1 + 2 * vel2 + 2 * vel3 + vel4);
        double velDt = vel + (dt / 6.0) * (acc1 + 2 * acc2 + 2 * acc3 + acc4);

        this.state.setPos(posDt);
        this.state.setVel(velDt);
    }

    public static void main(String[] args) {

        double altSc = 350;         // km
        double massSc = 1000;         // kg
        double earthRadius = 6371.0;  // km
        double earthMu = 398600.4415; // km3/sec2

        double pos = earthRadius + altSc;
        double vel = 0;
        double acc = -earthMu / (pos * pos);

        double period = ClosedObitalCalcs.calcPeriod(earthMu, pos, 0);
        double dt = period / 10000;

        RungeKutta rk = new RungeKutta(pos, vel, acc, dt);
        double t = 0;
        System.out.println("State = " + rk.getState() + " t=" + t);

        for (t = 0; (rk.getState().getPos() > earthRadius) && (t < period); t += dt) {
            rk.rk4();
            pos = rk.getState().getPos();
            acc = -earthMu / (pos * pos);
            rk.getState().setAcc(acc);
//            System.out.println("State = " + rk.getState() + " t+dt=" + (t+dt));
        }
            System.out.println("State = " + rk.getState() + " tt=" + t);
        
    }
}
