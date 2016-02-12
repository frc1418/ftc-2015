package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by winterst on 2/11/16.
 */
public class ColorLowZone extends StatefulAutonomous{

    @timed_state(duration = 15, first = true)
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

    @timed_state(duration = 8 )
    public void drive(){
        tank.move(1, 1);
    }

}
