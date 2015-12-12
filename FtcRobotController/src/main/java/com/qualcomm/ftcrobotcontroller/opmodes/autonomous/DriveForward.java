package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by winterst on 11/21/15.
 */
public class DriveForward extends StatefulAutonomous {

    @timed_state(duration = 120)
    public void driveForward()
    {
        telemetry.addData("GYRO", gyro.getHeading());
        telemetry.addData("TANK ROTATION: ", tank.rotation);
        telemetry.addData("Left Speed", tank.rightSpeed);
        telemetry.addData("Right Speed", tank.leftSpeed);
        tank.move(.5f,tank.rotation);
    }

}
