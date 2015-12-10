package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by winterst on 12/8/15.
 */
public class StraightClimb extends StatefulAutonomous {
    @timed_state(duration = 2.5)
    public void aDriveForward()
    {
        tank.move(1, tank.angleRotation(0));
    }

    @timed_state(duration = 2)
    public void bRotate()
    {
        tank.move(0, tank.angleRotation(315));
    }
    @timed_state(duration = .5)
    public void bSetServo() {
        tank.move(1,tank.angleRotation(0));
        winchServo.setLocation(.05);
    }

    @timed_state(duration = 6)
    public void bExtendServo() {
        winchMotor.setPower(1);
    }

    @timed_state(duration = .5)
    public void cLowerServo() {
        winchServo.setLocation(.5);
    }

    @timed_state(duration = 1)
    public void dStartClimb()
    {
        winchMotor.setPower(-1);
    }

    @timed_state(duration = 4)
    public void eClimbDrive() {
        tank.move(0.4f, 0.4f);
    }

    @timed_state(duration = 1)
    public void fStopWinch()
    {
        winchMotor.setPower(0);
    }

}
