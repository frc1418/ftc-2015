package com.qualcomm.ftcrobotcontroller.opmodes;
/**
 * Created by winterst on 11/21/15.
 */
public class DriveForward extends StatefulAutonomous {
    TankDrive drive;
    @timed_state(duration = 1)
    public void driveForward()
    {
    drive.move(1,1);
    }

}
