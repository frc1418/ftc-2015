package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

public class ColorHighZone extends StatefulAutonomous{

    @timed_state(duration = 15, next_state = "backup", first = true)
    public void driveForward() throws NoSuchMethodException {
        tank.move(-.25f, -.25f);
        if (colorSensor.red()>=10) {
            System.out.println("color red found, moving state.");
            next_state("backup");
        }
    }

    @timed_state(duration = .65, next_state = "rotateToBox")
    public void backup(){
        tank.move(.25f, .25f);
    }

    @timed_state(duration = 2, next_state = "drive")
    public void rotateToBox() throws NoSuchMethodException {
        if (tank.angleRotation(90))
            next_state("drive");
    }

    @timed_state(duration = 8, next_state = "extendWinch")
    public void drive(){
        tank.move(1, 1);
        winchServo.setLocation(.15);
    }

    @timed_state(duration = 8, next_state = "climb")
    public void extendWinch()
    {
        winchMotor.setPower(1);
    }

    @timed_state(duration = 7)
    public void climb()
    {
        tank.move(0.4f, 0.4f);
        winchMotor.setPower(-1);
    }

}
