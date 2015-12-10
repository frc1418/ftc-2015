package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by winterst on 11/21/15.
 */
public class DriveForward extends StatefulAutonomous {

    @timed_state(duration = 120)
    public void driveForward()
    {
        telemetry.addData("GYRO: ", gyro.getHeading());
        tank.angleRotation(0);
    }

}
