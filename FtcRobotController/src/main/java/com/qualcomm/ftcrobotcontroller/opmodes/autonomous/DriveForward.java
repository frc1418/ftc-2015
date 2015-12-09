package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by winterst on 11/21/15.
 */
public class DriveForward extends StatefulAutonomous {

    @timed_state(duration = 1)
    public void forwardDrive()
    {
        tank.move(1,1);
    }
    @timed_state(duration = 1)
    public void driveBackward()
    {
        tank.move(-1,-1);
    }

}
