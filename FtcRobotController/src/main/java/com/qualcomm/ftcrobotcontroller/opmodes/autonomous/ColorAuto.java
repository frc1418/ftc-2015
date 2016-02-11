package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by winterst on 2/10/16.
 */
public class ColorAuto extends StatefulAutonomous{

    @timed_state(duration = 5, next_state = "rotateToBox", first = true)
    public void driveForward() throws NoSuchMethodException {
        tank.move(-1, -1);
        if (colorSensor.red()>=10)
            next_state("rotateToBox");
    }

    @timed_state(duration = 2, next_state = "extendWinch")
    public void rotateToBox() throws NoSuchMethodException {
        if (tank.angleRotation(90))
            next_state("extendWinch");
    }

    @timed_state(duration = 8, next_state = "dropAndLower")
    public void extendWinch()
    {
        winchServo.setLocation(.5);
        winchMotor.setPower(1);
    }

    @timed_state(duration = 8, next_state = "rotateToRamp")
    public void dropAndLower()
    {
        winchServo.setLocation(.3);
        winchMotor.setPower(-1);
    }

    @timed_state(duration = 2, next_state = "driveToRamp")
    public void rotateToRamp() throws NoSuchMethodException {
        winchMotor.setPower(0);
        if (tank.angleRotation(45))
            next_state("driveToRamp");
    }

    @timed_state(duration = 2)
    public void driveToRamp()
    {
        tank.move(0,0);
    }



}
