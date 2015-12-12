package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by winterst on 12/8/15.
 */
public class StraightClimbRed extends StatefulAutonomous {
    @timed_state(duration = 4.6, nextState = "rotate", first = true)
    public void DriveForward()
    {
        tank.move(1, 0);
        tank.angleRotation(0);
    }

    @timed_state(duration = 4, nextState = "setServo")
    public void rotate()
    {
        tank.angleRotation(45);
    }
    @timed_state(duration = .5, nextState = "extendServo")
    public void setServo() {
        winchServo.setLocation(.35);
    }
    @timed_state(duration = 6.6, nextState = "lowerServo")
    public void extendServo() {
        winchMotor.setPower(1);
        tank.move(.3f, 0);
    }

    @timed_state(duration = .5, nextState = "startClimb")
    public void lowerServo() {
        winchServo.setLocation(0);
    }

    @timed_state(duration = .1, nextState = "climbDrive")
    public void startClimb()
    {
        winchMotor.setPower(-1);
    }

    @timed_state(duration = 7, nextState = "reverseWinch")
    public void climbDrive() {
        tank.move(0.4f, 0);
    }

    @timed_state(duration = 1, nextState = "stopWinch")
    public void reverseWinch()
    {
        winchMotor.setPower(-1);
    }

    @timed_state(duration = .1)
    public void stopWinch() {winchMotor.setPower(0);}

}
